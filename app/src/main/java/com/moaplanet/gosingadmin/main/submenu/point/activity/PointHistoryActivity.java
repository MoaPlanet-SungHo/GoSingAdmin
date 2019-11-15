package com.moaplanet.gosingadmin.main.submenu.point.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.annotation.Nullable;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.main.submenu.point.adapter.PointHistoryListAdapter;
import com.moaplanet.gosingadmin.main.submenu.point.adapter.PointHistoryPagerAdapter;
import com.moaplanet.gosingadmin.main.submenu.point.fragment.PointHistoryFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class PointHistoryActivity extends BaseActivity implements View.OnClickListener {

    private Button btnOneDay;
    private Button btnSevenDay;
    private Button btnOneMonth;
    private Button btnThreeMonth;
    private Button btnSixMonth;
    private Button btnOneYear;
    private EditText etStartDate;
    private EditText etEndDate;

    private PointHistoryFragment pointHistoryFragment;
    private PointHistoryPagerAdapter pointHistoryPagerAdapter;
    private PointHistoryListAdapter pointHistoryListAdapter;

    private static final String DATE_FORMAT_SIMPLE = "yyyy-MM-dd";

    private static Calendar getTodayCalendar() {
        return new GregorianCalendar();
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_point_history;
    }

    @Override
    public void initView() {
        TabLayout tabPointHistory = findViewById(R.id.tl_point_history_tab);
        ViewPager vpPointHistory = findViewById(R.id.vp_point_history);
        btnOneDay = findViewById(R.id.btn_point_history_one_day);
        btnSevenDay = findViewById(R.id.btn_point_history_seven_day);
        btnOneMonth = findViewById(R.id.btn_point_history_thirty_day);
        btnThreeMonth = findViewById(R.id.btn_point_history_three_months);
        btnSixMonth = findViewById(R.id.btn_point_history_six_months);
        btnOneYear = findViewById(R.id.btn_point_history_one_year);
        etStartDate = findViewById(R.id.et_point_history_start_date);
        etEndDate = findViewById(R.id.et_point_history_end_date);
        pointHistoryFragment = ((PointHistoryFragment) getSupportFragmentManager().findFragmentById(R.id.rv_point_history));
        pointHistoryListAdapter = new PointHistoryListAdapter();

        // etStartDate, etEndDate 기본 값 설정
        setCalendarTextView(getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar(), -7)
                , getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar()));



        PointHistoryPagerAdapter pointHistoryPagerAdapter = new PointHistoryPagerAdapter(
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpPointHistory.setAdapter(pointHistoryPagerAdapter);
        tabPointHistory.setupWithViewPager(vpPointHistory);
    }

    @Override
    public void initListener() {
        btnOneDay.setOnClickListener(this);
        btnSevenDay.setOnClickListener(this);
        btnOneMonth.setOnClickListener(this);
        btnThreeMonth.setOnClickListener(this);
        btnSixMonth.setOnClickListener(this);
        btnOneYear.setOnClickListener(this);


    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState.putString("defaultStartDate",getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar(), -7));
        savedInstanceState.putString("defaultEndDate",getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_point_history_one_day: // 1일
                setCalendarTextView(getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar(), -1)
                        , getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar()));
                sendDate();
                pointHistoryFragment.onPointHistoryList();

                break;

            case R.id.btn_point_history_seven_day: // 1주일
                setCalendarTextView(getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar(), -7)
                        , getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar()));
                sendDate();
                pointHistoryFragment.onPointHistoryList();
                break;

            case R.id.btn_point_history_thirty_day: // 1개월
                setCalendarTextView(getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar(), -30)
                        , getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar()));
                sendDate();
                pointHistoryFragment.onPointHistoryList();
                break;

            case R.id.btn_point_history_three_months: // 3개월
                setCalendarTextView(getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar(), -90)
                        , getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar()));
                sendDate();
                pointHistoryFragment.onPointHistoryList();
                break;

            case R.id.btn_point_history_six_months: // 6개월
                setCalendarTextView(getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar(), -180)
                        , getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar()));
                sendDate();
                pointHistoryFragment.onPointHistoryList();
                break;

            case R.id.btn_point_history_one_year: // 1년
                setCalendarTextView(getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar(), -365)
                        , getStringFormatDate(DATE_FORMAT_SIMPLE, getTodayCalendar()));
                sendDate();
                pointHistoryFragment.onPointHistoryList();
                break;
        }
    }

    // PointHistoryFragment로 값 전달
    private void sendDate() {
        Bundle bundle = new Bundle();
        pointHistoryFragment = new PointHistoryFragment();
        bundle.putString("startDate", DateToString(etStartDate));
        bundle.putString("endDate", DateToString(etEndDate));
        pointHistoryFragment.setArguments(bundle);
    }

    public String DateToString(EditText editText) {
        return editText.getText().toString().trim().replaceAll("-", "");
    }

    // 오늘 날짜 가져오기
    private static String getStringFormatDate(String toFormat, Calendar calendar) {
        SimpleDateFormat transToFormat = new SimpleDateFormat(toFormat);
        return transToFormat.format(calendar.getTime());
    }

    // 버튼 누른 날짜 가져오기
    private static String getStringFormatDate(String toFormat, Calendar calendar, int day) {
        SimpleDateFormat transToFormat = new SimpleDateFormat(toFormat);
        calendar.add(Calendar.DATE, day);
        return transToFormat.format(calendar.getTime());
    }

    // 빈 값 체크
    private boolean checkNotNull(String value) {
        return value != null;
    }


    // 시작일 종료일 설정
    private void setCalendarTextView(String startDate, String endDate) {
        if (checkNotNull(startDate) && checkNotNull(endDate)) {
            if (etStartDate != null) {
                etStartDate.setText(startDate);
            }
            if (etEndDate != null) {
                etEndDate.setText(endDate);
            }
        }
    }

}

