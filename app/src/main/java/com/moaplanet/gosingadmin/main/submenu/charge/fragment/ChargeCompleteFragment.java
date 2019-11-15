package com.moaplanet.gosingadmin.main.submenu.charge.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.intro.login.LoginActivity;
import com.moaplanet.gosingadmin.main.submenu.charge.model.viewmodel.ChargeCompleteViewModel;
import com.moaplanet.gosingadmin.main.submenu.charge.model.viewmodel.ChargeViewModel;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.req.ReqCardChargeDto;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResCardListDto;
import com.moaplanet.gosingadmin.utils.StringUtil;

/**
 * 충전완료 화면 Fragment
 */
public class ChargeCompleteFragment extends BaseFragment {

    // 뷰 모델
    // 액티비티와 연결된 뷰 모델
    private ChargeViewModel mChargeViewModel;
    // 카드 충전 완료 화면의 뷰모델
    private ChargeCompleteViewModel mChargeCompleteViewModel;

    // 로딩뷰
    private ProgressBar mPrLoading;

    @Override
    protected void initFragment() {
        super.initFragment();
        if (getActivity() != null) {
            mChargeViewModel = ViewModelProviders.of(getActivity()).get(ChargeViewModel.class);
        }
        mChargeCompleteViewModel =
                ViewModelProviders.of(this).get(ChargeCompleteViewModel.class);
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_charge_complete;
    }

    @Override
    public void initView(View view) {

        // 로딩 관련
        mPrLoading = view.findViewById(R.id.pr_fragment_charge_complete_loading);
        mChargeCompleteViewModel.setIsLoading(true);

        TextView mTvChargeCompleteTitle = view.findViewById(R.id.tv_fragment_charge_complete_money_title);
        mTvChargeCompleteTitle.setVisibility(View.GONE);

        if (mChargeViewModel != null) {
            // 충전 금액
            TextView mPriceCharge = view.findViewById(R.id.tv_fragment_charge_complete_money);
            String price = mChargeViewModel.getPriceCharge().getValue();
            mPriceCharge.setText(getString(
                    R.string.fragment_payment_money_won,
                    price));

            // 선택한 카드
            ResCardListDto.CardInformationDto cardInfoDto =
                    mChargeViewModel.getSelectCardInfo().getValue();
            if (cardInfoDto != null) {
                TextView mSelectCard = view.findViewById(R.id.tv_fragment_charge_complete_charge_type);
                mSelectCard.setText(cardInfoDto.getCardName());
            }

            ReqCardChargeDto reqCardChargeDto = new ReqCardChargeDto();
            reqCardChargeDto.setCardHashPk(mChargeViewModel.getSelectCardInfo().getValue().getCardHash());
            if (price != null) {
                reqCardChargeDto.setChargeMoney(price.replace(",", ""));
            }
            mChargeCompleteViewModel.onCardPointCharge(reqCardChargeDto);

        } else {
            Toast.makeText(view.getContext(),
                    "재시도해 주세요",
                    Toast.LENGTH_SHORT)
                    .show();
        }

    }

    @Override
    public void initListener() {

        // 상단 백버튼 클릭
        CommonTitleBar commonTitle = view.findViewById(R.id.title_fragment_charge_complete);
        commonTitle.setBackButtonClickListener(view1 -> Navigation.findNavController(view).popBackStack());

        // 완료 버튼 클릭
        Button btnChargeConfirm = view.findViewById(R.id.btn_fragment_charge_complete_confirm);
        btnChargeConfirm.setOnClickListener(view1 -> {
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        observeViewModel();
    }

    private void observeViewModel() {

        // 로딩 관련 처리
        mChargeCompleteViewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                onStartLoading(mPrLoading);
            } else {
                onStopLoading(mPrLoading);
            }
            // 액티비티와 연결된 뷰 모델에 로딩 상태값 넘김
            if (getActivity() != null) {
                mChargeViewModel.setIsLoading(isLoading);
            }
        });

        // 세션 체크후 처리
        mChargeCompleteViewModel.getSession().observe(this, session -> {
            if (!session) {
                if (getActivity() != null) {
                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finishAffinity();
                }
                Toast.makeText(view.getContext(),
                        R.string.common_not_exist_session,
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        // 카드 결제후 포인트 데이터를 받음
        mChargeCompleteViewModel.getPointDto().observe(this, pointDto -> {
            TextView tvPoint = view.findViewById(R.id.tv_fragment_charge_complete_remain_point);
            String point = String.valueOf(pointDto.getPointGoSing());
            tvPoint.setText(getString(
                    R.string.fragment_payment_money_won,
                    StringUtil.convertCommaPrice(point)));
        });

        // 통신 성공 유무에 따른 처리
        mChargeCompleteViewModel.getIsApiSuccess().observe(this, isSuccess -> {
            if (!isSuccess) {
                Toast.makeText(view.getContext(),
                        "다시 시도해 주세요",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }
}
