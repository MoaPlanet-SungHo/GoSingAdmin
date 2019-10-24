package com.moaplanet.gosingadmin.main.submenu.notification;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;


public class NotificationActivity extends BaseActivity {

    private RecyclerView rvNotification;

    @Override
    public int layoutRes() {
        return R.layout.activity_notification;
    }

    @Override
    public void initView() {
        rvNotification = findViewById(R.id.rv_notification);
        rvNotification.setLayoutManager(new LinearLayoutManager(this));
        NotificationAdapter notificationAdapter = new NotificationAdapter();
        rvNotification.setAdapter(notificationAdapter);
    }

    @Override
    public void initListener() {

    }
}
