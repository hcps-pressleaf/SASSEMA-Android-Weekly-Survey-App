package com.example.surveyapp1;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class GADFragmentDirections {
  private GADFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionGADFragmentToADHDFragment() {
    return new ActionOnlyNavDirections(R.id.action_GADFragment_to_ADHDFragment);
  }
}
