package com.moaplanet.gosingadmin.main.slide_menu.customer_center;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.Navigation;

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

        // 1대1 문의하기
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

        View faqView = view.findViewById(R.id.cl_customer_center_many_question_group);
        RxView.clicks(faqView)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    Bundle bundle = new Bundle();
                    bundle.putString(FaqFragment.BUNDLE_KEY_VIEW_TYPE, FaqFragment.VIEW_TYPE_LIST);
                    bundle.putString(FaqFragment.BUNDLE_KEY_FAQ_SEQ, "0");
                    Navigation.findNavController(this.view).navigate(R.id.action_fragment_faq, bundle);
                });

    }
}
