package com.example.surveyapp1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.surveyapp1.databinding.FragmentPsBinding;
import com.example.surveyapp1.databinding.PsQuestionTemplateBinding;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

//look to adhd activity, it has all the same functionality and comments
public class PSActivity extends Activity {

    private FragmentPsBinding binding;

    private PsQuestionTemplateBinding[] views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentPsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        views = new com.example.surveyapp1.databinding.PsQuestionTemplateBinding[]{
                binding.constraintPSQ12, binding.constraintPSQ13, binding.constraintPSQ14,
                binding.constraintPSQ15, binding.constraintPSQ16, binding.constraintPSQ17,
                binding.constraintPSQ19, binding.constraintPSQ20, binding.constraintPSQ21,
                binding.constraintPSQ22
        };

        binding.buttonSecond.setOnClickListener(v -> {
            if (checkSubmissionEmpty()) {
                updateJSON();
                Intent intent = new Intent(PSActivity.this, GADActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(PSActivity.this, getString(R.string.answerAll), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean checkSubmissionEmpty() {
        for (PsQuestionTemplateBinding view : views) {
            RadioGroup radioGroup = view.radiogroup;
            if (radioGroup.getCheckedRadioButtonId() == -1) {
                return false;
            }
        }
        return true;
    }

    private void updateJSON(){
        try {
            MainActivity.answersJSON.put("userID", PostDatam.getFromUserInfo(this, "userID"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //since this is the first class, we will add userID to the top of json here. this buys time for the program to create the file
        for (PsQuestionTemplateBinding view : views) {
            String questionId = getResources().getResourceEntryName(view.getRoot().getId());
            String questionName = questionId.substring(questionId.length()-5);
            RadioButton radioButton = view.getRoot().findViewById(view.radiogroup.getCheckedRadioButtonId());
            String answer = getResources().getResourceEntryName(radioButton.getId());
            String answerName = answer.substring(answer.length()-1);
            try {
                MainActivity.answersJSON.put(questionName, answerName);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
