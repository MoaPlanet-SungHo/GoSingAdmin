package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model.ResDepositablePointDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.StringUtil;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 출금 가능 금액 Fragment
 */
public class PointwithdrawalToolTipFragment extends BaseFragment {

    // 로딩바
    private View loadingBar;

    @Override
    public int layoutRes() {
        return R.layout.fragment_point_withdrawal_tool_tip;
    }

    @Override
    public void initView(View view) {
        loadingBar = view.findViewById(R.id.pb_fragment_point_withdrawal_tool_tip_loading_bar);
        onStartLoading(loadingBar);
    }

    @Override
    public void initListener() {

        CommonTitleBar commonTitle = view.findViewById(R.id.title_fragment_point_withdrawal_tool_tip);
        RxView.clicks(commonTitle.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onBackNavigation());

        Button confirm = view.findViewById(R.id.btn_fragment_point_withdrawal_tool_tip_confirm);
        RxView.clicks(confirm)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onBackNavigation());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPoint();
    }

    private void initPoint() {
        RetrofitService.getInstance().getGoSingApiService()
                .onServerDepositablePoint().enqueue(new MoaAuthCallback<ResDepositablePointDto>(
                RetrofitService.getInstance().getMoaAuthConfig(),
                RetrofitService.getInstance().getSessionChecker()
        ) {
            @Override
            public void onFinalResponse(Call<ResDepositablePointDto> call, ResDepositablePointDto resModel) {
                onStopLoading(loadingBar);
                if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                    // 포인트
                    TextView tvPoint = view.findViewById(R.id.tv_fragment_point_withdrawal_tool_tip_withdrawalable_money);
                    tvPoint.setText(getString(R.string.common_price_won,
                            StringUtil.convertCommaPrice(resModel.getResPointDto().getPoint())));
                    // 충전한 포인트
                    TextView tvChargePoint = view.findViewById(R.id.tv_fragment_point_withdrawal_tool_tip_charge_point);
                    tvChargePoint.setText(getString(R.string.common_price_won,
                            StringUtil.convertCommaPrice(resModel.getResPointDto().getChargePoint())));

                    // 적립된 포인트
                    TextView tvSavingPoint = view.findViewById(R.id.tv_fragment_point_withdrawal_tool_tip_save_point);
                    tvSavingPoint.setText(getString(R.string.common_price_won,
                            StringUtil.convertCommaPrice(resModel.getResPointDto().getSavingPoint())));
                } else {
                    onNetworkConnectFail();
                }
            }

            @Override
            public void onFinalFailure(Call<ResDepositablePointDto> call, boolean isSession, Throwable t) {
                onStopLoading(loadingBar);
                onNetworkConnectFail();
            }

            @Override
            public void onFinalNotSession() {
                super.onFinalNotSession();
                onStopLoading(loadingBar);
                onNotSession();
            }
        });
    }
}
