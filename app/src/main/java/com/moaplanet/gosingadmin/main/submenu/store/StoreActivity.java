package com.moaplanet.gosingadmin.main.submenu.store;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.main.submenu.store.model.req.ReqStoreRegisterDto;
import com.moaplanet.gosingadmin.main.submenu.store.model.res.ResStoreRegisterDto;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnMultiSelectedListener;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;

public class StoreActivity extends BaseActivity {

    private CommonTitleBar commonTitleBar;
    private TextView tvCeoCommentCount;
    private Button btnDone;
    private EditText etStoreName, etStoreTel, etBossTel, etSimpleAddress, etDetailAddress, etCeoComment;
    public CompositeDisposable compositeDisposable;
    public RxPermissions rxPermissions;
    private List<? extends Uri> selectedUriList;
    private List<ImageView> pictureImageViewList;           //이미지 리스트
    private List<ImageView> pictureImageInnerIconList;      //이미지 추가하기 아이콘 리스트
    private List<Button> deletePictureButtonList;           //삭제 버튼 리스트
    private final int PICTURE_COUNT = 8;

    private ImageView[] ivStoreImage = new ImageView[8];
//    private Spinner spLargeRoom, spMiddleRoom, spSmallRoom;

    private ReqStoreRegisterDto reqStoreRegisterDto;


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
//                        if (getActivity() instanceof ReviewWriteActivity) {
                            TedImagePicker.with(this)
                                    .selectedUri(selectedUriList)
                                    .max(8, "최대 8개 선택가능합니다.")
                                    .startMultiImage(list -> {
                                        Logger.d("Selected list >>> " + list.toString());
                                        selectedUriList = list;
//                                        setImageAddComponentGroupUi(list);
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
//            pictureImageInnerIconList.get(i).setVisibility(View.VISIBLE);
//            deletePictureButtonList.get(i).setVisibility(View.GONE);
        }
    }

    @Override
    public void initView() {

        compositeDisposable = new CompositeDisposable();
        rxPermissions = new RxPermissions(this);

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

//        setImageAddComponentGroupUi(selectedUriList);
        detaultAddPictureUi();

//        spLargeRoom = findViewById(R.id.sp_store_large_room_personnel);
//        spMiddleRoom = findViewById(R.id.sp_store_middle_room_personnel);
//        spSmallRoom = findViewById(R.id.sp_store_small_room_personnel);
    }

//    /**
//     * 이미지 선택하기 UI 상태를 구성
//     * 그 내용은 아래와 같다.
//     * 최초 이미지 선택 가이드 화면
//     * 이미지 3장 선택 가이드 화면
//     */
//    private void setImageAddComponentGroupUi(List<? extends Uri> uriList) {
//        if (uriList != null && uriList.size() > 0) {
//        } else {
//        }
//    }

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
//            String filePath = R.class.getPackage().getName() + "/" + R.drawable.bg_ad_fifteen_day_product;
//            RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), filePath);
            PersistentCookieStore cookieStore = new PersistentCookieStore(this);
            CookieManager cookieManager = new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL);


            Map<String, okhttp3.RequestBody> map = new HashMap<>();
//            map.put("ic_question_mark", requestBody);
//            reqStoreRegisterDto.getStorePhoto().put("1", "bg_ad_fifteen_day_product");
            RetrofitService.getInstance().getGoSingApiService().registerStore(
                    reqStoreRegisterDto, map)
                    .enqueue(new MoaAuthCallback<ResStoreRegisterDto>(
                            RetrofitService.getInstance().getMoaAuthConfig(),
                            RetrofitService.getInstance().getSessionChecker()
                    ) {
                        @Override
                        public void onFinalResponse(Call<ResStoreRegisterDto> call, ResStoreRegisterDto resModel) {
                            Logger.d("업소 등록 성공");
                        }

                        @Override
                        public void onFinalFailure(Call<ResStoreRegisterDto> call, boolean isSession, Throwable t) {
                            Logger.d("업소 등록 실패");

                        }
                    });
        } else {
            Logger.d("필수 데이터 부족");
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
        reqStoreRegisterDto.setCeoComment(etCeoComment.getText().toString());
        reqStoreRegisterDto.setRoadAddress("로로명주소");
        reqStoreRegisterDto.setEntX("123");
        reqStoreRegisterDto.setEntY("123");
        reqStoreRegisterDto.setEmdNm("동 정보");
        reqStoreRegisterDto.setPostNumber("우편주소");
        reqStoreRegisterDto.setAdmCd("123");
        return true;

    }

}
