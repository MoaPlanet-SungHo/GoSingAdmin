package com.moaplanet.gosingadmin.main.submenu.charge.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.adapter.CommonViewPagerAdapter;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

public class ChargeFragment extends BaseFragment {

    // 타이틀 바
    private CommonTitleBar titleBar;

    @Override
    public int layoutRes() {
        return R.layout.fragment_charge;
    }

    @Override
    public void initView(View view) {
        titleBar = view.findViewById(R.id.common_fragment_charge_title_bar);
        initTab();
    }

    @Override
    public void initListener() {
        titleBar.setBackButtonClickListener(view -> {
            if (getActivity() != null) {
                getActivity().finish();
            }
        });
    }

    private void initTab() {
        TabLayout tabCharge = view.findViewById(R.id.tab_charge);
        ViewPager vpCharge = view.findViewById(R.id.vp_charge);
        CommonViewPagerAdapter viewPagerAdapter = new CommonViewPagerAdapter(
                getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        );
        List<String> titleList = new ArrayList<>();
        titleList.add(getString(R.string.fragment_charge_sub_title_card));
        titleList.add(getString(R.string.fragment_charge_sub_title_deposit_without_bank_book));

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new CardFragment());
        fragmentList.add(new DepositWithoutBankbookFragment());

        viewPagerAdapter.setTitleList(titleList);
        viewPagerAdapter.setFragmentList(fragmentList);
        viewPagerAdapter.notifyDataSetChanged();

        vpCharge.setAdapter(viewPagerAdapter);
        tabCharge.setupWithViewPager(vpCharge);

    }
}
