package com.moaplanet.gosingadmin.main.submenu.charge.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.req.ReqCardChargeDto;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResCardChargeDto;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResCardListDto;
import com.moaplanet.gosingadmin.main.submenu.charge.model.viewmodel.ChargeCompleteViewModel;
import com.moaplanet.gosingadmin.main.submenu.charge.model.viewmodel.ChargeViewModel;
import com.moaplanet.gosingadmin.utils.StringUtil;

import java.util.concurrent.TimeUnit;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import rx.android.schedulers.AndroidSchedulers;

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

    private ReqCardChargeDto reqCardChargeDto;

    @Override
    protected void initFragment() {
        super.initFragment();
        reqCardChargeDto = new ReqCardChargeDto();
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (getActivity() != null) {
            if (mChargeViewModel == null) {
                mChargeViewModel = ViewModelProviders.of(getActivity()).get(ChargeViewModel.class);
            }
        }

        if (mChargeCompleteViewModel == null) {
            mChargeCompleteViewModel =
                    ViewModelProviders.of(this).get(ChargeCompleteViewModel.class);
        }
        mChargeCompleteViewModel.setIsLoading(true);

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
        reqCardChargeDto.setCardHashPk(mChargeViewModel.getSelectCardInfo().getValue().getCardHash());
        if (price != null) {
            reqCardChargeDto.setChargeMoney(price.replace(",", ""));
        }
        mChargeCompleteViewModel.onCardPointCharge(reqCardChargeDto);

    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_charge_complete;
    }

    @Override
    public void initView(View view) {

        // 로딩 관련
        mPrLoading = view.findViewById(R.id.pr_fragment_charge_complete_loading);

        TextView mTvChargeCompleteTitle = view.findViewById(R.id.tv_fragment_charge_complete_money_title);
        mTvChargeCompleteTitle.setVisibility(View.GONE);

    }

    @Override
    public void initListener() {

        // 상단 백버튼 클릭
        CommonTitleBar commonTitle = view.findViewById(R.id.title_fragment_charge_complete);
        RxView.clicks(commonTitle.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onBackNavigation());

        // 완료 버튼 클릭
        Button btnChargeConfirm = view.findViewById(R.id.btn_fragment_charge_complete_confirm);
        RxView.clicks(btnChargeConfirm)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                });
    }

    @Override
    protected void initObserve() {
        super.initObserve();
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
                onNotSession();
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
                onNetworkConnectFail();
            }
        });

        mChargeCompleteViewModel.resCode.observe(this, resCode -> {
            if (resCode == 4001) {
                Toast.makeText(view.getContext(),
                        " 결제 실패하였습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
