package com.ayata.purvamart.data.preference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ayata.purvamart.PortalActivity;


public class PreferenceHandler {
    public static void saveUserTest(Context context) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Registered", true);
        editor.apply();
    }

    //after successful login
    public static void saveUser(String details, Context context) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Registered", true);
        editor.putString("Token", details);
        editor.apply();
    }

    public static void logout(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, PortalActivity.class);
        context.startActivity(i);
    }

    public static Boolean isUserAlreadyLoggedIn(Context context) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getBoolean("Registered", false);
    }

    public static String getToken(Context context) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString("Token", "");
    }
}
