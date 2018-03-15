package com.android.quickenquiry.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.android.quickenquiry.R;
import com.android.quickenquiry.activities.MainDashboardActivity;
import com.android.quickenquiry.utils.util.Logger;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/8/2018.
 */

public class SendSMSSingleContactAdapter extends ArrayAdapter<ContactDetail> {


    @BindView(R.id.send_sms_single_contact_item_name_tv)
    TextView mNameTv;
    private Context mContext;
    private Activity mActivity;
    private ArrayList<ContactDetail> mContactList;
    private ArrayList<ContactDetail> fullList;
    private int layoutResId;
    private ArrayFilter mFilter;


    public SendSMSSingleContactAdapter(Context activity,int resId,ArrayList<ContactDetail> list) {
        super(activity,resId,list);
        mContext=activity;
        mContactList=new ArrayList<>();
        mContactList.addAll(list);
        //inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mContactList.size();
    }

    @Override
    public ContactDetail getItem(int position) {
        Logger.LogDebug("hello","getitem"+position);
        return mContactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setmContactList(ArrayList<ContactDetail> list) {
        mContactList.clear();
        mContactList.addAll(list);
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater inflater= ((MainDashboardActivity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.layout_send_sms_contact_list, parent, false);
        }
        ButterKnife.bind(this,convertView);
        mNameTv.setText(mContactList.get(position).getmName()+ " ("+mContactList.get(position).getmPhone()+")");
        if(position%2!=0) {
            mNameTv.setBackgroundColor(mContext.getResources().getColor(R.color.LightGray));
        } else {
            mNameTv.setBackgroundColor(mContext.getResources().getColor(R.color.White));
        }
        return convertView;
    }

    private class ArrayFilter extends Filter {
        private Object lock;

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (fullList == null) {
                    fullList = new ArrayList<ContactDetail>(mContactList);
            }

            if (prefix == null || prefix.length() == 0) {
                    ArrayList<ContactDetail> list = new ArrayList<ContactDetail>(fullList);
                   // ArrayList<String> list1 = new ArrayList<String>();
                   /* for (ContactDetail contactDetail :mContactList){
                        list1.add(contactDetail.getmName());
                    }*/
                    results.values = list;
                    results.count = list.size();

            } else {
                final String prefixString = prefix.toString().toLowerCase();

                ArrayList<ContactDetail> values = fullList;
                int count = values.size();

                ArrayList<ContactDetail> newValues = new ArrayList<ContactDetail>(count);

                for (int i = 0; i < count; i++) {
                    ContactDetail item = values.get(i);
                    if (item.getmName().toLowerCase().contains(prefixString)||item.getmPhone().contains(prefixString)) {
                        newValues.add(item);
                    }

                }
                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (results.values != null) {
                mContactList = (ArrayList<ContactDetail>) results.values;
            } else {
                mContactList = new ArrayList<ContactDetail>(fullList);
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

}