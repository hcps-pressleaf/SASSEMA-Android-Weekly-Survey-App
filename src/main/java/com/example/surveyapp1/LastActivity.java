package com.example.surveyapp1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import com.example.surveyapp1.databinding.FragmentLastBinding;

public class LastActivity extends Activity {

    private FragmentLastBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PostDatam.saveJsonToFile(this, MainActivity.answersJSON);

        binding = FragmentLastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DataStoreManager dataStoreManager = DataStoreManager.getInstance(this);
        dataStoreManager.setActive(false);
        //since the survey has been completed, it is deactivated
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

