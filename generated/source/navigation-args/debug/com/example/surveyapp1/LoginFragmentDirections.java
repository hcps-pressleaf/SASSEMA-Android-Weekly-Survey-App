package com.example.surveyapp1;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class LoginFragmentDirections {
  private LoginFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionLoginFragmentToFirstFragment() {
    return new ActionOnlyNavDirections(R.id.action_LoginFragment_to_FirstFragment);
  }

  @NonNull
  public static NavDirections actionLoginFragmentToUnavailableActivity() {
    return new ActionOnlyNavDirections(R.id.action_LoginFragment_to_unavailableActivity);
  }
}
