package com.android.quickenquiry.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.apiResponseListener.SendQueryResponseListener;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.SendQueryApi;
import com.android.quickenquiry.utils.util.AppToast;
import com.android.quickenquiry.utils.util.dialogs.ShowDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Cortana on 4/18/2018.
 */

public class SendQueryFragment extends Fragment implements SendQueryResponseListener{


    @BindView(R.id.send_query_desc_et)
    EditText mDescEt;
    @BindView(R.id.send_query_submit_btn)
    Button mSubmitBtn;
    private Context mContext;
    private Activity mActivity;
    private AccountDetailHolder mAccountDetailHolder;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_send_query,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("  Send Query");
        init();
    }

    private void init() {
        initVariables();
    }

    private void initVariables() {
        mContext=getContext();
        mActivity=getActivity();
        mAccountDetailHolder=new AccountDetailHolder(mContext);
    }

    @OnClick({R.id.send_query_submit_btn})
    public void onClickSubmitBtn() {
        String desc=mDescEt.getText().toString().trim();
        String userId=mAccountDetailHolder.getUserDetail().getUserId();
        String ipAddress=getIPAddress();
        if(!desc.isEmpty()) {
            mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
            SendQueryApi sendQueryApi = new SendQueryApi(mContext, this, mProgressDialog);
            sendQueryApi.callSendQueryApi(userId,ipAddress,desc);
        } else {
            AppToast.showToast(mContext,"Please enter Description.");
        }
    }

    private String getIPAddress() {
        WifiManager wm = (WifiManager) mContext.getApplicationContext().getSystemService(WIFI_SERVICE);
        return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    }

    @Override
    public void getSendQueryResponse(boolean isSubmit, String message) {
        if(isSubmit) {
            return;
        }
    }
}
