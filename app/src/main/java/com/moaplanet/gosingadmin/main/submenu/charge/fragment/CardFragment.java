package com.moaplanet.gosingadmin.main.submenu.charge.fragment;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.interfaces.PriceWatcher;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.intro.login.LoginActivity;
import com.moaplanet.gosingadmin.main.submenu.charge.activity.CardRegisterActivity;
import com.moaplanet.gosingadmin.main.submenu.charge.adapter.CardAdapter;
import com.moaplanet.gosingadmin.main.submenu.charge.model.viewmodel.ChargeCardViewModel;
import com.moaplanet.gosingadmin.main.submenu.charge.model.viewmodel.ChargeViewModel;
import com.moaplanet.gosingadmin.utils.ViewUtil;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class CardFragment extends BaseFragment {

    // 뷰모델 관련
    private ChargeCardViewModel mChargeCardViewModel;
    private ChargeViewModel mChargeViewModel;

    //카드 선택 타이틀
    private ConstraintLayout clSelectCardTitle;

    private ConstraintLayout clAddCardGroup;
    private ConstraintLayout clCardListGroup;       //카드 리스트 그룹
    private Button btnCardCharge;

    // 카드 리스트 어뎁터
    private CardAdapter mCardAdapter;

    // 충전 금액 입력 뷰
    private EditText etPriceCharge;
    // 충전 금액 지우기
    private LinearLayout llPriceChargeClear;
    // 로딩뷰
    private ProgressBar mPrLoading;
    // 카드 추가 뷰
    private ConstraintLayout mClCardItemAdd;

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (getActivity() != null) {
            if (mChargeViewModel == null) {
                mChargeViewModel = ViewModelProviders.of(getActivity()).get(ChargeViewModel.class);
            }
        }
        if (mChargeCardViewModel == null) {
            mChargeCardViewModel = ViewModelProviders.of(this).get(ChargeCardViewModel.class);
        }
        mChargeCardViewModel.setIsLoading(true);
        mChargeCardViewModel.onCardListInit();
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_card;
    }

    @Override
    public void initView(View view) {
        mPrLoading = view.findViewById(R.id.pr_fragment_card_loading);

        clSelectCardTitle = view.findViewById(R.id.cl_fragment_card_select_card_title);

        clAddCardGroup = view.findViewById(R.id.cl_fragment_card_add_card_group);
        clCardListGroup = view.findViewById(R.id.cl_fragment_card_list_group);
        clCardListGroup.setVisibility(View.GONE);

        btnCardCharge = view.findViewById(R.id.btn_fragment_card_charge);

        etPriceCharge = view.findViewById(R.id.et_fragment_card_input_won);
        llPriceChargeClear = view.findViewById(R.id.ll_fragment_card_clear_input_price_group);

        mClCardItemAdd = view.findViewById(R.id.cl_fragment_card_add_group);
        initAdapter();

    }

    @Override
    public void initListener() {
        RxView.clicks(clSelectCardTitle)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    if (clSelectCardTitle.isSelected()) {
                        clSelectCardTitle.setSelected(false);
                        clCardListGroup.setVisibility(View.GONE);
                    } else {
                        clSelectCardTitle.setSelected(true);
                        clCardListGroup.setVisibility(View.VISIBLE);
                    }
                });

        RxView.clicks(clAddCardGroup)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    Intent intent = new Intent(getContext(), CardRegisterActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, GoSingConstants.ACTION_REQ_CODE_REGISTER_CARD);
                });

        // 카드 항목 추가 뷰 -- 카드 리스트에서 카드 추가
        RxView.clicks(mClCardItemAdd)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    Intent intent = new Intent(getContext(), CardRegisterActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, GoSingConstants.ACTION_REQ_CODE_REGISTER_CARD);
                    clCardListGroup.setVisibility(View.GONE);
                });

