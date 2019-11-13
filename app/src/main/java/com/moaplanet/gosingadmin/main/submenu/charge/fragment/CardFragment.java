package com.moaplanet.gosingadmin.main.submenu.charge.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.main.submenu.charge.activity.CardRegisterActivity;
import com.moaplanet.gosingadmin.main.submenu.charge.adapter.CardAdapter;
import com.moaplanet.gosingadmin.main.submenu.charge.model.ChargeViewModel;

public class CardFragment extends BaseFragment {

    private ChargeViewModel mChargeViewModel;

    //카드 선택 타이틀
    private ConstraintLayout clSelectCardTitle;

    private ConstraintLayout clAddCardGroup;
    private ConstraintLayout clCardListGroup;       //카드 리스트 그룹
    private Button btnCardCharge;

    // 카드 리스트 어뎁터
    private CardAdapter mCardAdapter;

    @Override
    protected void initFragment() {
        super.initFragment();
        mChargeViewModel = ViewModelProviders.of(this).get(ChargeViewModel.class);
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_card;
    }

    @Override
    public void initView(View view) {

        mChargeViewModel.setIsLoading(true);

        clSelectCardTitle = view.findViewById(R.id.cl_fragment_card_select_card_title);

        clAddCardGroup = view.findViewById(R.id.cl_fragment_card_add_card_group);
        clCardListGroup = view.findViewById(R.id.cl_fragment_card_list_group);
        clCardListGroup.setVisibility(View.GONE);

        btnCardCharge = view.findViewById(R.id.btn_fragment_card_charge);

        initAdapter();

    }

    @Override
    public void initListener() {
        clSelectCardTitle.setOnClickListener(view1 -> {
            if (clSelectCardTitle.isSelected()) {
                clSelectCardTitle.setSelected(false);
                clCardListGroup.setVisibility(View.GONE);
            } else {
                clSelectCardTitle.setSelected(true);
                clCardListGroup.setVisibility(View.VISIBLE);
            }
        });

        clAddCardGroup.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), CardRegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        btnCardCharge.setOnClickListener(view1 ->
                Navigation.findNavController(view)
                        .navigate(R.id.action_fragment_charge_complete, null));

        // 로딩 유무
        mChargeViewModel.getIsLoading().observe(this, isLoading -> {

        });

        // 카드 리스트 데이터
        mChargeViewModel.getCardInfoList().observe(this, cardInfoDtoList -> {
            if (cardInfoDtoList != null && cardInfoDtoList.size() > 0) {
                mCardAdapter.setCardList(cardInfoDtoList);
                initCarView(true);
            } else {
                initCarView(false);
            }
        });
    }

    /**
     * 카드 리스트 어뎁터 초기화
     */
    private void initAdapter() {
        //카드 리스트
        RecyclerView rvCardList = view.findViewById(R.id.rv_fragment_card);
        mCardAdapter = new CardAdapter();
        rvCardList.setAdapter(mCardAdapter);
        mChargeViewModel.onCardListInit();
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

}
