package com.moaplanet.gosingadmin.network.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;

import com.moaplanet.gosingadmin.constants.GoSingConstants;
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
        Logger.d("세션 아이디 : " + response.headers().get("Set-Cookie"));
        sessionChecker.sessionCheck(isT -> {
            if (isT) {
                if (response.body() != null) {
                    onFinalResponse(call, response.body());
                } else {
                    onFinalFailure(call, true, new Exception("Data null"));
                }
            } else {
                onFinalFailure(call, false, new Exception("Moa session not invalid"));
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

    private void retry() {
        Log.i(authConfig.getLogTagName(), "retry::" + this.retryCount);
        call.clone().enqueue(this);
    }
}
