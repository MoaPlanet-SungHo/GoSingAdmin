package com.moaplanet.gosingadmin.main.submenu.address;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.utils.StringUtil;

public class AddressSearchActivity extends BaseActivity {

    private AddressPaingAdapter addressPaingAdapter;
    private RecyclerView rvAddressSearch;
    private Button btnSearch;
    private EditText etAddressKeyword;

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
    }

    @Override
    public void initListener() {
        btnSearch.setOnClickListener(view -> {
            onAddressSearch();
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDefault();
    }

    private void initDefault() {
        addressPaingAdapter = new AddressPaingAdapter();
        rvAddressSearch.setAdapter(addressPaingAdapter);
    }

    private void onAddressSearch() {
        if (checkData()) {

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

}
