package com.moaplanet.gosingadmin.main.submenu.non_member.activity;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.main.submenu.non_member.model.NonMemberSavePointViewModel;

public class NonMemberSaveActivity extends BaseActivity {

    @Override
    public int layoutRes() {
        return R.layout.activity_non_member_save;
    }

    @Override
    public void initView() {
        View loadingBar = findViewById(R.id.pb_activity_non_member_save_loading);
        loadingBar.setVisibility(View.GONE);
        setLoadingBar(loadingBar);
    }

    @Override
    public void initListener() {
        // 뷰 모델
        NonMemberSavePointViewModel mViewModel = ViewModelProviders.of(this).get(NonMemberSavePointViewModel.class);
//        mViewModel.getIsLoading().observe(this, isLoading -> mIsLoading = isLoading);
    }
}
