package com.android.quickenquiry.fragments;

import android.app.Activity;
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
import android.widget.CheckBox;
import com.android.quickenquiry.R;
import com.android.quickenquiry.adapters.InviteFriendListAdapter;
import com.android.quickenquiry.interfaces.ContactSelectionListener;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.utils.util.AppToast;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/8/2018.
 */

public class InviteFriendFragment extends Fragment implements ContactSelectionListener{


    @BindView(R.id.invite_friend_select_all_cb)
    CheckBox mSelectAllCb;
    @BindView(R.id.invite_friend_contact_list_rv)
    RecyclerView mContactListRv;
    @BindView(R.id.invite_friend_btn)
    Button mInviteBtn;
    private Context mContext;
    private Activity mActivity;
    private InviteFriendListAdapter mInviteFriendListAdapter;
    private AccountDetailHolder mAccountDetailHolder;
    private ArrayList<ContactDetail> mContactList,mContactSelectedList;
    private ArrayList<Boolean> mContactSelecteionStatusList;
    private final static String CURRENT_TAG=InviteFriendFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_invite_friend,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setTitle("Invite To Freinds");
        }
        init();
    }

    private void init() {
        initVariables();
        mContactList=mAccountDetailHolder.getContactList();
        setContactSelectionList();
        setRecyclerView();
    }

    private void setContactSelectionList() {
        for(int i=0;i<mContactList.size();i++) {
            mContactSelecteionStatusList.add(false);
        }
    }

    private void updateContactSelectionList(boolean isSelect) {
        for(int i=0;i<mContactList.size();i++) {
            mContactSelecteionStatusList.set(i,isSelect);
        }
    }

    private void setRecyclerView() {
        mInviteFriendListAdapter=new InviteFriendListAdapter(mContext,this,mContactSelecteionStatusList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(mContext);
        mContactListRv.setLayoutManager(layoutManager);
        mContactListRv.setItemAnimator(new DefaultItemAnimator());
        mContactListRv.setAdapter(mInviteFriendListAdapter);
        mInviteFriendListAdapter.setmContactList(mContactList);
        mInviteFriendListAdapter.notifyDataSetChanged();
    }

    private void initVariables() {
        mContext=getContext();
        mActivity=getActivity();
        mAccountDetailHolder=new AccountDetailHolder(mContext);
        mContactList=new ArrayList<>();
        mContactSelectedList=new ArrayList<>();
        mContactSelecteionStatusList=new ArrayList<>();
    }

    @OnClick({R.id.invite_friend_select_all_cb})
    public void onClickAllSelectCB() {
        if(mSelectAllCb.isChecked()) {
            updateContactSelectionList(true);
            mContactSelectedList.clear();
            mContactSelectedList.addAll(mContactList);
        } else {
            updateContactSelectionList(false);
            mContactSelectedList.clear();
        }
        mInviteFriendListAdapter.setmContactSelectionStatusList(mContactSelecteionStatusList);
        mInviteFriendListAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.invite_friend_btn})
    public void onClickInviteBtn() {
        if(mContactSelectedList.size()>=1) {
            Fragment fragment = new InviteMessageFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
            fragmentTransaction.addToBackStack(CURRENT_TAG);
            fragmentTransaction.commit();
        } else {
            AppToast.showToast(mContext,"First Select the contacts");
        }
    }

    @Override
    public void contactSelected(ContactDetail contactDetail,int position, boolean isSelected) {
        if(isSelected) {
            mContactSelectedList.add(contactDetail);
            mContactSelecteionStatusList.set(position,true);
        } else {
            mContactSelecteionStatusList.set(position,false);
            ContactDetail detail;
            for(int i=0;i<mContactSelectedList.size();i++) {
                detail=mContactSelectedList.get(i);
                if(detail.getmPhone().equals(contactDetail.getmPhone())) {
                    mContactSelectedList.remove(i);
                    break;
                }
            }
        }
    }
}