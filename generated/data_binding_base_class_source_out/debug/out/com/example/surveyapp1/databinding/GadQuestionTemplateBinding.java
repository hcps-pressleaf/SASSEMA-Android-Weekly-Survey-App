// Generated by view binder compiler. Do not edit!
package com.example.surveyapp1.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.surveyapp1.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class GadQuestionTemplateBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final RadioButton radioButton1;

  @NonNull
  public final RadioButton radioButton2;

  @NonNull
  public final RadioButton radioButton3;

  @NonNull
  public final RadioButton radioButton4;

  @NonNull
  public final RadioGroup radiogroup;

  private GadQuestionTemplateBinding(@NonNull ConstraintLayout rootView,
      @NonNull RadioButton radioButton1, @NonNull RadioButton radioButton2,
      @NonNull RadioButton radioButton3, @NonNull RadioButton radioButton4,
      @NonNull RadioGroup radiogroup) {
    this.rootView = rootView;
    this.radioButton1 = radioButton1;
    this.radioButton2 = radioButton2;
    this.radioButton3 = radioButton3;
    this.radioButton4 = radioButton4;
    this.radiogroup = radiogroup;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static GadQuestionTemplateBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static GadQuestionTemplateBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.gad_question_template, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static GadQuestionTemplateBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.radioButton1;
      RadioButton radioButton1 = ViewBindings.findChildViewById(rootView, id);
      if (radioButton1 == null) {
        break missingId;
      }

      id = R.id.radioButton2;
      RadioButton radioButton2 = ViewBindings.findChildViewById(rootView, id);
      if (radioButton2 == null) {
        break missingId;
      }

      id = R.id.radioButton3;
      RadioButton radioButton3 = ViewBindings.findChildViewById(rootView, id);
      if (radioButton3 == null) {
        break missingId;
      }

      id = R.id.radioButton4;
      RadioButton radioButton4 = ViewBindings.findChildViewById(rootView, id);
      if (radioButton4 == null) {
        break missingId;
      }

      id = R.id.radiogroup;
      RadioGroup radiogroup = ViewBindings.findChildViewById(rootView, id);
      if (radiogroup == null) {
        break missingId;
      }

      return new GadQuestionTemplateBinding((ConstraintLayout) rootView, radioButton1, radioButton2,
          radioButton3, radioButton4, radiogroup);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
