package com.android.quickenquiry.dialoges;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.ConfirmDeleteListener;
import com.android.quickenquiry.interfaces.SignOutResponseListener;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by user on 3/5/2018.
 */

public class ConfirmDeleteDialog extends Dialog {

    @BindView(R.id.signout_yes_tv)
    TextView mYesTv;
    @BindView(R.id.signout_no_tv)
    TextView mNoTv;
    @BindView(R.id.signout_confirm_text_tv)
    TextView mConfirmTextTv;
    @BindView(R.id.signout_text_tv)
    TextView mDeleteTv;
    private Context mContext;
    private ConfirmDeleteListener mConfirmDeleteListener;
    private AccountDetailHolder mAccountDetailHolder;
    private String mContactId="";

    public ConfirmDeleteDialog(@NonNull Context context, ConfirmDeleteListener listener,String contactid) {
        super(context);
        mContext=context;
        mAccountDetailHolder=new AccountDetailHolder(mContext);
        mConfirmDeleteListener=listener;
        mContactId=contactid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_signout);
        ButterKnife.bind(this);
        setCancelable(false);
        setViews();
    }

    private void setViews() {
        mConfirmTextTv.setText("Confirm Delete");
        mDeleteTv.setText("Are you sure want to delete this contact?");
    }

    @OnClick({R.id.signout_yes_tv})
    public void onClickYesTv() {
        mConfirmDeleteListener.isConfirmDelete(mContactId,true);
        dismiss();
    }

    @OnClick({R.id.signout_no_tv})
    public void onClickNoTv() {
        mConfirmDeleteListener.isConfirmDelete(mContactId,false);
        dismiss();
    }

}