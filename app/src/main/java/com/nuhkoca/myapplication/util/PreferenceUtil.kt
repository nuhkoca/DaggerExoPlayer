package com.nuhkoca.myapplication.util

import android.content.SharedPreferences

import javax.inject.Inject
import javax.inject.Singleton

/**
 * A class that handles data persistence during the app runtime
 *
 * @author nuhkoca
 */
@Singleton
class PreferenceUtil @Inject constructor(private val sharedPreferences: SharedPreferences) {

    /**
     * Helps save Int data
     *
     * @param key indicator
     * @param val value
     */
    fun putIntData(key: String, `val`: Int) {
        sharedPreferences.edit().putInt(key, `val`).apply()
    }

    /**
     * Helps get int data
     *
     * @param key    indicator
     * @param defVal default value
     */
    fun getIntData(key: String, defVal: Int): Int {
        return sharedPreferences.getInt(key, defVal)
    }

    /**
     * Helps save long data
     *
     * @param key indicator
     * @param defVal value
     */
    fun putLongData(key: String, defVal: Long) {
        sharedPreferences.edit().putLong(key, defVal).apply()
    }

    /**
     * Helps get long data
     *
     * @param key    indicator
     * @param defVal default value
     */
    fun getLongData(key: String, defVal: Long): Long {
        return sharedPreferences.getLong(key, defVal)
    }

    /**
     * Helps save String data
     *
     * @param key indicator
     * @param defVal value
     */
    fun putStringData(key: String, defVal: String?) {
        sharedPreferences.edit().putString(key, defVal).apply()
    }

    /**
     * Helps get String data
     *
     * @param key    indicator
     * @param defVal default value
     */
    fun getStringData(key: String, defVal: String?): String? {
        return sharedPreferences.getString(key, defVal)
    }

    /**
     * Helps save Boolean data
     *
     * @param key indicator
     * @param defVal value
     */
    fun putBooleanData(key: String, defVal: Boolean) {
        sharedPreferences.edit().putBoolean(key, defVal).apply()
    }

    /**
     * Helps get Boolean data
     *
     * @param key    indicator
     * @param defVal default value
     */
    fun getBooleanData(key: String, defVal: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defVal)
    }

    /**
     * Deletes specific data in [SharedPreferences]
     *
     * @param key indicator
     */
    fun removeData(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}
