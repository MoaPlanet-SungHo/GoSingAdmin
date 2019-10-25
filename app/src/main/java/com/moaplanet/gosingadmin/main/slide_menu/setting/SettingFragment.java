package com.moaplanet.gosingadmin.main.slide_menu.setting;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

import androidx.navigation.Navigation;

public class SettingFragment extends BaseFragment {
    private CommonTitleBar titleVIew;
    private ImageView ivNotice;
    @Override
    public int layoutRes() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initView(View view) {
        titleVIew = view.findViewById(R.id.common_notice_title);
        titleVIew.setTitle("설정");
        ivNotice = view.findViewById(R.id.terms_and_policies_button);
    }

    @Override
    public void initListener() {
        titleVIew.setBackButtonClickListener(view ->
                Navigation.findNavController(this.view).popBackStack());
        ivNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SettingDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
