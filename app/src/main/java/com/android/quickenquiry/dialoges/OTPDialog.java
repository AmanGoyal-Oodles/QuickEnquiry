package com.android.quickenquiry.dialoges;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.quickenquiry.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/3/2018.
 */

public class OTPDialog extends Dialog {


    @BindView(R.id.otp_text_et)
    EditText mOTPEt;
    @BindView(R.id.otp_cancel_btn)
    Button mCancelBtn;
    @BindView(R.id.otp_validate_btn)
    Button mValidateBtn;
    private Context mContext;

    public OTPDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_otp);
        ButterKnife.bind(this);
        setCancelable(false);
        Window window=getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @OnClick({R.id.otp_cancel_btn})
    public void onClickCancelBtn() {
        dismiss();
    }

    @OnClick({R.id.otp_validate_btn})
    public void onClickValidate() {
        dismiss();
    }

}
