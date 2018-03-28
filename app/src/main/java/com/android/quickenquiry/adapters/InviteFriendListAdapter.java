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
import com.android.quickenquiry.interfaces.ContactSelectionListener;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/8/2018.
 */

public class InviteFriendListAdapter extends RecyclerView.Adapter<InviteFriendListAdapter.InviteFriendListHolder> {


    private Context mContext;
    private ArrayList<ContactDetail> mContactList;
    private ContactSelectionListener mContactSelectionListener;
    private ArrayList<Boolean> mContactSelectionStatusList;

    public InviteFriendListAdapter(Context context,ContactSelectionListener listener) {
        mContext=context;
        mContactList=new ArrayList<>();
        mContactSelectionStatusList=new ArrayList<>();
        mContactSelectionListener=listener;
    }

    @Override
    public InviteFriendListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_invite_friend_contact_list_item,parent,false);
        return new InviteFriendListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InviteFriendListHolder holder, int position) {
        if(position%2!=0) {
            holder.mLayout.setBackgroundColor(mContext.getResources().getColor(R.color.LightGray));
        } else {
            holder.mLayout.setBackgroundColor(mContext.getResources().getColor(R.color.White));
        }
        if(mContactSelectionStatusList.get(position)) {
            holder.mCheckBox.setChecked(true);
        } else {
            holder.mCheckBox.setChecked(false);
        }
        holder.mNameTv.setText(mContactList.get(position).getmName());
        holder.mPhoneTv.setText(mContactList.get(position).getmPhone());
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    public void setmContactList(ArrayList<ContactDetail> list,ArrayList<Boolean> selectedList) {
        mContactList.clear();
        mContactList.addAll(list);
        mContactSelectionStatusList.clear();
        mContactSelectionStatusList.addAll(selectedList);
    }

    public void setmContactSelectionStatusList(ArrayList<Boolean> list) {
        mContactSelectionStatusList.clear();
        mContactSelectionStatusList.addAll(list);
    }

    public class InviteFriendListHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.invite_friend_item_cb)
        CheckBox mCheckBox;
        @BindView(R.id.invite_friend_item_name_tv)
        TextView mNameTv;
        @BindView(R.id.invite_friend_item_phone_tv)
        TextView mPhoneTv;
        @BindView(R.id.invite_friend_item_layout)
        RelativeLayout mLayout;

        public InviteFriendListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick({R.id.invite_friend_item_cb})
        public void onClickCheckBox() {
            int position=getAdapterPosition();
            if(mCheckBox.isChecked()) {
                mContactSelectionStatusList.set(position,true);
                mContactSelectionListener.contactSelected(mContactList.get(position),position,true);
            } else {
                mContactSelectionStatusList.set(position,false);
                mContactSelectionListener.contactSelected(mContactList.get(position),position,false);
            }
        }

    }
}