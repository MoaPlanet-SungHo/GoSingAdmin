package com.moaplanet.gosingadmin.common.manager;

import com.moaplanet.gosingadmin.common.model.dto.res.ResPointDto;
import com.moaplanet.gosingadmin.main.slide_menu.main.model.dto.res.ResGoSingPointSearchDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import retrofit2.Call;

/**
 * 고싱 포인트 관련 메니저
 */
public class PointManager {

    private GoSingPointCallback mGoSingPointCallback;

    public void setGoSingPointCallback(GoSingPointCallback goSingPointCallback) {
        this.mGoSingPointCallback = goSingPointCallback;
    }

    /**
     * 고싱 포인트 불러오기
     */
    public void getGoSingPoint() {

        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerGoSingPoint()
                .enqueue(new MoaAuthCallback<ResGoSingPointSearchDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResGoSingPointSearchDto> call,
                                                ResGoSingPointSearchDto resModel) {

                        if (mGoSingPointCallback != null) {

                            if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS &&
                                    resModel.getPointDto() != null) {
                                mGoSingPointCallback.onSuccess(resModel.getPointDto());
                            } else {
                                mGoSingPointCallback.onFail();
                            }
                        }

                    }

                    @Override
                    public void onFinalFailure(Call<ResGoSingPointSearchDto> call,
                                               boolean isSession, Throwable t) {
                        mGoSingPointCallback.onFail();
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                        mGoSingPointCallback.onNotSession();
                    }
                });

    }

    /**
     * 통신 결과 콜백할 메소드
     */
    public interface GoSingPointCallback {

        // 성공
        void onSuccess(ResPointDto resPointDto);

        // 실패
        void onFail();

        // 세션이 만료됨
        void onNotSession();
    }

}
