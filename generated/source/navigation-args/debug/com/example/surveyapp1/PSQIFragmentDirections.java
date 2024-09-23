package com.example.surveyapp1;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class PSQIFragmentDirections {
  private PSQIFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionPSQIFragmentToQIDSFragment() {
    return new ActionOnlyNavDirections(R.id.action_PSQIFragment_to_QIDSFragment);
  }
}
