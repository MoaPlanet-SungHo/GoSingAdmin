package com.moaplanet.gosingadmin.main.submenu.store;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.moaplanet.gosingadmin.common.model.dto.req.ReqStoreSearchDto;
import com.moaplanet.gosingadmin.common.model.dto.res.ResStoreSearchDto;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import java.util.List;

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
                .getGoSingApiService()
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
        ResStoreSearchDto.ShopInfoDto shopInfoDto = storeSearchDto.getShopInfoDto();

        etStoreName.setText(shopInfoDto.getStoreName());
        tvRoadAddress.setText(shopInfoDto.getRoadAddress());
        etDetailAddress.setText(shopInfoDto.getDetailAddress());
        etSimpleAddress.setText(shopInfoDto.getSimpleAddress());
        etStoreTel.setText(shopInfoDto.getStoreTel());
        etBossTel.setText(shopInfoDto.getPhoneNumber());
        etCeoComment.setText(shopInfoDto.getCeoComment());

        List<ResStoreSearchDto.ShopRoomInfoDto> shopRoomInfoDtoList =
                storeSearchDto.getShopRoomInfoDtoList();

        if (shopRoomInfoDtoList != null && shopRoomInfoDtoList.size() > 0) {
            for (ResStoreSearchDto.ShopRoomInfoDto shopRoomInfoDto : shopRoomInfoDtoList) {
                if (shopRoomInfoDto.getRoomType() == 1) {
                    cbLargeRoom.setChecked(true);
                    tvLargeRoomPrice.setText(shopRoomInfoDto.getPrice());
                } else if (shopRoomInfoDto.getRoomType() == 2) {
                    cbMiddleRoom.setChecked(true);
                    tvMiddleRoomPrice.setText(shopRoomInfoDto.getPrice());
                } else {
                    cbSmallRoom.setChecked(true);
                    tvSmallRoomPrice.setText(shopRoomInfoDto.getPrice());
                }
            }
        }

        reqStoreRegisterDto.setEntX(shopInfoDto.getEntX());
        reqStoreRegisterDto.setEntY(shopInfoDto.getEntY());
        reqStoreRegisterDto.setPostNumber(shopInfoDto.getPostNumber());
        reqStoreRegisterDto.setAdmCd(shopInfoDto.getAdmCd());
        reqStoreRegisterDto.setEmdNm(shopInfoDto.getEmdNm());
    }

}
