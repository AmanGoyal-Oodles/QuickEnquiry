package com.android.quickenquiry.dialoges;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.ResetPassDialogListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/5/2018.
 */

public class NewPasswordDialog extends Dialog {


    @BindView(R.id.reset_pass_submit_btn)
    Button mSubmitBtn;
    @BindView(R.id.reset_pass_cancel_btn)
    Button mCancelBtn;
    private Context mContext;
    private ResetPassDialogListener mResetPassDialogListener;

    public NewPasswordDialog(@NonNull Context context,ResetPassDialogListener listener) {
        super(context);
        mContext=context;
        mResetPassDialogListener=listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_password);
        ButterKnife.bind(this);
        setCancelable(false);
        Window window=getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @OnClick({R.id.reset_pass_cancel_btn})
    public void onClickCancelBtn() {
        dismiss();
        mResetPassDialogListener.isPasswordReset(false);
    }

    @OnClick({R.id.reset_pass_submit_btn})
    public void onClickSubmitBtn() {
        dismiss();
        mResetPassDialogListener.isPasswordReset(true);
    }

}