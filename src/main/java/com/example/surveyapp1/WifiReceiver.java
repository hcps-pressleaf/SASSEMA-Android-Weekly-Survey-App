package com.example.surveyapp1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.File;

public class WifiReceiver extends BroadcastReceiver {

    //onreceive will trigger every time the survey is completed and at midnight every day

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        boolean isWifiEnabled = wifiManager.isWifiEnabled(); //if wifi is turned on in the phone
        boolean isConnected = netInfo != null && netInfo.isConnected(); //if the phone is connected to a network
        boolean isWifiConnection = netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI; //if the phone is connected to wifi

        if (isWifiEnabled && isConnected && isWifiConnection) {
            File dir = new File(context.getExternalFilesDir(null), "SASSEMA/toBeUploaded"); //directory inside the sassema directory that will hold all survey data pending upload
            //note that even though data type is "File", it can refer to a file or a directory
            if (dir.isDirectory()) { //should always be true
                File[] files = dir.listFiles();
                if (files != null && files.length > 0) { //if there are files to upload, upload them using the PostDatam class
                    PostDatam.uploadAllFilesInFolder(dir, context);
                } else {
                    Log.d("TestingTesting123", "No files to upload");
                }
            } else {
                Log.d("TestingTesting123", "Not a directory: " + dir.getAbsolutePath());
            }
        } else {
            Log.d("TestingTesting123", "Don't have Wifi Connection");
        }
    }

    public void triggerWifiReceiver(Context context) { // a method to manually check for wifi and upload files as opposed to the daily check
        // Create an Intent with a dummy action
        Intent intent = new Intent(ConnectivityManager.CONNECTIVITY_ACTION);

        // Manually trigger onReceive
        onReceive(context, intent);
    }
}
