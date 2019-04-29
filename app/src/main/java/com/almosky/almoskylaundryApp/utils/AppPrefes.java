/**
 *
 */
package com.almosky.almoskylaundryApp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPrefes {

    private static final String PREFS_NAME = "ALMOSKY";
    private SharedPreferences appSharedPrefs;
    private Editor prefsEditor;

    public AppPrefes(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(PREFS_NAME,
                Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    /****
     * getdata() get the value from the preference
     */
    public String getData(String key) {
        return appSharedPrefs.getString(key, "");
    }

    public double getDoubleData(String key) {
        return Double.longBitsToDouble(appSharedPrefs.getLong(key, 0));
    }

    public Integer getIntData(String key) {
        return appSharedPrefs.getInt(key, 0);
    }

    public boolean getBoolData(String key) {
        return appSharedPrefs.getBoolean(key, false);
    }

    /****
     * SaveData() save the value to the preference
     */
    public void saveData(String Tag, String text) {
        prefsEditor.putString(Tag, text);
        prefsEditor.commit();
    }

    public void saveIntData(String key, int value) {
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }

    public void saveDoubleData(String key, double value) {
        prefsEditor.putLong(key, Double.doubleToLongBits(value));
        prefsEditor.commit();
    }

    public void saveBoolData(String key, boolean value) {
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }
}
