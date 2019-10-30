package com.moaplanet.gosingadmin.main.submenu.store;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.main.submenu.store.model.req.ReqStoreRegisterDto;
import com.moaplanet.gosingadmin.main.submenu.store.model.res.ResStoreRegisterDto;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.SharedPreferencesManager;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class StoreActivity extends BaseActivity {

    private CommonTitleBar commonTitleBar;
    private TextView tvCeoCommentCount;
    private Button btnDone;
    private EditText etStoreName, etStoreTel, etSimpleAddress, etDetailAddress, etCeoComment;
//    private Spinner spLargeRoom, spMiddleRoom, spSmallRoom;

    private ReqStoreRegisterDto reqStoreRegisterDto;

    @Override
    public int layoutRes() {
        return R.layout.activity_store;
    }

    @Override
    public void initView() {
        etCeoComment = findViewById(R.id.et_store_ceo_comment);
        commonTitleBar = findViewById(R.id.common_store_title_bar);
        tvCeoCommentCount = findViewById(R.id.tv_store_ceo_comment_count);
        setTvCeoCommentCount(0);
        btnDone = findViewById(R.id.btn_store_register);
        etStoreName = findViewById(R.id.et_store_input_name);
        etStoreTel = findViewById(R.id.et_store_call_number);
        etSimpleAddress = findViewById(R.id.et_store_short_address);
        etDetailAddress = findViewById(R.id.et_store_detail_address);
//        spLargeRoom = findViewById(R.id.sp_store_large_room_personnel);
//        spMiddleRoom = findViewById(R.id.sp_store_middle_room_personnel);
//        spSmallRoom = findViewById(R.id.sp_store_small_room_personnel);
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

            Map<String, okhttp3.RequestBody> map = new HashMap<>();
//            map.put("ic_question_mark", requestBody);
//            reqStoreRegisterDto.getStorePhoto().put("1", "bg_ad_fifteen_day_product");
            RetrofitService.getInstance().getGoSingApiService(getApplicationContext()).registerStore(
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
        if (storeTel.length() > 0) {
            reqStoreRegisterDto.setStoreTel(storeTel);
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
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);
        reqStoreRegisterDto.setEmail(sharedPreferencesManager.getEmail());
        return true;

    }

}
