package com.example.surveyapp1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "first_notif_channel_id";

    private NotificationManager notificationManager;
    private NotificationChannel channel;

    public NotificationReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            NotificationSender notificationSender = new NotificationSender(context);
            notificationSender.scheduleNotification();
        }
        //if device is rebooted, notifications must be rescheduled. this will also reschedule deactivate alarm
        else
            handleNotif(context);
    }

    public void handleNotif(Context context){

        DataStoreManager dataStoreManager = DataStoreManager.getInstance(context);
        if (dataStoreManager == null) { //this should never happen because datastoremanager is created before any notifs are scheduled
            Log.d("TestingTesting123", "DataStoreManager is null");
            return;
        }

        // Initialize NotificationManager
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create notification channel
        createNotificationChannel();

        if (dataStoreManager.retrieveDaysLeft()== 2) { //determine notifcation message based on how many days are left
            sendNotification(R.string.notifContent1, context);
            dataStoreManager.setActive(true); //reopen survey
        } else if (dataStoreManager.retrieveDaysLeft() == 1) {
            if (dataStoreManager.retrieveActive())
                sendNotification(R.string.notifContent2, context);
        } else if (dataStoreManager.retrieveDaysLeft() == 0) {
            if (dataStoreManager.retrieveActive())
                sendNotification(R.string.notifContent3, context);
            NotificationSender notificationSender = new NotificationSender(context);
            notificationSender.scheduleNotification(); //reschedule notifs for next week
        }

        dataStoreManager.setDaysLeft(dataStoreManager.retrieveDaysLeft() - 1); //deincrement
    }

    public void sendNotification(int message, Context context) {
        if (notificationManager == null) { //this should never happen
            Log.d("TestingTesting123", "NotificationManager is null");
            return;
        }

        // Intent to launch the app when the notification is clicked
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setContentTitle(context.getString(R.string.notifTitle));
        builder.setContentText(context.getString(message));
        builder.setSmallIcon(R.drawable.ic_stat_sassema_small);
        builder.setAutoCancel(true); // The notification cancels itself automatically when clicked
        builder.setChannelId(CHANNEL_ID);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notificationManager.notify(0, notification);
    }

    private void createNotificationChannel() { //this is required to send notifications
        //I don't know if any of the channel attributes matter since we're only using one channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            channel = new NotificationChannel(CHANNEL_ID, name, importance);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            } else {
                Log.d("TestingTesting123", "NotificationManager is null while creating channel");
            }
        }
    }
}

