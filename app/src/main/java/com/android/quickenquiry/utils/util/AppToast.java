package com.android.quickenquiry.utils.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by user on 2/2/2018.
 */

public class AppToast {

    private static Toast toast;

    public static void showToast(Context context, String message) {
        try {
            if (!toast.getView().isShown()) {
                toast=Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (Exception ex) {
            toast=Toast.makeText(context,message,Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}