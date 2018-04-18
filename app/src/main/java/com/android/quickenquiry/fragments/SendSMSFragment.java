package com.android.quickenquiry.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.quickenquiry.R;
import com.android.quickenquiry.adapters.SendSMSMultipleContactAdapter;
import com.android.quickenquiry.adapters.SendSMSSingleContactAdapter;
import com.android.quickenquiry.dialoges.SelectContactDialog;
import com.android.quickenquiry.interfaces.GetSelectedContactListener;
import com.android.quickenquiry.interfaces.GetSelectedContactTypeListener;
import com.android.quickenquiry.interfaces.GetSingleContactSelectionListener;
import com.android.quickenquiry.interfaces.apiResponseListener.GetContactTypeResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.GetContactsResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.GetSMSApiResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.SendSMSApiResponseListener;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.GetContactTypeApi;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.GetContactsApi;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.GetSMSApi;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.SendSMSApi;
import com.android.quickenquiry.utils.apiResponseBean.GetSMSApiResponse;
import com.android.quickenquiry.utils.apiResponseBean.SendSMSApiResponse;
import com.android.quickenquiry.utils.util.AppToast;
import com.android.quickenquiry.utils.util.Logger;
import com.android.quickenquiry.utils.util.dialogs.ShowDialog;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;
import com.android.quickenquiry.utils.util.pojoClasses.ContactType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/5/2018.
 */

public class SendSMSFragment extends Fragment implements GetSMSApiResponseListener,SendSMSApiResponseListener
        ,GetSelectedContactListener,GetSelectedContactTypeListener{


    private static final String CURRENT_TAG =SendSMSFragment.class.getName() ;
    @BindView(R.id.send_sms_multiple_radio_btn)
    RadioButton mMultipleRadiobtn;
    @BindView(R.id.send_sms_single_radio_btn)
    RadioButton mSingleRadioButton;
    @BindView(R.id.send_sms_package_val_tv)
    TextView mPackageTv;
    @BindView(R.id.send_sms_active_date_val_tv)
    TextView mActiveDateTv;
    @BindView(R.id.send_sms_expiry_date_val_tv)
    TextView mExpiryDateTv;
    @BindView(R.id.send_sms_left_val_tv)
    TextView mSMSLeftTv;
    @BindView(R.id.send_sms_message_content_et)
    EditText mMessageEt;
    @BindView(R.id.send_sms_select_contact_btn)
    TextView mSelectContactBtn;
    private ArrayList<ContactDetail> mContactList;
    private ArrayList<ContactType> mContactTypeList;
    private Context mContext;
    private Activity mActivity;
    private AccountDetailHolder mAccountDetailHolder;
    private ProgressDialog mProgressDialog;
    private String contacts="";
    private String contactType="contactType";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_sms, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("  Send SMS");
        init();
    }

    private void init() {
        initVariables();
        callGetSMSApi();
    }

    private void callGetSMSApi() {
        String userId=mAccountDetailHolder.getUserDetail().getUserId();
        mProgressDialog= ShowDialog.show(mContext,"","Please Wait",true,false);
        GetSMSApi getSMSApi=new GetSMSApi(mContext,this,mProgressDialog);
        getSMSApi.callGetSMSApi(userId);
    }

    private void initVariables() {
        mContext = getContext();
        mActivity = getActivity();
        mContactList = new ArrayList<>();
        mContactTypeList=new ArrayList<>();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
    }

    @OnClick({R.id.send_sms_multiple_radio_btn})
    public void onClickMultipleRadioBtn() {
        if (mMultipleRadiobtn.isChecked()) {
            contactType="contactType";
            mSelectContactBtn.setText("Select Contact Type");
        } else {
            contactType="contact_number";
            mSelectContactBtn.setText("Select Contacts");
        }
    }

    @OnClick({R.id.send_sms_single_radio_btn})
    public void onClickSingleRadioBtn() {
        if (mSingleRadioButton.isChecked()) {
            contactType="contact_number";
            mSelectContactBtn.setText("Select Contacts");
        } else {
            contactType="contactType";
            mSelectContactBtn.setText("Select Contact Type");
        }
    }

    @OnClick({R.id.send_sms_send_btn})
    public void onClickSendBtn() {
        String userId=mAccountDetailHolder.getUserDetail().getUserId();
        String message=mMessageEt.getText().toString().trim()+"";
        if(!contacts.isEmpty()) {
            if(!message.isEmpty()) {
                mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
                SendSMSApi sendSMSApi = new SendSMSApi(mContext, this, mProgressDialog);
                sendSMSApi.callInviteFriendApi(userId, contacts, contactType, message);
            } else {
                AppToast.showToast(mContext,"Please Type Message data.");
            }
        } else {
            AppToast.showToast(mContext,"Please Select Contacts");
        }
    }

    @OnClick({R.id.send_sms_select_contact_btn})
    public void onClickSelectContact() {
        SelectContactDialog selectContactDialog=new SelectContactDialog(mContext,contactType,this,this,mContactList,mContactTypeList);
        selectContactDialog.show();
    }

    @Override
    public void getSendSMSApiResponse(boolean isSent, GetSMSApiResponse getSMSApiResponse) {
        if(isSent) {
            updateView(getSMSApiResponse);
        }
    }

    private void updateView(GetSMSApiResponse getSMSApiResponse) {
        mPackageTv.setText(getSMSApiResponse.getPackage_name());
        mActiveDateTv.setText(getSMSApiResponse.getCrm_active_date());
        mExpiryDateTv.setText(getSMSApiResponse.getCrm_expiry_date());
        mSMSLeftTv.setText(getSMSApiResponse.getSms_balance());
    }

    @Override
    public void getSendSMSApiResponse(boolean isSent, SendSMSApiResponse sendSMSApiResponse) {
     if(isSent) {
         /*Fragment fragment = new HomeFragment();
         FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
         fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
         fragmentTransaction.commit();*/
     }
    }

    @Override
    public void getSelectedContacct(ArrayList<ContactDetail> contactList) {
    contacts="";
    mContactList.clear();
    mContactList.addAll(contactList);
    for (int i=0;i<contactList.size();i++) {
         contacts=contacts+contactList.get(i).getmPhone()+",";
     }
     if(!contacts.isEmpty()) {
         StringBuilder sb = new StringBuilder(contacts);
         sb.deleteCharAt(contacts.length()-1);
         contacts=sb.toString();
     }
     contactType="contact_number";
    }

    @Override
    public void getSelectedContactType(ArrayList<ContactType> contactTypeList) {
        contacts="";
        mContactTypeList.clear();
        mContactTypeList.addAll(contactTypeList);
        for (int i=0;i<contactTypeList.size();i++) {
            contacts=contacts+contactTypeList.get(i).getContactTypeId()+",";
        }
        if(!contacts.isEmpty()) {
            StringBuilder sb = new StringBuilder(contacts);
            sb.deleteCharAt(contacts.length()-1);
            contacts=sb.toString();
        }
        contactType="contactType";
    }

}