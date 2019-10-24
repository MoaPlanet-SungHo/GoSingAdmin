package com.moaplanet.gosingadmin.main.submenu.point.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.moaplanet.gosingadmin.main.submenu.point.fragment.PointHistoryFragment;

import java.util.ArrayList;
import java.util.List;

public class PointHistoryPagerAdapter extends FragmentPagerAdapter {

    private List<String> titleList;

    public PointHistoryPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        titleList = new ArrayList<>();
        titleList.add("전체");
        titleList.add("입금");
        titleList.add("출금");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        PointHistoryFragment pointHistoryFragment = new PointHistoryFragment();
        switch (position) {
            case 0:
                bundle.putString("type", "all");
                break;
            case 1:
                bundle.putString("type", "deposit");
                break;
            case 2:
                bundle.putString("type", "withdrawal");
                break;
        }

        pointHistoryFragment.setArguments(bundle);
        return pointHistoryFragment;
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
