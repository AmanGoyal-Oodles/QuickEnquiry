package com.android.quickenquiry.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.android.quickenquiry.R;
import com.android.quickenquiry.activities.AddContactActivity;
import com.android.quickenquiry.activities.MainDashboardActivity;
import com.android.quickenquiry.dialoges.ForgotPasswordDialog;
import com.android.quickenquiry.dialoges.NewPasswordDialog;
import com.android.quickenquiry.dialoges.OTPDialog;
import com.android.quickenquiry.interfaces.ForgotPasswordDialogResponseListener;
import com.android.quickenquiry.interfaces.OTPDialogListener;
import com.android.quickenquiry.interfaces.ResetPassDialogListener;
import com.android.quickenquiry.utils.util.AppToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/2/2018.
 */

public class LoginFragment extends Fragment implements ForgotPasswordDialogResponseListener,OTPDialogListener,ResetPassDialogListener{


    @BindView(R.id.login_register_text_tv)
    TextView mRegisterTv;
    @BindView(R.id.login_forgot_pass_tv)
    TextView mForgotPassTv;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    private Context mContext;
    private Activity mActivity;
    private static final String CURRENT_TAG=LoginFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        init();
    }

    private void init() {
        initVariables();
    }

    private void initVariables() {
        mContext=getContext();
        mActivity=getActivity();
    }

    @OnClick({R.id.login_register_text_tv})
    public void onClickRegisterTv() {
        openRegisterFragment();
    }

    @OnClick({R.id.login_forgot_pass_tv})
    public void onClickForgotPassTv() {
        ForgotPasswordDialog dialog=new ForgotPasswordDialog(mContext,this);
        dialog.show();
    }

    @OnClick({R.id.login_btn})
    public void onClickLogin() {
        Intent intent=new Intent(getActivity(), MainDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        startActivity(intent);
    }

    private void openRegisterFragment() {
        Fragment fragment = new RegisterFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.login_signUp_frame, fragment, CURRENT_TAG);
        //fragmentTransaction.addToBackStack(CURRENT_TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void isOTPSent(boolean isSend) {
        if(isSend) {
            OTPDialog dialog=new OTPDialog(mContext,this);
            dialog.show();
        }
    }

    @Override
    public void isOTPValidate(boolean isValidate) {
        if(isValidate) {
            NewPasswordDialog dialog=new NewPasswordDialog(mContext,this);
            dialog.show();
        }
    }

    @Override
    public void isPasswordReset(boolean isReset) {
        AppToast.showToast(mContext,"Reset Password Successfully");
    }
}
