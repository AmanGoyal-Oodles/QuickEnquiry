package com.android.quickenquiry.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.android.quickenquiry.R;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.utils.constants.AppConstantKeys;
import com.android.quickenquiry.utils.util.AppToast;
import com.android.quickenquiry.utils.util.CheckPermission;
import com.android.quickenquiry.utils.util.ContactListComparator;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImportContactActivity extends AppCompatActivity {


    @BindView(R.id.import_contact_btn)
    Button mImportContactBtn;
    private Cursor cursor ;
    private ArrayList<ContactDetail> mContactList ;
    private Context mContext;
    private AccountDetailHolder mAccountDetailHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_contact);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initVariables();

    }

    private void initVariables() {
        mContext=getApplicationContext();
        mContactList=new ArrayList<>();
        mAccountDetailHolder=new AccountDetailHolder(mContext);
    }

    @OnClick({R.id.import_contact_btn})
    public void onClickImportContactButton() {
        if(CheckPermission.checkPermissionForReadContact(this)) {
            getContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AppConstantKeys.READ_CONTACT_PERMISSION: if(grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                                                                getContacts();
                                                            } else {
                                                                AppToast.showToast(mContext,"You can not import Contacts");
                                                            }
                                                            return;

        }
    }

    private void getContacts() {
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);
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