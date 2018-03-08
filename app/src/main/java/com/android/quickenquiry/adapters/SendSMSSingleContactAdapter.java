package com.android.quickenquiry.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.quickenquiry.R;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 3/8/2018.
 */

public class SendSMSSingleContactAdapter extends BaseAdapter {


    /*@BindView(R.id.send_sms_single_contact_item_name_tv)
    TextView mNameTv;*/
    private Context mContext;
    private Activity mActivity;
    private ArrayList<ContactDetail> mContactList;
    private static LayoutInflater inflater=null;

    public SendSMSSingleContactAdapter(Activity activity,ArrayList<ContactDetail> list) {
        mActivity=activity;
        mContactList=new ArrayList<>();
        mContactList.addAll(list);
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mContactList.size();
    }

    @Override
    public Object getItem(int position) {
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
        View view=convertView;
        if(convertView==null)
            view=inflater.inflate(R.layout.layout_send_sms_contact_list,null);
        //ButterKnife.bind(this,view);
        TextView mNameTv=(TextView)view.findViewById(R.id.send_sms_single_contact_item_name_tv) ;
        mNameTv.setText(mContactList.get(position).getmPhone()+ " ("+mContactList.get(position).getmPhone()+")");
        //init(position);
        return view;
    }

    private void init(int position) {
        initVariables(position);
    }

    private void initVariables(int pos) {
        //mNameTv.setText(mContactList.get(pos).getmPhone()+ " ("+mContactList.get(pos).getmPhone()+")");
    }

}