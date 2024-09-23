package com.example.surveyapp1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.surveyapp1.databinding.DifficultyQuestionTemplateBinding;
import com.example.surveyapp1.databinding.FragmentGadBinding;
import com.example.surveyapp1.databinding.GadQuestionTemplateBinding;
import com.example.surveyapp1.databinding.PsQuestionTemplateBinding;

import org.json.JSONException;

//look to adhd activity, it has all the same functionality and comments
public class GADActivity extends Activity {

    private FragmentGadBinding binding;

    private GadQuestionTemplateBinding[] views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentGadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        views = new com.example.surveyapp1.databinding.GadQuestionTemplateBinding[]{
                binding.constraintGADQ43, binding.constraintGADQ44, binding.constraintGADQ45,
                binding.constraintGADQ46, binding.constraintGADQ47, binding.constraintGADQ48,
                binding.constraintGADQ50
        };

        binding.buttonFourth.setOnClickListener(v -> {
            if (checkSubmissionEmpty()) {
                updateJSON();
                Intent intent = new Intent(GADActivity.this, ADHDActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(GADActivity.this, getString(R.string.answerAll), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkSubmissionEmpty() {
        for (GadQuestionTemplateBinding view : views) {
            RadioGroup radioGroup = view.radiogroup;
            if (radioGroup.getCheckedRadioButtonId() == -1) {
                return false;
            }
        }
        DifficultyQuestionTemplateBinding view = binding.constraintGADQ49;
        RadioGroup radioGroup = view.radiogroup;
        return radioGroup.getCheckedRadioButtonId() != -1;
    }

    private void updateJSON(){
        for (GadQuestionTemplateBinding view : views) {
            String questionId = getResources().getResourceEntryName(view.getRoot().getId());
            String questionName = questionId.substring(questionId.length()-6);
            RadioButton radioButton = view.getRoot().findViewById(view.radiogroup.getCheckedRadioButtonId());
            String answer = getResources().getResourceEntryName(radioButton.getId());
            String answerName = answer.substring(answer.length()-1);
            try {
                MainActivity.answersJSON.put(questionName, answerName);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        DifficultyQuestionTemplateBinding view = binding.constraintGADQ49;
        RadioButton radioButton = view.getRoot().findViewById(view.radiogroup.getCheckedRadioButtonId());
        String answer = getResources().getResourceEntryName(radioButton.getId());
        String answerName = answer.substring(answer.length()-1);
        try {
            MainActivity.answersJSON.put("GADQ49", answerName);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
