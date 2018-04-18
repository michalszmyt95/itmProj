package com.uwm.wmii.student.michal.itmproj.api.interceptor;

import android.content.Context;
import android.util.Log;

import com.uwm.wmii.student.michal.itmproj.singletons.AppLoginManager;
import com.uwm.wmii.student.michal.itmproj.singletons.AppStatusManager;
import com.uwm.wmii.student.michal.itmproj.utils.CallbackWynikInterface;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Michał on 04.04.2018.
 */

public class InterceptorTryRefreshJWT implements Interceptor {

    AppLoginManager appLoginManager;

    public InterceptorTryRefreshJWT(Context applicationContext) {
        this.appLoginManager = AppLoginManager.getInstance(applicationContext);
    }

    @Override
    public Response intercept(final Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        Log.d("TEST", "UDALO SIE WYKONAC INTERCEPTORA");
        if (response.code() > 400 && response.code() < 500) {
            Log.d("TEST", "MAMY KOD > 400");
            appLoginManager.odswiezTokenAsynchronicznie(new CallbackWynikInterface() {
                @Override
                public void gdySukces() {
                    if(appLoginManager.odswiezToken()) {
                        Request newRequest  = chain.request().newBuilder()
                                //     .addHeader("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MjM5MTE2NzgsIm5iZiI6MTUyMzkxMTY3OCwianRpIjoiOTMyM2FiOGUtMmNjZi00NjE2LTk4M2YtYzFjMGYxMTJmZWY1IiwiZXhwIjoxNTIzOTEyNTc4LCJpZGVudGl0eSI6IjVhY2JlYWRhY2M2MTdhMjc0MDUxNGNmMyIsImZyZXNoIjpmYWxzZSwidHlwZSI6ImFjY2VzcyJ9.ymtHyH0ibm9k3dvxfW263nBNWHQd99I5FwRc7GtaU_s")
                                .addHeader("Authorization", "Bearer " + appLoginManager.pobierzAccessTokenSerwera())
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Accept", "application/json")
                                .build();
                        try {
                            chain.proceed(newRequest);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void gdyBlad() {

                }
            });
        }
        return response;
    }
}
