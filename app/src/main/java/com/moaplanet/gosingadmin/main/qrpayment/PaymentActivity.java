package com.moaplanet.gosingadmin.main.qrpayment;


import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.moaplanet.gosingadmin.BuildConfig;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

/**
 * Qr Code 금액입력 및 생성 Activity
 */
public class PaymentActivity extends BaseActivity {

    private CommonTitleBar titleBar;

    private EditText input_save_money;
    private EditText input_no_save_money;
    private ImageView save_money_clear_btn;
    private ImageView no_save_money_clear_btn;
    private InputMethodManager imm;
    private Button btnNext;


    @Override
    public int layoutRes() {
        return R.layout.activity_input_money;
    }

    @Override
    public void initView() {
        titleBar = findViewById(R.id.common_inputmoney_title_bar);
        titleBar.setTitle("결제금액");


        input_save_money = findViewById(R.id.et_main_input_save_money);
        input_no_save_money = findViewById(R.id.et_main_input_no_save_money);
        save_money_clear_btn = findViewById(R.id.iv_main_save_money_clear);
        no_save_money_clear_btn = findViewById(R.id.iv_main_no_save_money_clear);
        btnNext = findViewById(R.id.make_qr_code);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    @Override
    public void initListener() {
        btnNext.setOnClickListener(view -> moveActivity( PaymentQrActivity.class));

        save_money_clear_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                input_save_money.setText("");
                input_save_money.requestFocus();
                imm.showSoftInput(input_save_money, 0);
            }
        });

        // no_save_money_clear_btn 클릭시 input_no_save_money EditText필드 초기화후, Focus하고, 키보드 올림
        no_save_money_clear_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                input_no_save_money.setText("");
                input_no_save_money.requestFocus();
                imm.showSoftInput(input_no_save_money, 0);
            }
        });
    }



    private void moveActivity(Class moveClass) {
        Intent intent = new Intent(this, moveClass);
        startActivity(intent);
    }
}
