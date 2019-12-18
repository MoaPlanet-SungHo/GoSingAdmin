package com.moaplanet.gosingadmin.main.submenu.notification;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.main.submenu.notification.dto.res.ResNotificationDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 알림 화면
 */
public class NotificationActivity extends BaseActivity {

    private NotificationAdapter mNotificationAdapter;

    @Override
    public int layoutRes() {
        return R.layout.activity_notification;
    }

    @Override
    public void initView() {
        RecyclerView recyclerView = findViewById(R.id.rv_activity_notification);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNotificationAdapter = new NotificationAdapter();
        recyclerView.setAdapter(mNotificationAdapter);
        onNotificationList();
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

    private void onNotificationList() {
        RetrofitService.getInstance().getGoSingApiService().onServerNotificationList(null, null)
                .enqueue(new MoaAuthCallback<ResNotificationDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResNotificationDto> call, ResNotificationDto resModel) {

                        if (resModel.getStateCode() == NetworkConstants.STATE_CODE_SUCCESS) {
                            if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                                mNotificationAdapter.setList(resModel.getNotificationDtoList());
                                return;
                            }
                        }

                        onNetworkConnectFail();

                    }

                    @Override
                    public void onFinalFailure(Call<ResNotificationDto> call, boolean isSession, Throwable t) {
                        onNetworkConnectFail();
                    }
                });
    }

}
