package com.android.quickenquiry.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.apiResponseListener.AddContactAPIResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.GetCategoryResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.GetContactTypeResponseListener;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.AddContactAPI;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.GetCategoryApi;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.GetContactTypeApi;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.UpdateContactAPi;
import com.android.quickenquiry.utils.util.AppToast;
import com.android.quickenquiry.utils.util.InputValidation;
import com.android.quickenquiry.utils.util.dialogs.ShowDialog;
import com.android.quickenquiry.utils.util.pojoClasses.CategoryType;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;
import com.android.quickenquiry.utils.util.pojoClasses.ContactType;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by user on 3/6/2018.
 */

public class AddContactFragment extends Fragment implements AddContactAPIResponseListener,GetContactTypeResponseListener {

    private static final String CURRENT_TAG = AddContactFragment.class.getName();
    @BindView(R.id.add_contact_save_btn)
    Button mSaveBtn;
    /*@BindView(R.id.add_contact_cancel_btn)
    Button mCancelBtn;*/
    @BindView(R.id.add_contact_relation_et)
    EditText mRelationEt;
    @BindView(R.id.add_contact_name_et)
    EditText mNameEt;
    @BindView(R.id.add_contact_mobile_et)
    EditText mMobileEt;
    @BindView(R.id.add_contact_email_et)
    EditText mEmailEt;
    @BindView(R.id.add_contact_address_et)
    EditText mAddressEt;
    @BindView(R.id.add_contact_contact_spinner)
    Spinner mSpinnerClientType;
    @BindView(R.id.add_contact_dob_date_spinner)
    Spinner mSpinnerDobDate;
    @BindView(R.id.add_contact_dob_month_spinner)
    Spinner mSpinnerDobMonth;
    @BindView(R.id.add_contact_dob_year_spinner)
    Spinner mSpinnerDobYear;
    @BindView(R.id.add_contact_anniversary_date_spinner)
    Spinner mSpinnerAnniversaryDate;
    @BindView(R.id.add_contact_anniversary_month_spinner)
    Spinner mSpinnerAnniversaryMonth;
    @BindView(R.id.add_contact_anniversary_year_spinner)
    Spinner mSpinnerAnniversaryYear;
    private ArrayList<String> mDateList, mMonthList, mYearList;
    private static final String SELECT_DATE = "Date";
    private static final String SELECT_MONTH = "Month";
    private static final String SELECT_YEAR = "Year";
    private Context mContext;
    private AccountDetailHolder mAccountDetailHolder;
    private ProgressDialog mProgressDialog;
    private ArrayList<ContactType> mContactTypeList;
    private String dobDay="", dobMon="", dobYear="", annivDay="", annivMon="", annivYear="", contactType="";
    private ArrayAdapter<String> mClientTypeAdapter;
    private ContactDetail contactDetail;
    private String tag="";
    private String contactId="";
    private boolean isRelationEtEnable=true,isDOBSelected=false,isAnnivSelected=false;
    private ArrayAdapter<String> mDobDateAdapter, mDobMonthAdapter, mDobYearAdapter, mAnniversaryDateAdapter, mAnniversaryMonthAdapter, mAnniversaryYearAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_contact, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        setToolBar("  ADD Contact");
        getData();
        initVariables();
        getContactType();
    }

    private void getData() {
        tag=getArguments().getString("tag");
        if(tag.equalsIgnoreCase("updateContact")) {
            setToolBar("  Update Contact");
            contactDetail = (ContactDetail) getArguments().getSerializable("contactdetail");
            setViews();
        }
    }

    private void setViews() {
        contactId=contactDetail.getContactid();
        contactType=contactDetail.getmClientType();
        if(contactType.equalsIgnoreCase("Friends")||contactType.equalsIgnoreCase("Staff")) {
            mRelationEt.setVisibility(View.GONE);
            isRelationEtEnable=false;
        } else {
            mRelationEt.setVisibility(View.VISIBLE);
            isRelationEtEnable=true;
        }
        mRelationEt.setText(contactDetail.getmCompany());
        mNameEt.setText(contactDetail.getmName());
        mMobileEt.setText(contactDetail.getmPhone());
        mEmailEt.setText(contactDetail.getmEmail());
        mAddressEt.setText(contactDetail.getmAddress());
        String date=contactDetail.getmDOB();
        if(!date.isEmpty()) {
            String tempDate[]=date.split("-");
            dobDay=tempDate[2];
            dobMon=tempDate[1];
            dobYear=tempDate[0];
            isDOBSelected=true;
        }
        String anniv=contactDetail.getmAnniversary();
        if(!anniv.isEmpty()) {
            String tempDate[]=anniv.split("-");
            annivDay=tempDate[2];
            annivMon=tempDate[1];
            annivYear=tempDate[0];
            isAnnivSelected=true;
        }
    }

    private void getContactType() {
        mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
        GetContactTypeApi getContactTypeApi = new GetContactTypeApi(mContext, this, mProgressDialog);
        getContactTypeApi.callGetContactTypeApi();
    }

    private void setToolBar(String title) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.White)));
        }
    }

    private void initVariables() {
        mContext = getContext();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        mDateList = new ArrayList<>();
        mMonthList = new ArrayList<>();
        mYearList = new ArrayList<>();
        mContactTypeList = new ArrayList<>();
        setList(mDateList, 1, 31);
        //setList(mMonthList,1,12);
        setList(mYearList, 1950, 2018);
        setSpinnerAdapters();
    }

    private void setContactTypeSpinnerAdapter() {
        mClientTypeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0 && super.isEnabled(position);
            }
        };
        mClientTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mClientTypeAdapter.clear();
        mClientTypeAdapter.add("Select Contact Type");
        ArrayList<String> catList = new ArrayList<>();
        int pos=0;
        for (int i = 0; i < mContactTypeList.size(); i++) {
            catList.add(mContactTypeList.get(i).getContactTypeName());
            if(contactType.equalsIgnoreCase(mContactTypeList.get(i).getContactTypeId())) {
                pos=i;
            }
        }

        mClientTypeAdapter.addAll(catList);
        mSpinnerClientType.setAdapter(mClientTypeAdapter);
        mSpinnerClientType.setSelection(pos);
    }

    private void setSpinnerAdapters() {
        mDobDateAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item);
        mDobDateAdapter.clear();
        mDobDateAdapter.add(SELECT_DATE);
        mDobDateAdapter.addAll(mDateList);
        mSpinnerDobDate.setAdapter(mDobDateAdapter);
        if(!dobDay.isEmpty()) {
            mSpinnerDobDate.setSelection(Integer.valueOf(dobDay));
        }

        mDobMonthAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item);
        mDobMonthAdapter.clear();
        mDobMonthAdapter.add(SELECT_MONTH);
        mDobMonthAdapter.addAll(getResources().getStringArray(R.array.MONTH));
        mSpinnerDobMonth.setAdapter(mDobMonthAdapter);
        if(!dobMon.isEmpty()) {
            mSpinnerDobMonth.setSelection(Integer.valueOf(dobMon));
        }

        mDobYearAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item);
        mDobYearAdapter.clear();
        mDobYearAdapter.add(SELECT_YEAR);
        mDobYearAdapter.addAll(mYearList);
        mSpinnerDobYear.setAdapter(mDobYearAdapter);
        if(!dobYear.isEmpty()) {
            mSpinnerDobYear.setSelection(Integer.valueOf(dobYear)-1969);
        }

        mAnniversaryDateAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item);
        mAnniversaryDateAdapter.clear();
        mAnniversaryDateAdapter.add(SELECT_DATE);
        mAnniversaryDateAdapter.addAll(mDateList);
        mSpinnerAnniversaryDate.setAdapter(mAnniversaryDateAdapter);
        if(!annivDay.isEmpty()) {
            mSpinnerAnniversaryDate.setSelection(Integer.valueOf(annivDay));
        }

        mAnniversaryMonthAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item);
        mAnniversaryMonthAdapter.clear();
        mAnniversaryMonthAdapter.add(SELECT_MONTH);
        mAnniversaryMonthAdapter.addAll(getResources().getStringArray(R.array.MONTH));
        mSpinnerAnniversaryMonth.setAdapter(mAnniversaryMonthAdapter);
        if(!annivMon.isEmpty()) {
            mSpinnerAnniversaryMonth.setSelection(Integer.valueOf(annivMon));
        }

        mAnniversaryYearAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item);
        mAnniversaryYearAdapter.clear();
        mAnniversaryYearAdapter.add(SELECT_YEAR);
        mAnniversaryYearAdapter.addAll(mYearList);
        mSpinnerAnniversaryYear.setAdapter(mAnniversaryYearAdapter);
        if(!annivYear.isEmpty()) {
            mSpinnerAnniversaryYear.setSelection(Integer.valueOf(annivYear)-1969);
        }
    }

    private void setList(ArrayList<String> list, int start, int end) {
        list.clear();
        for (int i = start; i <= end; i++) {
            list.add(i + "");
        }
    }

    @OnClick({R.id.add_contact_save_btn})
    public void onClickSave() {
        if (isInputValid()) {
            String userId = mAccountDetailHolder.getUserDetail().getUserId();
            String relation = mRelationEt.getText().toString().trim();
            String contactName = mNameEt.getText().toString().trim();
            String contactMobile = mMobileEt.getText().toString().trim();
            String contactEmail = mEmailEt.getText().toString().trim();
            String contactAddress = mAddressEt.getText().toString().trim();
            String contactDOB = dobYear + "-" + dobMon + "-" + dobDay;
            if(dobDay.isEmpty()||dobMon.isEmpty()||dobYear.isEmpty()) {
                contactDOB="";
            }
            String contactAnniv = annivYear + "-" + annivMon + "-" + annivDay;
            if(annivDay.isEmpty()||annivMon.isEmpty()||annivYear.isEmpty()) {
                contactAnniv="";
            }
            if(tag.equalsIgnoreCase("updateContact")) {
                mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
                UpdateContactAPi updateContactAPI = new UpdateContactAPi(mContext, this, mProgressDialog);
                updateContactAPI.callAddContactApi(userId,contactId, contactType, relation, contactName, contactMobile, contactEmail, contactAddress, contactDOB, contactAnniv);

            } else {
                mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
                AddContactAPI addContactAPI = new AddContactAPI(mContext, this, mProgressDialog);
                addContactAPI.callAddContactApi(userId, contactType, relation, contactName, contactMobile, contactEmail, contactAddress, contactDOB, contactAnniv);

            }
        }
    }

    private boolean isInputValid() {
        if (contactType != null && !contactType.isEmpty()) {
            if ( ((!isRelationEtEnable)||InputValidation.validateFirstName(mRelationEt) ) && (InputValidation.validateFirstName(mNameEt))
                    && (InputValidation.validateMobile(mMobileEt)) && (mEmailEt.getText().toString().isEmpty()||InputValidation.validateEmail(mEmailEt))
                    && (mAddressEt.getText().toString().isEmpty()||InputValidation.validateFirstName(mAddressEt)) ) {
                if (!dobDay.isEmpty() || !dobMon.isEmpty() || !dobYear.isEmpty()) {
                    if ((dobDay != null && !dobDay.isEmpty()) && (dobMon != null && !dobMon.isEmpty()) && (dobYear != null && !dobYear.isEmpty())) {
                        isDOBSelected=true;
                    } else {
                        AppToast.showToast(mContext, "Please Select Date Of Birth");
                        isDOBSelected=false;
                        return false;
                    }
                }
                if (!annivDay.isEmpty() || !annivMon.isEmpty() || !annivYear.isEmpty()) {
                    if ((annivDay != null && !annivDay.isEmpty()) && (annivMon != null && !annivMon.isEmpty()) && (annivYear != null && !annivYear.isEmpty())) {
                        isAnnivSelected=true;
                    } else {
                        AppToast.showToast(mContext, "Please Select Annivsary Date");
                        isAnnivSelected=false;
                        return false;
                    }
                }
                if(isDOBSelected&&isAnnivSelected) {
                    if(!isDOBLessThanAnniv()) {
                        AppToast.showToast(mContext,"Date Of Birth Should be less than Anniversary Date");
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            AppToast.showToast(mContext, "Please Select Contact Type");
            return false;
        }
        return true;
    }

    private boolean isDOBLessThanAnniv() {
        int dob_day=Integer.valueOf(dobDay);
        int dob_mon=Integer.valueOf(dobMon);
        int dob_year=Integer.valueOf(dobYear);
        int anniv_day=Integer.valueOf(annivDay);
        int anniv_mon=Integer.valueOf(annivMon);
        int anniv_year=Integer.valueOf(annivYear);
        if(dob_year<anniv_year) {
            return true;
        } else if(dob_year>anniv_year) {
            return false;
        } else {
            if(dob_mon<anniv_mon) {
                return true;
            } else if(dob_mon>anniv_mon) {
                return false;
            } else {
                if(dob_day<anniv_day) {
                    return true;
                } else if(dob_day>anniv_day) {
                    return false;
                } else {
                    return false;
                }
            }
        }


    }

    @OnItemSelected({R.id.add_contact_contact_spinner})
    public void onSelectedCarBrand(AdapterView<?> parent, View view, int position, long id) {
        if(position>0) {
            contactType = mContactTypeList.get(position-1).getContactTypeId();
        }
        String contactType=parent.getItemAtPosition(position).toString();
            if(contactType.equalsIgnoreCase("Friends")||contactType.equalsIgnoreCase("Staff")) {
                mRelationEt.setVisibility(View.GONE);
                isRelationEtEnable=false;
            } else {
                mRelationEt.setVisibility(View.VISIBLE);
                isRelationEtEnable=true;
        }
    }

    @OnItemSelected({R.id.add_contact_dob_date_spinner})
    public void onSelectedDOBDay(AdapterView<?> parent, View view, int position, long id) {
        if (position==0) {
            dobDay="";
        } else {
            dobDay = parent.getItemAtPosition(position).toString();
        }
    }

    @OnItemSelected({R.id.add_contact_dob_month_spinner})
    public void onSelectedDOBMon(AdapterView<?> parent, View view, int position, long id) {
        if (position==0) {
            dobMon="";
        } else {
            dobMon = position + "";
        }
    }

    @OnItemSelected({R.id.add_contact_dob_year_spinner})
    public void onSelectedDOBYear(AdapterView<?> parent, View view, int position, long id) {
        if(position==0) {
            dobYear="";
        } else {
            dobYear = parent.getItemAtPosition(position).toString();
        }
    }

    @OnItemSelected({R.id.add_contact_anniversary_date_spinner})
    public void onSelectedAnnivDay(AdapterView<?> parent, View view, int position, long id) {
        if(position==0) {
            annivDay="";
        } else {
            annivDay = parent.getItemAtPosition(position).toString();
        }
    }

    @OnItemSelected({R.id.add_contact_anniversary_month_spinner})
    public void onSelectedAnnivMon(AdapterView<?> parent, View view, int position, long id) {
        if(position==0) {
            annivMon="";
        } else {
            annivMon = position + "";
        }
    }

    @OnItemSelected({R.id.add_contact_anniversary_year_spinner})
    public void onSelectedAnnivYear(AdapterView<?> parent, View view, int position, long id) {
        if(position==0) {
            annivYear="";
        } else {
            annivYear = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void getAddContactResponse(boolean isAdded) {
        if (isAdded) {
            Fragment fragment = new ContactFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
            fragmentTransaction.commit();
        }
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    @Override
    public void getContactTypeResponse(boolean isReceived, ArrayList<ContactType> contactTypeList) {
        if(isReceived) {
            mContactTypeList.clear();
            mContactTypeList.addAll(contactTypeList);
            setContactTypeSpinnerAdapter();
        }
    }
}