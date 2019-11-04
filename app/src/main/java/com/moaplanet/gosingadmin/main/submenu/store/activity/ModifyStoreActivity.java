package com.moaplanet.gosingadmin.main.submenu.store.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.model.dto.req.ReqStoreSearchDto;
import com.moaplanet.gosingadmin.common.model.dto.res.ResStoreSearchDto;
import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressCoordDto;
import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressSearchDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;

public class ModifyStoreActivity extends BaseStoreActivity {

    @Override
    public void registerStore() {
        Map<String, RequestBody> fileMap = new HashMap<>();
        connectServer(fileMap);
    }

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
                        } else {
                            loadingBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFinalFailure(Call<ResStoreSearchDto> call, boolean isSession, Throwable t) {
                        loadingBar.setVisibility(View.GONE);
                    }
                });
    }

    private void initData(ResStoreSearchDto storeSearchDto) {
        ResStoreSearchDto.ShopInfoDto shopInfoDto = storeSearchDto.getShopInfoDto();

        etStoreName.setText(shopInfoDto.getStoreName());
        tvRoadAddress.setText(shopInfoDto.getRoadAddress());
        etDetailAddress.setText(shopInfoDto.getDetailAddress());
        etShortAddress.setText(shopInfoDto.getSimpleAddress());
        etStoreCallNumber.setText(shopInfoDto.getStoreTel());
        etCeoCallNumber.setText(shopInfoDto.getPhoneNumber());
        etCeoComment.setText(shopInfoDto.getCeoComment());

        List<ResStoreSearchDto.ShopPhotoDto> photoDtoList = storeSearchDto.getShopPhotoDtoList();

        List<String> storeImageList = new ArrayList<>();
        for (ResStoreSearchDto.ShopPhotoDto shopPhotoDto : photoDtoList) {
            storeImageList.add(
                    StringUtil.replaceText(
                            shopPhotoDto.getImgSrc(),
                            "_",
                            "/"));
        }

        for (int i = 0; i < storeImageList.size(); i++) {
            Glide.with(this)
//                    .load(NetworkConstants.IMAGE_BASE_URL + storeImageList.get(i))
                    .load(R.drawable.ic_4300ff_location)
                    .into(pictureImageViewList.get(i));
        }

//        List<ResStoreSearchDto.ShopRoomInfoDto> shopRoomInfoDtoList =
//                storeSearchDto.getShopRoomInfoDtoList();
//
//        if (shopRoomInfoDtoList != null && shopRoomInfoDtoList.size() > 0) {
//            for (ResStoreSearchDto.ShopRoomInfoDto shopRoomInfoDto : shopRoomInfoDtoList) {
//                if (shopRoomInfoDto.getRoomType() == 1) {
//                    cbLargeRoom.setChecked(true);
//                    tvLargeRoomPrice.setText(shopRoomInfoDto.getPrice());
//                } else if (shopRoomInfoDto.getRoomType() == 2) {
//                    cbMiddleRoom.setChecked(true);
//                    tvMiddleRoomPrice.setText(shopRoomInfoDto.getPrice());
//                } else {
//                    cbSmallRoom.setChecked(true);
//                    tvSmallRoomPrice.setText(shopRoomInfoDto.getPrice());
//                }
//            }
//        }
//
        addressCoordInfoDto = new ResAddressCoordDto().new AddressCoordInfoDto();
        addressCoordInfoDto.setEntX(shopInfoDto.getEntX());
        addressCoordInfoDto.setEntY(shopInfoDto.getEntY());

        addressInfoDto = new ResAddressSearchDto().new AddressInfoDto();
        addressInfoDto.setAdmCd(shopInfoDto.getAdmCd());
        addressInfoDto.setEmdNm(shopInfoDto.getEmdNm());
        addressInfoDto.setZipNo(shopInfoDto.getPostNumber());

        loadingBar.setVisibility(View.GONE);
    }

}
