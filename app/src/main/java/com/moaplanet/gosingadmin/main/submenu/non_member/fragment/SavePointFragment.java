package com.moaplanet.gosingadmin.main.submenu.non_member.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;

import java.text.DecimalFormat;

public class SavePointFragment extends BaseFragment {

    private EditText etInputPoint;
    private DecimalFormat decimalFormat;
    private Button btnSaving;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        decimalFormat = new DecimalFormat("#,###");
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_save_point;
    }

    @Override
    public void initView(View view) {
        etInputPoint = view.findViewById(R.id.et_save_point_input_point);
        btnSaving = view.findViewById(R.id.btn_save_point_saving);
//        String s = decimalFormat.format(3000);
//        etInputPoint.setText(s+"원");
//        Logger.d("데이터 입력");
    }

    @Override
    public void initListener() {
        btnSaving.setOnClickListener(
                view -> onMoveNavigation(R.id.actionfragment_non_member_save_password));
    }
}
