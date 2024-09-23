package com.example.surveyapp1;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.example.surveyapp1.databinding.FragmentFirstBinding;
import com.example.surveyapp1.databinding.FragmentUnavailableBinding;


public class UnavailableActivity extends Activity {

    public FragmentUnavailableBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentUnavailableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        long currentTime = System.currentTimeMillis();
        String formattedDate = getFormattedDate(currentTime);
        TextView textview = binding.textView3;
        String text = String.format(getString(R.string.unavailable), formattedDate);
        //calculates when the survey will open next using similar logic as in notification sender class
        textview.setText(text);
    }

    @NonNull
    private static String getFormattedDate(long currentTime) {
        TimeZone timeZone = TimeZone.getDefault();
        int offset = timeZone.getOffset(currentTime);
        long remainder = currentTime % 86400000;
        int dayOfWeek = (int) (MainActivity.startDate % (1000*60*60*24*7))/(1000*60*60*24);
        Log.d("TestingTesting123", "startdate: " + MainActivity.startDate);
        int thisdayOfWeek = (int)((currentTime -remainder)%(1000*60*60*24*7))/(1000*60*60*24);
        Log.d("TestingTesting123", "this dayofweek:" + thisdayOfWeek);
        int difference = dayOfWeek-thisdayOfWeek+7;
        if(difference > 7)
            difference-=7;
        String pattern = "MM/dd/yyyy HH:mm";
        DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        long nextSurveyDate = (currentTime -remainder) + difference*(24*60*60*1000) + (9*60*60*1000)-offset;
        Date date = new Date(nextSurveyDate);
        return dateFormat.format(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
