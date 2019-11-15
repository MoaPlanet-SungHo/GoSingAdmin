package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.activity;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.intro.login.LoginActivity;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model.ResBankInfoDto;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import retrofit2.Call;

/**
 * 은행 선택 화면
 */
public class BankSelectActivity extends BaseActivity {

    // 로딩 유무
    private boolean mIsLoading = true;

    // 로딩 브로그레스바
    private ProgressBar mPbLoading;

    @Override
    public int layoutRes() {
        return R.layout.activity_bank_select;
    }

    @Override
    public void initView() {
        onStartLoading();
        mPbLoading = findViewById(R.id.pr_activity_bank_select_loading);
    }

    @Override
    public void initListener() {
        CommonTitleBar commonTitleBar = findViewById(R.id.common_activity_bank_select_title_bar);
        commonTitleBar.setBackButtonClickListener(view -> finish());
        getBankList();
    }

    @Override
    public void onBackPressed() {
        if (!mIsLoading) {
            super.onBackPressed();
        }
    }

    /**
     * 로딩 시작
     */
    protected void onStartLoading() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        mPbLoading.setVisibility(View.VISIBLE);
        mIsLoading = false;
    }

    /**
     * 로딩 종료
     */
    protected void onStopLoading() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        mPbLoading.setVisibility(View.GONE);
        mIsLoading = true;
    }

    /**
     * 은행 정보 서버에서 불러오기
     */
    private void getBankList() {

        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerBankList()
                .enqueue(new MoaAuthCallback<ResBankInfoDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResBankInfoDto> call, ResBankInfoDto resModel) {
                        onStopLoading();
                    }

                    @Override
                    public void onFinalFailure(Call<ResBankInfoDto> call, boolean isSession, Throwable t) {
                        Toast.makeText(BankSelectActivity.this,
                                "다시 시도해 주세요",
                                Toast.LENGTH_SHORT)
                                .show();
                        onStopLoading();
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();

                        onStopLoading();
                        Toast.makeText(BankSelectActivity.this,
                                R.string.common_not_exist_session,
                                Toast.LENGTH_SHORT)
                                .show();

                        Intent intent = new Intent(BankSelectActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finishAffinity();

                    }
                });

    }
}
