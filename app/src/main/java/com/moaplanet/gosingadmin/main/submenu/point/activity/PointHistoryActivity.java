package com.moaplanet.gosingadmin.main.submenu.point.activity;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.main.submenu.point.adapter.PointHistoryPagerAdapter;

public class PointHistoryActivity extends BaseActivity {

    @Override
    public int layoutRes() {
        return R.layout.activity_point_history;
    }

    @Override
    public void initView() {
        TabLayout tabPointHistory = findViewById(R.id.tl_point_history_tab);
        ViewPager vpPointHistory = findViewById(R.id.vp_point_history);
        PointHistoryPagerAdapter pointHistoryPagerAdapter = new PointHistoryPagerAdapter(
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpPointHistory.setAdapter(pointHistoryPagerAdapter);
        tabPointHistory.setupWithViewPager(vpPointHistory);
    }

    @Override
    public void initListener() {

    }
}
