package com.android.quickenquiry.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.quickenquiry.R;
import com.android.quickenquiry.dialoges.LogOutDialog;
import com.android.quickenquiry.fragments.AddContactFragment;
import com.android.quickenquiry.fragments.ChangePasswordFragment;
import com.android.quickenquiry.fragments.ContactFragment;
import com.android.quickenquiry.fragments.HomeFragment;
import com.android.quickenquiry.fragments.ImportContactFragment;
import com.android.quickenquiry.fragments.InviteFriendFragment;
import com.android.quickenquiry.fragments.SendQueryFragment;
import com.android.quickenquiry.fragments.SendSMSFragment;
import com.android.quickenquiry.fragments.UpdateProfileFragment;
import com.android.quickenquiry.interfaces.SignOutResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.ImportContactResponseListener;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.ImportContactApi;
import com.android.quickenquiry.utils.constants.AppConstantKeys;
import com.android.quickenquiry.utils.util.AppToast;
import com.android.quickenquiry.utils.util.ContactListComparator;
import com.android.quickenquiry.utils.util.Logger;
import com.android.quickenquiry.utils.util.dialogs.ShowDialog;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;
import com.android.quickenquiry.utils.util.pojoClasses.ImportContactDetail;
import com.android.quickenquiry.utils.util.pojoClasses.ImportContactRequestBean;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainDashboardActivity extends AppCompatActivity implements SignOutResponseListener, ImportContactResponseListener {


    private static final String CURRENT_TAG = MainDashboardActivity.class.getName();
    @BindView(R.id.main_nav_drawer_layout)
    DrawerLayout mNavDrawerLayout;
    @BindView(R.id.nav_toolbar)
    Toolbar mToolbar;
    //@BindView(R.id.nav_email_tv)
    TextView mPhoneTv;
    //@BindView(R.id.nav_displayname_tv)
    TextView mNameTv;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar mActionBar;
    NavigationView mNavView;
    private int NAV_ITEM_INDEX = 0;
    private Context mContext;
    private Cursor cursor;
    private ArrayList<ImportContactDetail> mContactList;
    private AccountDetailHolder mAccountDetailHolder;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        ButterKnife.bind(this);
        mContext = getApplicationContext();
        Fabric.with(mContext, new Crashlytics());
        init();
    }

    private void init() {
        setToolBar();
        setActionBarDrawer();
        initViews();
        initVariables();
        handleNavigationSelectedItem();
        Fragment fragment = new HomeFragment();
        openFragment(fragment);
    }

    private void initViews() {
        mNavView = (NavigationView) findViewById(R.id.nav_view);
        View header = mNavView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        mPhoneTv = (TextView) header.findViewById(R.id.nav_email_tv);
        mNameTv = (TextView) header.findViewById(R.id.nav_displayname_tv);
    }

    private void setToolBar() {
        mToolbar.setTitleTextColor(getResources().getColor(R.color.Black));
        setSupportActionBar(mToolbar);
    }

    private void setActionBarDrawer() {
        setActionBarDrawerToggle();
        mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.White)));
    }

    private void setActionBarDrawerToggle() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mNavDrawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mNavDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void initVariables() {
        mContext = getApplicationContext();
        mContactList = new ArrayList<>();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        mPhoneTv.setText(mAccountDetailHolder.getUserDetail().getUserMob());
        mNameTv.setText(mAccountDetailHolder.getUserDetail().getUserName());
    }

    private void handleNavigationSelectedItem() {
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        NAV_ITEM_INDEX = 0;
                        fragment = new HomeFragment();
                        openFragment(fragment);
                        break;
                    case R.id.nav_update_profile:
                        NAV_ITEM_INDEX = 1;
                        fragment = new UpdateProfileFragment();
                        openFragment(fragment);
                        break;
                    case R.id.nav_change_pass:
                        NAV_ITEM_INDEX = 2;
                        fragment = new ChangePasswordFragment();
                        openFragment(fragment);
                        break;
                    case R.id.nav_invite_friends:
                        NAV_ITEM_INDEX = 3;
                        fragment = new InviteFriendFragment();
                        openFragment(fragment);
                        break;
                    case R.id.nav_import_contact:
                        NAV_ITEM_INDEX = 4;
                        fragment = new ImportContactFragment();
                        openFragment(fragment);
                        break;
                    case R.id.add_contact:
                        NAV_ITEM_INDEX = 5;
                        Bundle bundle = new Bundle();
                        bundle.putString("tag", "homeFragment");
                        fragment = new AddContactFragment();
                        fragment.setArguments(bundle);
                        openFragment(fragment);
                        /*Intent intent=new Intent(MainDashboardActivity.this,AddContactActivity.class);
                        startActivity(intent);*/
                        break;
                    case R.id.nav_contact_list:
                        NAV_ITEM_INDEX = 6;
                        fragment = new ContactFragment();
                        openFragment(fragment);
                        break;
                    case R.id.nav_send_sms:
                        NAV_ITEM_INDEX = 7;
                        fragment = new SendSMSFragment();
                        openFragment(fragment);
                        break;
                    case R.id.nav_send_query:
                        NAV_ITEM_INDEX = 8;
                        fragment = new SendQueryFragment();
                        openFragment(fragment);
                        break;
                    case R.id.nav_logout:
                        NAV_ITEM_INDEX = 9;
                        openSignOutDialog();
                        break;
                }
                mNavDrawerLayout.closeDrawers();
                return false;
            }
        });
    }

    private void openSignOutDialog() {
        LogOutDialog dialog = new LogOutDialog(this, this);
        dialog.show();
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    @Override
    public void signoutResponse(boolean status) {
        if (status) {
            Intent intent = new Intent(this, LoginSignUpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (NAV_ITEM_INDEX == 0) {
            finish();
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 0 && NAV_ITEM_INDEX != 0) {
            NAV_ITEM_INDEX = 0;
            getSupportActionBar().setTitle("Home");
            openFragment(new HomeFragment());
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AppConstantKeys.READ_CONTACT_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts();
                } else {
                    AppToast.showToast(mContext, "You can not import Contacts");
                }
                break;
            case AppConstantKeys.CALL_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callNumber();
                } else {
                    AppToast.showToast(mContext, "You don't have permission to call.");
                }
                break;

        }
    }

    private void callNumber() {
        String phoneNumber = mAccountDetailHolder.getCallNumber();
        if (!phoneNumber.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mAccountDetailHolder.setCallNumber("");
            startActivity(intent);
        }

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
        AppToast.showToast(mContext,"All contacts imported successfully");
        //storeContactsInLocalStorage();
        cursor.close();
        callImportContactApi();
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
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
            fragmentTransaction.commit();
        }
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
    }*/


}
