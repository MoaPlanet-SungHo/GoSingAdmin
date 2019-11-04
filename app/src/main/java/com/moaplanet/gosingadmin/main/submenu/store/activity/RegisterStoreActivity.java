package com.moaplanet.gosingadmin.main.submenu.store.activity;

import android.view.View;

import com.moaplanet.gosingadmin.main.submenu.store.model.req.ReqStoreRegisterDto;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RegisterStoreActivity extends BaseStoreActivity {

    @Override
    public void initView() {
        super.initView();
        loadingBar.setVisibility(View.GONE);
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
                roomInfoDto.setRoomType(i + 1);
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
