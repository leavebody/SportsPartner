package com.sportspartner.util.gcmNotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.activity.NotificationActivity;
import com.sportspartner.models.ActivityNoti;
import com.sportspartner.util.dbHelper.ActivityNotiDBHelper;
import com.sportspartner.util.dbHelper.NotificationDBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


/**
 * Created by xuanzhang on 12/4/17.
 */

public class MyNotificationService extends Service {

    static AsyncTask upcomingNotification;
    ActivityNoti activityNoti = new ActivityNoti();
    String activityId;
    Date currentLatestTime;
    Date upcomingTime;

    public MyNotificationService() {
        super();
    }

    @Override
    public void onCreate() {
        //set default current latest time to year 2080
        currentLatestTime = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentLatestTime);
        cal.set(Calendar.YEAR, 2080);
        currentLatestTime = cal.getTime();
        super.onCreate();
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
//        onHandleIntent(intent);
        if(upcomingNotification!=null){
            upcomingNotification.cancel(true);
        }
        upcomingNotification = new UpcomingNotification().execute(intent);
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Notification Service is stopped", Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private class UpcomingNotification extends AsyncTask<Intent, Void, Intent> {

        @Override
        protected Intent doInBackground(Intent... intent) {
            upcomingTime = (Date)intent[0].getSerializableExtra("upcomingTime");
            //Log.d("", intent[0].getSerializableExtra("upcomingDate"));
            Log.d("MyNoti upcomingTime", upcomingTime.toString());
            Log.d("notification", "service 60");

            Date currentTime = new Date();
            // let the thread sleep till 1 hour before the latest activity
            long diff = upcomingTime.getTime() - currentTime.getTime();
            if (diff >= 3600000) {
                try {
                    Thread.sleep(diff-3600000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            return intent[0];
        }

        @Override
        protected void onPostExecute(Intent intent) {
            recalculateWaitTime(intent);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    public void recalculateWaitTime(final Intent intent){
        // wake up the thread and send the notification
        sendNotification("Upcoming Activity Notification",
                "You have an activity starting in less than 1 hour.");
        // put the notification to database
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String dateString = format.format(Calendar.getInstance().getTime());

        NotificationDBHelper.getInstance(getApplicationContext()).insert(UUID.randomUUID().toString(),
                "Upcoming Activity Notification", "You have an activity starting in less than 1 hour.",
                "system", "MESSAGE", dateString, 0);
        activityId = intent.getStringExtra("activityId");

        // delete the notified activity from sqlite
        ActivityNotiDBHelper.getInstance(getApplicationContext()).deleteById(activityId);

        // get the other latest activity from database
        try {
            if(!ActivityNotiDBHelper.getInstance(getApplicationContext()).isEmpty()) {
                activityNoti = ActivityNotiDBHelper.getInstance(getApplicationContext()).getLatestActivity();
            }else{
                Date neverDate = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(neverDate);
                cal.set(Calendar.YEAR, 2080);
                neverDate = cal.getTime();
                activityNoti.setStartTime(neverDate);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // prepare for another notification
        currentLatestTime = activityNoti.getStartTime();
        activityId = activityNoti.getActivityId();
        upcomingTime = currentLatestTime;
        intent.putExtra("upcomingTime", upcomingTime);
        intent.putExtra("activityId", activityId);
        upcomingNotification = new UpcomingNotification().execute(intent);

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




