package com.example.surveyapp1;

import android.content.Context;
import android.util.Log;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import io.reactivex.rxjava3.core.Single;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//data store manager ensures active and daysleft states are maintained between sessions
public class DataStoreManager {
    private static volatile DataStoreManager INSTANCE;
    private final ExecutorService executorService;
    private final RxDataStore<Preferences> dataStore;
    //these keys are used to store and retrieve values
    private static final Preferences.Key<Boolean> KEY_ACTIVE = PreferencesKeys.booleanKey("active");
    private static final Preferences.Key<Long> KEY_STARTDATE = PreferencesKeys.longKey("startDate");
    private static final Preferences.Key<Integer> KEY_DAYSLEFT = PreferencesKeys.intKey("daysLeft");

    private DataStoreManager(Context context) {
        dataStore = new RxPreferenceDataStoreBuilder(context, "dataStore").build();
        executorService = Executors.newSingleThreadExecutor();
    }

    public static DataStoreManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DataStoreManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataStoreManager(context);
                }
            }
        }
        return INSTANCE;
        //singleton pattern, datastore does not like having multiple instances
    }

    public boolean retrieveActive() {
        Single<Boolean> value = dataStore.data().firstOrError()
                .map(prefs -> prefs.get(KEY_ACTIVE) != null ? prefs.get(KEY_ACTIVE) : false)
                .onErrorReturnItem(false);
        return value.blockingGet();
    }

    public long retrieveStartDate() {
        Single<Long> value = dataStore.data().firstOrError()
                .map(prefs -> prefs.get(KEY_STARTDATE) != null ? prefs.get(KEY_STARTDATE) : 0L)
                .onErrorReturnItem(0L);
        return value.blockingGet();
    }

    public int retrieveDaysLeft() {
        Single<Integer> value = dataStore.data().firstOrError()
                .map(prefs -> prefs.get(KEY_DAYSLEFT) != null ? prefs.get(KEY_DAYSLEFT) : 0)
                .onErrorReturnItem(0);
        return value.blockingGet();
    }

    public void setActive(boolean value) {
        updatePreference(KEY_ACTIVE, value);
        MainActivity.active = value;
    }

    public void setStartDate(long value) {
        updatePreference(KEY_STARTDATE, value);
        MainActivity.startDate = value;
    }

    public void setDaysLeft(int value) {
        updatePreference(KEY_DAYSLEFT, value);
        MainActivity.daysLeft = value;
    }

    //this method is generalized for all instance variables to save code
    private <T> void updatePreference(Preferences.Key<T> key, T value) {
        try {
            executorService.submit(() -> {
                dataStore.updateDataAsync(prefsIn -> {
                    MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
                    mutablePreferences.set(key, value);
                    return Single.just(mutablePreferences);
                }).blockingGet(); // This will block until the operation completes
                return null;
            }).get();
        } catch (ExecutionException | InterruptedException e) {
            Log.e("TestingTesting123", "Error updating preferences for key: " + key.toString(), e);
        }
    }
}
