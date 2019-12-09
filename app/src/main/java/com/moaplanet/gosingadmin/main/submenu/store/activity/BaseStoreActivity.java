package com.moaplanet.gosingadmin.main.submenu.store.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.interfaces.PriceWatcher;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.main.submenu.address.AddressSearchActivity;
import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressCoordDto;
import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressSearchDto;
import com.moaplanet.gosingadmin.main.submenu.store.model.req.ReqStoreRegisterDto;
import com.moaplanet.gosingadmin.main.submenu.store.model.res.ResStoreRegisterDto;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.DeviceUtil;
import com.moaplanet.gosingadmin.utils.StringUtil;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.RequestBody;
import retrofit2.Call;
import rx.android.schedulers.AndroidSchedulers;

public abstract class BaseStoreActivity extends BaseActivity {

    // 로딩
    protected ProgressBar loadingBar;
    // 업소명 입력
    protected EditText etStoreName;
    // 타이틀
    private CommonTitleBar titleBar;
    // 주소 검색
    private TextView tvAddressSearch;
    // 기본 주소
    protected TextView tvRoadAddress;
    // 간편 주소
    protected EditText etShortAddress;
    // 상세 주소
    protected EditText etDetailAddress;
    // 가맹점, 사장님 전화번호
    protected EditText etStoreCallNumber, etCeoCallNumber;

    // 권한 관련
    public CompositeDisposable compositeDisposable;
    public RxPermissions rxPermissions;

    // 이미지 관련
    protected List<ImageView> pictureImageViewList;           //이미지 리스트
    protected final int PICTURE_COUNT = 8;
    protected List<? extends Uri> selectedUriList;

    // 사장님 코멘트 관련
    private TextView tvCeoCommentCount;
    protected EditText etCeoComment;

    private Button btnDone;

    protected List<Spinner> roomPersonnelList;
    protected List<EditText> roomPriceList;
    protected List<CheckBox> roomTypeList;

    // 서버에 전송할 reqDto
    protected ReqStoreRegisterDto reqStoreRegisterDto;

    // 좌표 데이터 들어있는 dto
    protected ResAddressCoordDto.AddressCoordInfoDto addressCoordInfoDto;
    // 주소 정보가 들어있는 dto
    protected ResAddressSearchDto.AddressInfoDto addressInfoDto;

    private String lPrice = "", mPrice = "", sPrice = "";

    public abstract void registerStore();

    @Override
    public int layoutRes() {
        return R.layout.activity_base_store;
    }

