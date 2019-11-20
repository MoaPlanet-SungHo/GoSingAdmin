package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.activity;

import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model.RegisterWithdrawalAccountViewModel;

/**
 * 출금 계좌 등록 액티비티
 */
public class RegisterAccountActivity extends BaseActivity {

    @Override
    public void initActivity() {
        super.initActivity();
        RegisterWithdrawalAccountViewModel mViewModel =
                ViewModelProviders.of(this).get(RegisterWithdrawalAccountViewModel.class);
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_register_account;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }
}
