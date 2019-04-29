package com.almosky.almoskylaundryApp.notification;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.SplashScreenActivity;
import com.almosky.almoskylaundryApp.TabHostActivity;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.core.app.NotificationCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static  int NOTIFICATION_ID = 1;
    public static final String INTENT_FILTER = "INTENT_FILTER_NOTIFICATION";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
      //      scheduleJob();
        }

        System.out.println("nxt"+remoteMessage.getNotification().getBody());


        genNotif(remoteMessage);

     //   Map<String, String> notification = remoteMessage.getData();
       // String type = notification.get("type");
        //String result = notification.get("result");
      //  generateNotification(type,result);


      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            String id = "id_product";
            // The user-visible name of the channel.
            CharSequence name = "Product";
            // The user-visible description of the channel.
            String description = "Notifications regarding our products";
            int importance = NotificationManager.IMPORTANCE_MAX;
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            notificationManager.createNotificationChannel(mChannel);
        }*/

    }

    private void genNotif(RemoteMessage remoteMessage) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String messageBody = remoteMessage.getNotification().getBody();
        // Sets an ID for the notification, so it can be updated.
//        int notifyID = Integer.parseInt(data.get("notification_type"));
        String channelId = this.getResources().getString(R.string.default_notification_channel_id);
        CharSequence name = this.getResources().getString(R.string.app_name);// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

//            Toast.makeText(this, "received", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, TabHostActivity.class);
            // intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            //  intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                            PendingIntent.FLAG_ONE_SHOT);
            NotificationChannel mChannel = new NotificationChannel(channelId, name, importance);

// Create a notification and set the notification channel.
            Notification notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(messageBody).setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setStyle(new Notification.BigTextStyle().bigText(messageBody))
                    .setChannelId(channelId)
                    .build();

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager.notify(1, notification);


        } else {


            Intent intentNotification = new Intent(INTENT_FILTER);
            sendBroadcast(intentNotification);

            Intent intent = new Intent(this, TabHostActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setColor(getResources().getColor(R.color.colorPrimary))
                    .setContentTitle(getString(R.string.app_name))
                    //  .setContentIntent(pendingIntent)
                    .setContentText(messageBody).setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(Notification.PRIORITY_HIGH);

            notificationManager.notify(1, builder.build());
        }


    }

    private void scheduleJob() {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
    }

    private void generateNotification(String title, String messageBody) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getApplicationContext().getString(R.string.default_notification_channel_id);
            NotificationChannel channel = new NotificationChannel(channelId, "Almosky", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(messageBody);
            channel.setName(getString(R.string.app_name));
            mNotificationManager.createNotificationChannel(channel);

            Intent intent = new Intent(this, SplashScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.logo)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
            mNotifyBuilder.setChannelId(channelId);
            mNotifyBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mNotifyBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
            mNotificationManager.notify(1, mNotifyBuilder.build());
        } else {
            Intent intent = new Intent(this, SplashScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.logo)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            mNotifyBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mNotifyBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
            if (NOTIFICATION_ID > 1073741824) {
                NOTIFICATION_ID = 0;
            }
            mNotificationManager.notify(1, mNotifyBuilder.build());
        }
    }
}