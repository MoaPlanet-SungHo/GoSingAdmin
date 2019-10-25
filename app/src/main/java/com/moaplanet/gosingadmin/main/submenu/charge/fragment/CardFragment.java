package com.moaplanet.gosingadmin.main.submenu.charge.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.main.submenu.charge.activity.CardRegisterActivity;
import com.moaplanet.gosingadmin.main.submenu.charge.adapter.CardAdapter;

public class CardFragment extends BaseFragment {

    private ConstraintLayout clSelectCardTitle;      //카드 선택 타이틀

    private ConstraintLayout clAddCardGroup;
    private ConstraintLayout clCardListGroup;       //카드 리스트 그룹
    private RecyclerView rvCardList;                //카드 리스트
    private Button btnCardCharge;

    private CardAdapter cardAdapter;

    @Override
    public int layoutRes() {
        return R.layout.fragment_card;
    }

    @Override
    public void initView(View view) {

        clSelectCardTitle = view.findViewById(R.id.cl_fragment_card_select_card_title);
        clSelectCardTitle.setVisibility(View.GONE);

        clAddCardGroup = view.findViewById(R.id.cl_fragment_card_add_card_group);
        clCardListGroup = view.findViewById(R.id.cl_fragment_card_list_group);
        clCardListGroup.setVisibility(View.GONE);
        rvCardList = view.findViewById(R.id.rv_fragment_card);

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
    }

    private void initAdapter() {
        cardAdapter = new CardAdapter();
        rvCardList.setAdapter(cardAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        clSelectCardTitle.setVisibility(View.VISIBLE);
    }
}
