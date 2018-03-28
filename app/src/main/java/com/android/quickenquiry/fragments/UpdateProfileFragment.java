package com.android.quickenquiry.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.apiResponseListener.GetCityResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.GetLocalityResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.UpdateProfileResponseListener;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.GetCityApi;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.GetLocalityApi;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.UpdateProfileApi;
import com.android.quickenquiry.utils.util.InputValidation;
import com.android.quickenquiry.utils.util.dialogs.ShowDialog;
import com.android.quickenquiry.utils.util.pojoClasses.CityDetails;
import com.android.quickenquiry.utils.util.pojoClasses.LocalityDetails;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by user on 3/7/2018.
 */

public class UpdateProfileFragment extends Fragment implements GetCityResponseListener,GetLocalityResponseListener,UpdateProfileResponseListener{


    private static final String CURRENT_TAG =UpdateProfileFragment.class.getName() ;
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
    private ArrayList<CityDetails> cityList;
    private ArrayList<LocalityDetails> localityList;
    private AccountDetailHolder mAccountDetailHolder;
    private String selectedCity="0";
    private String selectedLocality="0";
    private Context mContext;
    private ProgressDialog mProgressDialog;


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
        getCityApi();
        setCitySpinnerAdapter();
        setLocalitySpinnerAdapter();
    }

    private void setLocalitySpinnerAdapter() {
        mLocalityAdapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mLocalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLocalityAdapter.clear();
        mLocalityAdapter.add("Select Locality");
        mLocalityAdapter.addAll(mLocalityList);
        mLocalitySpinner.setAdapter(mLocalityAdapter);
    }

    private void getCityApi() {
        mProgressDialog= ShowDialog.show(mContext,"","Please Wait",true,false);
        GetCityApi getCityApi=new GetCityApi(mContext,this,mProgressDialog);
        getCityApi.callGetCityApi();
    }

    private void setCitySpinnerAdapter() {
        mCityAdapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCityAdapter.clear();
        mCityAdapter.add("Select City");
        mCityAdapter.addAll(mCityList);
        mCitySpinner.setAdapter(mCityAdapter);
    }

    private void initVariables() {
        mContext=getContext();
        cityList=new ArrayList<>();
        localityList=new ArrayList<>();
        mCityList=new ArrayList<>();
        mLocalityList=new ArrayList<>();
        mAccountDetailHolder=new AccountDetailHolder(mContext);
    }

    @OnItemSelected({R.id.update_profile_city_spinner})
    public void onSelectedCity(AdapterView<?> parent, View view, int position, long id) {
        if(position>=1) {
            selectedCity = cityList.get(position - 1).getCityId();
            mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
            GetLocalityApi getLocalityApi = new GetLocalityApi(mContext, this, mProgressDialog);
            getLocalityApi.callGetLocalityApi(selectedCity);
        }
    }

    @OnItemSelected({R.id.update_profile_locality_spinner})
    public void onSelectedLocality(AdapterView<?> parent, View view, int position, long id) {
        if(position>=1) {
            selectedLocality = localityList.get(position - 1).getLocalityId();
        }
    }

    @OnClick({R.id.update_profile_btn})
    public void onClickUpdateInfo() {
        if(isInputValid()) {
            String name=mNameEt.getText().toString().trim();
            String email=mEmailEt.getText().toString().trim();
            String secondaryContact=mSecConEt.getText().toString().trim();
            String address=mAddressEt.getText().toString().trim();
            String userId=mAccountDetailHolder.getUserDetail().getUserId();
            mProgressDialog= ShowDialog.show(mContext,"","Please Wait",true,false);
            UpdateProfileApi updateProfileApi=new UpdateProfileApi(mContext,this,mProgressDialog);
            updateProfileApi.callUpdateProfileApi(userId,name,email,secondaryContact,selectedCity,selectedLocality,address);
        }
    }

    private boolean isInputValid() {
        if(InputValidation.validateFirstName(mNameEt)&&InputValidation.validateEmail(mEmailEt)&&InputValidation.validateMobile(mSecConEt)) {
            return true;
        }
        return false;
    }

    @Override
    public void getCityListResponse(ArrayList<CityDetails> list) {
        cityList.clear();
        cityList.addAll(list);
        for(int i=0;i<cityList.size();i++) {
            mCityList.add(cityList.get(i).getCityType());
        }
        setCitySpinnerAdapter();
        mLocalityList.clear();
        setLocalitySpinnerAdapter();
    }

    @Override
    public void getLocalityResponse(ArrayList<LocalityDetails> list) {
        mLocalityList.clear();
        localityList.clear();
        localityList.addAll(list);
        for(int i=0;i<localityList.size();i++) {
            mLocalityList.add(localityList.get(i).getLocalityName());
        }
        setLocalitySpinnerAdapter();
    }

    @Override
    public void getUpdateProfileResponse(boolean isUpdated, String message) {
        if(isUpdated) {
            Fragment fragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
            fragmentTransaction.commit();
        }
    }
}