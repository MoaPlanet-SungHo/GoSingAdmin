package com.moaplanet.gosingadmin.main.submenu.charge.fragment;

import android.view.View;
import android.widget.ProgressBar;

import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.main.submenu.charge.model.viewmodel.DepositWithoutBankbookViewModel;

public class DepositWithoutBankbookFragment extends BaseFragment {

    // 뷰모델
    private DepositWithoutBankbookViewModel mViewModel;

    // 로딩바
    private ProgressBar mLoadingBar;

    // 가상계좌 조회 여부 --> true : 조회하기 | false : 조회 안함
    private boolean mSearchVirtualAccount = true;

    @Override
    protected void initFragment() {
        super.initFragment();
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
            if (dto == null) {
                mSearchVirtualAccount = false;
            }
        });

        // 로딩 유무
        mViewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {

            } else {

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
