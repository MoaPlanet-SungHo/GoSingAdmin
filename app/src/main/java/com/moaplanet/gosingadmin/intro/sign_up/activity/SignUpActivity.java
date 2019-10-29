package com.moaplanet.gosingadmin.intro.sign_up.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.intro.sign_up.model.SignUpViewModel;
import com.moaplanet.gosingadmin.intro.sign_up.model.req.ReqSignUpDto;
import com.moaplanet.gosingadmin.intro.sign_up.model.res.ResSignUpDto;
import com.moaplanet.gosingadmin.network.retrofit.RetrofitCallBack;
import com.moaplanet.gosingadmin.network.retrofit.RetrofitListener;
import com.moaplanet.gosingadmin.network.service.GoSingApiService;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.orhanobut.logger.Logger;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends BaseActivity {

    private SignUpViewModel signUpViewModel;
    private ReqSignUpDto reqModel;

    @Override
    public int layoutRes() {
        return R.layout.activity_sign_up;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        reqModelInit();
    }

    private void reqModelInit() {
        reqModel = new ReqSignUpDto();
        signUpViewModel.getCheckEventPush().observe(this, reqModel::setEventType);

        signUpViewModel.getEmail().observe(this, reqModel::setEmail);

        signUpViewModel.getPw().observe(this, reqModel::setPw);

        signUpViewModel.getSalesCode().observe(this, salesCode -> {
            reqModel.setSalesCode(salesCode);
            onSignUp();
        });
    }

    private HttpLoggingInterceptor getLoggingInterface() {
        return new HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY
        );
    }

    private void onSignUp() {
//        Retrofit.Builder baseRestApi = new Retrofit.Builder();
//        baseRestApi.baseUrl(GoSingConstants.GOSING_ADDIN_BASE_URL);
//        baseRestApi.client(new OkHttpClient().newBuilder().addInterceptor(getLoggingInterface()).build());
//        baseRestApi.addConverterFactory(GsonConverterFactory.create());
//        baseRestApi.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
//        baseRestApi.build().create(GoSingApiService.class);

//        GoSingApiService goSingApiService = new Retrofit.Builder().baseUrl(GoSingConstants.GOSING_ADDIN_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build().create(GoSingApiService.class);
//
//        goSingApiService.ttt().enqueue(new Callback<ResSignUpDto>() {
//            @Override
//            public void onResponse(Call<ResSignUpDto> call, Response<ResSignUpDto> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<ResSignUpDto> call, Throwable t) {
//
//            }
//        });


//        GoSingApiService goSingApiService = baseRestApi.
//        baseRestApi.baseUrl(GoSingConstants.GOSING_ADDIN_BASE_URL);
        new RetrofitService()
                .getGoSingApiService()
//                .ttt()
//                .equals(new )
                .signUp(reqModel)
                .enqueue(new RetrofitCallBack<>(retrofitListener));
    }

    private RetrofitListener<ResSignUpDto> retrofitListener = new RetrofitListener<ResSignUpDto>() {
        @Override
        public void onSuccess(ResSignUpDto responseData) {
            Logger.d("성공");
        }

        @Override
        public void onReissuedAccessToken() {
            Logger.d("성공");
        }

        @Override
        public void onFail(String msg) {
            Logger.d("실패 : " + msg);

        }

        @Override
        public void onNetworkError(Throwable t) {
            Logger.d("실패");
        }
    };

}
