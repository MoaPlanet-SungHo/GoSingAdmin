package com.moaplanet.gosingadmin.main.submenu.review.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.adapter.CommonViewPagerAdapter;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.main.submenu.review.fragment.AllReviewFragment;
import com.moaplanet.gosingadmin.main.submenu.review.fragment.EmptyCommentReviewFragment;

import java.util.ArrayList;
import java.util.List;

public class ReviewManagerActivity extends BaseActivity {

    private CommonTitleBar commonTitleBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public int layoutRes() {
        return R.layout.activity_review_manager;
    }

    @Override
    public void initView() {
        commonTitleBar = findViewById(R.id.title_activity_review_manager_title_bar);
        tabLayout = findViewById(R.id.tab_activity_review_manager);

        List<String> tabList = new ArrayList<>();
        tabList.add(getString(R.string.activity_review_manager_all_review));
        tabList.add(getString(R.string.activity_review_manager_empty_comment_review));

        viewPager = findViewById(R.id.vp_activity_review_manager);
        CommonViewPagerAdapter viewPagerAdapter = new CommonViewPagerAdapter(
                getSupportFragmentManager(),
                CommonViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        );
        viewPagerAdapter.setTitleList(tabList);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new AllReviewFragment());
        fragmentList.add(new EmptyCommentReviewFragment());
        viewPagerAdapter.setFragmentList(fragmentList);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void initListener() {
        commonTitleBar.setBackButtonClickListener(view -> finish());
    }
}