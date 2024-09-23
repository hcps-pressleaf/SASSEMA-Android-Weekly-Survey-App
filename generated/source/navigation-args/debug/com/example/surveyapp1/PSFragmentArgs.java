package com.example.surveyapp1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class PSFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private PSFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private PSFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static PSFragmentArgs fromBundle(@NonNull Bundle bundle) {
    PSFragmentArgs __result = new PSFragmentArgs();
    bundle.setClassLoader(PSFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("user_id")) {
      String userId;
      userId = bundle.getString("user_id");
      if (userId == null) {
        throw new IllegalArgumentException("Argument \"user_id\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("user_id", userId);
    } else {
      throw new IllegalArgumentException("Required argument \"user_id\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static PSFragmentArgs fromSavedStateHandle(@NonNull SavedStateHandle savedStateHandle) {
    PSFragmentArgs __result = new PSFragmentArgs();
    if (savedStateHandle.contains("user_id")) {
      String userId;
      userId = savedStateHandle.get("user_id");
      if (userId == null) {
        throw new IllegalArgumentException("Argument \"user_id\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("user_id", userId);
    } else {
      throw new IllegalArgumentException("Required argument \"user_id\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getUserId() {
    return (String) arguments.get("user_id");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("user_id")) {
      String userId = (String) arguments.get("user_id");
      __result.putString("user_id", userId);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("user_id")) {
      String userId = (String) arguments.get("user_id");
      __result.set("user_id", userId);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    PSFragmentArgs that = (PSFragmentArgs) object;
    if (arguments.containsKey("user_id") != that.arguments.containsKey("user_id")) {
      return false;
    }
    if (getUserId() != null ? !getUserId().equals(that.getUserId()) : that.getUserId() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "PSFragmentArgs{"
        + "userId=" + getUserId()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull PSFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull String userId) {
      if (userId == null) {
        throw new IllegalArgumentException("Argument \"user_id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("user_id", userId);
    }

    @NonNull
    public PSFragmentArgs build() {
      PSFragmentArgs result = new PSFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setUserId(@NonNull String userId) {
      if (userId == null) {
        throw new IllegalArgumentException("Argument \"user_id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("user_id", userId);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getUserId() {
      return (String) arguments.get("user_id");
    }
  }
}
