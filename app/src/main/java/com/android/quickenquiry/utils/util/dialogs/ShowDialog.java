package com.android.quickenquiry.utils.util.dialogs;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.quickenquiry.utils.util.Logger;


/**
 * Created by user on 1/29/2018.
 */

public class ShowDialog {

    public static ProgressDialog show(Context context, CharSequence title,
                                      CharSequence message, boolean indeterminate, boolean cancelable) {
        try{
            return  ProgressDialog.show(context,title,message,indeterminate,cancelable);
        }catch(Exception e){
            Logger.LogError("DialogException",e.getMessage());
        }
        return null;
    }

    public static ProgressDialog show(Context context, CharSequence title,
                                      CharSequence message, boolean indeterminate, boolean cancelable,boolean cancelableOnTouchOutside) {
        try{
            ProgressDialog progressDialog=ProgressDialog.show(context,title,message,indeterminate,cancelable);
            progressDialog.setCanceledOnTouchOutside(cancelableOnTouchOutside);
            return progressDialog;
        }catch(Exception e){
            Logger.LogError("DialogException",e.getMessage());
        }
        return null;
    }

}