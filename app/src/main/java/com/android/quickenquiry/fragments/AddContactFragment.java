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
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.AddContactAPI;
import com.android.quickenquiry.utils.util.AppToast;
import com.android.quickenquiry.utils.util.InputValidation;
import com.android.quickenquiry.utils.util.dialogs.ShowDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by user on 3/6/2018.
 */

public class AddContactFragment extends Fragment implements AddContactAPIResponseListener{

    @BindView(R.id.add_contact_save_btn)
    Button mSaveBtn;
    @BindView(R.id.add_contact_cancel_btn)
    Button mCancelBtn;
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
    private ArrayList<String> mDateList,mMonthList,mYearList;
    private static final String SELECT_DATE="Date";
    private static final String SELECT_MONTH="Month";
    private static final String SELECT_YEAR="Year";
    private Context mContext;
    private AccountDetailHolder mAccountDetailHolder;
    private ProgressDialog mProgressDialog;
    private String dobDay,dobMon,dobYear,annivDay,annivMon,annivYear,contactType;
    private ArrayAdapter<String> mClientTypeAdapter;
    private ArrayAdapter<String> mDobDateAdapter,mDobMonthAdapter,mDobYearAdapter,mAnniversaryDateAdapter,mAnniversaryMonthAdapter,mAnniversaryYearAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_add_contact,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        init();
    }

    private void init() {
        setToolBar();
        initVariables();
    }

    private void setToolBar() {
        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setTitle("Add Contact");
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.White)));
        }
    }

    private void initVariables() {
        mContext=getContext();
        mAccountDetailHolder=new AccountDetailHolder(mContext);
        mDateList=new ArrayList<>();
        mMonthList=new ArrayList<>();
        mYearList=new ArrayList<>();
        setList(mDateList,1,31);
        //setList(mMonthList,1,12);
        setList(mYearList,1970,2050);
        setSpinnerAdapters();
    }

    private void setSpinnerAdapters() {
        mClientTypeAdapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mClientTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mClientTypeAdapter.clear();
        mClientTypeAdapter.addAll(getResources().getStringArray(R.array.CLIENT_TYPE));
        mSpinnerClientType.setAdapter(mClientTypeAdapter);

        mDobDateAdapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mDobDateAdapter.clear();
        mDobDateAdapter.add(SELECT_DATE);
        mDobDateAdapter.addAll(mDateList);
        mSpinnerDobDate.setAdapter(mDobDateAdapter);

        mDobMonthAdapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mDobMonthAdapter.clear();
        mDobMonthAdapter.add(SELECT_MONTH);
        mDobMonthAdapter.addAll(getResources().getStringArray(R.array.MONTH));
        mSpinnerDobMonth.setAdapter(mDobMonthAdapter);

        mDobYearAdapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mDobYearAdapter.clear();
        mDobYearAdapter.add(SELECT_YEAR);
        mDobYearAdapter.addAll(mYearList);
        mSpinnerDobYear.setAdapter(mDobYearAdapter);

        mAnniversaryDateAdapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mAnniversaryDateAdapter.clear();
        mAnniversaryDateAdapter.add(SELECT_DATE);
        mAnniversaryDateAdapter.addAll(mDateList);
        mSpinnerAnniversaryDate.setAdapter(mAnniversaryDateAdapter);

        mAnniversaryMonthAdapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mAnniversaryMonthAdapter.clear();
        mAnniversaryMonthAdapter.add(SELECT_MONTH);
        mAnniversaryMonthAdapter.addAll(getResources().getStringArray(R.array.MONTH));
        mSpinnerAnniversaryMonth.setAdapter(mAnniversaryMonthAdapter);

        mAnniversaryYearAdapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mAnniversaryYearAdapter.clear();
        mAnniversaryYearAdapter.add(SELECT_YEAR);
        mAnniversaryYearAdapter.addAll(mYearList);
        mSpinnerAnniversaryYear.setAdapter(mAnniversaryYearAdapter);
    }

    private void setList(ArrayList<String> list, int start, int end) {
        list.clear();
        for(int i=start;i<=end;i++) {
            list.add(i+"");
        }
    }

    @OnClick({R.id.add_contact_save_btn})
    public void onClickSave() {
        if(isInputValid()) {
            String userId=mAccountDetailHolder.getUserDetail().getUserId();
            String relation=mRelationEt.getText().toString().trim();
            String contactName=mNameEt.getText().toString().trim();
            String contactMobile=mMobileEt.getText().toString().trim();
            String contactEmail=mEmailEt.getText().toString().trim();
            String contactAddress=mAddressEt.getText().toString().trim();
            String contactDOB=dobYear+"-"+dobMon+"-"+dobDay;
            String contactAnniv=annivYear+"-"+annivMon+"-"+annivDay;
            mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
            AddContactAPI addContactAPI=new AddContactAPI(mContext,this,mProgressDialog);
            addContactAPI.callAddContactApi(userId,contactType,contactName,contactMobile,contactEmail,contactAddress,contactDOB,contactAnniv);
        }
    }

    private boolean isInputValid() {
        if(contactType!=null&&!contactType.isEmpty()) {
            if(InputValidation.validateFirstName(mRelationEt)&&InputValidation.validateFirstName(mNameEt)
                    &&InputValidation.validateMobile(mMobileEt)&&InputValidation.validateEmail(mEmailEt)
                    &&InputValidation.validateFirstName(mAddressEt)) {
                if( (dobDay!=null&&!dobDay.isEmpty()) &&(dobMon!=null&&!dobMon.isEmpty()) && (dobYear!=null&&!dobYear.isEmpty())) {
                    if( (annivDay!=null&&!annivDay.isEmpty()) && (annivMon!=null&&!annivMon.isEmpty()) && (annivYear!=null&&!annivYear.isEmpty()) ) {
                        return true;
                    } else {
                        AppToast.showToast(mContext,"Please Select Annivsary Date");
                    }
                } else {
                    AppToast.showToast(mContext,"Please Select Date Of Birth");
                }
            }
        } else {
            AppToast.showToast(mContext,"Please Select Contact Type");
        }
        return false;
    }

    @OnItemSelected({R.id.add_contact_contact_spinner})
    public void onSelectedCarBrand(AdapterView<?> parent, View view, int position, long id) {
        contactType=parent.getItemAtPosition(position).toString();
    }

    @OnItemSelected({R.id.add_contact_dob_date_spinner})
    public void onSelectedDOBDay(AdapterView<?> parent, View view, int position, long id) {
        dobDay=parent.getItemAtPosition(position).toString();
    }

    @OnItemSelected({R.id.add_contact_dob_month_spinner})
    public void onSelectedDOBMon(AdapterView<?> parent, View view, int position, long id) {
        dobMon=parent.getItemAtPosition(position).toString();
    }

    @OnItemSelected({R.id.add_contact_dob_year_spinner})
    public void onSelectedDOBYear(AdapterView<?> parent, View view, int position, long id) {
        dobYear=parent.getItemAtPosition(position).toString();
    }

    @OnItemSelected({R.id.add_contact_anniversary_date_spinner})
    public void onSelectedAnnivDay(AdapterView<?> parent, View view, int position, long id) {
        annivDay=parent.getItemAtPosition(position).toString();
    }

    @OnItemSelected({R.id.add_contact_anniversary_month_spinner})
    public void onSelectedAnnivMon(AdapterView<?> parent, View view, int position, long id) {
        annivMon=parent.getItemAtPosition(position).toString();
    }

    @OnItemSelected({R.id.add_contact_anniversary_year_spinner})
    public void onSelectedAnnivYear(AdapterView<?> parent, View view, int position, long id) {
        annivYear=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void getAddContactResponse(boolean isAdded) {
        if (isAdded) {
            Fragment fragment=new HomeFragment();
            openFragment(fragment);
        }
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commitNowAllowingStateLoss();
    }

}