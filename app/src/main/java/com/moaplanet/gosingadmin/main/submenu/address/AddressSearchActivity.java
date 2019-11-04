package com.moaplanet.gosingadmin.main.submenu.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.main.submenu.address.model.AddressViewModel;
import com.moaplanet.gosingadmin.main.submenu.address.model.req.ReqAddressCoordDto;
import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressCoordDto;
import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressSearchDto;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.StringUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressSearchActivity extends BaseActivity {

    private AddressPagingAdapter addressPagingAdapter;
    private RecyclerView rvAddressSearch;
    private Button btnSearch;
    private EditText etAddressKeyword;
    private AddressViewModel addressViewModel;
    private LinearLayout llEmptyView;
    private LinearLayout llTipView;
    private CommonTitleBar commonTitleBar;

    @Override
    public int layoutRes() {
        return R.layout.activity_address_search;
    }

    @Override
    public void initView() {
        rvAddressSearch = findViewById(R.id.rv_address_search);
        rvAddressSearch.setLayoutManager(new LinearLayoutManager(this));
        btnSearch = findViewById(R.id.btn_address_search);
        etAddressKeyword = findViewById(R.id.et_address_search_keyword);
        llEmptyView = findViewById(R.id.ll_address_search_empty_group);
        llEmptyView.setVisibility(View.GONE);
        llTipView = findViewById(R.id.ll_address_search_tip_group);
        commonTitleBar = findViewById(R.id.common_address_search_title_bar);
    }

    @Override
    public void initListener() {
        btnSearch.setOnClickListener(view -> onAddressSearch());
        commonTitleBar.setBackButtonClickListener(view -> finish());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDefault();
    }

    private void initDefault() {
        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel.class);
        addressPagingAdapter = new AddressPagingAdapter();
        rvAddressSearch.setAdapter(addressPagingAdapter);

        addressPagingAdapter.setOnItemClick(this::searchAddressCoordinates);
    }

    private void onAddressSearch() {
        if (checkData()) {
            addressViewModel.addressSearchInit(etAddressKeyword.getText().toString());

            addressViewModel.addressSearchList.observe(this, addressInfoDtoList -> {
                addressPagingAdapter.submitList(addressInfoDtoList);
            });

            addressViewModel.getAllAddress().observe(this, totalCount ->
                    addressPagingAdapter.setTotalAddressCount(totalCount,
                            AddressSearchActivity.this));

            addressViewModel.getIsEmptyData().observe(this, isEmptyData -> {
                if (isEmptyData) {
                    llEmptyView.setVisibility(View.VISIBLE);
                } else {
                    llEmptyView.setVisibility(View.GONE);
                }
            });

            addressViewModel.getIsLoading().observe(this, isLoading -> {

            });

            llTipView.setVisibility(View.GONE);
        }
    }

    private boolean checkData() {
        String addressKeyword = etAddressKeyword.getText().toString().trim();

        if (addressKeyword.length() == 0) {
            return false;
        }

        if (StringUtil.isContainSpecialCharacter(addressKeyword)) {
            Toast.makeText(this, "주소 검색시에는 특수문자를 포함할 수 없습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * 좌표 조회
     */
    private void searchAddressCoordinates(ResAddressSearchDto.AddressInfoDto addressInfoDto) {

        ReqAddressCoordDto reqAddressCoordDto = new ReqAddressCoordDto();
        reqAddressCoordDto.setAdmCd(addressInfoDto.getAdmCd());
        reqAddressCoordDto.setBuldMnnm(addressInfoDto.getBuldMnnm());
        reqAddressCoordDto.setBuldSlno(addressInfoDto.getBuldSlno());
        reqAddressCoordDto.setRnMgtSn(addressInfoDto.getRnMgtSn());
        reqAddressCoordDto.setUdrtYn(addressInfoDto.getUdrtYn());

        RetrofitService.getInstance().getAddressApiService().searchCoord(
                reqAddressCoordDto.getConfmKey(),
                reqAddressCoordDto.getAdmCd(),
                reqAddressCoordDto.getRnMgtSn(),
                reqAddressCoordDto.getUdrtYn(),
                reqAddressCoordDto.getBuldMnnm(),
                reqAddressCoordDto.getBuldSlno(),
                reqAddressCoordDto.getResultType()
        ).enqueue(new Callback<ResAddressCoordDto>() {
            @Override
            public void onResponse(@NonNull Call<ResAddressCoordDto> call,
                                   @NonNull Response<ResAddressCoordDto> response) {
                if (response.body() != null) {

                    if (response.body().getAddressCoordInfoDto() != null) {
                        Intent intent = new Intent();
                        intent.putExtra(GoSingConstants.INTENT_KEY_ADDRESS_INFO,
                                new Gson().toJson(addressInfoDto));
                        intent.putExtra(GoSingConstants.INTENT_KEY_ADDRESS_COORDINATES,
                                new Gson().toJson(
                                        response.body()
                                                .getAddressCoordInfoDto()
                                                .get(0)));
                        setResult(GoSingConstants.RESULT_CODE_ADDRESS_SEARCH, intent);
                        finish();
                    } else {
                        Toast.makeText(AddressSearchActivity.this,
                                "서버 통신 오류입니다.",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(AddressSearchActivity.this,
                            "서버 통신 오류입니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResAddressCoordDto> call,
                                  @NonNull Throwable t) {
                Toast.makeText(AddressSearchActivity.this,
                        "서버 통신 오류입니다.",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

}
