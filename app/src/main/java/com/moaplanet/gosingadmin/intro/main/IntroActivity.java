package com.moaplanet.gosingadmin.intro.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.BuildConfig;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.activity.CreatePinActivity;
import com.moaplanet.gosingadmin.common.dialog.NoTitleDialog;
import com.moaplanet.gosingadmin.common.manager.LoginManager;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.intro.GoSingAdminConfirmPermissionActivity;
import com.moaplanet.gosingadmin.intro.ResVersionDTO;
import com.moaplanet.gosingadmin.intro.login.LoginActivity;
import com.moaplanet.gosingadmin.intro.sign_up.activity.SignUpActivity;
import com.moaplanet.gosingadmin.main.MainActivity;
import com.moaplanet.gosingadmin.main.submenu.store.activity.RegisterStoreActivity;
import com.moaplanet.gosingadmin.main.submenu.store.activity.WaitingApprovalActivity;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.manager.SharedPreferencesManager;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 인트로 화면
 */
public class IntroActivity extends BaseActivity {

    // 회원가입 및 로그인 그룹
    private LinearLayout viewLoginOrSignUp;
    private int mDetailCode = -1;

    private IntroViewModel mViewModel;

    @Override
    public int layoutRes() {
        return R.layout.activity_intro;
    }

    @Override
    public void initView() {
        // StatusBar 삭제
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewLoginOrSignUp = findViewById(R.id.ll_activity_intro_user_group);
        viewLoginOrSignUp.setVisibility(View.GONE);
    }

    @Override
    public void initActivity() {
        super.initActivity();
        // 뷰 모델 초기화
        mViewModel = ViewModelProviders.of(this).get(IntroViewModel.class);
    }

    @Override
    public void initListener() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 앱 버전 체크 유무 로직
        boolean isAppVersionCheck = getIntent().getBooleanExtra(
                GoSingConstants.BUNDLE_KEY_APP_VERSION_CHECK,
                GoSingConstants.BUNDLE_VALUE_APP_VERSION_CHECK);

