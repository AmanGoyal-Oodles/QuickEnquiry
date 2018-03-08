package com.android.quickenquiry.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.android.quickenquiry.R;
import com.android.quickenquiry.adapters.InviteFriendListAdapter;
import com.android.quickenquiry.interfaces.ContactSelectionListener;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 3/8/2018.
 */

public class InviteFriendFragment extends Fragment implements ContactSelectionListener{


    @BindView(R.id.invite_friend_select_all_cb)
    CheckBox mSelectAllCb;
    @BindView(R.id.invite_friend_contact_list_rv)
    RecyclerView mContactListRv;
    private Context mContext;
    private Activity mActivity;
    private InviteFriendListAdapter mInviteFriendListAdapter;
    private AccountDetailHolder mAccountDetailHolder;
    private ArrayList<ContactDetail> mContactList,mContactSelectedList;

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
        init();
    }

    private void init() {
        initVariables();
        setRecyclerView();
        mContactList=mAccountDetailHolder.getContactList();
    }

    private void setRecyclerView() {
        mInviteFriendListAdapter=new InviteFriendListAdapter(mContext,this);
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
    }

    @Override
    public void contactSelected(ContactDetail contactDetail, boolean isSelected) {
        if(isSelected) {
            mContactSelectedList.add(contactDetail);
        } else {
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
