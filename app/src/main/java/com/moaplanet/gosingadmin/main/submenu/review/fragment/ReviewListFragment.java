package com.moaplanet.gosingadmin.main.submenu.review.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.main.submenu.review.activity.ReviewManagerActivity;
import com.moaplanet.gosingadmin.main.submenu.review.adapter.ReviewAdapter;
import com.moaplanet.gosingadmin.main.submenu.review.model.ReviewViewModel;

/**
 * 리뷰 리스트를 표시해줄 프레그먼트
 */
public class ReviewListFragment extends BaseFragment {

    private ReviewAdapter reviewAdapter;
    private ReviewViewModel mViewModel;
    // 리뷰 비었을때 표시할 뷰
    private View mEmptyReview;

    private int reviewType = GoSingConstants.BUNDLE_VALUE_REVIEW_LIST_ALL;

    @Override
    public int layoutRes() {
        return R.layout.fragment_review_list;
    }

    @Override
    protected void initFragment() {
        super.initFragment();
        if (getArguments() != null) {
            reviewType = getArguments().getInt(GoSingConstants.BUNDLE_KEY_REVIEW_TYPE);
        }
    }

    @Override
    public void initView(View view) {

        mEmptyReview = view.findViewById(R.id.cl_fragment_empty_review);
        mEmptyReview.setVisibility(View.GONE);

        // 리뷰 리스트 세팅
        RecyclerView recyclerView = view.findViewById(R.id.rv_fragment_review);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        reviewAdapter = new ReviewAdapter(getChildFragmentManager());
        recyclerView.setAdapter(reviewAdapter);

        reviewAdapter.setOnRefreshCallback(type -> {
            if (type == reviewAdapter.REVIEW_REFRESH_ALL) {
                if (getActivity() != null && getActivity() instanceof ReviewManagerActivity) {
                    ((ReviewManagerActivity) getActivity()).onAllRefresh();
                }
            } else {
                onRefresh();
            }
        });

        reviewAdapter.setOnNotSessionCallback(this::onNotSession);
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        mViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
    }

    @Override
    protected void initObserve() {
        super.initObserve();
        mViewModel.getReviewList(reviewType);

        mViewModel.getReviewList().observe(getViewLifecycleOwner(), list ->
                reviewAdapter.submitList(list));

        mViewModel.getStoreInfoModel().observe(getViewLifecycleOwner(), storeInfoModel -> {
            if (getActivity() != null && getActivity() instanceof ReviewManagerActivity) {
                ((ReviewManagerActivity) getActivity()).initStoreInfoModel(storeInfoModel);
            }

            TextView tvTotalCount = view.findViewById(R.id.tv_fragment_review_total_review_count);
            tvTotalCount.setText(getString(R.string.fragment_review_list_total_review_count,
                    storeInfoModel.getReviewTotalCount()));

            TextView tvReplyCount = view.findViewById(R.id.tv_fragment_review_reply_review_count);
            tvReplyCount.setText(getString(R.string.fragment_review_list_reply_review_count,
                    storeInfoModel.getReviewReplyCount()));

        });

        mViewModel.getmEmptyReview().observe(getViewLifecycleOwner(), isEmpty -> {
            if (isEmpty) {
                mEmptyReview.setVisibility(View.VISIBLE);
            } else {
                mEmptyReview.setVisibility(View.GONE);
            }
        });

    }

    /**
     * 리스트 갱신
     */
    public void onRefresh() {
        if (mViewModel != null) {
            mViewModel.onRefresh();
        }
    }
}
