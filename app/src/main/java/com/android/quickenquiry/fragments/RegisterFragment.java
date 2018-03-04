package com.android.quickenquiry.fragments;

/**
 * Created by user on 3/2/2018.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.quickenquiry.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterFragment extends Fragment {


    @BindView(R.id.register_login_text_tv)
    TextView mLoginTv;
    private static final String CURRENT_TAG=RegisterFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_register,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        init();
    }

    private void init() {
        initVariables();
    }

    private void initVariables() {
    }

    @OnClick({R.id.register_login_text_tv})
    public void onClickLoginTv() {
        openLoginFragment();
    }

    private void openLoginFragment() {
        Fragment fragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.login_signUp_frame, fragment, CURRENT_TAG);
        //fragmentTransaction.addToBackStack(CURRENT_TAG);
        fragmentTransaction.commit();
    }


}
