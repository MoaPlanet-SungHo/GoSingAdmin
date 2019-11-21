package com.moaplanet.gosingadmin.main.submenu.point.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.Nullable;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.manager.PointManager;
import com.moaplanet.gosingadmin.common.model.dto.res.ResPointDto;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.main.submenu.point.adapter.PointHistoryPagerAdapter;
import com.moaplanet.gosingadmin.utils.StringUtil;
import com.moaplanet.gosingadmin.utils.TimeUtil;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

import static com.moaplanet.gosingadmin.utils.TimeUtil.DATE_FORMAT_SIMPLE;


public class PointHistoryActivity extends BaseActivity {

//    private Button btnOneDay;
//    private Button btnSevenDay;
//    private Button btnOneMonth;
//    private Button btnThreeMonth;
//    private Button btnSixMonth;
//    private Button btnOneYear;
//    private EditText etStartDate;
//    private EditText etEndDate;

//    private PointHistoryFragment pointHistoryFragment;
//    private PointHistoryListAdapter pointHistoryListAdapter;

//    private static final String DATE_FORMAT_SIMPLE = "yyyy-MM-dd";

//    private static Calendar getTodayCalendar() {
//        return new GregorianCalendar();
//    }

    @Override
    public int layoutRes() {
        return R.layout.activity_point_history;
    }

    @Override
    public void initView() {
        TabLayout tabPointHistory = findViewById(R.id.tl_point_history_tab);
        ViewPager vpPointHistory = findViewById(R.id.vp_point_history);
//        btnOneDay = findViewById(R.id.btn_point_history_one_day);
//        btnSevenDay = findViewById(R.id.btn_point_history_seven_day);
//        btnOneMonth = findViewById(R.id.btn_point_history_thirty_day);
//        btnThreeMonth = findViewById(R.id.btn_point_history_three_months);
//        btnSixMonth = findViewById(R.id.btn_point_history_six_months);
//        btnOneYear = findViewById(R.id.btn_point_history_one_year);
//        etStartDate = findViewById(R.id.et_point_history_start_date);
//        etEndDate = findViewById(R.id.et_point_history_end_date);
//        pointHistoryFragment = ((PointHistoryFragment) getSupportFragmentManager().findFragmentById(R.id.rv_point_history));
//        pointHistoryListAdapter = new PointHistoryListAdapter();

        // etStartDate, etEndDate 기본 값 설정
//        setCalendarTextView(getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar(), -7)
//                , getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar()));


        PointHistoryPagerAdapter pointHistoryPagerAdapter = new PointHistoryPagerAdapter(
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpPointHistory.setAdapter(pointHistoryPagerAdapter);
        vpPointHistory.setOffscreenPageLimit(2);
        tabPointHistory.setupWithViewPager(vpPointHistory);

        initDate(0);
    }

    @Override
    public void initListener() {
        onPointLoad();
//        btnOneDay.setOnClickListener(this);
//        btnSevenDay.setOnClickListener(this);
//        btnOneMonth.setOnClickListener(this);
//        btnThreeMonth.setOnClickListener(this);
//        btnSixMonth.setOnClickListener(this);
//        btnOneYear.setOnClickListener(this);

        // 상단 툴바 뒤로 가기
        CommonTitleBar commonTitleBar = findViewById(R.id.common_point_history_title_bar);
        RxView.clicks(commonTitleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> finish());

        // 1일 포인트 내역 조회 날짜 세팅
        Button btnOneDay = findViewById(R.id.btn_point_history_one_day);
        RxView.clicks(btnOneDay)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> initDate(-1));

        // 7일 포인트 내역 조회 날짜 세팅
        Button btnSevenDay = findViewById(R.id.btn_point_history_seven_day);
        RxView.clicks(btnSevenDay)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> initDate(-7));

        // 1달 포인트 내역 조회 날짜 세팅
        Button btnAMonth = findViewById(R.id.btn_point_history_thirty_day);
        RxView.clicks(btnAMonth)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> initDate(-30));

        // 3개월 포인트 내역 조회 날짜 세팅
        Button btnThreeMonth = findViewById(R.id.btn_point_history_three_months);
        RxView.clicks(btnThreeMonth)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> initDate(-90));

        // 6월 포인트 내역 조회 날짜 세팅
        Button btnSixMonthDay = findViewById(R.id.btn_point_history_six_months);
        RxView.clicks(btnSixMonthDay)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> initDate(-180));

        // 1년 포인트 내역 조회 날짜 세팅
        Button btnAYear = findViewById(R.id.btn_point_history_one_year);
        RxView.clicks(btnAYear)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> initDate(-365));

    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        savedInstanceState.putString("defaultStartDate",getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar(), -7));
//        savedInstanceState.putString("defaultEndDate",getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar()));
    }

    //    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_point_history_one_day: // 1일
