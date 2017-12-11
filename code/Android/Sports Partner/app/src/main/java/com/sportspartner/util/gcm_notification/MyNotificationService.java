package com.sportspartner.util.gcm_notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.activity.NotificationActivity;
import com.sportspartner.models.SActivityOutline;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.service.ActivityService;
import com.sportspartner.service.ModelResult;
import com.sportspartner.util.DBHelper.NotificationDBHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


/**
 * Created by xuanzhang on 12/4/17.
 */

public class MyNotificationService extends Service {

    Thread thread;
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
        upcomingTime = (Date)intent.getSerializableExtra("upcomingDate");
        final String email = intent.getStringExtra("email");
        final Context context = this;

        // if the new activity's time is later than the current latest time
        // then the thread will not be killed
        if(upcomingTime.getTime()<currentLatestTime.getTime()){
            currentLatestTime = upcomingTime;
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Date currentTime = new Date();
                    // let the thread sleep till 1 hour before the latest activity
                    long diff = upcomingTime.getTime() - currentTime.getTime();
                    if(diff>=3600000) {
                        try {
                            thread.sleep(diff - 3600000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    // wake up the thread and send the notification
                    sendNotification("Upcoming Activity Notification",
                            "You have an activity starting in less than 1 hour.");
                    // put the notification to database
                    NotificationDBHelper.getInstance(context).insert(UUID.randomUUID().toString(),
                            "Upcoming Activity Notification", "You have an activity starting in less than 1 hour.",
                            "system", "MESSAGE",new Date().toString(), 0);

                    // get the other latest activity from backend
                    new ActivityService().getUpcomingActivities(context, email, 1, 0,
                            new ActivityCallBack<ArrayList<SActivityOutline>>(){
                                @Override
                                public void getModelOnSuccess(ModelResult<ArrayList<SActivityOutline>> modelResult) {
                                    ArrayList<SActivityOutline> sActivityOutlines  = modelResult.getModel();
                                    currentLatestTime = sActivityOutlines.get(0).getStartTime();
                                    upcomingTime = currentLatestTime;
                                    // start a new thread
                                    run();
                                }
                            });
                }
            });
            thread.run();

        }
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
