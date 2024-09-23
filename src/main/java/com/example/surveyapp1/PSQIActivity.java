package com.example.surveyapp1;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.MailTo;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.SimpleDateFormat;

import com.example.surveyapp1.databinding.FragmentPsqiBinding;
import com.example.surveyapp1.databinding.GadQuestionTemplateBinding;
import com.example.surveyapp1.databinding.PsQuestionTemplateBinding;
import com.example.surveyapp1.databinding.PsqiQuestionTemplateBinding;

import org.json.JSONException;

//look to adhd activity, it has all the same functionality and comments
public class PSQIActivity extends Activity {

    private FragmentPsqiBinding binding;

    private PsqiQuestionTemplateBinding[] views;

    private EditText timeEditText1;
    private EditText timeEditText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentPsqiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        views = new com.example.surveyapp1.databinding.PsqiQuestionTemplateBinding[]{
                binding.constraintPSQIQ6, binding.constraintPSQIQ7, binding.constraintPSQIQ8,
                binding.constraintPSQIQ9, binding.constraintPSQIQ10, binding.constraintPSQIQ11,
                binding.constraintPSQIQ12, binding.constraintPSQIQ13, binding.constraintPSQIQ14,
                binding.constraintPSQIQ15
        };

        timeEditText1 = binding.editPSQIQ1;
        timeEditText2 = binding.editPSQIQ3;
        Spinner hourSpinner = binding.editPSQIQ4;

        timeEditText1.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            //these dialogues are built in android functions that are pretty nifty
            TimePickerDialog timePickerDialog = new TimePickerDialog(PSQIActivity.this,
                    (TimePicker view1, int hourOfDay, int minuteOfHour) -> {
                        Calendar selectedTime = Calendar.getInstance();
                        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedTime.set(Calendar.MINUTE, minuteOfHour);
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                        String formattedTime = sdf.format(selectedTime.getTime());
                        timeEditText1.setText(formattedTime);
                        //gets selected time from dialogue and formats it into edit box
                    }, hour, minute, false);

            timePickerDialog.show();
        });

        timeEditText2.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(PSQIActivity.this,
                    (TimePicker view1, int hourOfDay, int minuteOfHour) -> {
                        Calendar selectedTime = Calendar.getInstance();
                        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedTime.set(Calendar.MINUTE, minuteOfHour);
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                        String formattedTime = sdf.format(selectedTime.getTime());
                        timeEditText2.setText(formattedTime);
                    }, hour, minute, false);

            timePickerDialog.show();
        });

        List<String> hourOptions = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            hourOptions.add(i + " "+ getString(R.string.hours));
        }

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, hourOptions);
        //spinners are essentially dropdown menues
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSpinner.setAdapter(adapter);

        binding.buttonSeventh.setOnClickListener(v -> {
            if(checkSubmissionEmpty()) {
                updateJSON();
                Intent intent = new Intent(PSQIActivity.this, QIDSActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else {
                Toast.makeText(PSQIActivity.this, getString(R.string.answerAll), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkSubmissionEmpty(){
        for (PsqiQuestionTemplateBinding view : views) {
            RadioGroup radioGroup = view.radiogroup;
            if (radioGroup.getCheckedRadioButtonId() == -1)
                return false;
        }
        EditText[] editTexts = {
                binding.editPSQIQ1, binding.editPSQIQ2, binding.editPSQIQ3
        };
        for (EditText view : editTexts) {
            if(view.getText().toString().isEmpty())
                return false;
        }
        Spinner spinner = binding.editPSQIQ4;
        return spinner.getCount()!=0;
    }

    private void updateJSON(){
        try{
            EditText question1 = binding.editPSQIQ1;
            EditText question2 = binding.editPSQIQ2;
            EditText question3 = binding.editPSQIQ3;
            Spinner question4 = binding.editPSQIQ4;
            String question1Answer = question1.getText().toString();
            String question3Answer = question3.getText().toString();
            String question4Answer = question4.getSelectedItem().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ROOT);
            Date question1Time = sdf.parse(question1Answer);
            Date question3Time = sdf.parse(question3Answer);
            MainActivity.answersJSON.put("PSQIQ1", sdf.format(question1Time));
            MainActivity.answersJSON.put("PSQIQ2", question2.getText().toString());
            MainActivity.answersJSON.put("PSQIQ3", sdf.format(question3Time));
            MainActivity.answersJSON.put("PSQIQ4", question4Answer.substring(0, question4Answer.indexOf(" ")));
        } catch (ParseException | JSONException e) {
            throw new RuntimeException(e);
        }
        for (PsqiQuestionTemplateBinding view : views) {
            String questionId = getResources().getResourceEntryName(view.getRoot().getId());
            String questionName = questionId.substring(questionId.indexOf("_")+1);
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
