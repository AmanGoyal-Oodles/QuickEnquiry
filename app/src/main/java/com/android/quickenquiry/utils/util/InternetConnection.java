package com.android.quickenquiry.utils.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by user on 2/2/2018.
 */

public class InternetConnection {

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isConnected=false;
        NetworkInfo activeNetwork= null;
        if (connectivityManager != null) {
            activeNetwork = connectivityManager.getActiveNetworkInfo();
            isConnected=activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
        }

        return isConnected;
    }

}