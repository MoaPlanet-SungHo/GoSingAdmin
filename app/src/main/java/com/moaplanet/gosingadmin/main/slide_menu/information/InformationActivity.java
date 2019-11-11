package com.moaplanet.gosingadmin.main.slide_menu.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.main.slide_menu.information.model.InformationViewModel;
import com.moaplanet.gosingadmin.main.slide_menu.information.model.dto.res.ResInformationDto;
import com.moaplanet.gosingadmin.main.slide_menu.setting.SettingDetailDataActivity;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import retrofit2.Call;

public class InformationActivity extends BaseActivity implements View.OnClickListener {

    private CommonTitleBar titleVIew;
    private InformationViewModel informationViewModel;

    private TextView tvUserEmail, tvPw, tvPhoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMyInfoServer();
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_my_info;
    }


    @Override
    public void initView() {

        informationViewModel = ViewModelProviders.of(this).get(InformationViewModel.class);

        titleVIew = findViewById(R.id.common_information_title_bar);
        titleVIew.setTitle("내 정보");

        tvUserEmail = findViewById(R.id.my_info_email_content);
        tvPw = findViewById(R.id.tv_activity_my_info_pw);
        tvPhoneNumber = findViewById(R.id.tv_activity_my_info_phone_number);

    }

    @Override
    public void initListener() {

        informationViewModel.getPhoneNumber().observe(this,
                phoneNumber -> tvPhoneNumber.setText(phoneNumber));

        informationViewModel.getPwd().observe(this,
                pw -> tvPw.setText(pw));

        informationViewModel.getUserEmail().observe(this,
                email -> tvUserEmail.setText(email));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SettingDetailDataActivity.class);
        switch (v.getId()) {
            case R.id.terms_service:
                intent.putExtra("url", "1");
                startActivity(intent);
                break;
        }
    }

    private void getMyInfoServer() {
        RetrofitService.getInstance().getGoSingApiService().onMyInfo()
                .enqueue(new MoaAuthCallback<ResInformationDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResInformationDto> call, ResInformationDto resModel) {
                        if (resModel.getStateCode() == NetworkConstants.STATE_CODE_SUCCESS) {
                            if (resModel.getStateCode() == 200) {
                                informationViewModel.setUserInfoDto(resModel.getInformationDto());
                                return;
                            }
                        }
                        errMsg();
                    }

                    @Override
                    public void onFinalFailure(Call<ResInformationDto> call, boolean isSession, Throwable t) {
                        errMsg();
                    }
                });
    }

    private void errMsg() {
        Toast.makeText(this, "잠시후 다시 실행해 주세요.", Toast.LENGTH_SHORT).show();
    }

}
