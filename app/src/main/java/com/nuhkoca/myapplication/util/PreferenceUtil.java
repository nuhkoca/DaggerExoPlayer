package com.nuhkoca.myapplication.util;

import android.content.SharedPreferences;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A class that handles data persistence during the app runtime
 *
 * @author nuhkoca
 */
public class PreferenceUtil {

    private SharedPreferences sharedPreferences;

    @Inject
    PreferenceUtil(@NonNull SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    /**
     * Helps save Int data
     *
     * @param key indicator
     * @param val value
     */
    public void putIntData(@NonNull String key, int val) {
        sharedPreferences.edit().putInt(key, val).apply();
    }

    /**
     * Helps get int data
     *
     * @param key    indicator
     * @param defVal default value
     */
    public int getIntData(@NonNull String key, int defVal) {
        return sharedPreferences.getInt(key, defVal);
    }

    /**
     * Helps save long data
     *
     * @param key indicator
     * @param val value
     */
    public void putLongData(@NonNull String key, long val) {
        sharedPreferences.edit().putLong(key, val).apply();
    }

    /**
     * Helps get long data
     *
     * @param key    indicator
     * @param defVal default value
     */
    public long getLongData(@NonNull String key, long defVal) {
        return sharedPreferences.getLong(key, defVal);
    }

    /**
     * Helps save String data
     *
     * @param key indicator
     * @param val value
     */
    public void putStringData(@NonNull String key, @Nullable String val) {
        sharedPreferences.edit().putString(key, val).apply();
    }

    /**
     * Helps get String data
     *
     * @param key    indicator
     * @param defVal default value
     */
    public String getStringData(@NonNull String key, @Nullable String defVal) {
        return sharedPreferences.getString(key, defVal);
    }

    /**
     * Helps save Boolean data
     *
     * @param key indicator
     * @param val value
     */
    public void putBooleanData(@NonNull String key, boolean val) {
        sharedPreferences.edit().putBoolean(key, val).apply();
    }

    /**
     * Helps get Boolean data
     *
     * @param key    indicator
     * @param defVal default value
     */
    public boolean getBooleanData(@NonNull String key, boolean defVal) {
        return sharedPreferences.getBoolean(key, defVal);
    }

    /**
     * Deletes specific data in {@link SharedPreferences}
     *
     * @param key indicator
     */
    public void removeData(@NonNull String key) {
        sharedPreferences.edit().remove(key).apply();
    }
}
