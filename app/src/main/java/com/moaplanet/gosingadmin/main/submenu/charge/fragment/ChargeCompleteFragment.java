package com.moaplanet.gosingadmin.main.submenu.charge.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.main.submenu.charge.model.ChargeViewModel;

/**
 * 충전완료 화면 Fragment
 */
public class ChargeCompleteFragment extends BaseFragment {

    private ChargeViewModel mChargeViewModel;

    @Override
    protected void initFragment() {
        super.initFragment();
        if (getActivity() != null) {
            mChargeViewModel = ViewModelProviders.of(getActivity()).get(ChargeViewModel.class);
        }
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_charge_complete;
    }

    @Override
    public void initView(View view) {

        CommonTitleBar commonTitle = view.findViewById(R.id.title_fragment_charge_complete);
        commonTitle.setBackButtonClickListener(view1 -> Navigation.findNavController(view).popBackStack());

        Button btnChargeConfrim = view.findViewById(R.id.btn_fragment_charge_complete_confirm);
        btnChargeConfrim.setOnClickListener(view1 -> {
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        TextView money = view.findViewById(R.id.tv_fragment_charge_complete_money_title);
        money.setVisibility(View.GONE);

        // 충전 금액
        TextView mPriceCharge = view.findViewById(R.id.tv_fragment_charge_complete_money);
        mPriceCharge.setText(getString(
                R.string.fragment_payment_money_won,
                mChargeViewModel.getPriceCharge().getValue()));

        // 선택한 카드
        TextView mSelectCard = view.findViewById(R.id.tv_fragment_charge_complete_charge_type);
        mSelectCard.setText(mChargeViewModel.getSelectCardInfo().getValue().getmCardName());

    }

    @Override
    public void initListener() {

    }
}
