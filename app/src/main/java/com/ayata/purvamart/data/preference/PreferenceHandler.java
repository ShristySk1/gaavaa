package com.ayata.purvamart.data.preference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.ayata.purvamart.ui.login.PortalActivity;

import java.io.ByteArrayOutputStream;


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

    public static void saveTokenTemp(Context context, String string) {
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

    public static void saveImage(Context context, Bitmap bitmap) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("image", encodeTobase64((Bitmap) bitmap));
        editor.apply();
    }

    // method for bitmap to base64
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    // method for base64 to bitmap
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static void logout(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, PortalActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

    public static Bitmap getImage(Context context) {
        String imageBitmap = PreferenceManager.getDefaultSharedPreferences(context).getString("image", "-1");
        Bitmap bitmap = null;
        if (!(imageBitmap.equals("-1"))) {
            bitmap = decodeBase64(imageBitmap);
        }
        return bitmap;
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
    public synchronized static void setGrandTotal(Context context, String grandTotal) {
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

    public static void setPaymentGateway(Context context, String paymentGateway) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("PaymentGateway", paymentGateway);
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

    public static String getPaymentGateway(Context context) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString("PaymentGateway", "");
    }
}
