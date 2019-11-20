package com.moaplanet.gosingadmin.main.submenu.store.activity;

import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;


public class WaitingApprovalActivity extends BaseActivity {

    private Button btnFinish;
    private CommonTitleBar commonTitleBar;

    @Override
    public int layoutRes() {
        return R.layout.activity_wating_approval;
    }

    @Override
    public void initView() {
        btnFinish = findViewById(R.id.btn_waiting_approval_finish);
        commonTitleBar = findViewById(R.id.common_waiting_approval_title_bar);
        commonTitleBar.setChangeLeftIc(R.drawable.ic_common_title_bar_back);
    }

    @Override
    public void initListener() {
        RxView.clicks(commonTitleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> finishAffinity());

        RxView.clicks(btnFinish)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> finishAffinity());
    }
}
