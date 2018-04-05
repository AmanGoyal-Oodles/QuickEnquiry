package com.android.quickenquiry.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.android.quickenquiry.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/8/2018.
 */

public class InviteMessageFragment extends Fragment {


    @BindView(R.id.invite_message_et)
    EditText mMessageEt;
    @BindView(R.id.invite_message_cancel_btn)
    Button mCancelBtn;
    @BindView(R.id.invite_message_send_btn)
    Button mSendBtn;
    private Context mContext;
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_invite_message,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        setActionBarTitle();
        init();
    }

    private void setActionBarTitle() {
        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setTitle("  Invite Message");
        }
    }

    private void init() {
        initVariables();
    }

    private void initVariables() {
        mContext=getContext();
        mActivity=getActivity();
    }

    @OnClick({R.id.invite_message_cancel_btn})
    public void onClickCancelBtn() {

    }

    @OnClick({R.id.invite_message_send_btn})
    public void onClickSendBtn() {

    }

}