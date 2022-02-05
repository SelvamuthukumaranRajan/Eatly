package com.example.deliveryapp.commonutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefsUtils
{
    public enum PREF_KEY
    {
        //login
        shareUserSerial("shareUserSerial"),shareUserName("shareUserName"),shareGender("shareGender"),
        sharePassword("sharePassword"),shareStreet("shareStreet"),shareCountry("shareCountry"),
        shareState("shareState"),shareCity("shareCity"),shareCountryName("shareCountryName"),
        shareStateName("shareStateName"),shareCityName("shareCityName"),sharePincode("sharePincode"),
        shareMobile("shareMobile"),shareLoginStatus("shareLoginStatus"),

        //Location
        shareLocation("shareLocation"),shareSelectedLocation("shareSelectedLocation"),shareLatitude("shareLatitude"), shareLongitude("shareLongitude"),shareCurrentLocation("shareCurrentLocation"),
        shareNewLocation("shareNewLocation"),navigateActivity("navigateActivity"),

        //AddressBook
        forHome("forHome"),forWork("forWork"),forOther("forOther"),
        homeAddress("homeAddress"),workAddress("workAddress"),otherAddress("otherAddress");

        public final String KEY;

        PREF_KEY(String key)
        {
            this.KEY = key;
        }
    }
    public static void putInt(PREF_KEY key, int value) {
        putInt(DeliveryApp.getInstance(), key, value);
    }
    public static int getInt(PREF_KEY key) {
        return getInt(DeliveryApp.getInstance(), key);
    }
    public static void putLong(PREF_KEY key, long value) {
        putLong(DeliveryApp.getInstance(), key, value);
    }
    public static long getLong(PREF_KEY key) {
        return getLong(DeliveryApp.getInstance(), key);
    }
    public static void putString(PREF_KEY key, String value) {
        putString(DeliveryApp.getInstance(), key, value);
    }
    public static String getString(PREF_KEY key) {
        return getString(DeliveryApp.getInstance(), key);
    }
    public static boolean putBoolean(PREF_KEY key, boolean value) {
        putBoolean(DeliveryApp.getInstance(), key, value);
        return value;
    }
    public static boolean getBoolean(PREF_KEY key) {
        return getBoolean(DeliveryApp.getInstance(), key);
    }
    private static void putInt(Context context, PREF_KEY key, int value) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key.KEY, value);
        // Commit the edits!
        editor.commit();
    }
    private static int getInt(Context context, PREF_KEY key) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        int value = sharedPref.getInt(key.KEY, 0);
        return value;
    }
    private static void putLong(Context context, PREF_KEY key, long value) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(key.KEY, value);
        // Commit the edits!
        editor.commit();
    }
    private static long getLong(Context context, PREF_KEY key) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        long value = sharedPref.getLong(key.KEY, 0);
        return value;
    }
    private static void putString(Context context, PREF_KEY key, String value) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key.KEY, value);
        // Commit the edits!
        editor.commit();
    }
    private static String getString(Context context, PREF_KEY key) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String value = sharedPref.getString(key.KEY, null);
        return value;
    }
    private static void putBoolean(Context context, PREF_KEY key, boolean value) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key.KEY, value);
        // Commit the edits!
        editor.commit();
    }
    public static boolean getBoolean(Context context, PREF_KEY key) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        boolean value = sharedPref.getBoolean(key.KEY, false);
        return value;
    }
    public static void putString(String key, String value) {
        putString(DeliveryApp.getInstance(), key, value);
    }
    private static void putString(Context context, String key, String value) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }
    private static String getString(Context context, String key) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String value = sharedPref.getString(key, null);
        return value;
    }
    public static String getString(String key) {
        return getString(DeliveryApp.getInstance(), key);
    }
    public static void clearAllPrefs(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }
    public static void clearStringPrefs(Context context, String key) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPref.edit().remove(key).commit();
    }


}
