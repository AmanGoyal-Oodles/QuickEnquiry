package com.android.quickenquiry.fragments;

/**
 * Created by user on 3/2/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.quickenquiry.R;
import com.android.quickenquiry.activities.MainDashboardActivity;

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
    @BindView(R.id.register_layout_corporate)
    LinearLayout mCorporateLayout;
    @BindView(R.id.register_category_spinner)
    Spinner mCategorySpinner;
    private static final String CURRENT_TAG=RegisterFragment.class.getName();
    private ArrayAdapter<String> mCategoryAdapter;

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
        setSpinnerAdapter();
    }

    private void setSpinnerAdapter() {
        mCategoryAdapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategoryAdapter.clear();
        mCategoryAdapter.addAll(getResources().getStringArray(R.array.CATEGORY));
        mCategorySpinner.setAdapter(mCategoryAdapter);
    }

    private void initVariables() {
    }

    @OnClick({R.id.register_login_text_tv})
    public void onClickLoginTv() {
        openLoginFragment();
    }

    @OnClick({R.id.register_btn})
    public void onClickRegisterBtn() {
        Intent intent=new Intent(getActivity(), MainDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @OnClick({R.id.register_corporate_radio_btn})
    public void onChangeCorporateRadioBtn() {
        if(mCorporateRadioBtn.isChecked()) {
            mCorporateLayout.setVisibility(View.VISIBLE);
        } else {
            mCorporateLayout.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.register_individual_radio_btn})
    public void onChangeIndividualRadioBtn() {
        if(mIndividualRadioBtn.isChecked()) {
            mCorporateLayout.setVisibility(View.GONE);
        } else {
            mCorporateLayout.setVisibility(View.VISIBLE);
        }
    }

    private void openLoginFragment() {
        Fragment fragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.login_signUp_frame, fragment, CURRENT_TAG);
        fragmentTransaction.commit();
    }


}
