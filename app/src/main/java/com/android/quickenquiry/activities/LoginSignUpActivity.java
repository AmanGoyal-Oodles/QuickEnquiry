package com.android.quickenquiry.activities;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.quickenquiry.R;
import com.android.quickenquiry.fragments.LoginFragment;

public class LoginSignUpActivity extends AppCompatActivity {


    private Context mContext;
    private static final String CURRENT_TAG=LoginSignUpActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        init();
    }

    private void init() {
        initVariables();
        openLoginFragment();
    }

    private void openLoginFragment() {
        Fragment fragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.login_signUp_frame, fragment, CURRENT_TAG);
        //fragmentTransaction.addToBackStack(CURRENT_TAG);
        fragmentTransaction.commit();
    }

    private void initVariables() {
        mContext=getApplicationContext();
    }
}
