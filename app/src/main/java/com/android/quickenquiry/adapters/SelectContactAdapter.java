package com.android.quickenquiry.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.quickenquiry.R;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Cortana on 4/12/2018.
 */

public class SelectContactAdapter extends RecyclerView.Adapter<SelectContactAdapter.SelectContactHolder> {


    private Context mContext;
    private ArrayList<ContactDetail> mContactList,mSelectedContactList;

    public SelectContactAdapter(Context context,ArrayList<ContactDetail> contactList,ArrayList<ContactDetail> selectedContactList) {
        mContext=context;
        mContactList=new ArrayList<>();
        mContactList.clear();
        mContactList.addAll(contactList);
        mSelectedContactList=new ArrayList<>();
        mSelectedContactList.clear();
        mSelectedContactList.addAll(selectedContactList);
    }

    @Override
    public SelectContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_select_contact_item,parent,false);
        return new SelectContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SelectContactHolder holder, int position) {
        if(position%2!=0) {
            holder.mLayout.setBackgroundColor(mContext.getResources().getColor(R.color.LightGray));
        } else {
            holder.mLayout.setBackgroundColor(mContext.getResources().getColor(R.color.White));
        }
        holder.mNameTv.setText(mContactList.get(position).getmName()+ " ("+mContactList.get(position).getmPhone()+")");
        boolean flag=false;
        for(int i=0;i<mSelectedContactList.size();i++) {
            ContactDetail contactDetail=mSelectedContactList.get(i);
            if(contactDetail.getmPhone().equalsIgnoreCase(mContactList.get(position).getmPhone())) {
                flag=true;
            }
        }
        holder.mCheckbox.setChecked(flag);
    }

    public ArrayList<ContactDetail> getSelectedContactDetail() {
        return mSelectedContactList;
    }

    public void setContactList(ArrayList<ContactDetail> contactList,ArrayList<ContactDetail> selectedContactList) {
        mContactList.clear();
        mContactList.addAll(contactList);
        mSelectedContactList.clear();
        mSelectedContactList.addAll(selectedContactList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    public class SelectContactHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.select_contact_item_name_tv)
        TextView mNameTv;
        @BindView(R.id.select_contact_item_checkbox)
        CheckBox mCheckbox;
        @BindView(R.id.select_contact_item_layout)
        RelativeLayout mLayout;

        public SelectContactHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnCheckedChanged({R.id.select_contact_item_checkbox})
        public void onClickCheckBox() {
            int position=getAdapterPosition();
            if(!mCheckbox.isChecked()) {
                ContactDetail contactDetail=mContactList.get(position);
                for(int i=0;i<mSelectedContactList.size();i++) {
                    if(contactDetail.getmPhone().equalsIgnoreCase(mSelectedContactList.get(i).getmPhone())) {
                        mSelectedContactList.remove(i);
                    }
                }
            } else {
                mSelectedContactList.add(mContactList.get(position));
            }
        }
    }
}