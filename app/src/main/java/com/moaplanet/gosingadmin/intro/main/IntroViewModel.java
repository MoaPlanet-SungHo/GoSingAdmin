package com.moaplanet.gosingadmin.intro.main;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.intro.GoSingAdminConfirmPermissionActivity;
import com.moaplanet.gosingadmin.utils.SharedPreferencesUtil;
import com.orhanobut.logger.Logger;

/**
 * 인트로 뷰 모델
 */
public class IntroViewModel extends ViewModel {

    private MutableLiveData<Class> mMoveActivity = new MutableLiveData<>();

    /**
     * @return 이동할 activity class
     */
    public LiveData<Class> getMoveActivity() {
        return mMoveActivity;
    }

    /**
     * 인트로 타입을 불러옴
     */
    public void onIntroType() {
        // 인트로 타입을 체크 할때 딜레이 시간
        final int INTRO_TYPE_DELAYED_TIME = 1000;

        Handler delayHandler = new Handler();
        delayHandler.postDelayed(() -> {
            final int INTRO_TYPE = SharedPreferencesUtil.getInstance().getType();

            if (INTRO_TYPE == GoSingConstants.INTRO_TYPE_FIRST_START) {
                // 권한 설정 화면으로 이동
                Logger.i("인트로 타입 INTRO_TYPE : " + INTRO_TYPE + " 권한 설정 화면으로 이동");
                mMoveActivity.setValue(GoSingAdminConfirmPermissionActivity.class);
            } else if (INTRO_TYPE == GoSingConstants.INTRO_TYPE_AUTO_LOGIN) {
                Logger.i("인트로 타입 INTRO_TYPE : " + INTRO_TYPE + " 자동 로그인 실행");
                // 자동 로그인
//                onLogin();
            } else {
                Logger.i("인트로 타입 INTRO_TYPE : " + INTRO_TYPE + " 로그인 및 회원가입 뷰 표시");
                // 로그인 또는 회원가입 그룹 표시
//                initLoginAndSignUp();
//                viewLoginOrSignUp.setVisibility(View.VISIBLE);
            }

        }, INTRO_TYPE_DELAYED_TIME);
    }

}
