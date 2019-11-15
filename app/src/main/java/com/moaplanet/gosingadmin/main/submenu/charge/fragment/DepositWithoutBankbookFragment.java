package com.moaplanet.gosingadmin.main.submenu.charge.fragment;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.main.submenu.charge.model.viewmodel.ChargeViewModel;
import com.moaplanet.gosingadmin.main.submenu.charge.model.viewmodel.DepositWithoutBankbookViewModel;

public class DepositWithoutBankbookFragment extends BaseFragment {

    // 뷰모델
    // 무통장 입금 화면의 뷰모델
    private DepositWithoutBankbookViewModel mViewModel;
    // 충전 화면 activity 와 연결된 뷰 모델
    private ChargeViewModel mChargeViewModel;

    // 로딩바
    private ProgressBar mLoadingBar;

    // 가상계좌 조회 여부 --> true : 조회하기 | false : 조회 안함
    private boolean mSearchVirtualAccount = true;

    @Override
    protected void initFragment() {
        super.initFragment();
        if (getActivity() != null) {
            mChargeViewModel = ViewModelProviders.of(getActivity()).get(ChargeViewModel.class);
        }
        mViewModel = ViewModelProviders.of(this).get(DepositWithoutBankbookViewModel.class);
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_deposit_without_bankbook;
    }

    @Override
    public void initView(View view) {
        mLoadingBar = view.findViewById(R.id.pb_fragment_deposit_without_bankbook_loading);
        mLoadingBar.setVisibility(View.GONE);
    }

    @Override
    public void initListener() {
        initObserve();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mSearchVirtualAccount) {
            mViewModel.onSearchVirtualAccount();
        }

    }

    private void initObserve() {

        // 가상 계좌 조회 api 호출
        mViewModel.getVirtualAccountDto().observe(this, dto -> {
            mSearchVirtualAccount = false;
            if (dto == null) {

            } else {
                // 은행 이름
                TextView bankName =
                        view.findViewById(R.id.tv_fragment_deposit_without_bankbook_bank_name);
                bankName.setText(dto.getBankName());

                // 계좌 번호
                TextView bankAccountNumber =
                        view.findViewById(R.id.tv_fragment_deposit_without_bankbook_bank_number);
                bankAccountNumber.setText(dto.getVirtaulAccountNumber());
            }
        });

        // 로딩 유무
        mViewModel.getIsLoading().observe(this, isLoading -> {
            onInitLoading(mLoadingBar, isLoading);
            mChargeViewModel.setIsLoading(isLoading);
        });

        // 세션 없을경우 처리
        mViewModel.getSession().observe(this, isSession -> {
            if (!isSession) {
                onNotSession();
            }
        });

        // 통신 실패
        mViewModel.getIsApiSuccess().observe(this, isSuccess -> {
            if (!isSuccess) {
                onNetworkConnectFail();
            }
        });
    }

    @Override
    protected void onStartLoading(View viewLoading) {
        super.onStartLoading(viewLoading);
    }

    @Override
    protected void onStopLoading(View viewLoading) {
        super.onStopLoading(viewLoading);
    }
}
