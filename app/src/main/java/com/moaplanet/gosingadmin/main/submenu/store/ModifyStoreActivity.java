package com.moaplanet.gosingadmin.main.submenu.store;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.moaplanet.gosingadmin.common.model.dto.req.ReqStoreSearchDto;
import com.moaplanet.gosingadmin.common.model.dto.res.ResStoreSearchDto;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.orhanobut.logger.Logger;

import retrofit2.Call;

/**
 * TODO StoreActivity 로 통합 필수
 */
public class ModifyStoreActivity extends BaseStoreActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadStoreInfo();
    }

    private void loadStoreInfo() {
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
                        if (resModel.getStateCode() == 200 && resModel.getDetailCode() == 200) {
                            initData(resModel);
                        }
                    }

                    @Override
                    public void onFinalFailure(Call<ResStoreSearchDto> call, boolean isSession, Throwable t) {

                    }
                });
    }

    private void initData(ResStoreSearchDto storeSearchDto) {

    }

}
