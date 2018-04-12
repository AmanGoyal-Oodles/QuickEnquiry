package com.android.quickenquiry.utils.util;

import android.util.Patterns;
import android.widget.EditText;

/**
 * Created by Cortana on 1/8/2018.
 */

public class InputValidation {

    public static boolean validateFirstName(EditText nameEt) {
        String name=nameEt.getText().toString().trim();
        if(name!=null && !name.isEmpty()) {
            return true;
        }else {
            nameEt.setError("Please enter your Name");
            return false;
        }
    }

    public static boolean validateCompanyName(EditText nameEt) {
        String name=nameEt.getText().toString().trim();
        if(name!=null && !name.isEmpty()) {
            return true;
        }else {
            nameEt.setError("Please enter company Name");
            return false;
        }
    }

    public static boolean validateEmail(EditText emailEt) {
        String email=emailEt.getText().toString().trim();
        String regex="^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*\n" +
                "      @[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$";
        if(email==null || email.isEmpty()) {
            emailEt.setError("Please enter your email");
        } else if(!email.matches(Patterns.EMAIL_ADDRESS.pattern())) {
            emailEt.setError("Please enter valid email address");
        } else {
            return true;
        }
        return false;
    }

    public static boolean validateMobile(EditText mobileEt) {
        String mobile=mobileEt.getText().toString().trim();
        String regex="[0-9]+";
        if(mobile==null || mobile.isEmpty()) {
            mobileEt.setError("Please enter your phone number");
        } else if(!mobile.matches(regex)) {
            mobileEt.setError("Please enter valid phone number");
        } else if(!(mobile.length()==10)) {
            mobileEt.setError("Phone number digits should be 10");
        } else {
            return true;
        }
        return false;
    }

    public static boolean validatePassword(EditText passEt) {
        String pass=passEt.getText().toString().trim();
        if(pass==null || pass.isEmpty()) {
            passEt.setError("Please enter password");
        } else if( !(pass.length()>=6 && pass.length()<=12) ) {
            passEt.setError("Password length should be 6 to 12");
        } else {
            return true;
        }
        return false;
    }

}
