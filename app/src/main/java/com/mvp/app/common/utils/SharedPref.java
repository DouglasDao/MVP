package com.mvp.app.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * This class handles the all the shared preference operation.
 * .i.e., creating shared preference and to set and get value.
 */

public class SharedPref {

    private static final String PREF_NAME = "SharedPref";
    // Single ton objects...
    private static SharedPreferences preference = null;
    private static SharedPref sharedPref = null;
    private Context context;

    public SharedPref() {
    }

    public SharedPref(Context context) {
        this.context = context;
    }

    //Single ton method for this class...
    public static SharedPref getInstance() {
        if (sharedPref != null) {
            return sharedPref;
        } else {
            sharedPref = new SharedPref();
            return sharedPref;
        }
    }



/*    public static SharedPref getInstance(Context context) {
        context = context;
        if (sharedPref != null) {
            return sharedPref;
        } else {
            sharedPref = new SharedPref();
            return sharedPref;
        }
    }*/


    /**
     * Singleton object for the shared preference.
     *
     * @param context Context of current state of the application/object
     * @return SharedPreferences object is returned.
     */

    private SharedPreferences getPreferenceInstance(Context context) {
        if (preference != null) {
            return preference;
        } else {
            //TODO: Shared Preference name has to be set....
            preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            return preference;
        }
    }

    /**
     * Set the String value in the shared preference W.R.T the given key.
     *
     * @param context Context of current state of the application/object
     * @param key     String used as a key for accessing the value.
     * @param value   String value which is to be stored in shared preference.
     */

    public void setSharedValue(Context context, String key, String value) {
        getPreferenceInstance(context);
        Editor editor = preference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void setSharedValue(Context context, String key, Long value) {
        getPreferenceInstance(context);
        Editor editor = preference.edit();
        editor.putLong(key, value);
        editor.apply();
    }


   /* *//***//*
    public void onClearAllPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("ConneKt", Context.MODE_PRIVATE);
        //SharedPreferences preferences = context.getSharedPreferences("PREFERENCE", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        FacebookManager.getInstance().logoutFacebook();
    }*/


    /**
     * Set the Integer value in the shared preference W.R.T the given key.
     *
     * @param context Context of current state of the application/object
     * @param key     String used as a key for accessing the value.
     * @param value   Integer value which is to be stored in shared preference.
     */

    public void setSharedValue(Context context, String key, int value) {
        getPreferenceInstance(context);
        Editor editor = preference.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Set the boolean value in the shared preference W.R.T the given key.
     *
     * @param context Context of current state of the application/object
     * @param key     String used as a key for accessing the value.
     * @param value   Boolean value which is to be stored in shared preference.
     */

    public void setSharedValue(Context context, String key, Boolean value) {
        getPreferenceInstance(context);
        Editor editor = preference.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Returns Boolean value for the given key.
     * By default it will return "false".
     *
     * @param context Context of current state of the application/object
     * @param key     String used as a key for accessing the value.
     * @return false by default; returns the Boolean value for the given key.
     */

    public Boolean getBooleanValue(Context context, String key) {
        return getPreferenceInstance(context).getBoolean(key, false);
    }

    /**
     * Returns Integer value for the given key.
     * By default it will return "-1".
     *
     * @param context Context of current state of the application/object
     * @param key     String used as a key for accessing the value.
     * @return -1 by default; returns the Integer value for the given key.
     */

    public int getIntValue(Context context, String key) {
        return getPreferenceInstance(context).getInt(key, -1);
    }


    /**
     * Returns String value for the given key.
     * By default it will return null.
     *
     * @param context Context of current state of the application/object
     * @param key     String used as a key for accessing the value.
     * @return null by default; returns the String value for the given key.
     */

    public String getStringValue(Context context, String key) {
        return getPreferenceInstance(context).getString(key, null);
    }


    public Long getLongValue(Context context, String key) {
        return getPreferenceInstance(context).getLong(key, -1);
    }


/*    public long getToken() {
        return getPreferenceInstance(context).getLong(context.getString(R.string.CompanyId), -1);
    }

    public void setToken(String value) {
        setSharedValue(context, context.getString(R.string.CompanyId), value);
    }*/

    public void clearSharedPreferences(Context context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().clear().apply();
    }
}
