package com.moaplanet.gosingadmin.network.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;

import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.model.CommonResDto;
import com.orhanobut.logger.Logger;

import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

abstract public class MoaAuthCallback<T> implements Callback<T> {
    private Call<T> call;
    private int retryCount = 0;
    private MoaAuthConfig authConfig;

    private SessionChecker sessionChecker;

    public MoaAuthCallback(MoaAuthConfig authConfig, SessionChecker sessionChecker) {
        this.authConfig = authConfig;
        this.sessionChecker = sessionChecker;
    }

    public MoaAuthCallback(Call<T> call) {
        this.call = call;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull final Response<T> response) {
        if (this.call == null) {
            this.call = call;
        }
//        GoSingConstants.Test = response.headers().get("Set-Cookie");
        Logger.d("세션 아이디 : " + GoSingConstants.cookieHash);
        sessionChecker.sessionCheck(isT -> {
            if (isT) {

                // 데이터가 Null 일경우
                if (response.body() == null) {
                    onFinalFailure(call, true, new Exception("Data null"));
                    return;
                }

                // CommonResDto 로 형변환 불가
                if (!(response.body() instanceof CommonResDto)) {
                    onFinalResponse(call, response.body());
                    return;
                }

                CommonResDto commonResDto = (CommonResDto) response.body();

                if (commonResDto.getStateCode() == null) {
                    onFinalFailure(call, true, new Exception("State Code null"));
                    return;
                }

                if (commonResDto.getDetailCode() == null) {
                    onFinalFailure(call, true, new Exception("Detail Code null"));
                    return;
                }

                // 서버 통신 성공 유무
                if (commonResDto.getStateCode() == NetworkConstants.STATE_CODE_SUCCESS) {

                    // 세션이 없을 경우
                    if (commonResDto.getDetailCode() == NetworkConstants.DETAIL_CODE_NOT_EXIST_SESSION) {
                        onFinalNotSession();
                    } else {
                        // 세션이 있을 경우
                        onFinalResponse(call, response.body());
                    }

                } else {
                    onFinalFailure(call, false, new Exception("서버 통신 실패"));
                }

            } else {
                onFinalNotSession();
//                onFinalFailure(call, false, new Exception("Moa session not invalid"));
            }
        });
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        if (this.call == null) {
            this.call = call;
        }
        Log.e(authConfig.getLogTagName(), t.getMessage() != null ? t.getMessage() : "null");
        if (retryCount++ < authConfig.getTotalRetryCnt()) {
            Log.v(authConfig.getLogTagName(), "Retrying API Call -  (" + retryCount + " / " + authConfig.getTotalRetryCnt() + ")");
            retry();
        } else
            onFinalFailure(call, true, t);
    }

    abstract public void onFinalResponse(Call<T> call, T resModel);

    abstract public void onFinalFailure(Call<T> call, boolean isSession, Throwable t);

    /**
     * 세션이 없을 경우
     */
    public void onFinalNotSession() {
    }

    private void retry() {
        Log.i(authConfig.getLogTagName(), "retry::" + this.retryCount);
        call.clone().enqueue(this);
    }
}
