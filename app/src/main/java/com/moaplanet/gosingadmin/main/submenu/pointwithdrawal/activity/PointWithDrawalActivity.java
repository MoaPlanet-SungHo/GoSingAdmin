package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.activity;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.main.submenu.charge.model.viewmodel.DepositWithoutBankbookViewModel;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.AccountResisterFragment;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model.DepositAccountViewModel;


/**
 * 인출 화면
 */
public class PointWithDrawalActivity extends BaseActivity {

    private DepositAccountViewModel viewModel;

    @Override
    public int layoutRes() {
        return R.layout.activity_point_withdrawal;
    }

    @Override
    public void initView() {

        viewModel = ViewModelProviders.of(this).get(DepositAccountViewModel.class);

//        Bundle bundle = new Bundle();
//        bundle.putString(GoSingConstants.BUNDLE_REQUEST_FROM_VIEW, AccountResisterFragment.BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_REGISTER);
//        Navigation.findNavController(
//                this,
//                R.id.fragment_activity_point_withdrawal)
//                .navigate(R.id.action_fragment_account_register, bundle);
    }

    @Override
    public void initListener() {

    }
}
