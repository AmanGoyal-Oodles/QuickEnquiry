package com.android.quickenquiry.fragments;

/**
 * Created by user on 3/2/2018.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.quickenquiry.R;
import com.android.quickenquiry.activities.MainDashboardActivity;
import com.android.quickenquiry.dialoges.OTPDialog;
import com.android.quickenquiry.interfaces.OTPDialogListener;
import com.android.quickenquiry.interfaces.apiResponseListener.GetCategoryResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.RegisterResponseListener;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.GetCategoryApi;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.RegisterApi;
import com.android.quickenquiry.utils.util.AppToast;
import com.android.quickenquiry.utils.util.InputValidation;
import com.android.quickenquiry.utils.util.dialogs.DismissDialog;
import com.android.quickenquiry.utils.util.dialogs.ShowDialog;
import com.android.quickenquiry.utils.util.pojoClasses.CategoryType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class RegisterFragment extends Fragment implements OTPDialogListener, RegisterResponseListener, GetCategoryResponseListener {


    @BindView(R.id.register_login_text_tv)
    TextView mLoginTv;
    @BindView(R.id.register_corporate_radio_btn)
    RadioButton mCorporateRadioBtn;
    @BindView(R.id.register_individual_radio_btn)
    RadioButton mIndividualRadioBtn;
    @BindView(R.id.register_company_name_et)
    EditText mCompanyNameEt;
    @BindView(R.id.register_name_et)
    EditText mNameEt;
    @BindView(R.id.register_mobile_et)
    EditText mMobileEt;
    @BindView(R.id.register_email_et)
    EditText mEmailEt;
    @BindView(R.id.register_password_et)
    EditText mPasswordEt;
    @BindView(R.id.register_repass_et)
    EditText mRePassEt;
    @BindView(R.id.register_layout_corporate)
    LinearLayout mCorporateLayout;
    @BindView(R.id.register_category_spinner)
    Spinner mCategorySpinner;
    @BindView(R.id.register_agree_cb)
    CheckBox mAgreeCb;
    private static final String CURRENT_TAG = RegisterFragment.class.getName();
    private ArrayAdapter<String> mCategoryAdapter;
    private ArrayList<CategoryType> mCategoryList;
    private Context mContext;
    private Activity mActivity;
    private int userType;
    private String mobile;
    private String selectedCatId = "0";
    private ProgressDialog mProgressDialog;
    private AccountDetailHolder mAccountDetailHolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        initVariables();
        setSpinnerAdapter();
        callGetCategoryApi();
    }

    private void callGetCategoryApi() {
        mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
        GetCategoryApi getCategoryApi = new GetCategoryApi(mContext, this, mProgressDialog);
        getCategoryApi.callGetCatApi();
    }

    private void setSpinnerAdapter() {
        mCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0 && super.isEnabled(position);
            }
        };
        mCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategoryAdapter.clear();
        mCategoryAdapter.add("Busness Category Type");
        ArrayList<String> catList = new ArrayList<>();
        for (int i = 0; i < mCategoryList.size(); i++) {
            catList.add(mCategoryList.get(i).getCatName());
        }
        mCategoryAdapter.addAll(catList);
        mCategorySpinner.setAdapter(mCategoryAdapter);
    }

    private void initVariables() {
        mContext = getContext();
        mActivity = getActivity();
        mCategoryList = new ArrayList<>();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
    }

    @OnItemSelected({R.id.register_category_spinner})
    public void onSelectedCarType(AdapterView<?> parent, View view, int position, long id) {
        if (mCategoryList.size() > 0) {
            selectedCatId = mCategoryList.get(position).getCatId();
        }
    }

    @OnClick({R.id.register_login_text_tv})
    public void onClickLoginTv() {
        openLoginFragment();
    }

    @OnClick({R.id.register_btn})
    public void onClickRegisterBtn() {
        String companyName = "";
        String busCat = "1";
        String userName = mNameEt.getText().toString();
        String userMobile = mobile = mMobileEt.getText().toString();
        String userEmail = mEmailEt.getText().toString();
        String userPassword = mPasswordEt.getText().toString();
        if (mCorporateRadioBtn.isChecked()) {
            companyName = mCompanyNameEt.getText().toString();
            if (InputValidation.validateCompanyName(mCompanyNameEt) && isInputValid()) {
                if ((!selectedCatId.equals("0"))) {
                    mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
                    RegisterApi registerApi = new RegisterApi(mContext, this, mProgressDialog);
                    registerApi.callRegisterApi(userType, companyName, userName, userMobile, userEmail, userPassword, selectedCatId);
                } else {
                    AppToast.showToast(mContext, "Please Business Category Type");
                }
            }
        } else {
            if (isInputValid()) {
                mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
                RegisterApi registerApi = new RegisterApi(mContext, this, mProgressDialog);
                registerApi.callRegisterApi(userType, companyName, userName, userMobile, userEmail, userPassword, busCat);
            }
        }
    }

    private boolean isInputValid() {
        if (InputValidation.validateFirstName(mNameEt)
                && InputValidation.validateMobile(mMobileEt) && InputValidation.validatePassword(mPasswordEt) && InputValidation.validatePassword(mRePassEt)) {
            if (!mEmailEt.getText().toString().isEmpty()) {
                if (InputValidation.validateEmail(mEmailEt)) {
                    if (!(mPasswordEt.getText().toString().equals(mRePassEt.getText().toString()))) {
                        AppToast.showToast(mContext, "Password and RePassword must be same");
                        return false;
                    }
                    if(mAgreeCb.isChecked()) {
                        return true;
                    } else {
                        AppToast.showToast(mContext,"Please agree with the Terms of Services.");
                        return false;
                    }
                }
            } else {
                if (!(mPasswordEt.getText().toString().equals(mRePassEt.getText().toString()))) {
                    AppToast.showToast(mContext, "Password and RePassword must be same");
                    return false;
                }
                if(mAgreeCb.isChecked()) {
                    return true;
                } else {
                    AppToast.showToast(mContext,"Please agree with the Terms of Services.");
                    return false;
                }
            }
        }
        return false;
    }

        @OnClick({R.id.register_corporate_radio_btn})
        public void onChangeCorporateRadioBtn () {
            if (mCorporateRadioBtn.isChecked()) {
                userType = 1;
                mCorporateLayout.setVisibility(View.VISIBLE);
            } else {
                userType = 2;
                mCorporateLayout.setVisibility(View.GONE);
            }
        }

        @OnClick({R.id.register_individual_radio_btn})
        public void onChangeIndividualRadioBtn () {
            if (mIndividualRadioBtn.isChecked()) {
                userType = 2;
                mCorporateLayout.setVisibility(View.GONE);
            } else {
                userType = 1;
                mCorporateLayout.setVisibility(View.VISIBLE);
            }
        }

    private void openLoginFragment() {
        Fragment fragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.login_signUp_frame, fragment, CURRENT_TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void getRegisterResponse(boolean isSuccess, String otp) {
        DismissDialog.dismissWithCheck(mProgressDialog);
        if (isSuccess) {
            OTPDialog dialog = new OTPDialog(mContext, mActivity, this, mobile, otp);
            dialog.show();
        } else {
            AppToast.showToast(mContext, "Registration Failed");
        }
    }

    @Override
    public void getCatResponse(ArrayList<CategoryType> catList) {
        mCategoryList.clear();
        mCategoryList.addAll(catList);
        setSpinnerAdapter();
    }

    @Override
    public void isOTPValidate(boolean isValidate, String mobile) {
        if (isValidate) {
            mAccountDetailHolder.setUserLoggedIn(true);
            Intent intent = new Intent(getActivity(), MainDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}