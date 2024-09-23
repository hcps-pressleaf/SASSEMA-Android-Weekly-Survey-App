package com.example.surveyapp1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.TimeZone;

//simple class that just deactivates survey
public class SurveyDeactivator extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        DataStoreManager dataStoreManager = DataStoreManager.getInstance(context);
        dataStoreManager.setActive(false);
    }
}