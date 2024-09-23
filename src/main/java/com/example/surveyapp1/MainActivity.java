package com.example.surveyapp1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.surveyapp1.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.System;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DataStoreManager dataStoreManager; //the datastore manager ensures active and daysleft states are maintained between sessions
    public static boolean active;
    public static int daysLeft;
    public static long startDate;
    //these instance variables just reduce the amount of datastore requests that happen while the app is open
    public static JSONObject answersJSON = new JSONObject(); //this will collect all survey answers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataStoreManager = DataStoreManager.getInstance(this);
        active = dataStoreManager.retrieveActive(); //retrieve values from datastore
        daysLeft = dataStoreManager.retrieveDaysLeft();
        startDate = dataStoreManager.retrieveStartDate();

        Log.d("TestingTesting123", "Retrieved from DataStore - active: " + active + ", daysLeft: " + daysLeft + ", startDate: " + startDate);

        File dir = new File(getExternalFilesDir(null), "SASSEMA"); //parent directory which will hold userInfo
        if (!dir.mkdirs()) { //ensures directory exists
            Log.d("TestingTesting123", "failed creation of dir (dir exists already?)");
        }
        File userInfo = new File(dir, "USER_INFO");
        if (userInfo.exists()) { //if user info exists, the app has been run before and user id should not be collected
            Intent messageIntent;
            if (active) { //the app has been run before and the survey is in its active state
                messageIntent = new Intent(getApplicationContext(), FirstActivity.class);
            } else { //the app has been run and the survey has been completed
                messageIntent = new Intent(getApplicationContext(), UnavailableActivity.class);
            }
            messageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //android studio likes these flags to keep the activities straight and clear previous activities
            startActivity(messageIntent);
        } else { //this should run is userinfo does not exist, meaning this is the first time the app is run
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            Button buttonLogin = binding.buttonLogin;
            EditText editLogin = binding.editLogin;
            buttonLogin.setOnClickListener(v -> { //what will happen when button is pushed
                //If valid userid, then create user info file with userID and phone's deviceID
                dataStoreManager.setActive(true); //sets active to true only the first time app is run
                Intent messageIntent = new Intent(getApplicationContext(), FirstActivity.class);
                String userid = editLogin.getText().toString();
                if (userid.length() == 6) { //if user id is accepted...
                    if (!userInfo.exists()) { //superfluous because this shouldn't be reached if userInfo exists, but a safeguard in case
                        try (FileOutputStream fos = new FileOutputStream(userInfo)) {
                            JSONObject jsonObject = new JSONObject(); //creates json that will become user info files
                            jsonObject.put("userID", userid); // Add key/value pair
                            String deviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID); //weird method to retrieve device id
                            jsonObject.put("deviceID", deviceID); // Add key/value pair
                            String defaultJsonContent = jsonObject.toString(4);
                            fos.write(defaultJsonContent.getBytes());
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        long currentTime = System.currentTimeMillis();
                        long remainder = currentTime % 86400000;
                        long calculatedStartDate = currentTime - remainder; //the last midnight
                        dataStoreManager.setStartDate(calculatedStartDate); //to calculate when to start survey and send notifs
                        NotificationSender notificationSender = new NotificationSender(this);
                        notificationSender.scheduleNotification(); //starts the notification and app lifecycle. this assumes the survey will be completed on this same day
                        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
                        Intent deactivate = new Intent(this, SurveyDeactivator.class);
                        PendingIntent deactivateIntent = PendingIntent.getBroadcast(this, 50, deactivate, PendingIntent.FLAG_IMMUTABLE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calculatedStartDate+259200000, deactivateIntent);
                        //this intent will close the survey after two days. If the phone is rebooted, it is cancelled. Place for improvement in the future, but doesn't affect greater functionality of the app
                    } else {
                        Log.d("TestingTesting123", "something very wrong, userInfo exists at this state, this should never happen");
                    }
                    startActivity(messageIntent); //navigates to beginning of survey
                } else {
                    Log.d("TestingTesting123", "Invalid userID entered");
                    Toast.makeText(this, getString(R.string.wrongID), Toast.LENGTH_SHORT).show(); //error message is user id is not accepted
                }

            });
        }
    }
}
