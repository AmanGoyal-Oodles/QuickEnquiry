package com.android.quickenquiry.utils.constants;


import com.android.quickenquiry.BuildConfig;

/**
 * Created by Cortana on 1/11/2018.
 */

public class AppConfig {


    private static final String QUICK_ENQUIRY_PRODUCTION_URL="http://ascinfratech.in/ascinfratech/api";
    private static final String QUICK_ENQUIRY_STAGING_URL="http://ascinfratech.in/ascinfratech/api";
    public static String APP_SERVER_URL="http://ascinfratech.in/ascinfratech/api";

    public static void setConfig() {
        if(BuildConfig.DEBUG) {
            APP_SERVER_URL=QUICK_ENQUIRY_PRODUCTION_URL;
        } else {
            APP_SERVER_URL=QUICK_ENQUIRY_STAGING_URL;
        }
    }

}
