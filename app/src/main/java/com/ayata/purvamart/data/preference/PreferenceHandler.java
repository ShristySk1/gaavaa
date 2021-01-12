package com.ayata.purvamart.data.preference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ayata.purvamart.ui.login.PortalActivity;


public class PreferenceHandler {
    public static void saveUserTest(Context context) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Registered", true);
        editor.apply();
    }

    //after successful login
    public static void saveUser(String details, String email, String phone, String username, Context context) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Registered", true);
        editor.putString("Token", "bearer" + details);
        editor.putString("email", email);
        editor.putString("phone", phone);
        editor.putString("username", username);
        editor.apply();
    }
    public static void saveTokenTemp(Context context,String string) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Token", string);
        editor.apply();
    }
    public static void updateUser(String details, String email, String phone, String username, Context context) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Registered", true);
        editor.putString("Token", details);
        editor.putString("email", email);
        editor.putString("phone", phone);
        editor.putString("username", username);
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

    public static String getEmail(Context context) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString("email", "");
    }

    public static String getUsername(Context context) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString("username", "");
    }

    public static String getPhone(Context context) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString("phone", "");
    }

    //setTotal
    public static void setGrandTotal(Context context, String grandTotal) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("GrandTotal", grandTotal);
        editor.apply();
    }

    public static String getGrandTotal(Context context) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString("GrandTotal", "");
    }


    //for order
    public static void setOrderId(Context context, String orderId) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("OrderId", orderId);
        editor.apply();
    }

    public static void setAddressId(Context context, String addressId) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("AddressId", addressId);
        editor.apply();
    }

    public static String getOrderId(Context context) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString("OrderId", "");
    }

    public static String getAddressId(Context context) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString("AddressId", "");
    }
}
