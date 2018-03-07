package com.android.quickenquiry.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.quickenquiry.R;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/7/2018.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder>{

    private Context mContext;
    private ArrayList<ContactDetail> mContactList;

    public ContactAdapter(Context context) {
       mContext=context;
       mContactList=new ArrayList<>();
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_contact_list_item,parent,false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        holder.mNameTv.setText(mContactList.get(position).getmName());
        holder.mPhoneTv.setText(mContactList.get(position).getmPhone());
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    public void setContactList(ArrayList<ContactDetail> list) {
        mContactList.clear();
        mContactList.addAll(list);
    }

    public class ContactHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.contact_list_item_name_tv)
        TextView mNameTv;
        @BindView(R.id.contact_list_item_phone_tv)
        TextView mPhoneTv;
        @BindView(R.id.contact_list_item_delete_iv)
        ImageView mDeleteIv;
        @BindView(R.id.contact_list_item_edit_iv)
        ImageView mEditIv;

        public ContactHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick({R.id.contact_list_item_delete_iv})
        public void onClickDelete() {

        }

        @OnClick({R.id.contact_list_item_edit_iv})
        public void onClickEdit() {

        }

    }
}