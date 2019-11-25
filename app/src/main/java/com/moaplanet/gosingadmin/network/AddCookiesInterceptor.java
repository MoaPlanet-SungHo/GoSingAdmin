package com.moaplanet.gosingadmin.network;

import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
//        builder.removeHeader("JSESSIONID").addHeader("JSESSIONID", "5BBBA68529EA8C734FD4F24C4C19EC58");
//        builder.addHeader("Cookie")
        if (GoSingConstants.cookieHash != null) {
            for (String ck : GoSingConstants.cookieHash) {
                builder.addHeader("Cookie", ck);
            }
        }
        return chain.proceed(builder.build());
    }
}
