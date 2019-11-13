package com.moaplanet.gosingadmin.main.submenu.notification;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.main.submenu.notification.dto.res.ResNotificationDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import retrofit2.Call;


public class NotificationActivity extends BaseActivity {

    private RecyclerView rvNotification;
    private NotificationAdapter notificationAdapter;
    // 타이틀
    private CommonTitleBar titleBar;

    @Override
    public int layoutRes() {
        return R.layout.activity_notification;
    }

    @Override
    public void initView() {
        rvNotification = findViewById(R.id.rv_notification);
        rvNotification.setLayoutManager(new LinearLayoutManager(this));
        notificationAdapter = new NotificationAdapter();
        rvNotification.setAdapter(notificationAdapter);

        titleBar = findViewById(R.id.common_notification_title_bar);

    }

    @Override
    public void initListener() {
        titleBar.setBackButtonClickListener(view -> finish());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNotificationList();
    }

    private void onNotificationList() {
        RetrofitService.getInstance().getGoSingApiService().onNotificationList(null, null)
                .enqueue(new MoaAuthCallback<ResNotificationDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResNotificationDto> call, ResNotificationDto resModel) {

                        if (resModel.getStateCode() == NetworkConstants.STATE_CODE_SUCCESS) {
                            if (resModel.getDetailCode() == 200) {
                                notificationAdapter.setList(resModel.getNotificationDtoList());
                                return;
                            }
                        }

                        serverErrorMsg();

                    }

                    @Override
                    public void onFinalFailure(Call<ResNotificationDto> call, boolean isSession, Throwable t) {
                        serverErrorMsg();
                    }
                });
    }

    private void serverErrorMsg() {
        Toast.makeText(this, "네트워크 에러입니다.", Toast.LENGTH_SHORT).show();
    }
}
