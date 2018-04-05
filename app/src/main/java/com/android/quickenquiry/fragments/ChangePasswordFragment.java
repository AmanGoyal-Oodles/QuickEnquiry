package com.android.quickenquiry.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.quickenquiry.R;
import com.android.quickenquiry.activities.LoginSignUpActivity;
import com.android.quickenquiry.interfaces.apiResponseListener.ChangePasswordResponseListener;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.ChangePasswordApi;
import com.android.quickenquiry.utils.util.AppToast;
import com.android.quickenquiry.utils.util.InputValidation;
import com.android.quickenquiry.utils.util.dialogs.ShowDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/5/2018.
 */

public class ChangePasswordFragment extends Fragment implements ChangePasswordResponseListener{


    private static final String CURRENT_TAG =ChangePasswordFragment.class.getName() ;
    @BindView(R.id.change_pass_old_pass_et)
    EditText mOldPassEt;
    @BindView(R.id.change_pass_new_pass_et)
    EditText mNewPassEt;
    @BindView(R.id.change_pass_confirm_pass_et)
    EditText mConfirmPassEt;
    @BindView(R.id.change_pass_submit_btn)
    Button mSubmitBtn;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private AccountDetailHolder mAccountDetailHolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_change_pass,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar!=null)
            actionBar.setTitle("  Change Password");
        init();
    }

    private void init() {
        initVariables();
    }

    private void initVariables() {
        mContext=getContext();
        mAccountDetailHolder=new AccountDetailHolder(mContext);
    }

    @OnClick({R.id.change_pass_submit_btn})
    public void onClickSubmitBtn() {
        if(isInputValid()) {
            String userId=mAccountDetailHolder.getUserDetail().getUserId();
            String oldPass=mOldPassEt.getText().toString().trim();
            String newPass=mNewPassEt.getText().toString().trim();
            mProgressDialog= ShowDialog.show(mContext,"","Please Wait",true,false);
            ChangePasswordApi changePasswordApi=new ChangePasswordApi(mContext,this,mProgressDialog);
            changePasswordApi.callForgotPassResetPassApi(userId,oldPass,newPass);
        }
    }

    private boolean isInputValid() {
        if(InputValidation.validatePassword(mOldPassEt)&&InputValidation.validatePassword(mNewPassEt)&&InputValidation.validatePassword(mConfirmPassEt)) {
            String newPass=mNewPassEt.getText().toString().trim();
            String confirmPass=mConfirmPassEt.getText().toString().trim();
            if(newPass.equalsIgnoreCase(confirmPass)) {
                return true;
            } else {
                AppToast.showToast(mContext,"New and confirm password must be same.");
            }
        }
        return false;
    }

    @Override
    public void changePassResponse(boolean isChanged, String message) {
        if(isChanged) {
            /*Fragment fragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
            fragmentTransaction.commit();*/
             Intent intent = new Intent(getActivity(), LoginSignUpActivity.class);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
             startActivity(intent);
        }
    }
}