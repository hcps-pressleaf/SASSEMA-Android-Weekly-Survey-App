package com.example.surveyapp1;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class ADHDFragmentDirections {
  private ADHDFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionADHDFragmentToPSQIFragment() {
    return new ActionOnlyNavDirections(R.id.action_ADHDFragment_to_PSQIFragment);
  }
}
