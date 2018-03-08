package com.android.quickenquiry.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.android.quickenquiry.R;
import com.android.quickenquiry.adapters.ContactAdapter;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

/**
 * Created by user on 3/7/2018.
 */

public class ContactFragment extends Fragment {


    @BindView(R.id.contact_list_search_et)
    EditText mSearchEt;
    @BindView(R.id.contact_list_search_iv)
    ImageView mSearchIv;
    @BindView(R.id.contact_list_rv)
    RecyclerView mContactListRv;
    private Context mContext;
    private ContactAdapter mContactAdapter;
    private ArrayList<ContactDetail> mContactList,mSearchList;
    private AccountDetailHolder mAccountDetailHolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contact_list,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar!=null)
            actionBar.setTitle("Contacts");
        init();
    }

    private void init() {
        initVariables();
        setRecyclerView();
        setContactList();
        //getContactFromLocalStorage();
        mContactAdapter.setContactList(mContactList);
        mContactAdapter.notifyDataSetChanged();
    }

    private void getContactFromLocalStorage() {
        mContactList.clear();
        mContactList=mAccountDetailHolder.getContactList();
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

    @OnTextChanged({R.id.contact_list_search_et})
    public void onSearchTextChaged() {
        mSearchList.clear();
        String text=mSearchEt.getText().toString().trim();
        if(!text.isEmpty()) {
            for (int i = 0; i < mContactList.size(); i++) {
                if (mContactList.get(i).getmName().contains(text) || mContactList.get(i).getmPhone().contains(text)) {
                    mSearchList.add(mContactList.get(i));
                }
            }
        } else {
            mSearchList.addAll(mContactList);
        }
        mContactAdapter.setContactList(mSearchList);
        mContactAdapter.notifyDataSetChanged();
    }

    private void setRecyclerView() {
        mContactAdapter =new ContactAdapter(mContext);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(mContext);
        mContactListRv.setLayoutManager(layoutManager);
        mContactListRv.setItemAnimator(new DefaultItemAnimator());
        mContactListRv.setAdapter(mContactAdapter);
    }

    private void initVariables() {
        mContext=getContext();
        mContactList=new ArrayList<>();
        mSearchList=new ArrayList<>();
        mAccountDetailHolder=new AccountDetailHolder(mContext);
    }
}
