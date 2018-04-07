package com.uwm.wmii.student.michal.itmproj.api.service;

import android.content.Context;

import com.uwm.wmii.student.michal.itmproj.singletons.AppLoginManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Michał on 04.04.2018.
 */

public class InterceptorRefreshTokenHeader implements Interceptor {

    AppLoginManager appLoginManager;

    public InterceptorRefreshTokenHeader(Context applicationContext) {
        this.appLoginManager = AppLoginManager.getInstance(applicationContext);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest  = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + appLoginManager.pobierzRefreshTokenSerwera())
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        return chain.proceed(newRequest);
    }
}
