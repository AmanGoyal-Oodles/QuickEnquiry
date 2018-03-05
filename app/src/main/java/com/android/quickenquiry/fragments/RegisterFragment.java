package com.android.quickenquiry.fragments;

/**
 * Created by user on 3/2/2018.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.quickenquiry.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class RegisterFragment extends Fragment {


    @BindView(R.id.register_login_text_tv)
    TextView mLoginTv;
    @BindView(R.id.register_corporate_radio_btn)
    RadioButton mCorporateRadioBtn;
    @BindView(R.id.register_individual_radio_btn)
    RadioButton mIndividualRadioBtn;
    @BindView(R.id.register_company_name_tv)
    TextView mCompanyNameTv;
    @BindView(R.id.register_company_name_et)
    EditText mCompanyNameEt;
    private static final String CURRENT_TAG=RegisterFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_register,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        init();
    }

    private void init() {
        initVariables();
    }

    private void initVariables() {
    }

    @OnClick({R.id.register_login_text_tv})
    public void onClickLoginTv() {
        openLoginFragment();
    }

    @OnClick({R.id.register_corporate_radio_btn})
    public void onChangeCorporateRadioBtn() {
        if(mCorporateRadioBtn.isChecked()) {
            mCompanyNameTv.setVisibility(View.VISIBLE);
            mCompanyNameEt.setVisibility(View.VISIBLE);
        } else {
            mCompanyNameTv.setVisibility(View.GONE);
            mCompanyNameEt.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.register_individual_radio_btn})
    public void onChangeIndividualRadioBtn() {
        if(mIndividualRadioBtn.isChecked()) {
            mCompanyNameTv.setVisibility(View.GONE);
            mCompanyNameEt.setVisibility(View.GONE);
        } else {
            mCompanyNameTv.setVisibility(View.VISIBLE);
            mCompanyNameEt.setVisibility(View.VISIBLE);
        }
    }

    private void openLoginFragment() {
        Fragment fragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.login_signUp_frame, fragment, CURRENT_TAG);
        fragmentTransaction.commit();
    }


}
