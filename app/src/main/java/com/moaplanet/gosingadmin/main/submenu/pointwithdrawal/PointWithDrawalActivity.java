package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal;

import android.os.Bundle;

import androidx.navigation.Navigation;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.constants.GoSingConstants;


/**
 * 인출 화면
 */
public class PointWithDrawalActivity extends BaseActivity {

    @Override
    public int layoutRes() {
        return R.layout.activity_point_withdrawal;
    }

    @Override
    public void initView() {
        Bundle bundle = new Bundle();
        bundle.putString(GoSingConstants.BUNDLE_REQUEST_FROM_VIEW, AccountResisterFragment.BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_REGISTER);
        Navigation.findNavController(this, R.id.fragment_activity_point_withdrawal).navigate(R.id.action_fragment_account_register, bundle);
    }

    @Override
    public void initListener() {

    }
}
