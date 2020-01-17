package com.moaplanet.gosingadmin.network.retrofit;

import com.google.gson.Gson;
import com.moaplanet.gosingadmin.BuildConfig;
import com.moaplanet.gosingadmin.main.slide_menu.main.model.dto.res.ResGoSingPointSearchDto;
import com.moaplanet.gosingadmin.network.AddCookiesInterceptor;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.ReceivedCookiesInterceptor;
import com.moaplanet.gosingadmin.network.model.CommonResDto;
import com.moaplanet.gosingadmin.utils.ObjectUtil;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import okio.Utf8;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {


    public <T> T init(String baseUrl, Map<String, String> headerMap, Class<T> cls) {
        return getRetrofitBuilder(baseUrl, getOkHttpClient(headerMap), false).create(cls);
    }

    public <T> T init(String baseUrl, Class<T> cls) {
        return getRetrofitBuilder(baseUrl).create(cls);
    }

    private Retrofit getRetrofitBuilder(String baseUrl) {
        if (baseUrl != null && baseUrl.length() > 0) {
            return new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        } else {
            return null;
        }
    }

    private Retrofit getRetrofitBuilder(String baseUrl, OkHttpClient okHttpClient, boolean useRxJava) {
        if (ObjectUtil.checkNotNull(baseUrl) && okHttpClient != null) {
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            retrofitBuilder.baseUrl(baseUrl);
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
            retrofitBuilder.client(okHttpClient);
            if (useRxJava) {
                retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            }
            return retrofitBuilder.build();
        } else {
            return null;
        }
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(getLoggingInterface()).build();
        okHttpClient.addNetworkInterceptor(new AddCookiesInterceptor());
        okHttpClient.addInterceptor(new ReceivedCookiesInterceptor());

        // todo 서버에서 retry 로직 추가되면 작성
        // retry 용 Interceptor 추가
//        okHttpClient.addInterceptor(chain -> {
//            Request request = chain.request();
//            Response response = chain.proceed(request);
//
//            // retry 로직
//            for (int i = 0; i < 3; i++) {
//
//                Logger.d("req 데이터 : " + request.toString());
//                // req 파라메터 변경
//                HttpUrl url = request.url().newBuilder().setQueryParameter("shop_qr_seq", String.valueOf(i)).build();
//                request = request.newBuilder().url(url).build();
//
//                // res 데이터 읽어오기
//                BufferedSource source = response.body().source();
//                source.request(Long.MAX_VALUE);
//                String resData = source.getBuffer().clone().readUtf8();
//                Logger.d("데이터 : " + i +" 데이터"+ resData);
//
//                response.close();
//
//                // retry
//                response = chain.proceed(request);
//            }
//
//            return response;
//
//        });
        return okHttpClient.build();
    }

    private OkHttpClient getOkHttpClient(Map<String, String> headerMap) {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(chain -> {
            Request.Builder requestBuilder = chain.request().newBuilder();
            if (headerMap != null) {
                for (String key : headerMap.keySet()) {
                    if (key != null && !key.isEmpty()
                            && headerMap.get(key) != null && !headerMap.get(key).isEmpty()) {
                        requestBuilder.addHeader(key, headerMap.get(key));
                    }
                }
            }
            return chain.proceed(requestBuilder.build());
        });
        okHttpClient.addInterceptor(getLoggingInterface());
        okHttpClient.addNetworkInterceptor(new AddCookiesInterceptor());
        okHttpClient.addInterceptor(new ReceivedCookiesInterceptor());
        return okHttpClient.build();
    }

    private HttpLoggingInterceptor getLoggingInterface() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(BuildConfig.DEBUG ?
                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return interceptor;
    }
}
