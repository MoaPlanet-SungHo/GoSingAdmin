package com.moaplanet.gosingadmin.network.retrofit;

import androidx.annotation.NonNull;

import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RetrofitCallBack<T> implements Callback<T> {

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {

        // null 일경우 실패로 간주
        if (response.body() == null) {
            onFail(response, new Exception("Data null"));
            return;
        }

        // CommonResDto 로 형변환 불가
        if (!(response.body() instanceof CommonResDto)) {
            onFail(response, new Exception("not convert CommonResDto"));
            return;
        }

        CommonResDto model = (CommonResDto) response.body();

        if (model.getStateCode() == NetworkConstants.STATE_CODE_SUCCESS) {
            // 정상 데이터일시

            // DetailCode 가 null 일경우
            if (model.getDetailCode() == null) {
                onFail(response, new Exception("Detail Code Null"));
                return;
            }

            // 세션 만료시
            if (model.getDetailCode() == NetworkConstants.DETAIL_CODE_NOT_EXIST_SESSION) {
                onExpireSession(response, new Exception("ExpireSession Session"));
                return;
            }

            onSuccess(response.body());

        } else {
            onFail(response, new Exception("STATE_CODE : " + model.getStateCode()));
        }

    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        onFail(null, t);
    }

    /**
     * 성공
     */
    abstract public void onSuccess(T response);

    /**
     * 실패
     */
    abstract public void onFail(Response<T> response, Throwable t);

    /**
     * 세션 만료
     */
    abstract public void onExpireSession(Response<T> response, Throwable t);
}
