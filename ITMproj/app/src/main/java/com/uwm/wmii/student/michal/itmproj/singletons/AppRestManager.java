package com.uwm.wmii.student.michal.itmproj.singletons;

import android.content.Context;

import com.uwm.wmii.student.michal.itmproj.api.service.AlkoRestService;
import com.uwm.wmii.student.michal.itmproj.api.service.AuthRestService;
import com.uwm.wmii.student.michal.itmproj.api.interceptor.InterceptorJWT;
import com.uwm.wmii.student.michal.itmproj.api.interceptor.InterceptorRefreshTokenHeader;
import com.uwm.wmii.student.michal.itmproj.api.service.TestRestService;
import com.uwm.wmii.student.michal.itmproj.api.service.UserRestService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Michał on 04.04.2018.
 */

public class AppRestManager {
    private static final AppRestManager ourInstance = new AppRestManager();
    private static Context applicationContext;
    private Retrofit retrofit;

    public static AppRestManager getInstance(Context applicationContext) {
        AppRestManager.applicationContext = applicationContext;
        return ourInstance;
    }

    private AppRestManager() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/") // Nadany InterceptorJWT sprawia ze zawsze wysylamy token jwt do serwera oraz oczekujemy jsona i wysylamy jsona.
                .client(new OkHttpClient().newBuilder().addInterceptor(new InterceptorJWT(applicationContext)).build())
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
    }

    public UserRestService podajUserService() {
        return retrofit.create(UserRestService.class);
    }

    public AuthRestService podajAuthService() {
        return retrofit.newBuilder().client(new OkHttpClient().newBuilder()
                .addInterceptor(new InterceptorRefreshTokenHeader(applicationContext)).build())
                .build().create(AuthRestService.class);
    }

    public TestRestService podajTestService() {
        return retrofit.create(TestRestService.class);
    }

    public AlkoRestService dodajAlkohole() { return retrofit.create(AlkoRestService.class);}

}
