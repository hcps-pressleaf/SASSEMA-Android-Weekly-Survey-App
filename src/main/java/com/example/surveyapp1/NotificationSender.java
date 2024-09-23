package com.example.surveyapp1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.lang.System;
import java.util.TimeZone;

public class NotificationSender {
    private final Context context;
    public NotificationSender(Context context) {
        this.context = context;
    }

    public void scheduleNotification() {
        DataStoreManager dataStoreManager = DataStoreManager.getInstance(context);
        // Creating pending intents for NotificationReceiver class
        Intent intent = new Intent(context, NotificationReceiver.class);
        Intent deactivate = new Intent(context, SurveyDeactivator.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 40, intent, PendingIntent.FLAG_IMMUTABLE);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 41, intent, PendingIntent.FLAG_IMMUTABLE);
        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(context, 42, intent, PendingIntent.FLAG_IMMUTABLE);
        PendingIntent deactivateIntent = PendingIntent.getBroadcast(context, 50, deactivate, PendingIntent.FLAG_IMMUTABLE);
        //each notif neeeds its own pending intent with a different request code or they can override each other
        long currentTime = System.currentTimeMillis();
        TimeZone timeZone = TimeZone.getDefault();
        int offset = timeZone.getOffset(currentTime);
        long remainder = currentTime % 86400000;
        int dayOfWeek = (int) ((dataStoreManager.retrieveStartDate() % (1000*60*60*24*7))/(1000*60*60*24));
        int thisdayOfWeek = (int)(((currentTime-remainder)%(1000*60*60*24*7))/(1000*60*60*24));
        int difference = dayOfWeek-thisdayOfWeek+7;
        if(difference > 7)
            difference-=7;
        else if(difference==7 && !dataStoreManager.retrieveActive())
            difference=0; //this should only occur if the phone reboots the day that the survey opens
        //calculate the difference in day of week to know when to send next survey
        //we use this logic so that the calculation will be correct even if the device reboots and notifs are rescheduled
        long millsUntilOpen = (currentTime -remainder) + difference*(24*60*60*1000) + (9*60*60*1000)-offset;
        long delayBetweenNotifs = 86400000;
        long delayUntilClose = 226800000;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long delay2 = millsUntilOpen + delayBetweenNotifs;
        long delay3 = delay2+ delayBetweenNotifs;
        alarmManager.set(AlarmManager.RTC_WAKEUP, millsUntilOpen, pendingIntent1); //rtc wakeup makes sure notifs are sent even if device is sleeping
        alarmManager.set(AlarmManager.RTC_WAKEUP, delay2, pendingIntent2);
        alarmManager.set(AlarmManager.RTC_WAKEUP, delay3, pendingIntent3);
        alarmManager.set(AlarmManager.RTC_WAKEUP, millsUntilOpen +delayUntilClose, deactivateIntent);
        dataStoreManager.setDaysLeft(2); //resetting notification counter
    }
}

