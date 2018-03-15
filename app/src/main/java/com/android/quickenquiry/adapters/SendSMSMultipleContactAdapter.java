package com.android.quickenquiry.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.android.quickenquiry.R;
import com.android.quickenquiry.activities.MainDashboardActivity;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;
import java.util.ArrayList;

/**
 * Created by user on 3/8/2018.
 */

public class SendSMSMultipleContactAdapter extends ArrayAdapter<ContactDetail> {


    /*@BindView(R.id.send_sms_single_contact_item_name_tv)
    TextView mNameTv;*/
    private Context mContext;
    private Activity mActivity;
    private ArrayList<ContactDetail> mContactList;
    private int layoutResId;

    public SendSMSMultipleContactAdapter(Context activity,int resId,ArrayList<ContactDetail> list) {
        super(activity,resId,list);
        mContext=activity;
        mContactList=new ArrayList<>();
        mContactList.addAll(list);
    }

    @Override
    public int getCount() {
        return mContactList.size();
    }

    @Override
    public ContactDetail getItem(int position) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        //View view=convertView;
        if(convertView==null) {
            LayoutInflater inflater= ((MainDashboardActivity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.layout_send_sms_contact_list, parent, false);
        }
        //ButterKnife.bind(this,view);//kammal h janaab aapki to salute acha suno abhi mat jao ruko
        TextView mNameTv=(TextView)convertView.findViewById(R.id.send_sms_single_contact_item_name_tv) ;
        mNameTv.setText(mContactList.get(position).getmName()+ " ("+mContactList.get(position).getmPhone()+")");
        //init(position);
        return convertView;
    }
    //acha ab ye wala ho gya na ?

    private void init(int position) {
        initVariables(position);
    }

    private void initVariables(int pos) {
        //mNameTv.setText(mContactList.get(pos).getmPhone()+ " ("+mContactList.get(pos).getmPhone()+")");
    }

}