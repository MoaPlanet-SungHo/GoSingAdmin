package com.moaplanet.gosingadmin.main.submenu.point.activity;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.manager.PointManager;
import com.moaplanet.gosingadmin.common.model.dto.res.ResPointDto;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.main.submenu.point.adapter.PointHistoryPagerAdapter;
import com.moaplanet.gosingadmin.main.submenu.point.model.viewmodel.PointHistoryViewModel;
import com.moaplanet.gosingadmin.utils.StringUtil;
import com.moaplanet.gosingadmin.utils.TimeUtil;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

import static com.moaplanet.gosingadmin.utils.TimeUtil.DATE_FORMAT_SIMPLE;


public class PointHistoryActivity extends BaseActivity {

    private DatePickerDialog startDatePickerDialog;     //달력 시작일
    private DatePickerDialog endDatePickerDialog;       //달력 종료일

    // 포인트 히스토리 뷰모델
    private PointHistoryViewModel mViewModel;
    // 1일 7일등 기간 조회 버튼을 클릭했을때 뷰
    private Button mBtnSelectDaySearch;

    private TextView etStartDate;
    private TextView etEndDate;

    @Override
    public int layoutRes() {
        return R.layout.activity_point_history;
    }

    @Override
    public void initActivity() {
        super.initActivity();
        mViewModel = ViewModelProviders.of(this).get(PointHistoryViewModel.class);
    }

    @Override
    public void initView() {
        TabLayout tabPointHistory = findViewById(R.id.tl_point_history_tab);
        ViewPager vpPointHistory = findViewById(R.id.vp_point_history);

        PointHistoryPagerAdapter pointHistoryPagerAdapter = new PointHistoryPagerAdapter(
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpPointHistory.setAdapter(pointHistoryPagerAdapter);
        vpPointHistory.setOffscreenPageLimit(2);
        tabPointHistory.setupWithViewPager(vpPointHistory);

        etStartDate = findViewById(R.id.et_point_history_start_date);
        etEndDate = findViewById(R.id.et_point_history_end_date);

        initDate(0);

        startDatePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            if (etStartDate != null) {
                etStartDate.setText(TimeUtil.getStringFormatDate(TimeUtil.DATE_FORMAT_SIMPLE, year, month, dayOfMonth));
                mViewModel.setStartDate(etStartDate.getText().toString());
            }
        }, TimeUtil.getCalendar(DATE_FORMAT_SIMPLE, etStartDate.getText().toString()).get(Calendar.YEAR)
                , TimeUtil.getCalendar(DATE_FORMAT_SIMPLE, etStartDate.getText().toString()).get(Calendar.MONTH)
                , TimeUtil.getCalendar(DATE_FORMAT_SIMPLE, etStartDate.getText().toString()).get(Calendar.DATE)
        );

        endDatePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            if (etEndDate != null) {
                etEndDate.setText(TimeUtil.getStringFormatDate(TimeUtil.DATE_FORMAT_SIMPLE, year, month, dayOfMonth));
                mViewModel.setEndDate(etEndDate.getText().toString());
            }
        }, TimeUtil.getCalendar(DATE_FORMAT_SIMPLE, etEndDate.getText().toString()).get(Calendar.YEAR)
                , TimeUtil.getCalendar(DATE_FORMAT_SIMPLE, etEndDate.getText().toString()).get(Calendar.MONTH)
                , TimeUtil.getCalendar(DATE_FORMAT_SIMPLE, etEndDate.getText().toString()).get(Calendar.DATE)
        );

    }

    @Override
    public void initListener() {
        onPointLoad();

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
                .subscribe(click -> {
                    onChangeSelectButton(btnOneDay);
                    initDate(-1);
                });

        // 7일 포인트 내역 조회 날짜 세팅
        Button btnSevenDay = findViewById(R.id.btn_point_history_seven_day);
        RxView.clicks(btnSevenDay)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    onChangeSelectButton(btnSevenDay);
                    initDate(-7);
                });

        // 1달 포인트 내역 조회 날짜 세팅
        Button btnAMonth = findViewById(R.id.btn_point_history_thirty_day);
        RxView.clicks(btnAMonth)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    onChangeSelectButton(btnAMonth);
                    initDate(-30);
                });

        // 3개월 포인트 내역 조회 날짜 세팅
        Button btnThreeMonth = findViewById(R.id.btn_point_history_three_months);
        RxView.clicks(btnThreeMonth)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    onChangeSelectButton(btnThreeMonth);
                    initDate(-90);
                });

        // 6월 포인트 내역 조회 날짜 세팅
        Button btnSixMonthDay = findViewById(R.id.btn_point_history_six_months);
        RxView.clicks(btnSixMonthDay)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    onChangeSelectButton(btnSixMonthDay);
                    initDate(-180);
                });

        // 1년 포인트 내역 조회 날짜 세팅
        Button btnAYear = findViewById(R.id.btn_point_history_one_year);
        RxView.clicks(btnAYear)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    onChangeSelectButton(btnAYear);
                    initDate(-365);
                });

        // 활성 예정 포인트
        TextView tvActiveSchedulePoint = findViewById(R.id.tv_point_history_active_schedule_point_title);
        tvActiveSchedulePoint.setOnClickListener(view -> {
            Toast.makeText(this, R.string.common_toast_ready, Toast.LENGTH_SHORT).show();
        });

//        RxView.clicks(etStartDate)
//                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(click -> {
//                    showStartDatePickerDialog();
//                });

//        RxView.clicks(etEndDate)
//                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(click -> {
//                    showEndDatePickerDialog();
//                });


    }

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
     * 선택된 버튼 변경
     */
    private void onChangeSelectButton(Button button) {

        if (mBtnSelectDaySearch != null) {
            mBtnSelectDaySearch.setBackgroundResource(R.drawable.border_rect_4_4_4_4_1dp_e9e9e9_ffffff);
            mBtnSelectDaySearch.setTextColor(
                    ContextCompat.getColor(this, R.color.color_aaaaaa)
            );
        }
        mBtnSelectDaySearch = button;
        mBtnSelectDaySearch.setBackgroundResource(R.drawable.border_rect_4_4_4_4_1dp_4300ff_ffffff);
        mBtnSelectDaySearch.setTextColor(
                ContextCompat.getColor(this, R.color.color_4300ff)
        );

    }

    /**
     * 날짜 초기화
     */
    private void initDate(int beforeDate) {
        // 시작날짜
//        EditText etStartDate = findViewById(R.id.et_point_history_start_date);
        String startDate =
                TimeUtil.getStringFormatDate(
                        DATE_FORMAT_SIMPLE,
                        TimeUtil.getTodayCalendar(),
                        beforeDate);
        etStartDate.setText(startDate);

        // 종료 날짜
//        EditText etEndDate = findViewById(R.id.et_point_history_end_date);
        String endDate =
                TimeUtil.getStringFormatDate(
                        DATE_FORMAT_SIMPLE,
                        TimeUtil.getTodayCalendar());
        etEndDate.setText(endDate);

        mViewModel.setStartDate(startDate);
        mViewModel.setEndDate(endDate);
        mViewModel.setSearchDateComplete(true);
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

    private void showStartDatePickerDialog() {
        if (startDatePickerDialog != null && !startDatePickerDialog.isShowing()) {
            startDatePickerDialog.show();
        }
    }

    private void showEndDatePickerDialog() {
        if (endDatePickerDialog != null && !endDatePickerDialog.isShowing()) {
            endDatePickerDialog.show();
        }
    }

}

