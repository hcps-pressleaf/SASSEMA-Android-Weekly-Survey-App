package com.example.surveyapp1;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class QIDSFragmentDirections {
  private QIDSFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionQIDSFragmentToLastActivity() {
    return new ActionOnlyNavDirections(R.id.action_QIDSFragment_to_lastActivity);
  }
}
