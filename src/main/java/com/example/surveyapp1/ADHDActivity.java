package com.example.surveyapp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.surveyapp1.databinding.AdhdQuestionTemplateBinding;
import com.example.surveyapp1.databinding.FragmentAdhdBinding;
import com.example.surveyapp1.databinding.NotsureQuestionTemplateBinding;
import com.example.surveyapp1.databinding.YesornoQuestionTemplateBinding;

import org.json.JSONException;

public class ADHDActivity extends AppCompatActivity {

    private FragmentAdhdBinding binding;

    private AdhdQuestionTemplateBinding[] views;
    private boolean hasSymptoms;
    private boolean wasDiagnosed;
    //these manage the state of questions which affect if other questions appear or not

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentAdhdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        views = new com.example.surveyapp1.databinding.AdhdQuestionTemplateBinding[]{
                binding.constraintADHDQ100, binding.constraintADHDQ101, binding.constraintADHDQ102,
                binding.constraintADHDQ103, binding.constraintADHDQ104, binding.constraintADHDQ105,
                binding.constraintADHDQ106, binding.constraintADHDQ107, binding.constraintADHDQ108,
                binding.constraintADHDQ111, binding.constraintADHDQ112, binding.constraintADHDQ113,
                binding.constraintADHDQ114, binding.constraintADHDQ115, binding.constraintADHDQ117,
                binding.constraintADHDQ118, binding.constraintADHDQ119, binding.constraintADHDQ120,
                binding.constraintADHDQ121, binding.constraintADHDQ122, binding.constraintADHDQ123,
                binding.constraintADHDQ124, binding.constraintADHDQ125, binding.constraintADHDQ126,
                binding.constraintADHDQ127, binding.constraintADHDQ128, binding.constraintADHDQ129
        }; //all questions that can be checked in an iterator

        binding.buttonSixth.setOnClickListener(v -> { //if submit button is clicked
            if (checkSubmissionEmpty()) { //if all questions have been answered
                updateJSON(); //records all question answers to mainactivity.answersJSON
                Intent intent = new Intent(ADHDActivity.this, PSQIActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(ADHDActivity.this, getString(R.string.answerAll), Toast.LENGTH_SHORT).show();
            }
        });
        pickQuestions(); //sets click listeners that will update questions that rely on the answers of other questions
    }

    private boolean checkSubmissionEmpty() { //checks if all questions have been answered
        for (AdhdQuestionTemplateBinding option : views) {
            RadioGroup radioGroup = option.radiogroup; //gets radiogroup which contains the question answer
            if (radioGroup.getCheckedRadioButtonId() == -1) //return false if any radiogroup doesn't have an answer checked
                return false;
        }
        //have to manage all questions which have more complicated logic than just if they have an answer checked
        YesornoQuestionTemplateBinding symptomsView = binding.constraintADHDQ130; //question about if they have had adhd symptoms before
        RadioGroup radioGroup = symptomsView.radiogroup;
        int symptoms = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = symptomsView.getRoot().findViewById(symptoms);
        if (symptoms == -1)
            return false; //if it has no answer, return false
        if (selectedRadioButton.getText().toString().equals(getResources().getString(R.string.yes))) {
            hasSymptoms = true; //this will impact the behavior of other methods since we now know the next question needs to be answered
            EditText agetext = binding.editADHDQ131;
            if (agetext.getText().toString().isEmpty())
                return false; //if the next question has no answer, return false
        }
        else
            hasSymptoms = false;
        YesornoQuestionTemplateBinding adhdView = binding.constraintADHDQ259; //question about if they have been diagnosed before
        radioGroup = adhdView.radiogroup;
        int adhd = radioGroup.getCheckedRadioButtonId();
        if (adhd == -1) {
            return false; //if there is no answer, return false
        }
        selectedRadioButton = adhdView.getRoot().findViewById(adhd);
        if (selectedRadioButton.getText().toString().equals(getResources().getString(R.string.yes))) {
            wasDiagnosed = true; //this will impact the behavior of other methods since we now know the next question needs to be answered
            NotsureQuestionTemplateBinding notsureView = binding.constraintADHDQ260;
            radioGroup = notsureView.radiogroup;
            return radioGroup.getCheckedRadioButtonId() != -1; //if the next question has no answer, return false
        }
        else
            wasDiagnosed=false;
        return true; //if no questions are unanswered, return true
    }

