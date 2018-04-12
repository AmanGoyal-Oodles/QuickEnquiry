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
import com.android.quickenquiry.utils.util.pojoClasses.ContactType;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Cortana on 4/12/2018.
 */

public class SelectContactTypeAdapter extends RecyclerView.Adapter<SelectContactTypeAdapter.SelectContactTypeHolder> {


    private Context mContext;
    private ArrayList<ContactType> mContactList,mSelectedContactList;

    public SelectContactTypeAdapter(Context context,ArrayList<ContactType> contactList,ArrayList<ContactType> selectedContactList) {
        mContext=context;
        mContactList=new ArrayList<>();
        mContactList.clear();
        mContactList.addAll(contactList);
        mSelectedContactList=new ArrayList<>();
        mSelectedContactList.clear();
        mSelectedContactList.addAll(selectedContactList);
    }

    @Override
    public SelectContactTypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_select_contact_item,parent,false);
        return new SelectContactTypeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SelectContactTypeHolder holder, int position) {
        if(position%2!=0) {
            holder.mLayout.setBackgroundColor(mContext.getResources().getColor(R.color.LightGray));
        } else {
            holder.mLayout.setBackgroundColor(mContext.getResources().getColor(R.color.White));
        }
        holder.mNameTv.setText(mContactList.get(position).getContactTypeName());
        boolean flag=false;
        for(int i=0;i<mSelectedContactList.size();i++) {
            ContactType contactDetail=mSelectedContactList.get(i);
            if(contactDetail.getContactTypeId().equalsIgnoreCase(mContactList.get(position).getContactTypeId())) {
                flag=true;
            }
        }
        holder.mCheckbox.setChecked(flag);

    }

    public ArrayList<ContactType> getSelectedContactDetail() {
        return mSelectedContactList;
    }

    public void setContactList(ArrayList<ContactType> contactList, ArrayList<ContactType> selectedContactList) {
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

    public class SelectContactTypeHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.select_contact_item_name_tv)
        TextView mNameTv;
        @BindView(R.id.select_contact_item_checkbox)
        CheckBox mCheckbox;
        @BindView(R.id.select_contact_item_layout)
        RelativeLayout mLayout;

        public SelectContactTypeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick({R.id.select_contact_item_checkbox})
        public void onClickCheckBox() {
            int position=getAdapterPosition();
            if(!mCheckbox.isChecked()) {
                ContactType contactDetail=mContactList.get(position);
                for(int i=0;i<mSelectedContactList.size();i++) {
                    if(contactDetail.getContactTypeId().equalsIgnoreCase(mSelectedContactList.get(i).getContactTypeId())) {
                        mSelectedContactList.remove(i);
                    }
                }
            } else {
                mSelectedContactList.add(mContactList.get(position));
            }
        }
    }
}