    @Override
    public void initView() {

        loadingBar = findViewById(R.id.progress_activity_base_store_loading);

        titleBar = findViewById(R.id.title_base_store_title_bar);

        TextView tvStoreTitle = findViewById(R.id.tv_activity_base_store_name);
        tvStoreTitle.setText(getMakeEndStar(R.string.activity_base_store_name));

        etStoreName = findViewById(R.id.et_activity_base_store_input_name);

        TextView tvStoreAddressTitle = findViewById(R.id.tv_activity_base_store_address);
        tvStoreAddressTitle.setText(getMakeEndStar(R.string.activity_base_store_address));

        tvAddressSearch = findViewById(R.id.tv_activity_base_store_search_address);

        TextView tvShortAddressTitle = findViewById(R.id.tv_activity_base_store_short_address);
        tvShortAddressTitle.setText(getMakeEndStar(R.string.activity_base_store_short_address));

        TextView tvStoreCallNumber = findViewById(R.id.tv_activity_base_store_call_number);
        tvStoreCallNumber.setText(getMakeEndStar(R.string.activity_base_store_call_number));

        TextView tvCeoCallNumber = findViewById(R.id.tv_activity_base_ceo_call_number);
        tvCeoCallNumber.setText(getMakeEndStar(R.string.activity_base_ceo_call_number));

        tvRoadAddress = findViewById(R.id.tv_activity_base_store_default_address);

        etStoreCallNumber = findViewById(R.id.et_activity_base_store_call_number);
        etCeoCallNumber = findViewById(R.id.et_activity_base_ceo_call_number);

        compositeDisposable = new CompositeDisposable();
        rxPermissions = new RxPermissions(this);

        etCeoComment = findViewById(R.id.et_activity_base_store_ceo_comment);
        tvCeoCommentCount = findViewById(R.id.tv_activity_base_store_ceo_comment_count);
        setTvCeoCommentCount(0);

        pictureImageViewList = new ArrayList<>();
        pictureImageViewList.add(findViewById(R.id.iv_activity_base_store_image_one));
        pictureImageViewList.add(findViewById(R.id.iv_activity_base_store_image_two));
        pictureImageViewList.add(findViewById(R.id.iv_activity_base_store_image_three));
        pictureImageViewList.add(findViewById(R.id.iv_activity_base_store_image_four));
        pictureImageViewList.add(findViewById(R.id.iv_activity_base_store_image_five));
        pictureImageViewList.add(findViewById(R.id.iv_activity_base_store_image_six));
        pictureImageViewList.add(findViewById(R.id.iv_activity_base_store_image_seven));
        pictureImageViewList.add(findViewById(R.id.iv_activity_base_store_image_eight));

        int screenSize = DeviceUtil.getScreenWith(this);

        float thumbnailsGroupMargin =
                getResources().getDimension(R.dimen.activity_base_store_thumbnails_group_margin) * 2;
        float thumbnailsItemMargin =
                getResources().getDimension(R.dimen.activity_base_store_thumbnails_item_margin);

        int imgSize = (int) ((screenSize - thumbnailsGroupMargin - (thumbnailsItemMargin * 8)) / 4);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(imgSize, imgSize);
        layoutParams.leftMargin = (int) thumbnailsItemMargin;
        layoutParams.rightMargin = (int) thumbnailsItemMargin;
        for (ImageView imageView : pictureImageViewList) {
            imageView.setLayoutParams(layoutParams);
        }

        selectedUriList = new ArrayList<>();

        btnDone = findViewById(R.id.btn_activity_base_store_register);

        etShortAddress = findViewById(R.id.et_activity_base_store_short_address);
        etDetailAddress = findViewById(R.id.et_activity_base_store_detail_address);

        roomPersonnelList = new ArrayList<>();
        roomPersonnelList.add(findViewById(R.id.sp_activity_base_store_large_room_personnel));
        roomPersonnelList.add(findViewById(R.id.sp_activity_base_store_middle_room_personnel));
        roomPersonnelList.add(findViewById(R.id.sp_activity_base_store_small_room_personnel));

        List<Integer> roomPersonnelDataList = new ArrayList<>();
        roomPersonnelDataList.add(R.array.store_large_room_array);
        roomPersonnelDataList.add(R.array.store_middle_room_array);
        roomPersonnelDataList.add(R.array.store_small_room_array);

        for (int i = 0; i < roomPersonnelDataList.size(); i++) {
            List<String> list = Arrays.asList(getResources().getStringArray(roomPersonnelDataList.get(i)));
            ArrayAdapter<String> spAdapter = new ArrayAdapter<>(
                    this,
                    R.layout.item_spinner_store_room,
                    list);
            spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            roomPersonnelList.get(i).setAdapter(spAdapter);
        }

        EditText etLargeRoom = findViewById(R.id.et_activity_base_store_large_room_price);
        EditText etMiddleRoom = findViewById(R.id.et_activity_base_store_middle_room_price);
        EditText etSmallRoom = findViewById(R.id.et_activity_base_store_small_room_price);

        PriceWatcher lpriceWatcher = new PriceWatcher(etLargeRoom);
        etLargeRoom.addTextChangedListener(lpriceWatcher);
        lpriceWatcher.setCallback((completePrice, price) -> {

        });

        PriceWatcher mPriceWatcher = new PriceWatcher(etMiddleRoom);
        etMiddleRoom.addTextChangedListener(mPriceWatcher);

        mPriceWatcher.setCallback((completePrice, price) -> {

        });

        PriceWatcher sPriceWatcher = new PriceWatcher(etSmallRoom);
        etSmallRoom.addTextChangedListener(sPriceWatcher);

        sPriceWatcher.setCallback((completePrice, price) -> {

        });

        roomPriceList = new ArrayList<>();
        roomPriceList.add(etLargeRoom);
        roomPriceList.add(etMiddleRoom);
        roomPriceList.add(etSmallRoom);

        roomTypeList = new ArrayList<>();
        roomTypeList.add(findViewById(R.id.cb_activity_base_store_large_room));
        roomTypeList.add(findViewById(R.id.cb_activity_base_store_middle_room));
        roomTypeList.add(findViewById(R.id.cb_activity_base_store_small_room));

        defaultAddPictureUi();
    }