//                setCalendarTextView(getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar(), -1)
//                        , getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar()));
//                sendDate();
//                pointHistoryFragment.onPointHistoryList();
//
//                break;
//
//            case R.id.btn_point_history_seven_day: // 1주일
//                setCalendarTextView(getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar(), -7)
//                        , getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar()));
//                sendDate();
//                pointHistoryFragment.onPointHistoryList();
//                break;
//
//            case R.id.btn_point_history_thirty_day: // 1개월
//                setCalendarTextView(getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar(), -30)
//                        , getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar()));
//                sendDate();
//                pointHistoryFragment.onPointHistoryList();
//                break;
//
//            case R.id.btn_point_history_three_months: // 3개월
//                setCalendarTextView(getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar(), -90)
//                        , getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar()));
//                sendDate();
//                pointHistoryFragment.onPointHistoryList();
//                break;
//
//            case R.id.btn_point_history_six_months: // 6개월
//                setCalendarTextView(getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar(), -180)
//                        , getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar()));
//                sendDate();
//                pointHistoryFragment.onPointHistoryList();
//                break;
//
//            case R.id.btn_point_history_one_year: // 1년
//                setCalendarTextView(getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar(), -365)
//                        , getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar()));
//                sendDate();
//                pointHistoryFragment.onPointHistoryList();
//                break;
//        }
    }

    // PointHistoryFragment로 값 전달
//    private void sendDate() {
//        Bundle bundle = new Bundle();
//        pointHistoryFragment = new PointHistoryFragment();
//        bundle.putString("startDate", DateToString(etStartDate));
//        bundle.putString("endDate", DateToString(etEndDate));
//        pointHistoryFragment.setArguments(bundle);
//    }

//    public String DateToString(EditText editText) {
//        return editText.getText().toString().trim().replaceAll("-", "");
//    }

    // 오늘 날짜 가져오기
//    private static String getStringFormatDate(String toFormat, Calendar calendar) {
//        SimpleDateFormat transToFormat = new SimpleDateFormat(toFormat);
//        return transToFormat.format(calendar.getTime());
//    }

    // 버튼 누른 날짜 가져오기
//    private static String getStringFormatDate(String toFormat, Calendar calendar, int day) {
//        SimpleDateFormat transToFormat = new SimpleDateFormat(toFormat);
//        calendar.add(Calendar.DATE, day);
//        return transToFormat.format(calendar.getTime());
//    }

    // 빈 값 체크
//    private boolean checkNotNull(String value) {
//        return value != null;
//    }


    // 시작일 종료일 설정
//    private void setCalendarTextView(String startDate, String endDate) {
//        if (checkNotNull(startDate) && checkNotNull(endDate)) {
//            if (etStartDate != null) {
//                etStartDate.setText(startDate);
//            }
//            if (etEndDate != null) {
//                etEndDate.setText(endDate);
//            }
//        }
//    }

    /**
     * 포인트 초기화
     */
    private void initPoint(ResPointDto pointModel) {
        // 포인트
        TextView tvPoint = findViewById(R.id.tv_point_history_point);
        tvPoint.setText(getString(
                R.string.common_price_won,
                StringUtil.convertCommaPrice(pointModel.getPoint())));

        // 활성 포정 포인트
        TextView tvPointExpectedActive = findViewById(R.id.tv_point_history_schedule_point);
        tvPointExpectedActive.setText(getString(
                R.string.common_price_won,
                StringUtil.convertCommaPrice(pointModel.getPointExpectedActive())));

        // 소멸 예정 포인트
        TextView tvPointRemove = findViewById(R.id.tv_point_history_remove_schedule_point);
        tvPointRemove.setText(getString(
                R.string.common_price_won,
                StringUtil.convertCommaPrice(pointModel.getRemovePoint())));
    }

    /**
     * 날짜 초기화
     */
    private void initDate(int beforeDate) {
        EditText etStartDate = findViewById(R.id.et_point_history_start_date);
        String startDate =
                TimeUtil.getStringFormatDate(
                        DATE_FORMAT_SIMPLE,
                        TimeUtil.getTodayCalendar(),
                        beforeDate);
        etStartDate.setText(startDate);

        EditText etEndDate = findViewById(R.id.et_point_history_end_date);
        String endDate =
                TimeUtil.getStringFormatDate(
                        DATE_FORMAT_SIMPLE,
                        TimeUtil.getTodayCalendar());
        etEndDate.setText(endDate);
    }

    // --- 서버 통신 부분 --- //

    /**
     * 포인트 호출
     */
    private void onPointLoad() {
        PointManager pointManager = new PointManager();
        pointManager.setGoSingPointCallback(new PointManager.GoSingPointCallback() {
            @Override
            public void onSuccess(ResPointDto resPointDto) {
                initPoint(resPointDto);
            }

            @Override
            public void onFail() {
                onNetworkConnectFail();
            }

            @Override
            public void onNotSession() {
                PointHistoryActivity.this.onNotSession();
            }
        });
        pointManager.getGoSingPoint();
    }

}

