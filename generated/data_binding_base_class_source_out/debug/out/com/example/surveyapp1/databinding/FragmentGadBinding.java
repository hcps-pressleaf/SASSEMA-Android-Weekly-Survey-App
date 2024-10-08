// Generated by view binder compiler. Do not edit!
package com.example.surveyapp1.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.surveyapp1.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentGadBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button buttonFourth;

  @NonNull
  public final GadQuestionTemplateBinding constraintGADQ43;

  @NonNull
  public final GadQuestionTemplateBinding constraintGADQ44;

  @NonNull
  public final GadQuestionTemplateBinding constraintGADQ45;

  @NonNull
  public final GadQuestionTemplateBinding constraintGADQ46;

  @NonNull
  public final GadQuestionTemplateBinding constraintGADQ47;

  @NonNull
  public final GadQuestionTemplateBinding constraintGADQ48;

  @NonNull
  public final DifficultyQuestionTemplateBinding constraintGADQ49;

  @NonNull
  public final GadQuestionTemplateBinding constraintGADQ50;

  @NonNull
  public final ConstraintLayout fragmentGadConstraint;

  private FragmentGadBinding(@NonNull ConstraintLayout rootView, @NonNull Button buttonFourth,
      @NonNull GadQuestionTemplateBinding constraintGADQ43,
      @NonNull GadQuestionTemplateBinding constraintGADQ44,
      @NonNull GadQuestionTemplateBinding constraintGADQ45,
      @NonNull GadQuestionTemplateBinding constraintGADQ46,
      @NonNull GadQuestionTemplateBinding constraintGADQ47,
      @NonNull GadQuestionTemplateBinding constraintGADQ48,
      @NonNull DifficultyQuestionTemplateBinding constraintGADQ49,
      @NonNull GadQuestionTemplateBinding constraintGADQ50,
      @NonNull ConstraintLayout fragmentGadConstraint) {
    this.rootView = rootView;
    this.buttonFourth = buttonFourth;
    this.constraintGADQ43 = constraintGADQ43;
    this.constraintGADQ44 = constraintGADQ44;
    this.constraintGADQ45 = constraintGADQ45;
    this.constraintGADQ46 = constraintGADQ46;
    this.constraintGADQ47 = constraintGADQ47;
    this.constraintGADQ48 = constraintGADQ48;
    this.constraintGADQ49 = constraintGADQ49;
    this.constraintGADQ50 = constraintGADQ50;
    this.fragmentGadConstraint = fragmentGadConstraint;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentGadBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentGadBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_gad, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentGadBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button_fourth;
      Button buttonFourth = ViewBindings.findChildViewById(rootView, id);
      if (buttonFourth == null) {
        break missingId;
      }

      id = R.id.constraint_GADQ43;
      View constraintGADQ43 = ViewBindings.findChildViewById(rootView, id);
      if (constraintGADQ43 == null) {
        break missingId;
      }
      GadQuestionTemplateBinding binding_constraintGADQ43 = GadQuestionTemplateBinding.bind(constraintGADQ43);

      id = R.id.constraint_GADQ44;
      View constraintGADQ44 = ViewBindings.findChildViewById(rootView, id);
      if (constraintGADQ44 == null) {
        break missingId;
      }
      GadQuestionTemplateBinding binding_constraintGADQ44 = GadQuestionTemplateBinding.bind(constraintGADQ44);

      id = R.id.constraint_GADQ45;
      View constraintGADQ45 = ViewBindings.findChildViewById(rootView, id);
      if (constraintGADQ45 == null) {
        break missingId;
      }
      GadQuestionTemplateBinding binding_constraintGADQ45 = GadQuestionTemplateBinding.bind(constraintGADQ45);

      id = R.id.constraint_GADQ46;
      View constraintGADQ46 = ViewBindings.findChildViewById(rootView, id);
      if (constraintGADQ46 == null) {
        break missingId;
      }
      GadQuestionTemplateBinding binding_constraintGADQ46 = GadQuestionTemplateBinding.bind(constraintGADQ46);

      id = R.id.constraint_GADQ47;
      View constraintGADQ47 = ViewBindings.findChildViewById(rootView, id);
      if (constraintGADQ47 == null) {
        break missingId;
      }
      GadQuestionTemplateBinding binding_constraintGADQ47 = GadQuestionTemplateBinding.bind(constraintGADQ47);

      id = R.id.constraint_GADQ48;
      View constraintGADQ48 = ViewBindings.findChildViewById(rootView, id);
      if (constraintGADQ48 == null) {
        break missingId;
      }
      GadQuestionTemplateBinding binding_constraintGADQ48 = GadQuestionTemplateBinding.bind(constraintGADQ48);

      id = R.id.constraint_GADQ49;
      View constraintGADQ49 = ViewBindings.findChildViewById(rootView, id);
      if (constraintGADQ49 == null) {
        break missingId;
      }
      DifficultyQuestionTemplateBinding binding_constraintGADQ49 = DifficultyQuestionTemplateBinding.bind(constraintGADQ49);

      id = R.id.constraint_GADQ50;
      View constraintGADQ50 = ViewBindings.findChildViewById(rootView, id);
      if (constraintGADQ50 == null) {
        break missingId;
      }
      GadQuestionTemplateBinding binding_constraintGADQ50 = GadQuestionTemplateBinding.bind(constraintGADQ50);

      ConstraintLayout fragmentGadConstraint = (ConstraintLayout) rootView;

      return new FragmentGadBinding((ConstraintLayout) rootView, buttonFourth,
          binding_constraintGADQ43, binding_constraintGADQ44, binding_constraintGADQ45,
          binding_constraintGADQ46, binding_constraintGADQ47, binding_constraintGADQ48,
          binding_constraintGADQ49, binding_constraintGADQ50, fragmentGadConstraint);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
