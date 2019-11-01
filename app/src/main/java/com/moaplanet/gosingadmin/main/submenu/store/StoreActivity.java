package com.moaplanet.gosingadmin.main.submenu.store;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.main.submenu.address.AddressSearchActivity;
import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressCoordDto;
import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressSearchDto;
import com.moaplanet.gosingadmin.main.submenu.store.model.req.ReqStoreRegisterDto;
import com.moaplanet.gosingadmin.main.submenu.store.model.res.ResStoreRegisterDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;

import gun0912.tedimagepicker.builder.TedImagePicker;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class StoreActivity extends BaseActivity {

    private CommonTitleBar commonTitleBar;
    private TextView tvCeoCommentCount;
    private Button btnDone;
    private TextView tvAddressSearch;
    private EditText etStoreName, etStoreTel, etBossTel, etSimpleAddress, etDetailAddress, etCeoComment;
    public CompositeDisposable compositeDisposable;
    public RxPermissions rxPermissions;
    private List<? extends Uri> selectedUriList;
    private List<ImageView> pictureImageViewList;           //이미지 리스트
    private List<ImageView> pictureImageInnerIconList;      //이미지 추가하기 아이콘 리스트
    private List<Button> deletePictureButtonList;           //삭제 버튼 리스트
    private final int PICTURE_COUNT = 8;
    private TextView tvRoadAddress;
    private CheckBox cbLargeRoom, cbMiddleRoom, cbSmallRoom;
    private TextView tvLargeRoomPrice, tvMiddleRoomPrice, tvSmallRoomPrice;
    private Spinner spLargeRoom, spMiddleRoom, spSmallRooom;

    private ImageView[] ivStoreImage = new ImageView[8];

    private ReqStoreRegisterDto reqStoreRegisterDto;
    private ResAddressCoordDto.AddressCoordInfoDto addressCoordInfoDto;
    private ResAddressSearchDto.AddressInfoDto addressInfoDto;

    @Override
    public int layoutRes() {
        return R.layout.activity_store;
    }

    /**
     * 갤러리
     */
    private void selectPicture() {
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
                                        detaultAddPictureUi();
                                        for (int position = 0; position < list.size(); position++) {
                                            pictureImageViewList.get(position).setImageURI(list.get(position));
                                        }
                                    });
                        } else {
                            // Oups permission denied
                            Logger.d("permission denied");
                        }
                    }));
        } catch (Exception e) {
        }
    }


    /**
     * 이미지 선택하기 UI Default
     */
    private void detaultAddPictureUi() {
        for (int i = 0; i < PICTURE_COUNT; i++) {
            pictureImageViewList.get(i).setImageURI(null);
            pictureImageViewList.get(i).setBackgroundResource(R.drawable.border_rect_0_0_0_0_1dp_e9e9e9_f8f8f8);
        }
    }

    @Override
    public void initView() {

        compositeDisposable = new CompositeDisposable();
        rxPermissions = new RxPermissions(this);

        cbMiddleRoom = findViewById(R.id.cb_store_middle_room);
        cbSmallRoom = findViewById(R.id.cb_store_small_room);
        tvSmallRoomPrice = findViewById(R.id.tv_store_small_room_price);
        tvMiddleRoomPrice = findViewById(R.id.tv_store_middle_room_price);
        spMiddleRoom = findViewById(R.id.sp_store_middle_room_personnel);
        spSmallRooom = findViewById(R.id.sp_store_small_room_personnel);
        spLargeRoom = findViewById(R.id.sp_store_large_room_personnel);
        tvLargeRoomPrice = findViewById(R.id.et_store_large_room_price);
        cbLargeRoom = findViewById(R.id.cb_store_large_room);
        tvRoadAddress = findViewById(R.id.tv_store_default_address);
        tvAddressSearch = findViewById(R.id.tv_store_search_address);
        etCeoComment = findViewById(R.id.et_store_ceo_comment);
        commonTitleBar = findViewById(R.id.common_store_title_bar);
        tvCeoCommentCount = findViewById(R.id.tv_store_ceo_comment_count);
        setTvCeoCommentCount(0);
        btnDone = findViewById(R.id.btn_store_register);
        etStoreName = findViewById(R.id.et_store_input_name);
        etStoreTel = findViewById(R.id.et_store_call_number);
        etBossTel = findViewById(R.id.et_boss_call_number);
        etSimpleAddress = findViewById(R.id.et_store_short_address);
        etDetailAddress = findViewById(R.id.et_store_detail_address);
        ivStoreImage[0] = findViewById(R.id.store_image_1);
        ivStoreImage[1] = findViewById(R.id.store_image_2);
        ivStoreImage[2] = findViewById(R.id.store_image_3);
        ivStoreImage[3] = findViewById(R.id.store_image_4);
        ivStoreImage[4] = findViewById(R.id.store_image_5);
        ivStoreImage[5] = findViewById(R.id.store_image_6);
        ivStoreImage[6] = findViewById(R.id.store_image_7);
        ivStoreImage[7] = findViewById(R.id.store_image_8);

        selectedUriList = new ArrayList<>();
        pictureImageViewList = new ArrayList<>();
        pictureImageInnerIconList = new ArrayList<>();
        deletePictureButtonList = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            pictureImageViewList.add(ivStoreImage[i]);
        }

        detaultAddPictureUi();
    }

    @Override

    public void initListener() {
        commonTitleBar.setBackButtonClickListener(view -> finish());

        etCeoComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setTvCeoCommentCount(editable.length());
            }
        });

        btnDone.setOnClickListener(view -> {
            registerStore();
        });
        etStoreTel.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        etBossTel.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        for (int i = 0; i < 8; i++) {
            ivStoreImage[i].setOnClickListener(view1 -> selectPicture());
        }

        tvAddressSearch.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddressSearchActivity.class);
            startActivityForResult(intent, 3000);