//        btnCardCharge.setOnClickListener(view1 ->
//                Navigation.findNavController(view)
//                        .navigate(R.id.action_fragment_charge_complete, null));

        RxView.clicks(btnCardCharge)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onMoveNavigation(R.id.action_fragment_charge_input_password));

        RxView.clicks(llPriceChargeClear)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> etPriceCharge.setText("0"));

        // cardAdapter 에서 사용자가 선택한 카드에 대한 정보를 받은후 뷰 모델로 넘김
        mCardAdapter.setmSelectCard(cardInformation -> {

            mChargeViewModel.setSelectCardInfo(cardInformation);

            Toast.makeText(view.getContext(),
                    getString(R.string.fragment_card_toast_select_card, cardInformation.getCardName()),
                    Toast.LENGTH_SHORT).show();
            clSelectCardTitle.setSelected(false);
            clCardListGroup.setVisibility(View.GONE);

        });

        PriceWatcher priceWatcher = new PriceWatcher(etPriceCharge);

        priceWatcher.setCallback((completePrice, price) -> {
            mChargeViewModel.setPriceCharge(completePrice);

            if (price >= 1000) {
                mChargeCardViewModel.setChargeButtonActive(true);
            } else {
                mChargeCardViewModel.setChargeButtonActive(false);
            }

        });
        etPriceCharge.addTextChangedListener(priceWatcher);

    }

    @Override
    protected void initObserve() {
        super.initObserve();

        // -- 뷰 모델 관련 -- //
        // 선택된 카드에 대한 정보를 받음
        mChargeViewModel.getSelectCardInfo().observe(this, cardInformationDto -> {
            TextView tvSelectCard = view.findViewById(R.id.tv_fragment_card_selected_card);
            tvSelectCard.setText(cardInformationDto.getCardName());
        });

        // 카드 리스트 데이터를 받음
        mChargeCardViewModel.getCardInfoList().observe(this, cardInfoDtoList -> {
            if (cardInfoDtoList != null && cardInfoDtoList.size() > 0) {
                // 카드 리스트가 존재할경우 카드 추가 뷰를 숨김
                mCardAdapter.setCardList(cardInfoDtoList);
                mChargeViewModel.setSelectCardInfo(cardInfoDtoList.get(0));
                initCarView(true);
            } else {
                // 카드가 리스트가 없을경우 카드 추가 뷰를 표시
                initCarView(false);
            }
        });

        // 버튼 활성화 및 비활성화
        mChargeCardViewModel.getChargeButtonActive().observe(this,
                active -> btnCardCharge.setEnabled(active));

        // 로딩 데이터를 Activity 와 연결된 뷰 모델에 넘김
        mChargeCardViewModel.getIsLoading().observe(this, isLoading -> {
            mChargeViewModel.setIsLoading(isLoading);
            if (isLoading) {
                onStartLoading(mPrLoading);
            } else {
                onStopLoading(mPrLoading);
            }
        });

        mChargeCardViewModel.getSession().observe(this, session -> {
            if (!session) {
                onNotSession();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 카드 리스트 재호출
        if (requestCode == GoSingConstants.ACTION_REQ_CODE_REGISTER_CARD &&
                resultCode == GoSingConstants.ACTION_RESULT_CODE_REGISTER_CARD) {
            mChargeCardViewModel.setIsLoading(true);
            mChargeCardViewModel.onCardListInit();
        }

    }

    /**
     * 카드 리스트 어뎁터 초기화
     */
    private void initAdapter() {
        //카드 리스트
        RecyclerView rvCardList = view.findViewById(R.id.rv_fragment_card);
        mCardAdapter = new CardAdapter();
        rvCardList.setAdapter(mCardAdapter);
    }

    /**
     * 카드 뷰 초기화
     * 카드가 존재하면 카드 추가뷰를 숨김
     *
     * @param existCard 카드 존재 유무
     */
    private void initCarView(boolean existCard) {
        if (existCard) {
            clSelectCardTitle.setVisibility(View.VISIBLE);
            clAddCardGroup.setVisibility(View.GONE);
        } else {
            clSelectCardTitle.setVisibility(View.GONE);
            clAddCardGroup.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        ViewUtil.onHideKeyboard(view);
        super.onPause();
    }
}
