package com.moaplanet.gosingadmin.network;

import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jiwun on 2019-10-30.
 */
public class AddCookiesInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
//        builder.removeHeader("JSESSIONID").addHeader("JSESSIONID", "5BBBA68529EA8C734FD4F24C4C19EC58");
        Logger.d("ㅅㄷㄴㅅ");
        return chain.proceed(builder.build());
    }
}