//            startActivity(intent);
        });


    }

    private void initDefault() {
        reqStoreRegisterDto = new ReqStoreRegisterDto();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDefault();
    }

    private void setTvCeoCommentCount(int count) {
        tvCeoCommentCount.setText(getString(R.string.activity_stroe_ceo_commnet_count, count));
    }

    private void registerStore() {
        if (checkData()) {
            Map<String, String> storeImgMap = new HashMap<>();
            Map<String, RequestBody> fileMap = new HashMap<>();
            for (int i = 0; i < selectedUriList.size(); i++) {
//                File file = new File(selectedUriList.get(i).toString());


                File file = new File(selectedUriList.get(i).getPath());
                storeImgMap.put(String.valueOf(i), file.getName());

                RequestBody requestBody = RequestBody.create(
                        MediaType.parse("application/octet-stream"),
                        file);

                fileMap.put(file.getName() + "\"; filename=\"" + file.getName(), requestBody);
            }
            reqStoreRegisterDto.setStorePhoto(storeImgMap);

            RetrofitService.getInstance().getGoSingApiService(getApplicationContext()).registerStore(
                    reqStoreRegisterDto, fileMap)
                    .enqueue(new MoaAuthCallback<ResStoreRegisterDto>(
                            RetrofitService.getInstance().getMoaAuthConfig(),
                            RetrofitService.getInstance().getSessionChecker()
                    ) {
                        @Override
                        public void onFinalResponse(Call<ResStoreRegisterDto> call, ResStoreRegisterDto resModel) {
                            if (resModel.getStateCode() == NetworkConstants.STATE_CODE_SUCCESS) {
                                if (resModel.getDetailCode() == 200) {
                                    Logger.d("업소 등록 성공");
                                    Intent intent = new Intent(StoreActivity.this,
                                            WaitingApprovalActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(
                                            StoreActivity.this,
                                            "업소 등록을 실패했습니다.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(
                                        StoreActivity.this,
                                        "업소 등록을 실패했습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFinalFailure(Call<ResStoreRegisterDto> call, boolean isSession, Throwable t) {
//                            Logger.d("업소 등록 실패");
                            Toast.makeText(
                                    StoreActivity.this,
                                    "업소 등록을 실패했습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Logger.d("데이터 부족");
        }
    }

    private boolean checkData() {
        String storeName = etStoreName.getText().toString().trim();
        if (storeName.length() > 0) {
            reqStoreRegisterDto.setStoreName(storeName);
        } else {
            return false;
        }

        String simpleAddress = etSimpleAddress.getText().toString().trim();
        if (simpleAddress.length() > 0) {
            reqStoreRegisterDto.setSimpleAddress(simpleAddress);
        } else {
            return false;
        }

        String detailAddress = etDetailAddress.getText().toString().trim();
        if (detailAddress.length() > 0) {
            reqStoreRegisterDto.setDetailAddress(detailAddress);
        } else {
            return false;
        }

        String storeTel = etStoreTel.getText().toString().trim();
        String bossTel = etBossTel.getText().toString().trim();
        if (storeTel.length() > 0) {
            reqStoreRegisterDto.setStoreTel(storeTel);
        } else {
            return false;
        }

        if (bossTel.length() > 0) {
            reqStoreRegisterDto.setStoreTel(bossTel);
        } else {
            return false;
        }

        String roadAddress = tvRoadAddress.getText().toString();
        if (roadAddress.length() > 0) {
            reqStoreRegisterDto.setRoadAddress(roadAddress);
        } else {
            return false;
        }

        reqStoreRegisterDto.setCeoComment(etCeoComment.getText().toString());
        reqStoreRegisterDto.setEntX(addressCoordInfoDto.getEntX());
        reqStoreRegisterDto.setEntY(addressCoordInfoDto.getEntY());
        reqStoreRegisterDto.setPostNumber(addressInfoDto.getZipNo());
        reqStoreRegisterDto.setAdmCd(addressInfoDto.getAdmCd());
        reqStoreRegisterDto.setEmdNm(addressInfoDto.getEmdNm());

        List<ReqStoreRegisterDto.RoomInfoDto> roomInfoDtoList = new ArrayList<>();
        // 룸체크
        if (cbLargeRoom.isChecked()) {
            ReqStoreRegisterDto.RoomInfoDto roomInfoDto = reqStoreRegisterDto.new RoomInfoDto();
            String larginRoomPrice = tvLargeRoomPrice.getText().toString().trim();
            if (larginRoomPrice.length() == 0) {
                return false;
            }

            if (spLargeRoom.getSelectedItemPosition() == 0) {
                return false;
            }

            roomInfoDto.setPrice(larginRoomPrice);
            roomInfoDto.setRoomType(1);
            roomInfoDto.setPeoplePerRoom(spLargeRoom.getSelectedItem().toString());
            roomInfoDto.setSentType("insert");
            roomInfoDtoList.add(roomInfoDto);
        }

        if (cbMiddleRoom.isChecked()) {
            ReqStoreRegisterDto.RoomInfoDto roomInfoDto = reqStoreRegisterDto.new RoomInfoDto();
            String middleRoomPrice = tvMiddleRoomPrice.getText().toString().trim();
            if (middleRoomPrice.length() == 0) {
                return false;
            }

            if (spMiddleRoom.getSelectedItemPosition() == 0) {
                return false;
            }

            roomInfoDto.setPrice(middleRoomPrice);
            roomInfoDto.setRoomType(2);
            roomInfoDto.setPeoplePerRoom(spLargeRoom.getSelectedItem().toString());
            roomInfoDto.setSentType("insert");
            roomInfoDtoList.add(roomInfoDto);
        }

        if (cbSmallRoom.isChecked()) {
            ReqStoreRegisterDto.RoomInfoDto roomInfoDto = reqStoreRegisterDto.new RoomInfoDto();
            String smallRoomPrice = tvSmallRoomPrice.getText().toString().trim();
            if (smallRoomPrice.length() == 0) {
                return false;
            }

            if (spLargeRoom.getSelectedItemPosition() == 0) {
                return false;
            }

            roomInfoDto.setPrice(smallRoomPrice);
            roomInfoDto.setRoomType(3);
            roomInfoDto.setPeoplePerRoom(spLargeRoom.getSelectedItem().toString());
            roomInfoDto.setSentType("insert");
            roomInfoDtoList.add(roomInfoDto);
        }

        reqStoreRegisterDto.setRoomInfoDtoList(roomInfoDtoList);

        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        addressCoordInfoDto =
                new Gson().fromJson(data.getStringExtra("coord"), ResAddressCoordDto.AddressCoordInfoDto.class);

        addressInfoDto =
                new Gson().fromJson(data.getStringExtra("juso"), ResAddressSearchDto.AddressInfoDto.class);

        tvRoadAddress.setText(addressInfoDto.getRoadAddress());

    }
}
