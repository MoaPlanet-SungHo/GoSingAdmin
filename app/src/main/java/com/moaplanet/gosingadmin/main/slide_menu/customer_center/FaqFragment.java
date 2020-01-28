package com.moaplanet.gosingadmin.main.slide_menu.customer_center;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.interfaces.AdapterClick;
import com.orhanobut.logger.Logger;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class FaqFragment extends BaseFragment {

    public static final String BUNDLE_KEY_VIEW_TYPE = "BUNDLE_KEY_VIEW_TYPE";

    public static final String VIEW_TYPE_LIST = "VIEW_TYPE_LIST";
    public static final String VIEW_TYPE_DETAIL = "VIEW_TYPE_DETAIL";

    public static final String BUNDLE_KEY_FAQ_SEQ = "BUNDLE_KEY_FAQ_SEQ";
    public final String BUNDLE_KEY_SUB_TITLE = "BUNDLE_KEY_SUB_TITLE";
    public final String BUNDLE_KEY_LOAD_URL = "BUNDLE_KEY_LOAD_URL";


    private String viewType = VIEW_TYPE_LIST;
    private String requestFaqSeq = "0";
    private String loadUrl;
    private FaqAdatper faqAdatper;
    private boolean isFirstRequest;

    // 뷰모델
    private FaqViewModel viewModel;
    // 서브 타이릍
    private TextView tvSubTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirstRequest = true;
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_faq;
    }

    @Override
    public void initView(View view) {
        tvSubTitle = view.findViewById(R.id.tv_fragment_faq_sub_title);

        if (getArguments() != null) {
            viewType = getArguments().getString(BUNDLE_KEY_VIEW_TYPE, VIEW_TYPE_LIST);
            requestFaqSeq = getArguments().getString(BUNDLE_KEY_FAQ_SEQ, "0");
            loadUrl = getArguments().getString(BUNDLE_KEY_LOAD_URL, "");

            if (getArguments().getString(BUNDLE_KEY_SUB_TITLE) != null) {
                String subTitle = getArguments().getString(BUNDLE_KEY_SUB_TITLE);
                tvSubTitle.setText(subTitle);
            }

            Logger.d("viewType is >>> " + viewType);

            View clFragmentFaqListGroup =
                    view.findViewById(R.id.cl_fragment_faq_list_group);
            View clFragmentFaqWebviewGroup =
                    view.findViewById(R.id.cl_fragment_faq_webview_group);
            if (viewType.equals(VIEW_TYPE_LIST)) {
                clFragmentFaqListGroup.setVisibility(View.VISIBLE);
                clFragmentFaqWebviewGroup.setVisibility(View.GONE);
            } else {
                clFragmentFaqListGroup.setVisibility(View.GONE);
                clFragmentFaqWebviewGroup.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(FaqViewModel.class);
        }
    }

    @Override
    public void initListener() {

        // 상단 뒤로가기 버튼 클릭
        CommonTitleBar titleBar = view.findViewById(R.id.title_fragment_faq);
        RxView.clicks(titleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onBackNavigation());
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        if (viewType.equals(VIEW_TYPE_LIST)) {
            viewModel.getResFaqDTO().observe(getViewLifecycleOwner(), resFaqDTO -> {
                if (resFaqDTO != null) {
                    if (resFaqDTO.getTopTitle() != null) {
                        tvSubTitle.setText(resFaqDTO.getTopTitle());
                    } else {
                        tvSubTitle.setText("자주묻는 질문");
                    }

                    loadUrl = resFaqDTO.getWebUrl();

                    faqAdatper.setHostUrl(resFaqDTO.getWebUrl());
                    faqAdatper.submitList(resFaqDTO.getFaqList());
                }
                isFirstRequest = false;
            });
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (viewType.equals(VIEW_TYPE_LIST)) {
            initAdapter();
            if (isFirstRequest) {
                viewModel.getFaqList(requestFaqSeq);
            }

        } else {
            //loadUrl
            WebView webView = view.findViewById(R.id.webiew_fragment_faq);
            webView.loadUrl(loadUrl);
        }
    }

    private void initAdapter() {

        faqAdatper = new FaqAdatper(new AdapterClick<FaqModel>() {
            @Override
            public void click(FaqModel model) {
                if (model.isDetail()) {
                    //웹뷰이동
                    Bundle bundle = new Bundle();
                    bundle.putString(BUNDLE_KEY_VIEW_TYPE, VIEW_TYPE_DETAIL);
                    bundle.putString(BUNDLE_KEY_SUB_TITLE, model.getTitle());
                    bundle.putString(BUNDLE_KEY_LOAD_URL, loadUrl);
                    Navigation.findNavController(Objects.requireNonNull(getView()))
                            .navigate(R.id.action_fragment_faq, bundle);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(BUNDLE_KEY_VIEW_TYPE, VIEW_TYPE_LIST);
                    bundle.putString(BUNDLE_KEY_FAQ_SEQ, model.getFaqSeq());

                    Navigation.findNavController(Objects.requireNonNull(getView()))
                            .navigate(R.id.action_fragment_faq, bundle);
                }
            }

            @Override
            public void click(FaqModel model, int type) {

            }

        }, new DiffUtil.ItemCallback<FaqModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull FaqModel oldItem, @NonNull FaqModel newItem) {
                return oldItem.getFaqSeq().equals(newItem);
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(@NonNull FaqModel oldItem, @NonNull FaqModel newItem) {
                return oldItem.equals(newItem);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.rv_item_faq);
        recyclerView.setAdapter(faqAdatper);
    }
}
