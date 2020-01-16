package com.moaplanet.gosingadmin.main.submenu.review.activity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.adapter.CommonViewPagerAdapter;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.main.submenu.review.fragment.ReviewListFragment;
import com.moaplanet.gosingadmin.main.submenu.review.model.ResReviewDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class ReviewManagerActivity extends BaseActivity {

    private ReviewListFragment allReviewFragment;
    private ReviewListFragment noReplyReviewFragment;

    @Override
    public int layoutRes() {
        return R.layout.activity_review_manager;
    }

    @Override
    public void initView() {
        TabLayout tabLayout = findViewById(R.id.tab_activity_review_manager);

        // 타이틀 세팅
        List<String> tabList = new ArrayList<>();
        tabList.add(getString(R.string.activity_review_manager_all_review));
        tabList.add(getString(R.string.activity_review_manager_empty_comment_review));

        // 뷰페이져
        ViewPager viewPager = findViewById(R.id.vp_activity_review_manager);
        CommonViewPagerAdapter viewPagerAdapter = new CommonViewPagerAdapter(
                getSupportFragmentManager(),
                CommonViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        );
        viewPagerAdapter.setTitleList(tabList);

        // 전체 리뷰
        allReviewFragment = new ReviewListFragment();
        Bundle allReviewBundle = new Bundle();
        allReviewBundle.putInt(GoSingConstants.BUNDLE_KEY_REVIEW_TYPE,
                GoSingConstants.BUNDLE_VALUE_REVIEW_LIST_ALL);
        allReviewFragment.setArguments(allReviewBundle);

        // 댓글 없는 리뷰
        noReplyReviewFragment = new ReviewListFragment();
        Bundle noReplyReviewBundle = new Bundle();
        noReplyReviewBundle.putInt(GoSingConstants.BUNDLE_KEY_REVIEW_TYPE,
                GoSingConstants.BUNDLE_VALUE_REVIEW_LIST_NOT_REPLY);
        noReplyReviewFragment.setArguments(noReplyReviewBundle);

        // 표시해줄 프레그먼트 리스트
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(allReviewFragment);
        fragmentList.add(noReplyReviewFragment);
        viewPagerAdapter.setFragmentList(fragmentList);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void initListener() {
        CommonTitleBar commonTitleBar = findViewById(R.id.title_activity_review_manager_title_bar);
        RxView.clicks(commonTitleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> finish());
    }

    public void initStoreInfoModel(ResReviewDTO.StoreInfoModel storeInfoModel) {
        RatingBar ratingBar = findViewById(R.id.rb_activity_review_manager_rating);
        ratingBar.setRating(storeInfoModel.getStoreAvg());

        TextView tvAvg = findViewById(R.id.tv_activity_review_manager_rating);
        tvAvg.setText(getString(R.string.activity_review_manager_total_rating, storeInfoModel.getStoreAvg()));
    }

    public void onAllRefresh() {
        allReviewFragment.onRefresh();
        noReplyReviewFragment.onRefresh();
    }
}
