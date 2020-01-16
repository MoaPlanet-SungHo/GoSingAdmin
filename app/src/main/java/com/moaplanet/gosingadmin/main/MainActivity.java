package com.moaplanet.gosingadmin.main;

import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.main.slide_menu.information.InformationActivity;
import com.moaplanet.gosingadmin.main.slide_menu.main.MainFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {

    private long backKeyPressedTime = 0;                //사용자가 뒤로가기를 하였을시 시간
    private Toast toast;
    private DrawerLayout drawerLayout;
    private TextView mTvInformation;

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

        mTvInformation = navHeaderView.findViewById(R.id.tv_header_slide_menu_name);

    }

    @Override
    public void initListener() {
        mTvInformation.setOnClickListener(view -> moveActivity(InformationActivity.class));
    }


    public void openNavigation() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void moveActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        drawerLayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if (getNowFragment() instanceof MainFragment) {
            onCustomBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    private Fragment getNowFragment() {
        Fragment nowFragment = getSupportFragmentManager().findFragmentById(R.id.fm_main_nav_host);
        if (nowFragment != null) {
            return nowFragment.getChildFragmentManager().getFragments().get(0);
        } else {
            return null;
        }
    }

    public void onCustomBackPressed() {
        //두번 터치시 동작 유효시간 정의
        long onBackPresedFiishTime = 2000;
        if (System.currentTimeMillis() > backKeyPressedTime + onBackPresedFiishTime) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, getString(R.string.common_toast_msg_finish), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + onBackPresedFiishTime) {
            toast.cancel();
            moveTaskToBack(true);
            finish();
        }
    }
}
