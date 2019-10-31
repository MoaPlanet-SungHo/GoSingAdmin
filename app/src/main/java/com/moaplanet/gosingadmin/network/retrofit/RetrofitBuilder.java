package com.moaplanet.gosingadmin.network.retrofit;

import android.content.Context;

import com.moaplanet.gosingadmin.BuildConfig;
import com.moaplanet.gosingadmin.network.AddCookiesInterceptor;
import com.moaplanet.gosingadmin.network.PersistentCookieStore;
import com.moaplanet.gosingadmin.network.ReceivedCookiesInterceptor;
import com.moaplanet.gosingadmin.utils.ObjectUtil;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {


    public <T> T init(String baseUrl, Map<String, String> headerMap, Class<T> cls, Context context) {
        return getRetrofitBuilder(baseUrl, getOkHttpClient(headerMap, context), false).create(cls);
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

    private OkHttpClient getOkHttpClient(Map<String, String> headerMap, Context context) {

//        CookieHandler cookieHandler = new CookieManager();
//        PersistentCookieStore persistentCookieStore = new PersistentCookieStore(context);
//        CookieManager cookieManager = new CookieManager(persistentCookieStore, CookiePolicy.ACCEPT_ORIGINAL_SERVER);
//        CookieHandler.setDefault(cookieManager);

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
//        okHttpClient.cookieJar(new JavaNetCookieJar(cookieHandler))
//                .connectTimeout(10000, TimeUnit.SECONDS)
//                .writeTimeout(10000, TimeUnit.SECONDS)
//                .readTimeout(10000, TimeUnit.SECONDS);
//        okHttpClient.cookieJar(new JavaNetCookieJar(cookieManager));
        okHttpClient.addNetworkInterceptor(new AddCookiesInterceptor());
        okHttpClient.addInterceptor(new ReceivedCookiesInterceptor());
        return okHttpClient.build();
    }

    private HttpLoggingInterceptor getLoggingInterface() {
        return new HttpLoggingInterceptor().setLevel(
                BuildConfig.DEBUG ?
                        HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE
        );
    }
}
