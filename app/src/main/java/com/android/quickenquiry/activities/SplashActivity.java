package com.android.quickenquiry.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.android.quickenquiry.R;
import com.android.quickenquiry.activities.LoginSignUpActivity;

public class SplashActivity extends AppCompatActivity {


    private static final int DELAY=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        init();
        openLoginOrHomeActivity();
    }

    private void openLoginOrHomeActivity() {
        final Intent intent=new Intent(this,LoginSignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openActivity(intent);
            }
        }, DELAY);
    }

    private void openActivity(Intent intent) {
        startActivity(intent);
    }

    private void init() {
    }
}
