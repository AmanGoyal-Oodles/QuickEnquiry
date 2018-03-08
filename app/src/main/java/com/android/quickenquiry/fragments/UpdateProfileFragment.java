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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import com.android.quickenquiry.R;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 3/7/2018.
 */

public class UpdateProfileFragment extends Fragment {


    @BindView(R.id.update_profile_name_et)
    EditText mNameEt;
    @BindView(R.id.update_profile_email_et)
    EditText mEmailEt;
    @BindView(R.id.update_profile_pri_con_et)
    EditText mPriConEt;
    @BindView(R.id.update_profile_sec_con_et)
    EditText mSecConEt;
    @BindView(R.id.update_profile_city_spinner)
    Spinner mCitySpinner;
    @BindView(R.id.update_profile_locality_spinner)
    Spinner mLocalitySpinner;
    @BindView(R.id.update_profile_address_et)
    EditText mAddressEt;
    @BindView(R.id.update_profile_btn)
    Button mUpdateInfoBtn;
    private ArrayAdapter<String> mCityAdapter,mLocalityAdapter;
    private ArrayList<String> mCityList,mLocalityList;
    private Context mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_update_profile,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar!=null)
            actionBar.setTitle("Update Profile");
        init();
    }

    private void init() {
        initVariables();
        setSpinnerAdapter();
    }

    private void setSpinnerAdapter() {
        mCityAdapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCityAdapter.clear();
        mCityAdapter.addAll(getResources().getStringArray(R.array.CITY));
        mCitySpinner.setAdapter(mCityAdapter);

        mLocalityAdapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mLocalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLocalityAdapter.clear();
        mLocalityAdapter.addAll(getResources().getStringArray(R.array.LOCALITY));
        mLocalitySpinner.setAdapter(mLocalityAdapter);
    }

    private void initVariables() {
        mContext=getContext();
    }

}
