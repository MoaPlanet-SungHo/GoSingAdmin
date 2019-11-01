package com.moaplanet.gosingadmin.common.manager;

import com.moaplanet.gosingadmin.common.model.dto.req.ReqStoreSearchDto;
import com.moaplanet.gosingadmin.common.model.dto.res.ResStoreSearchDto;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import retrofit2.Call;

public class StoreManager {

    /**
     * 가맹점 정보 조회
     */
    public void onStoreSearch() {
        ReqStoreSearchDto reqStoreSearchDto = new ReqStoreSearchDto();
        RetrofitService
                .getInstance()
                .getGoSingApiService(null)
                .onStoreSearch(reqStoreSearchDto.getSignType())
                .enqueue(new MoaAuthCallback<ResStoreSearchDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResStoreSearchDto> call, ResStoreSearchDto resModel) {

                    }

                    @Override
                    public void onFinalFailure(Call<ResStoreSearchDto> call, boolean isSession, Throwable t) {

                    }
                });
    }

}
