package com.moaplanet.gosingadmin.main.qrpayment;


import android.widget.TextView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Qr Code 금액입력 및 생성 Activity
 */
public class PaymentQrActivity extends BaseActivity {

    private CommonTitleBar titleBar;
    private TextView tvFiveTimer;
    TimerTask timerTask;
    Timer timer = new Timer();

    @Override
    public int layoutRes() {
        return R.layout.activity_input_qr;
    }

    @Override
    public void initView() {
        titleBar = findViewById(R.id.common_inputqr_title_bar);
        titleBar.setTitle("결제 QR 코드");
        tvFiveTimer = findViewById(R.id.show_five_minute);
        startTimerTask();
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    private void startTimerTask() {
        stopTimerTask();

        timerTask = new TimerTask() {
            int count = 300;

            @Override
            public void run() {
                count--;
                tvFiveTimer.post(new Runnable() {
                    @Override
                    public void run() {

                        int day = count / (60 * 60 * 24);
                        int hour = (count - day * 60 * 60 * 24) / (60 * 60);
                        int minute = (count - day * 60 * 60 * 24 - hour * 3600) / 60;
                        int second = count % 60;
                        tvFiveTimer.setText("0"+minute +":"+ second);
//                        tvFiveTimer.setText(count + " 초");

                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private void stopTimerTask() {
        if (timerTask != null) {
            tvFiveTimer.setText("60 초");
            timerTask.cancel();
            timerTask = null;
        }
    }
}

