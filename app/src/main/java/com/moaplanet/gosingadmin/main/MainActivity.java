package com.moaplanet.gosingadmin.main;

import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;

    @Override
    public int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        drawerLayout = findViewById(R.id.drawer_main);
        NavigationView navigationView = findViewById(R.id.nav_main_slide_view);
        View navHeaderView = navigationView.getHeaderView(0);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.fragment_main,
                R.id.fragment_event,
                R.id.fragment_notice,
                R.id.fragment_customer_center,
                R.id.fragment_setting)
                .setDrawerLayout(drawerLayout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.fm_main_nav_host);
        NavigationUI.setupWithNavController(navigationView, navController);

        List<String> slideMenuTitleList =
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.slide_menu)));
        Menu menuView = navigationView.getMenu();
        for (int i = 0; i < slideMenuTitleList.size(); i++) {
            View menuItemView = menuView.getItem(i).getActionView();
            TextView sideMenuTitle = menuItemView.findViewById(R.id.tv_main_side_navigation_title);
            sideMenuTitle.setText(slideMenuTitleList.get(i));
        }

    }

    @Override
    public void initListener() {

    }


    public void openNavigation() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

}
