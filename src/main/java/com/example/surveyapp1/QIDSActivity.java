package com.example.surveyapp1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.surveyapp1.databinding.AdhdQuestionTemplateBinding;
import com.example.surveyapp1.databinding.FragmentQidsBinding;
import com.example.surveyapp1.databinding.GadQuestionTemplateBinding;
import com.example.surveyapp1.databinding.PsqiQuestionTemplateBinding;

import org.json.JSONException;

//look to adhd activity, it has all the same functionality and comments
public class QIDSActivity extends Activity {

    private FragmentQidsBinding binding;

    private RadioGroup[] views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentQidsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //note that these views are radiogroups and not constraint layouts
        //this is because each question had its own set of answers, so there was no code to reused
        views = new RadioGroup[]{
                binding.QIDSQ1RadioGroup, binding.QIDSQ2RadioGroup, binding.QIDSQ3RadioGroup,
                binding.QIDSQ4RadioGroup, binding.QIDSQ5RadioGroup, binding.QIDSQ10RadioGroup,
                binding.QIDSQ11RadioGroup, binding.QIDSQ13RadioGroup, binding.QIDSQ14RadioGroup,
                binding.QIDSQ15RadioGroup, binding.QIDSQ16RadioGroup
        };

        binding.buttonEighth.setOnClickListener(v -> {
            if (checkSubmissionEmpty()) {
                updateJSON();
                Intent intent = new Intent(QIDSActivity.this, LastActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(QIDSActivity.this, getString(R.string.answerAll), Toast.LENGTH_SHORT).show();
            }
        });
        pickQuestions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private boolean checkSubmissionEmpty() {
        for (RadioGroup group : views) {
            if (group.getCheckedRadioButtonId() == -1)
                return false;
        }
        int appetiteId = binding.QIDSQ67RadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = binding.QIDSQ67RadioGroup.findViewById(appetiteId);
        if(selectedRadioButton.getText().toString().equals(getResources().getString(R.string.QIDSQ6_7_Answer1))){
            if(binding.QIDSQ6RadioGroup.getCheckedRadioButtonId() == -1)
                return false;
        }
        else{
            if(binding.QIDSQ7RadioGroup.getCheckedRadioButtonId() == -1)
                return false;
        }
        int weightId = binding.QIDSQ89RadioGroup.getCheckedRadioButtonId();
        selectedRadioButton = binding.QIDSQ89RadioGroup.findViewById(weightId);
        if(selectedRadioButton.getText().toString().equals(getResources().getString(R.string.QIDSQ8_9_Answer1))){
            return binding.QIDSQ8RadioGroup.getCheckedRadioButtonId() != -1;
        }
        else{
            return binding.QIDSQ9RadioGroup.getCheckedRadioButtonId() != -1;
        }
    }

    public void pickQuestions(){
        RadioGroup appetiteGroup = binding.QIDSQ67RadioGroup;
        RadioGroup weightGroup = binding.QIDSQ89RadioGroup;
        appetiteGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioGroup question6 = binding.QIDSQ6RadioGroup;
            RadioGroup question7 = binding.QIDSQ7RadioGroup;
            TextView question6textview = binding.textviewQIDSQ6;
            TextView question7textview = binding.textviewQIDSQ7;
            RadioButton checkedRadioButton = group.findViewById(checkedId);
            if(checkedRadioButton.getText().toString().equals(getResources().getString(R.string.QIDSQ6_7_Answer1)) && checkedRadioButton.isChecked()){
                question6.setVisibility(View.VISIBLE);
                question6textview.setVisibility(View.VISIBLE);
                question7.setVisibility(View.GONE);
                question7textview.setVisibility(View.GONE);
            }
            else if(checkedRadioButton.getText().toString().equals(getResources().getString(R.string.QIDSQ6_7_Answer2)) && checkedRadioButton.isChecked()){
                question6.setVisibility(View.GONE);
                question6textview.setVisibility(View.GONE);
                question7.setVisibility(View.VISIBLE);
                question7textview.setVisibility(View.VISIBLE);
            }
        });

        weightGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioGroup question8 = binding.QIDSQ8RadioGroup;
            RadioGroup question9 = binding.QIDSQ9RadioGroup;
            TextView question8textview = binding.textviewQIDSQ8;
            TextView question9textview = binding.textviewQIDSQ9;
            RadioButton checkedRadioButton = group.findViewById(checkedId);
            if(checkedRadioButton.getText().toString().equals(getResources().getString(R.string.QIDSQ8_9_Answer1)) && checkedRadioButton.isChecked()){
                question8.setVisibility(View.VISIBLE);
                question8textview.setVisibility(View.VISIBLE);
                question9.setVisibility(View.GONE);
                question9textview.setVisibility(View.GONE);
            }
            else if(checkedRadioButton.getText().toString().equals(getResources().getString(R.string.QIDSQ8_9_Answer2)) && checkedRadioButton.isChecked()){
                question8.setVisibility(View.GONE);
                question8textview.setVisibility(View.GONE);
                question9.setVisibility(View.VISIBLE);
                question9textview.setVisibility(View.VISIBLE);
            }
        });
    }

    public void updateJSON(){
        for (RadioGroup view : views) {
            String questionId = getResources().getResourceEntryName(view.getId());
            String questionName = questionId.substring(0, questionId.indexOf("_"));
            RadioButton radioButton = view.findViewById(view.getCheckedRadioButtonId());
            String answer = getResources().getResourceEntryName(radioButton.getId());
            String answerName = answer.substring(answer.length()-1);
            try {
                MainActivity.answersJSON.put(questionName, answerName);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        RadioGroup appetiteGroup = binding.QIDSQ67RadioGroup;
        int selectedId = appetiteGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        String answer = selectedRadioButton.getText().toString();
        if(answer.equals(getResources().getString(R.string.QIDSQ6_7_Answer1))){
            try {
                MainActivity.answersJSON.put("QIDSQ6", answer.substring(answer.length()-1));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                MainActivity.answersJSON.put("QIDSQ7", answer.substring(answer.length()-1));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        RadioGroup weightGroup = binding.QIDSQ89RadioGroup;
        selectedId = weightGroup.getCheckedRadioButtonId();
        selectedRadioButton = findViewById(selectedId);
        answer = selectedRadioButton.getText().toString();
        if(answer.equals(getResources().getString(R.string.QIDSQ8_9_Answer1))){
            try {
                MainActivity.answersJSON.put("QIDSQ8", answer.substring(answer.length()-1));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                MainActivity.answersJSON.put("QIDSQ9", answer.substring(answer.length()-1));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

