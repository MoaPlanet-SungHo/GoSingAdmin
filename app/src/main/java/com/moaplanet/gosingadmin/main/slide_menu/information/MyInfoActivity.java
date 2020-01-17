package com.moaplanet.gosingadmin.main.slide_menu.information;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class MyInfoActivity extends BaseActivity {

    private MyInfoViewModel viewModel;

    @Override
    public void initActivity() {
        super.initActivity();
        viewModel = ViewModelProviders.of(this).get(MyInfoViewModel.class);
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_my_info;
    }

    @Override
    public void initView() {

//        myInfoViewModel = ViewModelProviders.of(this).get(MyInfoViewModel.class);
//
//        titleVIew = findViewById(R.id.common_information_title_bar);
//        titleVIew.setTitle("내 정보");
//
//        tvUserEmail = findViewById(R.id.my_info_email_content);
//        tvPw = findViewById(R.id.tv_activity_my_info_pw);
//        tvPhoneNumber = findViewById(R.id.tv_activity_my_info_phone_number);
    }

    @Override
    public void initListener() {

        // 뒤로가기
        CommonTitleBar titleBar = findViewById(R.id.common_activity_my_info_title);
        RxView.clicks(titleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> finish());


//        myInfoViewModel.getPhoneNumber().observe(this,
//                phoneNumber -> tvPhoneNumber.setText(phoneNumber));
//
//        myInfoViewModel.getPwd().observe(this,
//                pw -> tvPw.setText(pw));
//
//        myInfoViewModel.getUserEmail().observe(this,
//                email -> tvUserEmail.setText(email));

    }

    @Override
    protected void initObserve() {
        super.initObserve();

        if (viewModel != null) {

            // 이메일 세팅
            viewModel.getEmail().observe(this, email -> {
                TextView tvEmail = findViewById(R.id.tv_activity_my_info_email);
                tvEmail.setText(email);
            });

            // 패스워드 세팅
            viewModel.getPw().observe(this, pw -> {
                TextView tvPw = findViewById(R.id.tv_activity_my_info_pw);
                tvPw.setText(pw);
            });

            // 휴대폰 번호
            viewModel.getPhoneNumber().observe(this, phoneNumber -> {
                TextView tvPhoneNumber = findViewById(R.id.tv_activity_my_info_phone);
                tvPhoneNumber.setText(phoneNumber);
            });

        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.postMyInfo();
    }

    //    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(this, SettingDetailDataActivity.class);
//        switch (v.getId()) {
//            case R.id.terms_service:
//                intent.putExtra("url", "1");
//                startActivity(intent);
//                break;
//        }
//    }

//    private void getMyInfoServer() {
//        RetrofitService.getInstance().getGoSingApiService().onServerMyInformation()
//                .enqueue(new MoaAuthCallback<MyInfoDTO>(
//                        RetrofitService.getInstance().getMoaAuthConfig(),
//                        RetrofitService.getInstance().getSessionChecker()
//                ) {
//                    @Override
//                    public void onFinalResponse(Call<MyInfoDTO> call, MyInfoDTO resModel) {
//                        if (resModel.getStateCode() == NetworkConstants.STATE_CODE_SUCCESS) {
//                            if (resModel.getStateCode() == 200) {
//                                myInfoViewModel.setUserInfoDto(resModel.getInformationDto());
//                                return;
//                            }
//                        }
//                        errMsg();
//                    }
//
//                    @Override
//                    public void onFinalFailure(Call<MyInfoDTO> call, boolean isSession, Throwable t) {
//                        errMsg();
//                    }
//                });
//    }

//    private void errMsg() {
//        Toast.makeText(this, "잠시후 다시 실행해 주세요.", Toast.LENGTH_SHORT).show();
//    }

}
