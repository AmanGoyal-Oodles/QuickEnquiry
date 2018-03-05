package com.android.quickenquiry.dialoges;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.SignOutResponseListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by user on 3/5/2018.
 */

public class LogOutDialog extends Dialog {

    @BindView(R.id.signout_yes_tv)
    TextView mYesTv;
    @BindView(R.id.signout_no_tv)
    TextView mNoTv;
    private Context mContext;
    private SignOutResponseListener mSignOutResponseListener;

    public LogOutDialog(@NonNull Context context,SignOutResponseListener signOutResponseListener) {
        super(context);
        mContext=context;
        mSignOutResponseListener=signOutResponseListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_signout);
        ButterKnife.bind(this);
        setCancelable(false);
    }

    @OnClick({R.id.signout_yes_tv})
    public void onClickYesTv() {
        mSignOutResponseListener.signoutResponse(true);
        dismiss();
    }

    @OnClick({R.id.signout_no_tv})
    public void onClickNoTv() {
        mSignOutResponseListener.signoutResponse(false);
        dismiss();
    }

    @OnTouch({R.id.signout_yes_tv})
    public boolean onTouchYesTv() {
        mYesTv.setBackgroundColor(mContext.getResources().getColor(R.color.LightGray));
        mSignOutResponseListener.signoutResponse(true);
        dismiss();
        return true;
    }

    @OnTouch({R.id.signout_no_tv})
    public boolean onTouchNoTv() {
        mNoTv.setBackgroundColor(mContext.getResources().getColor(R.color.LightGray));
        mSignOutResponseListener.signoutResponse(false);
        dismiss();
        return true;
    }
}