        if (isAppVersionCheck) {
            onAppVersionCheck();
        } else {
            checkIntroType();
        }

    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mViewModel.getMoveActivity().observe(this, this::moveActivity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GoSingConstants.ACTION_REQ_CODE_PIN) {
            if (resultCode == GoSingConstants.ACTION_RESULT_CODE_PIN_SUCCESS) {
                onLogin();
            } else {
                Toast.makeText(this,
                        getString(R.string.common_toast_payment_password_not_create),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }

    /**
     * 앱 버전체크
     */
    private void onAppVersionCheck() {
//        checkIntroType();

        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerAppVersionCheck(
                        "1", "2"
                ).enqueue(new MoaAuthCallback<ResVersionDTO>(
                RetrofitService.getInstance().getMoaAuthConfig(),
                RetrofitService.getInstance().getSessionChecker()
        ) {
            @Override
            public void onFinalResponse(Call<ResVersionDTO> call, ResVersionDTO resModel) {

                int nowAppVersion = BuildConfig.VERSION_CODE;

                if (nowAppVersion < resModel.getAppVersionModel().getVersionMin()) {
                    NoTitleDialog noTitleDialog = new NoTitleDialog();
                    noTitleDialog.setUseYesOrNo(true);
                    noTitleDialog.setContent(R.string.activity_intro_force_update);
                    noTitleDialog.show(getSupportFragmentManager(), "다이얼로그");

                    noTitleDialog.onNoOnClickListener(view -> {
                        noTitleDialog.dismiss();
                        finish();
                    });

                    noTitleDialog.onDoneOnCliListener(view -> {
                        noTitleDialog.dismiss();
                        goPlayStoreMoaApp();
                        finish();
                    });

                } else if (nowAppVersion >= resModel.getAppVersionModel().getVersionMin()
                        && nowAppVersion < resModel.getAppVersionModel().getVersionMax()) {

                    NoTitleDialog noTitleDialog = new NoTitleDialog();
                    noTitleDialog.setUseYesOrNo(true);
                    noTitleDialog.setContent(R.string.activity_intro_select_update);
                    noTitleDialog.show(getSupportFragmentManager(), "다이얼로그");

                    noTitleDialog.onNoOnClickListener(view -> {
                        noTitleDialog.dismiss();
                        checkIntroType();
//                        mViewModel.onIntroType();
                    });

                    noTitleDialog.onDoneOnCliListener(view -> {
                        noTitleDialog.dismiss();
                        goPlayStoreMoaApp();
                        finish();
                    });

                } else {
                    checkIntroType();
//                    mViewModel.onIntroType();
                }
            }

            @Override
            public void onFinalFailure(Call<ResVersionDTO> call, boolean isSession, Throwable t) {
                checkIntroType();
//                mViewModel.onIntroType();
            }
        });

    }

    /**
     * 액티비티 이동
     */
    private void moveActivity(Class moveActivity) {
        Intent intent = new Intent(this, moveActivity);
        startActivity(intent);
    }

    /**
     * 플레이스토어로 이동
     */
    private void goPlayStoreMoaApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://play.google.com/store/apps/details?id=com.moaplanet.gosingadmin"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 인트로 타입에 따라 화면 이동
     */
    private void checkIntroType() {
//        mViewModel.onIntroType();

        Handler delayHandler = new Handler();
        delayHandler.postDelayed(() -> {
            int introType = SharedPreferencesManager.getInstance().getType();

            if (introType == GoSingConstants.INTRO_TYPE_FIRST_START) {
                // 권한 설정 화면으로 이동
                moveActivity(GoSingAdminConfirmPermissionActivity.class);
            } else if (introType == GoSingConstants.INTRO_TYPE_AUTO_LOGIN) {
                // 자동 로그인
                onLogin();
            } else {
                // 로그인 또는 회원가입 그룹 표시
                initLoginAndSignUp();
                viewLoginOrSignUp.setVisibility(View.VISIBLE);
            }

        }, 1800);
    }

    /**
     * 로그인
     */
    private void onLogin() {
        LoginManager loginManager = new LoginManager();
        loginManager.setOnLoginListener(onLoginListener);
        loginManager.onLogin(this, LoginManager.LoginType.AUTO_LOGIN);
    }

    /**
     * 결제 코드 체크
     */
    private void onCheckPaymentCode() {
        if (SharedPreferencesManager.getInstance().getPin().equals("")) {
            // 결제 코드 생성
            Toast.makeText(this,
                    getString(R.string.common_toast_create_payment_password),
                    Toast.LENGTH_SHORT)
                    .show();
            Intent intent = new Intent(this, CreatePinActivity.class);
            startActivityForResult(intent, GoSingConstants.ACTION_REQ_CODE_PIN);
        } else {
            if (mDetailCode == NetworkConstants.LOGIN_CODE_SUCCESS) {
                // 메인 화면 이동
                moveActivity(MainActivity.class);
            } else if (mDetailCode == NetworkConstants.LOGIN_CODE_ACCOUNT_INACTIVE) {
                // 승인 대기 화면으로 이동
                moveActivity(WaitingApprovalActivity.class);
            } else if (mDetailCode == NetworkConstants.LOGIN_CODE_EMPTY_STORE) {
                // 매장 등록 화면으로 이동
                moveActivity(RegisterStoreActivity.class);
            }
            finish();
        }
    }

    /**
     * 로그인이랑 회원가입 리스너 초기화
     */
    private void initLoginAndSignUp() {
        // 회원가입 클릭
        Button btnSignUp = findViewById(R.id.btn_activity_intro_sign_up);
        RxView.clicks(btnSignUp)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> moveActivity(SignUpActivity.class));

        // 로그인 클릭
        Button btnLogin = findViewById(R.id.btn_activity_intro_login);
        RxView.clicks(btnLogin)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> moveActivity(LoginActivity.class));
    }

    /**
     * 로그인 콜백
     */
    private LoginManager.onLoginListener onLoginListener = new LoginManager.onLoginListener() {
        @Override
        public void onLoginSuccess(int stateCode, int detailCode) {
            mDetailCode = detailCode;
            onCheckPaymentCode();
        }

        @Override
        public void onLoginFail(int stateCode, int detailCode) {
            initLoginAndSignUp();
            viewLoginOrSignUp.setVisibility(View.VISIBLE);
            Toast.makeText(
                    IntroActivity.this,
                    getString(R.string.activity_intro_auto_login_fail),
                    Toast.LENGTH_SHORT).show();
        }
    };

}
