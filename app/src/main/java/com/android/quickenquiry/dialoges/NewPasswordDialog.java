package com.android.quickenquiry.dialoges;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.ResetPassDialogListener;
import com.android.quickenquiry.interfaces.apiResponseListener.ForgotPassResetPassResponseListener;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.ForgotPassResetPass;
import com.android.quickenquiry.utils.util.AppToast;
import com.android.quickenquiry.utils.util.InputValidation;
import com.android.quickenquiry.utils.util.dialogs.ShowDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/5/2018.
 */

public class NewPasswordDialog extends Dialog implements ForgotPassResetPassResponseListener{


    @BindView(R.id.reset_pass_submit_btn)
    Button mSubmitBtn;
    @BindView(R.id.reset_pass_cancel_btn)
    Button mCancelBtn;
    @BindView(R.id.reset_pass_new_pass_et)
    EditText mNewPassEt;
    @BindView(R.id.reset_pass_confirm_pass_et)
    EditText mConfirmPassEt;
    private Context mContext;
    private ResetPassDialogListener mResetPassDialogListener;
    private ProgressDialog mProgressDialog;
    private String mMobile="";

    public NewPasswordDialog(@NonNull Context context,ResetPassDialogListener listener,String mobile) {
        super(context);
        mMobile=mobile;
        mContext=context;
        mResetPassDialogListener=listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_password);
        ButterKnife.bind(this);
        setCancelable(false);
        Window window=getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @OnClick({R.id.reset_pass_cancel_btn})
    public void onClickCancelBtn() {
        dismiss();
        //mResetPassDialogListener.isPasswordReset(false);
    }

    @OnClick({R.id.reset_pass_submit_btn})
    public void onClickSubmitBtn() {
        if(isInputValid()) {
            String pass=mNewPassEt.getText().toString().trim();
            mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
            ForgotPassResetPass forgotPassResetPass = new ForgotPassResetPass(mContext, this, mProgressDialog);
            forgotPassResetPass.callForgotPassResetPassApi(mMobile, pass);
        }//dismiss();
        //mResetPassDialogListener.isPasswordReset(true);
    }

    private boolean isInputValid() {
        if(InputValidation.validatePassword(mNewPassEt)&&InputValidation.validatePassword(mConfirmPassEt)) {
            String newPass=mNewPassEt.getText().toString().trim();
            String confirmPass=mConfirmPassEt.getText().toString().trim();
            if(newPass.equalsIgnoreCase(confirmPass)) {
                return true;
            } else {
                AppToast.showToast(mContext,"Password & Confirm Password should be same");
                return false;
            }
        }
        return false;
    }

    @Override
    public void getFPRPResponse(boolean isChanged, String message) {
        dismiss();
    }

}