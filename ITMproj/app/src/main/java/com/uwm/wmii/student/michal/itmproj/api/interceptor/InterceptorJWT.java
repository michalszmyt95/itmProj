package com.uwm.wmii.student.michal.itmproj.api.interceptor;

import android.content.Context;

import com.uwm.wmii.student.michal.itmproj.singletons.AppLoginManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Michał on 04.04.2018.
 */

public class InterceptorJWT implements Interceptor {

    AppLoginManager appLoginManager;

    public InterceptorJWT(Context applicationContext) {
        this.appLoginManager = AppLoginManager.getInstance(applicationContext);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest  = chain.request().newBuilder()
           //     .addHeader("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MjM5MTE2NzgsIm5iZiI6MTUyMzkxMTY3OCwianRpIjoiOTMyM2FiOGUtMmNjZi00NjE2LTk4M2YtYzFjMGYxMTJmZWY1IiwiZXhwIjoxNTIzOTEyNTc4LCJpZGVudGl0eSI6IjVhY2JlYWRhY2M2MTdhMjc0MDUxNGNmMyIsImZyZXNoIjpmYWxzZSwidHlwZSI6ImFjY2VzcyJ9.ymtHyH0ibm9k3dvxfW263nBNWHQd99I5FwRc7GtaU_s")
                .addHeader("Authorization", "Bearer " + appLoginManager.pobierzAccessTokenSerwera())
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        return chain.proceed(newRequest);
    }
}