    @Override
    public void initListener() {
//        for (int i = 0; i < PICTURE_COUNT; i++) {
//            pictureImageViewList.get(i).setOnClickListener(view1 -> selectPicture());
//        }

        RxView.clicks(titleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> finish());

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

        RxView.clicks(btnDone)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    if (checkData()) {
                        registerStore();
                    }
                });

        etStoreCallNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        etCeoCallNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        RxView.clicks(tvAddressSearch)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    Intent intent = new Intent(this, AddressSearchActivity.class);
                    startActivityForResult(intent, GoSingConstants.REQ_CODE_ADDRESS_SEARCH);
                });

    }

    /**
     * 이미지 선택하기 UI Default
     */
    public void defaultAddPictureUi() {
        for (int i = 0; i < PICTURE_COUNT; i++) {
            pictureImageViewList.get(i).setImageURI(null);
            pictureImageViewList.get(i).setBackgroundResource(R.drawable.border_rect_0_0_0_0_1dp_e9e9e9_f8f8f8);
        }
    }

    private void setTvCeoCommentCount(int count) {
        tvCeoCommentCount.setText(getString(R.string.activity_base_store_ceo_comment_count, count));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reqStoreRegisterDto = new ReqStoreRegisterDto();
    }

    public void connectServer(Map<String, RequestBody> fileMap) {

        startLoading();
        RetrofitService.getInstance().getGoSingApiService().onServerRegisterStore(
                reqStoreRegisterDto, fileMap)
                .enqueue(new MoaAuthCallback<ResStoreRegisterDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResStoreRegisterDto> call, ResStoreRegisterDto resModel) {
                        endLoading();
                        if (resModel.getDetailCode() == 200) {
                            Logger.d("업소 등록 성공");
                            onServerSuccess();
                        } else {
                            Toast.makeText(
                                    BaseStoreActivity.this,
                                    "업소 등록을 실패했습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFinalFailure(Call<ResStoreRegisterDto> call, boolean isSession, Throwable t) {
                        Toast.makeText(
                                BaseStoreActivity.this,
                                "업소 등록을 실패했습니다.",
                                Toast.LENGTH_SHORT).show();
                        endLoading();
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                        endLoading();
                        onNotSession();
                    }
                });
    }

    private void startLoading() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        loadingBar.setVisibility(View.VISIBLE);
    }

    private void endLoading() {
        loadingBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private boolean checkData() {

        if (checkEmpty(etStoreName)) {
            reqStoreRegisterDto.setStoreName(etStoreName.getText().toString());
        } else {
            return false;
        }

        if (checkEmpty(etShortAddress)) {
            reqStoreRegisterDto.setSimpleAddress(etShortAddress.getText().toString());
        } else {
            return false;
        }

        if (checkEmpty(tvRoadAddress)) {
            reqStoreRegisterDto.setRoadAddress(tvRoadAddress.getText().toString());
        } else {
            return false;
        }

        if (checkEmpty(etDetailAddress)) {
            reqStoreRegisterDto.setDetailAddress(etDetailAddress.getText().toString());
        } else {
            return false;
        }

        if (checkEmpty(etStoreCallNumber)) {
            reqStoreRegisterDto.setStoreTel(etStoreCallNumber.getText().toString());
        } else {
            return false;
        }

        if (checkEmpty(etCeoCallNumber)) {
            reqStoreRegisterDto.setCeoTel(etCeoCallNumber.getText().toString());
        } else {
            return false;
        }

        reqStoreRegisterDto.setCeoComment(etCeoComment.getText().toString());

        if (addressCoordInfoDto != null) {
            reqStoreRegisterDto.setEntX(addressCoordInfoDto.getEntX());
            reqStoreRegisterDto.setEntY(addressCoordInfoDto.getEntY());
        } else {
            return false;
        }

        if (addressInfoDto != null) {
            reqStoreRegisterDto.setPostNumber(addressInfoDto.getZipNo());
            reqStoreRegisterDto.setAdmCd(addressInfoDto.getAdmCd());
            reqStoreRegisterDto.setEmdNm(addressInfoDto.getEmdNm());
        } else {
            return false;
        }

        return true;
    }

    protected boolean checkEmpty(TextView textView) {
        return textView.getText().toString().trim().length() != 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GoSingConstants.REQ_CODE_ADDRESS_SEARCH
                && resultCode == GoSingConstants.RESULT_CODE_ADDRESS_SEARCH) {

            if (data != null) {
                addressCoordInfoDto =
                        new Gson().fromJson(
                                data.getStringExtra(GoSingConstants.INTENT_KEY_ADDRESS_COORDINATES),
                                ResAddressCoordDto.AddressCoordInfoDto.class);

                addressInfoDto =
                        new Gson().fromJson(
                                data.getStringExtra(GoSingConstants.INTENT_KEY_ADDRESS_INFO),
                                ResAddressSearchDto.AddressInfoDto.class);

                tvRoadAddress.setText(addressInfoDto.getRoadAddress());
            }
        }

    }

    private SpannableStringBuilder getMakeEndStar(@StringRes int stringRes) {
        SpannableStringBuilder storeNameBuilder = new SpannableStringBuilder();
        storeNameBuilder.append(getString(stringRes));
        storeNameBuilder.append(StringUtil.convertFontColor(
                " *",
                ContextCompat.getColor(this, R.color.color_4300ff)));
        return storeNameBuilder;
    }

    @Override
    public void onBackPressed() {
        if (loadingBar.getVisibility() == View.GONE) {
            super.onBackPressed();
        }
    }

    private void onServerSuccess() {

        if (this instanceof ModifyStoreActivity) {
            finish();
        } else {
            Intent intent = new Intent(BaseStoreActivity.this,
                    WaitingApprovalActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
