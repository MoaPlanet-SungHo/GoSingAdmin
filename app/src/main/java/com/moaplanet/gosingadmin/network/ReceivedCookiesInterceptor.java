package com.moaplanet.gosingadmin.network;

import com.moaplanet.gosingadmin.constants.GoSingConstants;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by jiwun on 2019-10-30.
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (originalResponse.header("Set-Cookie") != null &&!originalResponse.header("Set-Cookie").isEmpty()) {

            HashSet<String> cookies = new HashSet<>();
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            GoSingConstants.cookieHash = cookies;

        }


        return originalResponse;
    }
}
