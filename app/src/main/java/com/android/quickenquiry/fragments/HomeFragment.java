package com.android.quickenquiry.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.quickenquiry.R;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 3/5/2018.
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.home_fragment_welcome_tv)
    TextView mWelcomeTv;
    private Context mContext;
    private AccountDetailHolder mAccountDetailHolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setTitle("  Home");
            actionBar.setLogo(getResources().getDrawable(R.drawable.ic_dash_icon));
        }
        init();
    }

    private void init() {
        initVariables();
        setViews();
    }

    private void setViews() {
        String name=mAccountDetailHolder.getUserDetail().getUserName();
        mWelcomeTv.setText("Welcome, "+name+"!");
    }

    private void initVariables() {
        mContext=getContext();
        mAccountDetailHolder=new AccountDetailHolder(mContext);
    }
}