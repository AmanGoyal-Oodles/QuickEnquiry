package com.android.quickenquiry.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.quickenquiry.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 3/5/2018.
 */

public class SendSMSFragment extends Fragment {


    @BindView(R.id.send_sms_contact_type_spinner)
    Spinner mContactTypeSpinner;
    private ArrayAdapter<String> mContactTypeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_send_sms,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar!=null)
            actionBar.setTitle("Send SMS");
        init();
    }

    private void init() {
        initVariables();
        setSpinnerAdapter();
    }

    private void setSpinnerAdapter() {
        mContactTypeAdapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mContactTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mContactTypeAdapter.clear();
        mContactTypeAdapter.addAll(getResources().getStringArray(R.array.CATEGORY));
        mContactTypeSpinner.setAdapter(mContactTypeAdapter);
    }

    private void initVariables() {
    }
}
