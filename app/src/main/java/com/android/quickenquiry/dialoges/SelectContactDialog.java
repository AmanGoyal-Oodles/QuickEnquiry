package com.android.quickenquiry.dialoges;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.android.quickenquiry.R;
import com.android.quickenquiry.adapters.SelectContactAdapter;
import com.android.quickenquiry.adapters.SelectContactTypeAdapter;
import com.android.quickenquiry.interfaces.GetSelectedContactListener;
import com.android.quickenquiry.interfaces.GetSelectedContactTypeListener;
import com.android.quickenquiry.interfaces.apiResponseListener.GetContactTypeResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.GetContactsResponseListener;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.GetContactTypeApi;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.GetContactsApi;
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

public class SelectContactDialog extends Dialog implements GetContactTypeResponseListener,GetContactsResponseListener{


    @BindView(R.id.select_contact_rv)
    RecyclerView mSelectContactRv;
    @BindView(R.id.select_contact_next_btn)
    Button mNextBtn;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private String mContactType;
    private ArrayList<ContactType> mContactTypeList,mSelectedContactTypeList;
    private ArrayList<ContactDetail> mContactList,mSelectedContactList;
    private SelectContactAdapter mSelectContactAdapter;
    private SelectContactTypeAdapter mSelectContactTypeAdapter;
    private GetSelectedContactListener mGetSelectedContactListener;
    private GetSelectedContactTypeListener mGetSelectedContactTypeListener;
    private AccountDetailHolder mAccountDetailHolder;

    public SelectContactDialog(@NonNull Context context,String contactType,GetSelectedContactListener contactListener
            ,GetSelectedContactTypeListener contactTypelistener,ArrayList<ContactDetail> contactList,ArrayList<ContactType> contactTypeList) {
        super(context);
        mContext=context;
        mContactType=contactType;
        mSelectedContactList=new ArrayList<>();
        mSelectedContactList.addAll(contactList);
        mSelectedContactTypeList=new ArrayList<>();
        mSelectedContactTypeList.addAll(contactTypeList);
        mGetSelectedContactListener=contactListener;
        mGetSelectedContactTypeListener=contactTypelistener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_contact);
        ButterKnife.bind(this);
        setCancelable(true);
        Window window=getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        mContactTypeList=new ArrayList<>();
        mContactList=new ArrayList<>();
        mAccountDetailHolder=new AccountDetailHolder(mContext);
        if(mContactType.equalsIgnoreCase("contactType")) {
            setSelectContactTypeRv();
            getContactType();
        } else {
            setSelectContactRv();
            callGetContacts();
        }
    }
    private void getContactType() {
        mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
        GetContactTypeApi getContactTypeApi = new GetContactTypeApi(mContext, this, mProgressDialog);
        getContactTypeApi.callGetContactTypeApi();
    }

    private void callGetContacts() {
        String userid=mAccountDetailHolder.getUserDetail().getUserId();
        mProgressDialog= ShowDialog.show(mContext,"","Please Wait",true,false);
        GetContactsApi getContactsApi=new GetContactsApi(mContext,this,mProgressDialog);
        getContactsApi.callGetContactsApi(userid);
    }

    @OnClick({R.id.select_contact_next_btn})
    public void onClickNextBtn() {
        if(mContactType.equalsIgnoreCase("contactType")) {
            mSelectedContactTypeList.clear();
            mSelectedContactTypeList.addAll(mSelectContactTypeAdapter.getSelectedContactDetail());
            mGetSelectedContactTypeListener.getSelectedContactType(mSelectedContactTypeList);
        } else {
            mSelectedContactList.clear();
            mSelectedContactList.addAll(mSelectContactAdapter.getSelectedContactDetail());
            mGetSelectedContactListener.getSelectedContacct(mSelectedContactList);
        }
        dismiss();
    }

    @Override
    public void getContactTypeResponse(boolean isReceived, ArrayList<ContactType> contactTypeList) {
        if(isReceived) {
            mContactTypeList.clear();
            mContactTypeList.addAll(contactTypeList);
            mSelectContactTypeAdapter.setContactList(mContactTypeList,mSelectedContactTypeList);
        }
    }

    private void setSelectContactTypeRv() {
        mContactTypeList=new ArrayList<>();
        mSelectContactTypeAdapter=new SelectContactTypeAdapter(mContext,mContactTypeList,mSelectedContactTypeList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(mContext);
        mSelectContactRv.setLayoutManager(layoutManager);
        mSelectContactRv.setItemAnimator(new DefaultItemAnimator());
        mSelectContactRv.setAdapter(mSelectContactTypeAdapter);
    }

    @Override
    public void getContectsResponse(boolean isReceived, ArrayList<ContactDetail> contactList) {
        if(isReceived) {
            mContactList.clear();
            mContactList.addAll(contactList);
            mSelectContactAdapter.setContactList(mContactList,mSelectedContactList);
            //setSelectContactRv();
        }
    }

    private void setSelectContactRv() {
        mContactList=new ArrayList<>();
        mSelectContactAdapter=new SelectContactAdapter(mContext,mContactList,mSelectedContactList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(mContext);
        mSelectContactRv.setLayoutManager(layoutManager);
        mSelectContactRv.setItemAnimator(new DefaultItemAnimator());
        mSelectContactRv.setAdapter(mSelectContactAdapter);
    }

}