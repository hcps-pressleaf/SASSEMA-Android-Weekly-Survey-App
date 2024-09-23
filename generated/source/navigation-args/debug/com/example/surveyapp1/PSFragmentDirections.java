package com.example.surveyapp1;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class PSFragmentDirections {
  private PSFragmentDirections() {
  }

  @NonNull
  public static NavDirections PSFragmentToGADFragment() {
    return new ActionOnlyNavDirections(R.id.PSFragment_to_GADFragment);
  }
}