    public void pickQuestions(){ //sets certain questions to dissapear and reappear depending on answers of other questions
        YesornoQuestionTemplateBinding adhdView = binding.constraintADHDQ130; //this question's answer determines whether or not Q131 must be answered and whether or not Q132 can be answered (no answer is required)
        RadioGroup radiogroup = adhdView.radiogroup;
        radiogroup.setOnCheckedChangeListener((group, checkedId) -> {
            // This will be called whenever a RadioButton is selected
            EditText age = binding.editADHDQ131;
            TextView tv1 = binding.textViewADHDQ131;
            ConstraintLayout whereView = binding.constraintADHDQ132;
            TextView tv2 = binding.textViewADHDQ132;
            //the four above elements will dissapear and reappear
            RadioButton checkedRadioButton = group.findViewById(checkedId);
            if(checkedRadioButton.getText().toString().equals(getResources().getString(R.string.yes)) && checkedRadioButton.isChecked()){ //make all visible
                age.setVisibility(View.VISIBLE);
                tv1.setVisibility(View.VISIBLE);
                whereView.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
            }
            else if(checkedRadioButton.getText().toString().equals(getResources().getString(R.string.no)) && checkedRadioButton.isChecked()){ //make all invisible
                age.setVisibility(View.GONE);
                tv1.setVisibility(View.GONE);
                whereView.setVisibility(View.GONE);
                tv2.setVisibility(View.GONE);
            }
        });
        //same logic for this question except it only impacts the visibility of one question, Q260 instead of two
        adhdView = binding.constraintADHDQ259;
        radiogroup = adhdView.radiogroup;
        radiogroup.setOnCheckedChangeListener((group, checkedId) -> {
            NotsureQuestionTemplateBinding notSure = binding.constraintADHDQ260;
            TextView tv1 = binding.textViewADHDQ260;
            RadioButton checkedRadioButton = group.findViewById(checkedId);
            if(checkedRadioButton.getText().toString().equals(getResources().getString(R.string.yes)) && checkedRadioButton.isChecked()){
                notSure.getRoot().setVisibility(View.VISIBLE);
                tv1.setVisibility(View.VISIBLE);
            }
            else if(checkedRadioButton.getText().toString().equals(getResources().getString(R.string.no)) && checkedRadioButton.isChecked()){
                notSure.getRoot().setVisibility(View.GONE);
                tv1.setVisibility(View.GONE);
            }
        });
    }

    private void updateJSON(){ //records all json answers after it is validated that they have all been answered
        for (AdhdQuestionTemplateBinding view : views) {
            String questionId = getResources().getResourceEntryName(view.getRoot().getId());
            String questionName = questionId.substring(questionId.length()-8); //the last characters of the layout's id will be something like ADHDQ132
            RadioButton radioButton = view.getRoot().findViewById(view.radiogroup.getCheckedRadioButtonId());
            String answer = getResources().getResourceEntryName(radioButton.getId());
            String answerName = answer.substring(answer.length()-1); //the last character of the radiobutton id is which answer choice it is
            try {
                MainActivity.answersJSON.put(questionName, answerName); //adds to json
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        if(hasSymptoms){ //if the following questions should have answers...
            try {
                MainActivity.answersJSON.put("ADHDQ130", 1);
                int age = Integer.parseInt(binding.editADHDQ131.getText().toString());
                MainActivity.answersJSON.put("ADHDQ131", age);
                StringBuilder result = new StringBuilder();
                CheckBox[] checkboxes = {binding.checkBox1, binding.checkBox2, binding.checkBox3, binding.checkBox4}; //in this question, multiple answers can be selected
                for(int i=0; i<4; i++){
                    if(checkboxes[i].isChecked()){
                        result.append(i + 1).append(",");
                    }
                }
                String answer = result.toString();
                MainActivity.answersJSON.put("ADHDQ132", answer);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                MainActivity.answersJSON.put("ADHDQ130", 2);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        if(wasDiagnosed){ //same as above but just for one question
            try{
                MainActivity.answersJSON.put("ADHDQ259", 1);
                RadioGroup question260 = binding.constraintADHDQ260.radiogroup;
                RadioButton radiobutton = question260.findViewById(question260.getCheckedRadioButtonId());
                String answerId = getResources().getResourceEntryName(radiobutton.getId());
                String answer = answerId.substring(answerId.length()-1);
                MainActivity.answersJSON.put("ADHDQ260", answer);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                MainActivity.answersJSON.put("ADHDQ259", 2);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
