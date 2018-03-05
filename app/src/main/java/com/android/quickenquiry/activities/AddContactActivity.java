package com.android.quickenquiry.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.quickenquiry.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddContactActivity extends AppCompatActivity {


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
    private ArrayAdapter<String> mClientTypeAdapter;
    private ArrayAdapter<String> mDobDateAdapter,mDobMonthAdapter,mDobYearAdapter,mAnniversaryDateAdapter,mAnniversaryMonthAdapter,mAnniversaryYearAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initVariables();
    }

    private void initVariables() {
        mDateList=new ArrayList<>();
        mMonthList=new ArrayList<>();
        mYearList=new ArrayList<>();
        setList(mDateList,1,31);
        setList(mMonthList,1,12);
        setList(mYearList,1970,2050);
        setSpinnerAdapters();
    }

    private void setSpinnerAdapters() {
        mClientTypeAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mClientTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mClientTypeAdapter.clear();
        mClientTypeAdapter.addAll(getResources().getStringArray(R.array.CLIENT_TYPE));
        mSpinnerClientType.setAdapter(mClientTypeAdapter);

        mDobDateAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mDobDateAdapter.clear();
        mDobDateAdapter.add(SELECT_DATE);
        mDobDateAdapter.addAll(mDateList);
        mSpinnerDobDate.setAdapter(mDobDateAdapter);

        mDobMonthAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mDobMonthAdapter.clear();
        mDobMonthAdapter.add(SELECT_MONTH);
        mDobMonthAdapter.addAll(mMonthList);
        mSpinnerDobMonth.setAdapter(mDobMonthAdapter);

        mDobYearAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mDobYearAdapter.clear();
        mDobYearAdapter.add(SELECT_YEAR);
        mDobYearAdapter.addAll(mYearList);
        mSpinnerDobYear.setAdapter(mDobYearAdapter);

        mAnniversaryDateAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mAnniversaryDateAdapter.clear();
        mAnniversaryDateAdapter.add(SELECT_DATE);
        mAnniversaryDateAdapter.addAll(mDateList);
        mSpinnerAnniversaryDate.setAdapter(mAnniversaryDateAdapter);

        mAnniversaryMonthAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position!=0&& super.isEnabled(position);
            }
        };
        mAnniversaryMonthAdapter.clear();
        mAnniversaryMonthAdapter.add(SELECT_MONTH);
        mAnniversaryMonthAdapter.addAll(mMonthList);
        mSpinnerAnniversaryMonth.setAdapter(mAnniversaryMonthAdapter);

        mAnniversaryYearAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item) {
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
}
