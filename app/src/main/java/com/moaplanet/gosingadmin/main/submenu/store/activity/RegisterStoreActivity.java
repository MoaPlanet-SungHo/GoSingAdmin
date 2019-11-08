package com.moaplanet.gosingadmin.main.submenu.store.activity;

import android.Manifest;
import android.view.View;

import com.bumptech.glide.Glide;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.main.submenu.store.model.req.ReqStoreRegisterDto;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gun0912.tedimagepicker.builder.TedImagePicker;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RegisterStoreActivity extends BaseStoreActivity {

    @Override
    public void initView() {
        super.initView();
        loadingBar.setVisibility(View.GONE);
    }

    @Override
    public void initListener() {
        super.initListener();
        for (int i = 0; i < PICTURE_COUNT; i++) {
            pictureImageViewList.get(i).setOnClickListener(view1 -> selectPicture());
        }
    }

    public void selectPicture() {
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
                                    .max(8, "최대 8개 선택가능합니다.")
                                    .startMultiImage(list -> {
                                        Logger.d("Selected list >>> " + list.toString());
                                        selectedUriList = list;
                                        // setImageAddComponentGroupUi(list);
                                        defaultAddPictureUi();
                                        for (int position = 0; position < list.size(); position++) {
                                            Glide.with(this)
                                                    .load(list.get(position))
                                                    .thumbnail(0.1f)
                                                    .placeholder(R.drawable.bg_store_thumbnail_loading)
                                                    .error(R.drawable.bg_store_thumbnail_loading)
                                                    .into(pictureImageViewList.get(position));
                                        }
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
    public void registerStore() {
        roomCheck();

    }

    private void roomCheck() {
        List<ReqStoreRegisterDto.RoomInfoDto> roomList = new ArrayList<>();
        int ROOM_COUNT = 3;
        ReqStoreRegisterDto.RoomInfoDto roomInfoDto = reqStoreRegisterDto.new RoomInfoDto();
        for (int i = 0; i < ROOM_COUNT; i++) {
            if (checkRoomType(i) && checkRoomPrice(i) && checkRoomPersonnel(i)) {
                roomInfoDto.setPrice(roomPriceList.get(i).getText().toString());
                roomInfoDto.setRoomType(String.valueOf(i + 1));
                roomInfoDto.setPeoplePerRoom(roomPersonnelList.get(i).getSelectedItem().toString());
                roomInfoDto.setSentType("insert");
                roomList.add(roomInfoDto);
            }
        }

        reqStoreRegisterDto.setRoomInfoDtoList(roomList);

        Map<String, String> storeImgMap = new HashMap<>();
        Map<String, RequestBody> fileMap = new HashMap<>();
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
}
