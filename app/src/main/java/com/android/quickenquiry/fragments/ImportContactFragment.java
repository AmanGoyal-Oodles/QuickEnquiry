package com.android.quickenquiry.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.android.quickenquiry.utils.util.AppToast;
import com.android.quickenquiry.utils.util.CheckPermission;
import com.android.quickenquiry.utils.util.ContactListComparator;
import com.android.quickenquiry.utils.util.Logger;
import com.android.quickenquiry.utils.util.dialogs.ShowDialog;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;
import com.android.quickenquiry.utils.util.pojoClasses.ImportContactDetail;
import com.android.quickenquiry.utils.util.pojoClasses.ImportContactListBean;
import com.android.quickenquiry.utils.util.pojoClasses.ImportContactRequestBean;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;

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
       //setContactList();
       //callImportContactApi();
    }

    private void getContacts() {
        cursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);
        String name,phone;
        while (cursor!=null&&cursor.moveToNext()&&cursor!=null) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            phone=phone.trim();
            mContactList.add(new ImportContactDetail(name,phone));
        }
        //AppToast.showToast(mContext,"All contacts imported successfully");
        //storeContactsInLocalStorage();
        callImportContactApi();
        cursor.close();
    }

    /*private void storeContactsInLocalStorage() {
        ArrayList<ImportContactDetail> list=mAccountDetailHolder.getContactList();
        boolean flag;
        for(int i=0;i<mContactList.size();i++) {
            flag=false;
            for(int j=0;j<list.size();j++) {
                if(list.get(j).getmPhone().equals(mContactList.get(i).getmPhone()))  {
                    flag=true;
                    break;
                }
            }
            if(!flag) {
                list.add(mContactList.get(i));
            }
        }
        Collections.sort(list,new ContactListComparator());
        mAccountDetailHolder.setContactList(list);
        mContactList.clear();
        mContactList.addAll(list);
        callImportContactApi();
    }*/

    private void setContactList() {
        mContactList.clear();
        mContactList.add(new ImportContactDetail("Aman","9355606425"));
        mContactList.add(new ImportContactDetail("Ajay","9355606424"));
        mContactList.add(new ImportContactDetail("Amar","9355606423"));
        mContactList.add(new ImportContactDetail("Akhil","9355606422"));
        mContactList.add(new ImportContactDetail("Rahul","9355606421"));
        mContactList.add(new ImportContactDetail("Abhinav","9355606420"));
        mContactList.add(new ImportContactDetail("Sahil","9355606435"));
        mContactList.add(new ImportContactDetail("Ravi","9355606445"));
        mContactList.add(new ImportContactDetail("Puneet","9355606455"));
        mContactList.add(new ImportContactDetail("Rishabh","9355606465"));
        mContactList.add(new ImportContactDetail("Anmol","9355606475"));
        mContactList.add(new ImportContactDetail("Ankit","9355606485"));
        mContactList.add(new ImportContactDetail("Ajit","9355606495"));
        mContactList.add(new ImportContactDetail("Akash","9355606405"));
    }

    private void callImportContactApi() {
        String userId=mAccountDetailHolder.getUserDetail().getUserId();
        Gson gson=new Gson();
        ImportContactRequestBean importContactRequestBean=new ImportContactRequestBean();
        importContactRequestBean.setUserid(userId);
        importContactRequestBean.setContactList(mContactList);
        String jsonDetails=gson.toJson(mContactList);
        Logger.LogDebug(CURRENT_TAG,jsonDetails);
        mProgressDialog= ShowDialog.show(mContext,"","Please Wait",true,false);
        ImportContactApi importContactApi=new ImportContactApi(mContext,this,mProgressDialog);
        importContactApi.callImportContact(importContactRequestBean.getUserid(),jsonDetails);
    }

    @Override
    public void getImportContactResponse(boolean isImported, String message) {
        if(isImported) {
            Fragment fragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
            fragmentTransaction.commit();
        }
    }
}
