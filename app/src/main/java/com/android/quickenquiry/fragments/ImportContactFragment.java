package com.android.quickenquiry.fragments;

import android.app.Activity;
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
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.utils.util.AppToast;
import com.android.quickenquiry.utils.util.CheckPermission;
import com.android.quickenquiry.utils.util.ContactListComparator;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/6/2018.
 */

public class ImportContactFragment extends Fragment {


    @BindView(R.id.import_contact_btn)
    Button mImportBtn;
    private Context mContext;
    private Activity mActivity;
    private Cursor cursor ;
    private ArrayList<ContactDetail> mContactList ;
    private AccountDetailHolder mAccountDetailHolder;

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
            actionBar.setTitle("Import Contact");
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
        cursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);
        String name,phone;
        while (cursor!=null&&cursor.moveToNext()&&cursor!=null) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            phone=phone.trim();
            mContactList.add(new ContactDetail(name,phone,"","","","","",""));
        }
        AppToast.showToast(mContext,"All contacts imported successfully");
        storeContactsInLocalStorage();
        cursor.close();
    }

    private void storeContactsInLocalStorage() {
        ArrayList<ContactDetail> list=mAccountDetailHolder.getContactList();
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
    }

}
