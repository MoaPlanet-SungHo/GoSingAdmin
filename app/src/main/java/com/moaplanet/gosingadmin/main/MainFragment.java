package com.moaplanet.gosingadmin.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.main.submenu.ad.activity.GoSingAdActivity;
import com.moaplanet.gosingadmin.main.submenu.food.activity.FoodOrderActivity;
import com.moaplanet.gosingadmin.main.submenu.non_member.NonMemberSaveActivity;
import com.moaplanet.gosingadmin.main.submenu.notification.NotificationActivity;
import com.moaplanet.gosingadmin.main.submenu.point.activity.PointHistoryActivity;
import com.moaplanet.gosingadmin.main.submenu.review.ReviewActivity;
import com.moaplanet.gosingadmin.main.submenu.store.StoreActivity;

public class MainFragment extends Fragment {

    private View view;
    private View btnPointHistory, btnNotification, btnAd, btnStore,
            btnReview, btnFoodOrder, btnNonmemberSave;
    private View slideMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(inflater, container);
        initListener();
        return view;
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {
        LinearLayout noticeView = view.findViewById(R.id.ll_main_notice_group);
        for (int i = 0; i < 3; i++) {
            View noticeContent = inflater.inflate(R.layout.item_main_notice_top_three, container, false);
            noticeView.addView(noticeContent);
        }

        btnPointHistory = view.findViewById(R.id.cl_main_point_history);
        btnNotification = view.findViewById(R.id.cl_main_notification);
        btnAd = view.findViewById(R.id.cl_main_ad_management);
        slideMenu = view.findViewById(R.id.cl_main_slide_menu);
        btnStore = view.findViewById(R.id.cl_main_store_management);
        btnReview = view.findViewById(R.id.cl_main_review_management);
        btnFoodOrder = view.findViewById(R.id.cl_main_food_order);
        btnNonmemberSave = view.findViewById(R.id.cl_main_non_member_save);
    }

    private void initListener() {
        btnPointHistory.setOnClickListener(view -> moveActivity(PointHistoryActivity.class));
        btnNotification.setOnClickListener(view -> moveActivity(NotificationActivity.class));
        btnAd.setOnClickListener(view -> moveActivity(GoSingAdActivity.class));
        btnStore.setOnClickListener(view -> moveActivity(StoreActivity.class));
        btnReview.setOnClickListener(view -> moveActivity(ReviewActivity.class));
        btnFoodOrder.setOnClickListener(view -> moveActivity(FoodOrderActivity.class));
        btnNonmemberSave.setOnClickListener(view -> moveActivity(NonMemberSaveActivity.class));
        slideMenu.setOnClickListener(view -> {
            if (getActivity() != null && getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).openNavigation();
            }
        });
    }

    private void moveActivity(Class moveClass) {
        Intent intent = new Intent(view.getContext(), moveClass);
        startActivity(intent);
    }
}
