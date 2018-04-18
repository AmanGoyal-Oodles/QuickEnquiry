package com.android.quickenquiry.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.apiResponseListener.ImportContactResponseListener;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.ImportContactApi;
import com.android.quickenquiry.utils.util.CheckPermission;
import com.android.quickenquiry.utils.util.Logger;
import com.android.quickenquiry.utils.util.dialogs.ShowDialog;
import com.android.quickenquiry.utils.util.pojoClasses.ImportContactDetail;
import com.android.quickenquiry.utils.util.pojoClasses.ImportContactRequestBean;
import com.google.gson.Gson;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/6/2018.
 */

public class ImportContactFragment extends Fragment implements ImportContactResponseListener{


    private static final String CURRENT_TAG =ImportContactFragment.class.getName() ;
    @BindView(R.id.import_contact_btn)
    Button mImportBtn;
    private Context mContext;
    private Activity mActivity;
    private Cursor cursor ;
    private ArrayList<ImportContactDetail> mContactList ;
    private AccountDetailHolder mAccountDetailHolder;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_import_contact,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar!=null)
            actionBar.setTitle("  Import Contact");
        init();
    }

    private void init() {
        initVariables();
    }

    private void initVariables() {
        mContext=getContext();
        mActivity=getActivity();
        mContactList=new ArrayList<>();
        mAccountDetailHolder=new AccountDetailHolder(mContext);
    }

    @OnClick({R.id.import_contact_btn})
    public void onClickImportBtn() {
        if(CheckPermission.checkPermissionForReadContact(mActivity)) {
            getContacts();
        }
    }

    private void getContacts() {
        mProgressDialog= ShowDialog.show(mContext,"","Please Wait",true,false);
        cursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);
        String name,phone;
        while (cursor!=null&&cursor.moveToNext()&&cursor!=null) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            phone=phone.trim();
            mContactList.add(new ImportContactDetail(name,phone));
        }
        callImportContactApi();
        cursor.close();
    }

    private void callImportContactApi() {
        String userId=mAccountDetailHolder.getUserDetail().getUserId();
        Gson gson=new Gson();
        ImportContactRequestBean importContactRequestBean=new ImportContactRequestBean();
        importContactRequestBean.setUserid(userId);
        importContactRequestBean.setContactList(mContactList);
        String jsonDetails=gson.toJson(mContactList);
        Logger.LogDebug(CURRENT_TAG,jsonDetails);
        ImportContactApi importContactApi=new ImportContactApi(mContext,this,mProgressDialog);
        importContactApi.callImportContact(importContactRequestBean.getUserid(),jsonDetails);
    }

    @Override
    public void getImportContactResponse(boolean isImported, String message) {
        if(isImported) {
            /*Intent intent = new Intent(mContext, MainDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);*/
        }
    }

}