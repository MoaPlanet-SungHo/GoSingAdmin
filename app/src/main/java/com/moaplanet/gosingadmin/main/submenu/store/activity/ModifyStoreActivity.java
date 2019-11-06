package com.moaplanet.gosingadmin.main.submenu.store.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.ArrayRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.model.dto.req.ReqStoreSearchDto;
import com.moaplanet.gosingadmin.common.model.dto.res.ResStoreSearchDto;
import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressCoordDto;
import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressSearchDto;
import com.moaplanet.gosingadmin.main.submenu.store.model.req.ReqStoreRegisterDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.StringUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import gun0912.tedimagepicker.builder.TedImagePicker;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ModifyStoreActivity extends BaseStoreActivity {

    private List<String> storeImageList;
    private Map<Integer, Uri> selectImgMap;

    @Override
    public void registerStore() {
        roomCheck();
    }

    @Override
    public void initListener() {
        super.initListener();
        for (int i = 0; i < PICTURE_COUNT; i++) {
            int count = i;
            pictureImageViewList.get(i).setOnClickListener(view1 -> selectPicture(count));
        }
    }

    public void selectPicture(int pos) {
        try {
            compositeDisposable.add(rxPermissions.request(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            )
                    .subscribe(granted -> {
                        if (granted) { // Always true pre-M
                            Logger.d("permission granted");
                            // if (getActivity() instanceof ReviewWriteActivity) {
                            TedImagePicker.with(this)
                                    .selectedUri(selectedUriList)
                                    .max(1, "최대 1개 선택가능합니다.")
                                    .startMultiImage(list -> {
                                        Uri imgUri = list.get(0);
                                        selectImgMap.put(pos, imgUri);
                                        int ivPos = pos;
                                        if (ivPos > selectImgMap.size() - 1) {
                                            ivPos = selectImgMap.size() - 1;
                                        }
                                        pictureImageViewList.get(ivPos).setImageURI(imgUri);
                                    });

                        } else {
                            // Oups permission denied
                            Logger.d("permission denied");
                        }
                    }));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @SuppressLint("UseSparseArrays")
    private void initData(ResStoreSearchDto storeSearchDto) {
        selectImgMap = new HashMap<>();
        ResStoreSearchDto.ShopInfoDto shopInfoDto = storeSearchDto.getShopInfoDto();

        etStoreName.setText(shopInfoDto.getStoreName());
        tvRoadAddress.setText(shopInfoDto.getRoadAddress());
        etDetailAddress.setText(shopInfoDto.getDetailAddress());
        etShortAddress.setText(shopInfoDto.getSimpleAddress());
        etStoreCallNumber.setText(shopInfoDto.getStoreTel());
        etCeoCallNumber.setText(shopInfoDto.getPhoneNumber());
        etCeoComment.setText(shopInfoDto.getCeoComment());

        List<ResStoreSearchDto.ShopPhotoDto> photoDtoList = storeSearchDto.getShopPhotoDtoList();
        for (int i = 0; i < photoDtoList.size(); i++) {
            selectImgMap.put(i, null);
        }

        storeImageList = new ArrayList<>();
        for (ResStoreSearchDto.ShopPhotoDto shopPhotoDto : photoDtoList) {
            String imgSrc = shopPhotoDto.getImgSrc();

            String[] middlePathArray = imgSrc.split("_");
            StringBuilder middlePath = new StringBuilder();

            final int SRC_MIDDLE_PATH_COUNT = middlePathArray.length - 1;
            for (int i = 0; i < SRC_MIDDLE_PATH_COUNT; i++) {
                middlePath.append(middlePathArray[i]).append("/");
            }

            String srcFullPath = NetworkConstants.IMAGE_BASE_URL +
                    middlePath +
                    imgSrc;

            storeImageList.add(srcFullPath);

        }

        for (int i = 0; i < storeImageList.size(); i++) {
            Glide.with(this)
                    .load(storeImageList.get(i))
                    .into(pictureImageViewList.get(i));
        }

        List<ResStoreSearchDto.ShopRoomInfoDto> shopRoomInfoDtoList =
                storeSearchDto.getShopRoomInfoDtoList();

        for (int i = 0; i < shopRoomInfoDtoList.size(); i++) {
            ResStoreSearchDto.ShopRoomInfoDto shopRoomInfoDto = shopRoomInfoDtoList.get(i);

            int roomPos = shopRoomInfoDto.getRoomType() - 1;
            roomTypeList.get(roomPos).setChecked(true);
            roomPriceList.get(roomPos).setText(shopRoomInfoDto.getPrice());

            int arrayRes;
            if (shopRoomInfoDto.getRoomType() == 1) {
                arrayRes = R.array.store_large_room_array;
            } else if (shopRoomInfoDto.getRoomType() == 2) {
                arrayRes = R.array.store_middle_room_array;
            } else {
                arrayRes = R.array.store_small_room_array;
            }
            int pos = getPersonnelPosition(arrayRes,
                    shopRoomInfoDto.getPerRoom());
            if (pos > 0) {
                roomPersonnelList.get(roomPos).setSelection(pos);
            }
        }

        addressCoordInfoDto = new ResAddressCoordDto().new AddressCoordInfoDto();
        addressCoordInfoDto.setEntX(shopInfoDto.getEntX());
        addressCoordInfoDto.setEntY(shopInfoDto.getEntY());

        addressInfoDto = new ResAddressSearchDto().new AddressInfoDto();
        addressInfoDto.setAdmCd(shopInfoDto.getAdmCd());
        addressInfoDto.setEmdNm(shopInfoDto.getEmdNm());
        addressInfoDto.setZipNo(shopInfoDto.getPostNumber());

        loadingBar.setVisibility(View.GONE);
    }

    private void roomCheck() {
        List<ReqStoreRegisterDto.RoomInfoDto> roomList = new ArrayList<>();
        int ROOM_COUNT = 3;
        ReqStoreRegisterDto.RoomInfoDto roomInfoDto = reqStoreRegisterDto.new RoomInfoDto();
//        for (int i = 0; i < ROOM_COUNT; i++) {
//            if (checkRoomType(i) && checkRoomPrice(i) && checkRoomPersonnel(i)) {
//                roomInfoDto.setPrice(roomPriceList.get(i).getText().toString());
//                roomInfoDto.setRoomType(i + 1);
//                roomInfoDto.setPeoplePerRoom(roomPersonnelList.get(i).getSelectedItem().toString());
//                roomInfoDto.setSentType("48");
//                roomList.add(roomInfoDto);
//            }
//        }

        reqStoreRegisterDto.setRoomInfoDtoList(roomList);

        Map<String, String> storeImgMap = new HashMap<>();
        Map<String, RequestBody> fileMap = new HashMap<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            selectImgMap.values().removeIf(Objects::isNull);
        } else {
            selectImgMap.values().removeAll(Collections.singleton(null));
        }

        selectedUriList = new ArrayList<>(selectImgMap.values());
        for (int i = 0; i < selectedUriList.size(); i++) {

            File file = new File(selectedUriList.get(i).getPath());
            storeImgMap.put(String.valueOf(i), file.getName());

            RequestBody requestBody = RequestBody.create(
                    MediaType.parse("application/octet-stream"),
                    file);

            fileMap.put(file.getName() + "\"; filename=\"" + file.getName(), requestBody);
        }
        reqStoreRegisterDto.setStorePhoto(storeImgMap);

        connectServer(fileMap);

    }

    private boolean checkRoomType(int position) {
        return roomTypeList.get(position).isChecked();
    }

    private boolean checkRoomPrice(int position) {
        return checkEmpty(roomPriceList.get(position));
    }

    private boolean checkRoomPersonnel(int position) {
        return roomPersonnelList.get(position).getSelectedItemPosition() != 0;
    }

    private int getPersonnelPosition(@ArrayRes int arrayRes, String checkData) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                arrayRes,
                android.R.layout.simple_spinner_item);
        return adapter.getPosition(checkData);
    }

}
