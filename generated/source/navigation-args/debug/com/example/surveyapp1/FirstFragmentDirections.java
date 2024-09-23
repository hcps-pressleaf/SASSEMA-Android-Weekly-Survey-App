package com.example.surveyapp1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class FirstFragmentDirections {
  private FirstFragmentDirections() {
  }

  @NonNull
  public static ActionFirstFragmentToPSFragment actionFirstFragmentToPSFragment(
      @NonNull String userId) {
    return new ActionFirstFragmentToPSFragment(userId);
  }

  public static class ActionFirstFragmentToPSFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionFirstFragmentToPSFragment(@NonNull String userId) {
      if (userId == null) {
        throw new IllegalArgumentException("Argument \"user_id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("user_id", userId);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionFirstFragmentToPSFragment setUserId(@NonNull String userId) {
      if (userId == null) {
        throw new IllegalArgumentException("Argument \"user_id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("user_id", userId);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("user_id")) {
        String userId = (String) arguments.get("user_id");
        __result.putString("user_id", userId);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_FirstFragment_to_PSFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getUserId() {
      return (String) arguments.get("user_id");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionFirstFragmentToPSFragment that = (ActionFirstFragmentToPSFragment) object;
      if (arguments.containsKey("user_id") != that.arguments.containsKey("user_id")) {
        return false;
      }
      if (getUserId() != null ? !getUserId().equals(that.getUserId()) : that.getUserId() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionFirstFragmentToPSFragment(actionId=" + getActionId() + "){"
          + "userId=" + getUserId()
          + "}";
    }
  }
}
