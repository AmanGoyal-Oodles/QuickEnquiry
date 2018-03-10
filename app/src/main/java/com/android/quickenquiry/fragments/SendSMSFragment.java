package com.android.quickenquiry.fragments;

import android.app.Activity;
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
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.android.quickenquiry.R;
import com.android.quickenquiry.adapters.SendSMSSingleContactAdapter;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/5/2018.
 */

public class SendSMSFragment extends Fragment {


    @BindView(R.id.send_sms_contact_type_spinner)
    Spinner mContactTypeSpinner;
    @BindView(R.id.send_sms_multiple_spinner_layout)
    LinearLayout mMultipleLayout;
    @BindView(R.id.send_sms_single_list_layout)
    LinearLayout mSingleItemLayout;
    /*@BindView(R.id.send_sms_search_et)
    EditText mSearchEt;*/
    /*@BindView(R.id.send_sms_single_contact_list_view)
    ListView mContactListView;*/
    @BindView(R.id.send_sms_multiple_radio_btn)
    RadioButton mMultipleRadiobtn;
    @BindView(R.id.send_sms_single_radio_btn)
    RadioButton mSingleRadioButton;
    @BindView(R.id.send_sms_single_contact_actv)
    AutoCompleteTextView mSingleContactACTV;
    private ArrayAdapter<String> mContactTypeAdapter;
    private ArrayAdapter<ContactDetail> mSendSMSSingleContactAdapter;
    private ArrayAdapter<String> arrayAdapter;
    private String[] list={"A","B","C","D"};
    private ArrayList<ContactDetail> mContactList,mSearchList;
    private Context mcContext;
    private Activity mActivity;
    private AccountDetailHolder mAccountDetailHolder;

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
        mContactList.clear();
        mContactList.addAll(mAccountDetailHolder.getContactList());
        setContactList();
        setACTVAdapter();
        //setListViewAdapter();

        //setListView(mContactList);
    }

    private void setContactList() {
        mContactList.add(new ContactDetail("Aman","9355606425","","","","","",""));
        mContactList.add(new ContactDetail("Ajay","9355606424","","","","","",""));
        mContactList.add(new ContactDetail("Amar","9355606423","","","","","",""));
        mContactList.add(new ContactDetail("Akhil","9355606422","","","","","",""));
        mContactList.add(new ContactDetail("Rahul","9355606421","","","","","",""));
        mContactList.add(new ContactDetail("Abhinav","9355606420","","","","","",""));
        mContactList.add(new ContactDetail("Sahil","9355606435","","","","","",""));
        mContactList.add(new ContactDetail("Ravi","9355606445","","","","","",""));
        mContactList.add(new ContactDetail("Puneet","9355606455","","","","","",""));
        mContactList.add(new ContactDetail("Rishabh","9355606465","","","","","",""));
        mContactList.add(new ContactDetail("Anmol","9355606475","","","","","",""));
        mContactList.add(new ContactDetail("Ankit","9355606485","","","","","",""));
        mContactList.add(new ContactDetail("Ajit","9355606495","","","","","",""));
        mContactList.add(new ContactDetail("Akash","9355606405","","","","","",""));
    }

    private void setACTVAdapter() {
        mSendSMSSingleContactAdapter=new SendSMSSingleContactAdapter(mActivity,R.layout.layout_send_sms_contact_list, mContactList);
      //  mSingleContactACTV.setAdapter(mSendSMSSingleContactAdapter);
       /* String[] language ={"C","C++","Java",".NET","iPhone","Android","ASP.NET","PHP"};

        //janab jugaad dekhte hai ab
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (mcContext,android.R.layout.select_dialog_item,language);
        mSingleContactACTV.setThreshold(1);*/
        mSingleContactACTV.setAdapter(mSendSMSSingleContactAdapter);
    }

    /*private void setListViewAdapter() {
        mSendSMSSingleContactAdapter=new SendSMSSingleContactAdapter(mActivity,mContactList);
        mContactListView.setAdapter(mSendSMSSingleContactAdapter);
    }*/

    /*private void setListView(ArrayList<ContactDetail> list) {
        mSendSMSSingleContactAdapter.setmContactList(list);
        mSendSMSSingleContactAdapter.notifyDataSetChanged();
    }*/

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
        mcContext=getContext();
        mActivity=getActivity();
        mContactList =new ArrayList<>();
        mSearchList=new ArrayList<>();
        mAccountDetailHolder=new AccountDetailHolder(mcContext);
    }

    @OnClick({R.id.send_sms_multiple_radio_btn})
    public void onClickMultipleRadioBtn() {
        if(mMultipleRadiobtn.isChecked()) {
            mSingleItemLayout.setVisibility(View.GONE);
            mMultipleLayout.setVisibility(View.VISIBLE);
        } else {
            mMultipleLayout.setVisibility(View.GONE);
            mSingleItemLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.send_sms_single_radio_btn})
    public void onClickSingleRadioBtn() {
        if(mSingleRadioButton.isChecked()) {
            mMultipleLayout.setVisibility(View.GONE);
            mSingleItemLayout.setVisibility(View.VISIBLE);
        } else {
            mSingleItemLayout.setVisibility(View.GONE);
            mMultipleLayout.setVisibility(View.VISIBLE);
        }
    }

}
