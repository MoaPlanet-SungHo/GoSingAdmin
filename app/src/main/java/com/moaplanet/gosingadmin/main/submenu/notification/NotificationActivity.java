package com.moaplanet.gosingadmin.main.submenu.notification;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.orhanobut.logger.Logger;

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

        // 알림 리스트 데이터를 받아옴
        mViewModel.getNotificationList().observe(this, list -> mAdapter.setList(list));

        // 세션 유무 -> 세션 없을 경우만 호출
        mViewModel.getSession().observe(this, isSession -> {
            Logger.i("세션 유무 : " + isSession);
            if (!isSession) {
                onNotSession();
            }
        });

        // 서버 통신 성공 유무 -> 실패시에만 호출
        mViewModel.getIsApiSuccess().observe(this, isSuccess -> {
            Logger.i("서버 통신 유무 : " + isSuccess);
            if (!isSuccess) {
                onNetworkConnectFail();
            }
        });

        // 로딩 유무를
        mViewModel.getIsLoading().observe(this, isLoading -> {
            Logger.i("isLoading : " + isLoading);
        });

    }

}
