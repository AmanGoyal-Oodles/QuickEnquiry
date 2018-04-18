package com.android.quickenquiry.adapters;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.quickenquiry.R;
import com.android.quickenquiry.fragments.AddContactFragment;
import com.android.quickenquiry.interfaces.GetUpdateContactListener;
import com.android.quickenquiry.interfaces.OnClickContactDeleteListener;
import com.android.quickenquiry.interfaces.apiResponseListener.DeleteContactResponseListener;
import com.android.quickenquiry.services.databases.preferences.AccountDetailHolder;
import com.android.quickenquiry.services.databases.preferences.webServices.apiRequests.DeleteContactApi;
import com.android.quickenquiry.utils.util.CheckPermission;
import com.android.quickenquiry.utils.util.dialogs.ShowDialog;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/7/2018.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private Context mContext;
    private ArrayList<ContactDetail> mContactList;
    private Activity mActivity;
    private AccountDetailHolder mAccountDetailHolder;
    private GetUpdateContactListener mGetUpdateContactListener;
    private OnClickContactDeleteListener mOnClickContactDeleteListener;

    public ContactAdapter(Context context, Activity activity, GetUpdateContactListener listener, OnClickContactDeleteListener contactDeletelistener) {
        mContext = context;
        mActivity=activity;
        mContactList = new ArrayList<>();
        mGetUpdateContactListener = listener;
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        mOnClickContactDeleteListener = contactDeletelistener;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_contact_list_item, parent, false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        if (position % 2 != 0) {
            holder.mLayout.setBackgroundColor(mContext.getResources().getColor(R.color.White));
        } else {
            holder.mLayout.setBackgroundColor(mContext.getResources().getColor(R.color.LightGray));
        }
        holder.mSnTv.setText(position + 1 + "");
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
        notifyDataSetChanged();
    }

    public class ContactHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.contact_list_item_sn_tv)
        TextView mSnTv;
        @BindView(R.id.contact_list_item_name_tv)
        TextView mNameTv;
        @BindView(R.id.contact_list_item_phone_tv)
        TextView mPhoneTv;
        @BindView(R.id.contact_list_item_delete_iv)
        ImageView mDeleteIv;
        @BindView(R.id.contact_list_item_edit_iv)
        ImageView mEditIv;
        @BindView(R.id.contact_list_item_layout)
        LinearLayout mLayout;
        @BindView(R.id.contact_item_detail_layout)
        LinearLayout mDetailLayout;

        private ProgressDialog mProgressDialog;
        private int position;

        public ContactHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.contact_list_item_delete_iv})
        public void onClickDelete() {
            mOnClickContactDeleteListener.onClickContactDelete(mContactList.get(getAdapterPosition()).getContactid());
        }

        @OnClick({R.id.contact_list_item_edit_iv})
        public void onClickEdit() {
            mGetUpdateContactListener.getContactPosition(mContactList.get(getAdapterPosition()));
        }

        @OnClick({R.id.contact_item_detail_layout})
        public void onClickDetailLayout() {
            int position = getAdapterPosition();
            String phoneNumber = mContactList.get(position).getmPhone();
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                mAccountDetailHolder.setCallNumber(phoneNumber);
                if(CheckPermission.checkPermissionForCall(mActivity)) {
                    mContext.startActivity(intent);
                }
                return;
            }
            mAccountDetailHolder.setCallNumber("");
            mContext.startActivity(intent);
        }

    }

}