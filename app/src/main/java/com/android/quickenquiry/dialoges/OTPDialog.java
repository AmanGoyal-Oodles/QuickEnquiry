package com.android.quickenquiry.dialoges;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.quickenquiry.R;
import com.android.quickenquiry.activities.MainDashboardActivity;
import com.android.quickenquiry.interfaces.OTPDialogListener;
import com.android.quickenquiry.interfaces.apiResponseListener.ForgotPassMobileValidateResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.LoginResponseListener;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.ForgotPassMobileValidate;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.RegisterApi;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.ValidateOTPAPI;
import com.android.quickenquiry.utils.apiResponseBean.UserResponseBean;
import com.android.quickenquiry.utils.util.AppToast;
import com.android.quickenquiry.utils.util.InputValidation;
import com.android.quickenquiry.utils.util.dialogs.ShowDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/3/2018.
 */

public class OTPDialog extends Dialog implements LoginResponseListener, ForgotPassMobileValidateResponseListener {


    @BindView(R.id.otp_text_et)
    EditText mOTPEt;
    @BindView(R.id.otp_cancel_btn)
    Button mCancelBtn;
    @BindView(R.id.otp_validate_btn)
    Button mValidateBtn;
    @BindView(R.id.otp_resend_tv)
    TextView mResendTv;
    private Context mContext;
    private OTPDialogListener mOTPOtpDialogListener;
    private String mOTP = "";
    private String mMobile = "";
    private ProgressDialog mProgressDialog;
    private AccountDetailHolder mAccountDetailHolder;
    private Activity mActivity;
    private String mTag = "";

    public OTPDialog(@NonNull Context context, Activity activity, OTPDialogListener listener, String mobile, String otp, String tag) {
        super(context);
        mContext = context;
        mActivity = activity;
        mOTPOtpDialogListener = listener;
        mOTP = otp;
        mMobile = mobile;
        mTag = tag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_otp);
        ButterKnife.bind(this);
        setCancelable(false);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        init();
    }

    private void init() {
        initVariables();
    }

    private void initVariables() {
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        if(mTag.equalsIgnoreCase("register")) {
            mResendTv.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.otp_cancel_btn})
    public void onClickCancelBtn() {
        dismiss();
        //mOTPOtpDialogListener.isOTPValidate(false);
    }

    @OnClick({R.id.otp_validate_btn})
    public void onClickValidate() {
        String otp = mOTPEt.getText().toString().trim();
        if (mTag.equalsIgnoreCase("forgot password")) {
            if (otp.equalsIgnoreCase(mOTP)) {
                mOTPOtpDialogListener.isOTPValidate(true, mMobile);
                dismiss();
            } else {
                AppToast.showToast(mContext, "Please enter correct OTP");
            }
        } else {
            mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
            ValidateOTPAPI validateOTPAPI = new ValidateOTPAPI(mContext, this, mProgressDialog);
            validateOTPAPI.callValidateOTPApi(mMobile, otp);
        }
        //mOTPOtpDialogListener.isOTPValidate(true);
    }

    @OnClick({R.id.otp_resend_tv})
    public void onClickResendTv() {
        if(mTag.equalsIgnoreCase("forgot Password")) {
            String mobile = mMobile;
            mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
            ForgotPassMobileValidate forgotPassMobileValidate = new ForgotPassMobileValidate(mContext, this, mProgressDialog);
            forgotPassMobileValidate.callForgotPassMobileValidateApi(mobile);
        }
    }

    @Override
    public void getLoginResponse(boolean isLogin, UserResponseBean userResponseBean) {
        if (isLogin) {
            dismiss();
            mAccountDetailHolder.setUserDetail(userResponseBean);
            mOTPOtpDialogListener.isOTPValidate(true, mMobile);
        }
    }

    @Override
    public void getForgotPassMobileValidateResponse(boolean isOTPSent, String OTP) {
        if (isOTPSent) {
            mOTP = OTP;
        }
    }

}