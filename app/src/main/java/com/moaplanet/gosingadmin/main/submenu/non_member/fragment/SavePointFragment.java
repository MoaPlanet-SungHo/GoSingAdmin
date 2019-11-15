package com.moaplanet.gosingadmin.main.submenu.non_member.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.main.submenu.non_member.model.NonMemberSavePointViewModel;

import java.text.DecimalFormat;

public class SavePointFragment extends BaseFragment {

    // 뷰모델
    private NonMemberSavePointViewModel mViewModel;

    private EditText etInputPoint;
    private DecimalFormat decimalFormat;
    private Button btnSaving;

    @Override
    protected void initFragment() {
        super.initFragment();
        if (getActivity() != null) {
            mViewModel = ViewModelProviders.of(getActivity()).get(NonMemberSavePointViewModel.class);
        }
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_save_point;
    }

    @Override
    public void initView(View view) {

        initObserve();

        btnSaving = view.findViewById(R.id.btn_save_point_saving);
        etInputPoint = view.findViewById(R.id.et_save_point_input_point);

        // 포인트 표시
        TextView tvPoint = view.findViewById(R.id.tv_fragment_save_point);

        if (mViewModel != null) {
            tvPoint.setText(getString(R.string.fragment_payment_money_won,
                    mViewModel.getPoint().getValue()));
        } else {
            tvPoint.setText("0원");
        }


    }

    @Override
    public void initListener() {

        // 적립 버튼
        btnSaving.setOnClickListener(
                view -> onMoveNavigation(R.id.actionfragment_non_member_save_password));

        etInputPoint.addTextChangedListener(mWatcherPriceCharge);

    }

    /**
     * 뷰 모델 옵저브
     */
    private void initObserve() {

        mViewModel.getSavePoint().observe(this, savePoint -> {
            int cp = etInputPoint.getSelectionStart();
            int startLen = etInputPoint.getText().length();
            int wonLen;
            if (etInputPoint.getText().length() == 1) {
                wonLen = -1;
            } else {
                wonLen = 0;
            }
            etInputPoint.setText(getString(R.string.fragment_payment_money_won, savePoint));
            int endLen = etInputPoint.getText().length();
            etInputPoint.setSelection((cp + (endLen - startLen)) + wonLen);
        });

    }

    private TextWatcher mWatcherPriceCharge = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            mViewModel.setSavePoint(editable.toString());
        }
    };

}
