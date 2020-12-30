package com.ayata.purvamart.utils;

import android.content.Context;

import com.ayata.purvamart.MainActivity;

public class AlertDialogHelper {
    public static void dismiss(Context mContext) {
        ((MainActivity) mContext).hideProgressBar();
    }

    public static void show(Context mContext) {
        ((MainActivity) mContext).showProgressBar();
    }
}
