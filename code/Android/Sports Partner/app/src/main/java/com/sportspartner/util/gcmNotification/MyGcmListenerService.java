package com.sportspartner.util.gcmNotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;
import com.sportspartner.R;
import com.sportspartner.activity.NotificationActivity;
import com.sportspartner.models.NightMode;
import com.sportspartner.models.SActivity;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.service.ActivityService;
import com.sportspartner.service.ModelResult;
import com.sportspartner.util.dbHelper.ActivityNotiDBHelper;
import com.sportspartner.service.chatsupport.MyFirebaseMessagingService;
import com.sportspartner.util.dbHelper.NightModeDBHelper;
import com.sportspartner.util.dbHelper.NotificationDBHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


/**
 * Created by yujiaxiao on 11/3/17.
 */

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */

// [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {

        if(data.getString("sendbird")!=null)
        {
            String channelUrl = null;
            try {
                JSONObject sendBird = new JSONObject(data.getString("sendbird"));
                JSONObject channel = (JSONObject) sendBird.get("channel");
                channelUrl = (String) channel.get("channel_url");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MyFirebaseMessagingService.sendNotification(this, data.getString("message"), channelUrl);
                return;

        }


        String title = data.getString("title");
        String detail = data.getString("detail");
        String sender = data.getString("sender");
        String type  = data.getString("type");
        String timeString  = data.getString("time");
        String priorityString = data.getString("priority");
        Date date = new Date(Long.valueOf(timeString));
        int priority = Integer.valueOf(priorityString);
        Log.d(TAG, "From FROM: " + from);
        Log.d(TAG, "Title: " + title);
        Log.d(TAG, "Detail: " + detail);
        Log.d(TAG, "Sender: " + sender);
        Log.d(TAG, "Type: " + type);
        Log.d(TAG, "Time: " + date.toString());
        Log.d(TAG, "Priority: " + priorityString);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        //parse time
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String timeDbString = format.format(date);

        //insert the notification in SQL
        NotificationDBHelper dbHelper = NotificationDBHelper.getInstance(this);
        String uuid = UUID.randomUUID().toString();
        dbHelper.insert(uuid,title,detail,sender,type,timeDbString,priority);

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */

        //Todo check whether night mode
        NightModeDBHelper nightdbHelper = NightModeDBHelper.getInstance(getApplicationContext());
        if (!nightdbHelper.isEmpty()){
            try {
                ArrayList<NightMode> nightModes = nightdbHelper.getAll();
                for (NightMode nightMode : nightModes){
                    //parse date
                    Date start = nightMode.getStartTime();
                    Date end = nightMode.getEndTime();
                    Date current = Calendar.getInstance().getTime();
                    SimpleDateFormat curFormat = new SimpleDateFormat("HH:mm a");
                    String curString = curFormat.format(current);
                    current = curFormat.parse(curString);

                    if (end.after(start)){
                        if (current.after(end) && current.before(start))
                            sendNotification(title, detail);
                        Log.d("Date current", current.toString());
                        Log.d("Date start", start.toString());
                        Log.d("Date end", end.toString());
                    }
                    else {
                        end.setDate(end.getDate()+1);
                        Date current1 = curFormat.parse(curString);
                        current1.setDate(current.getDate()+1);
                        if (current.before(start) && current1.after(end)){
                            sendNotification(title, detail);
                        }
                        Log.d("Date getDate", String.valueOf(current.getDate()));
                        Log.d("Date current", current.toString());
                        Log.d("Date current1", current1.toString());
                        Log.d("Date start", start.toString());
                        Log.d("Date end", end.toString());
                    }
                }
            } catch (ParseException e) {
                Toast.makeText(getApplicationContext(), "nightDBHelper getAll Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        else {
            // if the activity joining application is accepted
            // the user will have a new upcoming activity
            if(priority==21) {
                String activityId = null;
                try {
                    activityId = new JSONObject(detail).getString("activityId");
                }catch(JSONException e){
                    e.printStackTrace();
                }
                ActivityService.getSActivity(getApplicationContext(), activityId, new ActivityCallBack<SActivity>() {
                    @Override
                    public void getModelOnSuccess(ModelResult<SActivity> modelResult) {
                        if (modelResult.isStatus()) {
                            SActivity sActivity = modelResult.getModel();

                            // add activity to SQLite
                            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                            String dateString = format.format(sActivity.getStartTime());
                            ActivityNotiDBHelper.getInstance(getApplicationContext()).insert(sActivity.getActivityId(), dateString);

                            // start upcoming activity notification service
                            Intent intent = new Intent(getApplicationContext(), MyNotificationService.class);
                            intent.putExtra("activityId", sActivity.getActivityId());
                            Log.d("MyGCM upcomingTime", sActivity.getStartTime().toString());
                            intent.putExtra("upcomingTime", sActivity.getStartTime());
                            getApplicationContext().startService(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "Get Sport Activity Error:" + modelResult.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }else {
                sendNotification(title, detail);
            }
        }
        // [END_EXCLUDE]
    }

    /**
     * Create and show a simple notification containing the received GCM message.
     * @param title GCM title.
     * @param content GCM content.
     */
    public void sendNotification(String title, String content) {
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.edit)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
