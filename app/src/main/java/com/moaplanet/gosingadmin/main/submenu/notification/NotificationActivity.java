package com.moaplanet.gosingadmin.main.submenu.notification;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

/**
 * 알림 화면
 */
public class NotificationActivity extends BaseActivity {

    // 알림 리스트 어댑터
    private NotificationAdapter mAdapter;
    // 뷰모델
    private NotificationViewModel mViewModel;

    @Override
    public void initActivity() {
        super.initActivity();
        mViewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_notification;
    }

    @Override
    public void initView() {
        RecyclerView recyclerView = findViewById(R.id.rv_activity_notification);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NotificationAdapter();
        recyclerView.setAdapter(mAdapter);
        mViewModel.onLoadNotificationList();
//        onNotificationList();
    }

    @Override
    public void initListener() {
        // 타이틀
        CommonTitleBar titleBar = findViewById(R.id.common_activity_notification_title_bar);
        RxView.clicks(titleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> finish());
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mViewModel.getNotificationList().observe(this, list -> {
            mAdapter.setList(list);
        });

        mViewModel.getSession().observe(this, isSession -> {
            if (!isSession) {
                onNotSession();
            }
        });

        mViewModel.getIsApiSuccess().observe(this, isSuccess -> {
            if (!isSuccess) {
                onNetworkConnectFail();
            }
        });

    }

}
