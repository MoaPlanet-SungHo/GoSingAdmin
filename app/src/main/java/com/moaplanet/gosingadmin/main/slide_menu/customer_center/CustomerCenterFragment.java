package com.moaplanet.gosingadmin.main.slide_menu.customer_center;

import android.view.View;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.kakao.plusfriend.PlusFriendService;
import com.kakao.util.exception.KakaoException;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class CustomerCenterFragment extends BaseFragment {
    @Override
    public int layoutRes() {
        return R.layout.fragment_customer_center;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initListener() {
        // 상단 뒤로가기 버튼 클릭
        CommonTitleBar titleBar = view.findViewById(R.id.common_fragment_customer_center_title);
        RxView.clicks(titleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onBackNavigation());

        View oneToOneInquire = view.findViewById(R.id.cl_customer_center_one_to_one_question_group);
        RxView.clicks(oneToOneInquire)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    try {
                        PlusFriendService.getInstance().chat(view.getContext(), "_NjlZxb");
                    } catch (KakaoException e) {
                        Toast.makeText(view.getContext(), "다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
