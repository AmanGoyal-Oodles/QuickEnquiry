package com.android.quickenquiry.utils.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.android.quickenquiry.utils.constants.AppConstantKeys;


/**
 * Created by user on 2/2/2018.
 */

public class CheckPermission {

    public static boolean checkPermissionForCamera(Activity context) {
        String[] permission={"android.permission.CAMERA"};
        boolean hasPermission=(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA))== PackageManager.PERMISSION_GRANTED;
        if(!hasPermission) {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                context.requestPermissions(permission, AppConstantKeys.CAMERA_PERMISSION);
            }
        }
        return hasPermission;
    }

    public static boolean checkPermissionForReadContact(Activity context) {
        String[] permission={"android.permission.READ_CONTACTS"};
        boolean hasPermission=((ContextCompat.checkSelfPermission(context,Manifest.permission.READ_CONTACTS))
                == PackageManager.PERMISSION_GRANTED);
        if(!hasPermission) {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                context.requestPermissions(permission, AppConstantKeys.READ_CONTACT_PERMISSION);
            }
        }
        return hasPermission;
    }

}