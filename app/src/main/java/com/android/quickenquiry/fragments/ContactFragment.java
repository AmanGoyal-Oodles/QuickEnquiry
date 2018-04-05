package com.android.quickenquiry.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.android.quickenquiry.R;
import com.android.quickenquiry.adapters.ContactAdapter;
import com.android.quickenquiry.interfaces.GetUpdateContactListener;
import com.android.quickenquiry.interfaces.apiResponseListener.GetContactsResponseListener;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.GetContactsApi;
import com.android.quickenquiry.utils.util.dialogs.ShowDialog;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by user on 3/7/2018.
 */

public class ContactFragment extends Fragment implements GetContactsResponseListener,GetUpdateContactListener{


    @BindView(R.id.contact_list_search_et)
    EditText mSearchEt;
    @BindView(R.id.contact_list_search_iv)
    ImageView mSearchIv;
    @BindView(R.id.contact_list_rv)
    RecyclerView mContactListRv;
    @BindView(R.id.contact_list_add_contact_btn)
    Button mAddContactBtn;
    private Context mContext;
    private ContactAdapter mContactAdapter;
    private ArrayList<ContactDetail> mContactList,mSearchList;
    private AccountDetailHolder mAccountDetailHolder;
    private ProgressDialog mProgressDialog;
    private final static String CURRENT_TAG=ContactFragment.class.getName();

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
            actionBar.setTitle("  Contacts");
        init();
    }

    private void init() {
        initVariables();
        setRecyclerView();
        //setContactList();
        callGetContacts();
        //getContactFromLocalStorage();
        //mContactAdapter.setContactList(mContactList);
        //mContactAdapter.notifyDataSetChanged();
    }

    private void callGetContacts() {
        String userid=mAccountDetailHolder.getUserDetail().getUserId();
        mProgressDialog= ShowDialog.show(mContext,"","Please Wait",true,false);
        GetContactsApi getContactsApi=new GetContactsApi(mContext,this,mProgressDialog);
        getContactsApi.callGetContactsApi(userid);
    }

    private void getContactFromLocalStorage() {
        //mContactList.clear();
        //mContactList=mAccountDetailHolder.getContactList();
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
            text=text.toLowerCase();
            for (int i = 0; i < mContactList.size(); i++) {
                String name=mContactList.get(i).getmName().toLowerCase();
                String phone=mContactList.get(i).getmPhone();
                if (name.contains(text) || phone.contains(text)) {
                    mSearchList.add(mContactList.get(i));
                }
            }
        } else {
            mSearchList.addAll(mContactList);
        }
        mContactAdapter.setContactList(mSearchList);
        mContactAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.contact_list_add_contact_btn})
    public void onClickAddContactBtn() {
        Fragment fragment = new AddContactFragment();
        Bundle bundle=new Bundle();
        bundle.putString("tag","addContact");
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
        fragmentTransaction.addToBackStack(CURRENT_TAG);
        fragmentTransaction.commit();
    }

    private void setRecyclerView() {
        mContactAdapter =new ContactAdapter(mContext,this);
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

    @Override
    public void getContectsResponse(boolean isReceived, ArrayList<ContactDetail> contactList) {
        if(isReceived) {
            mContactList.clear();
            mContactList.addAll(contactList);
            mContactAdapter.setContactList(contactList);
            mContactAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getContactPosition(int position) {
        Fragment fragment = new AddContactFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("contactdetail",mContactList.get(position));
        bundle.putString("tag","updateContact");
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
        fragmentTransaction.addToBackStack(CURRENT_TAG);
        fragmentTransaction.commit();
    }
}