package com.android.quickenquiry.dialoges;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.ForgotPasswordDialogResponseListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/3/2018.
 */

public class ForgotPasswordDialog extends Dialog {


    @BindView(R.id.forgot_pass_mobile_et)
    EditText mMobileNoEt;
    @BindView(R.id.forgot_pass_send_otp_btn)
    Button mSendOTPBtn;
    @BindView(R.id.forgot_pass_cancel_btn)
    Button mCancelBtn;
    private ForgotPasswordDialogResponseListener mForgotPasswordDialogResponseListener;

    public ForgotPasswordDialog(@NonNull Context context, ForgotPasswordDialogResponseListener forgotPasswordDialogResponseListener) {
        super(context);
        mForgotPasswordDialogResponseListener=forgotPasswordDialogResponseListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_forgot_password);
        ButterKnife.bind(this);
        setCancelable(false);
        Window window=getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @OnClick({R.id.forgot_pass_cancel_btn})
    public void onClickCancel() {
        dismiss();
        mForgotPasswordDialogResponseListener.isOTPSent(false);
    }

    @OnClick({R.id.forgot_pass_send_otp_btn})
    public void onClickSendOTP() {
        dismiss();
        mForgotPasswordDialogResponseListener.isOTPSent(true);
        //todo send otp and open otp dialog.
    }

}
