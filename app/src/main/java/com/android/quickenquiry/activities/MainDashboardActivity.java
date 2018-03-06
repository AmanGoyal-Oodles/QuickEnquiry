package com.android.quickenquiry.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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
import com.android.quickenquiry.R;
import com.android.quickenquiry.dialoges.LogOutDialog;
import com.android.quickenquiry.fragments.AddContactFragment;
import com.android.quickenquiry.fragments.ChangePasswordFragment;
import com.android.quickenquiry.fragments.HomeFragment;
import com.android.quickenquiry.fragments.ImportContactFragment;
import com.android.quickenquiry.fragments.SendSMSFragment;
import com.android.quickenquiry.interfaces.SignOutResponseListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainDashboardActivity extends AppCompatActivity implements SignOutResponseListener{


    @BindView(R.id.main_nav_drawer_layout)
    DrawerLayout mNavDrawerLayout;
    @BindView(R.id.nav_toolbar)
    Toolbar mToolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar mActionBar;
    NavigationView mNavView;
    private int NAV_ITEM_INDEX=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setToolBar();
        setActionBarDrawer();
        initViews();
        initVariables();
        handleNavigationSelectedItem();
        Fragment fragment=new HomeFragment();
        openFragment(fragment);
    }

    private void initViews() {
        mNavView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void setToolBar() {
        mToolbar.setTitleTextColor(getResources().getColor(R.color.Black));
        setSupportActionBar(mToolbar);
    }

    private void setActionBarDrawer() {
        setActionBarDrawerToggle();
        mActionBar=getSupportActionBar();
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

    }

    private void handleNavigationSelectedItem() {
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        NAV_ITEM_INDEX = 0;
                        fragment=new HomeFragment();
                        openFragment(fragment);
                        break;
                    case R.id.nav_update_profile:
                        NAV_ITEM_INDEX = 1;
                        break;
                    case R.id.nav_change_pass:
                        NAV_ITEM_INDEX = 2;
                        fragment=new ChangePasswordFragment();
                        openFragment(fragment);
                        break;
                    case R.id.nav_invite_friends:
                        NAV_ITEM_INDEX = 3;
                        break;
                    case R.id.nav_import_contact:
                        NAV_ITEM_INDEX = 4;
                        fragment=new ImportContactFragment();
                        openFragment(fragment);
                        break;
                    case R.id.add_contact:
                        NAV_ITEM_INDEX = 5;
                        fragment=new AddContactFragment();
                        openFragment(fragment);
                        /*Intent intent=new Intent(MainDashboardActivity.this,AddContactActivity.class);
                        startActivity(intent);*/
                        break;
                    case R.id.nav_contact_list:
                        NAV_ITEM_INDEX = 6;
                        break;
                    case R.id.nav_send_sms:
                        NAV_ITEM_INDEX = 7;
                        fragment=new SendSMSFragment();
                        openFragment(fragment);
                        break;
                    case R.id.nav_logout:
                        NAV_ITEM_INDEX = 8;
                        openSignOutDialog();
                        break;
                }
                mNavDrawerLayout.closeDrawers();
                return false;
            }
        });
    }

    private void openSignOutDialog() {
        LogOutDialog dialog=new LogOutDialog(this,this);
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
        if(status) {
            Intent intent=new Intent(this, LoginSignUpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if(NAV_ITEM_INDEX==0) {
            finish();
        } else if(getSupportFragmentManager().getBackStackEntryCount()==0&&NAV_ITEM_INDEX!=0) {
            NAV_ITEM_INDEX=0;
            getSupportActionBar().setTitle("Home");
            openFragment(new HomeFragment());
        } else {
            super.onBackPressed();
        }
    }
}
