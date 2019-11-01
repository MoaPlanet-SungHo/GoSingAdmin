package com.moaplanet.gosingadmin.common.manager;

import android.content.Context;

import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.intro.login.moel.req.ReqLoginDto;
import com.moaplanet.gosingadmin.intro.login.moel.res.ResLoginDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.SharedPreferencesManager;
import com.orhanobut.logger.Logger;

import retrofit2.Call;

public class LoginManager {

    public enum LoginType {
        // 자동 로그인
        AUTO_LOGIN,
        // 일반 로그인
        LOGIN
    }

    private Context context;
    private String id, pw;

    private onLoginListener onLoginListener;

    public void setOnLoginListener(LoginManager.onLoginListener onLoginListener) {
        this.onLoginListener = onLoginListener;
    }

    public void onLogin(Context context, LoginType loginType) {
        this.context = context;
        SharedPreferencesManager pref = new SharedPreferencesManager(context);
        onLogin(pref.getEmail(), pref.getPw(), LoginType.AUTO_LOGIN, context);
    }

    public void onLogin(String id, String pw, LoginType loginType, Context context) {
        this.context = context;
        this.id = id;
        this.pw = pw;
        ReqLoginDto reqLoginDto = new ReqLoginDto();
        reqLoginDto.setPw(pw);
        reqLoginDto.setEmail(id);
        RetrofitService.getInstance()
                .getGoSingApiService(null)
                .login(reqLoginDto.getEmail(),
                        reqLoginDto.getPw(),
                        reqLoginDto.getSignType())
                .enqueue(loginCallback);
    }

    private MoaAuthCallback<ResLoginDto> loginCallback = new MoaAuthCallback<ResLoginDto>(
            RetrofitService.getInstance().getMoaAuthConfig(),
            RetrofitService.getInstance().getSessionChecker()
    ) {
        @Override
        public void onFinalResponse(Call<ResLoginDto> call, ResLoginDto resModel) {

            if (onLoginListener != null) {
                if (resModel.getStateCode() == NetworkConstants.STATE_CODE_SUCCESS) {

                    if (resModel.getDetailCode() == NetworkConstants.CODE_NOT_MEMBER) {
                        onLoginListener.onLoginFail(
                                resModel.getStateCode(),
                                resModel.getDetailCode());
                    } else {
                        SharedPreferencesManager sharedPreferencesManager =
                                new SharedPreferencesManager(context);
                        sharedPreferencesManager.setIntroType(GoSingConstants.TYPE_AUTO_LOGIN);
                        sharedPreferencesManager.setLoginInfo(id, pw);
                        onLoginListener.onLoginSuccess(
                                resModel.getStateCode(),
                                resModel.getDetailCode());
                    }

                } else {
                    onLoginListener.onLoginFail(
                            resModel.getStateCode(),
                            resModel.getDetailCode());
                }
            } else {
                Logger.d("onLoginListener 가 null 입니다.");
            }

        }

        @Override
        public void onFinalFailure(Call<ResLoginDto> call, boolean isSession, Throwable t) {
            Logger.e("로그인 실패");
            if (onLoginListener != null) {
                onLoginListener.onLoginFail(-1, -1);
            }
        }
    };

    public interface onLoginListener {
        void onLoginSuccess(int stateCode, int detailCode);

        void onLoginFail(int stateCode, int detailCode);
    }
}
