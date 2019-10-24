package com.moaplanet.gosingadmin.main.qrpayment;


import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

/**
 * Qr Code 금액입력 및 생성 Activity
 */
public class PaymentQrActivity extends BaseActivity {

    private CommonTitleBar titleBar;

    @Override
    public int layoutRes() {
        return R.layout.activity_input_qr;
    }

    @Override
    public void initView() {
        titleBar = findViewById(R.id.common_inputqr_title_bar);
        titleBar.setTitle("결제 QR 코드");
    }

    @Override
    public void initListener() {

    }
}
