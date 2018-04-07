package com.uwm.wmii.student.michal.itmproj.singletons;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.uwm.wmii.student.michal.itmproj.LoginActivity;
import com.uwm.wmii.student.michal.itmproj.api.dto.UserDTO;
import com.uwm.wmii.student.michal.itmproj.api.dto.WynikOdswiezeniaTokenaDTO;
import com.uwm.wmii.student.michal.itmproj.api.service.AuthRestService;
import com.uwm.wmii.student.michal.itmproj.model.DaneLogowania;
import com.uwm.wmii.student.michal.itmproj.model.enumy.MetodaLogowania;

import java.io.IOException;

/**
 * Created by Michał on 20.03.2018.
 */

public class AppLoginManager {
    private static final AppLoginManager ourInstance = new AppLoginManager();
    private static AppRestManager appRestManager;
    private static Context context;
    private static SharedPreferences sharedPreferences;

    private GoogleSignInClient mGoogleSignInClient;

    public static AppLoginManager getInstance(Context context) {
        if (AppLoginManager.context == null) {
            AppLoginManager.context = context;
            AppLoginManager.sharedPreferences = AppLoginManager.context.getSharedPreferences("LOGIN", Activity.MODE_PRIVATE);
            AppLoginManager.appRestManager = AppRestManager.getInstance(context);
        }
        return ourInstance;
    }

    private AppLoginManager() {
    }

    public void setGoogleSignInClient(GoogleSignInClient mGoogleSignInClient) {
        this.mGoogleSignInClient = mGoogleSignInClient;
    }

    public GoogleSignInClient getGoogleSignInClient() {
        return mGoogleSignInClient;
    }

    public boolean zapiszAccessTokenDoSharedPreferences(String accessToken) {
        if (accessToken != null) {
            SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
            preferencesEditor.putString("accessToken", accessToken);
            preferencesEditor.apply();
            preferencesEditor.commit();
            return true;
        }
        return false;
    }

    public boolean zapiszRefreshTokenDoSharedPreferences(String refreshToken) {
        if (refreshToken != null) {
            SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
            preferencesEditor.putString("refreshToken", refreshToken);
            preferencesEditor.apply();
            preferencesEditor.commit();
            return true;
        }
        return false;
    }

    public Boolean odswiezToken() {
        String refreshToken = pobierzRefreshTokenSerwera();
        if (refreshToken == null) {
            return false;
        }
        AuthRestService authService = appRestManager.podajAuthService();
        try {
            WynikOdswiezeniaTokenaDTO wynik = authService.odswiezToken().execute().body();
            if (wynik == null) {
                return false;
            }
            if (wynik.getOdswiezonoPoprawnie()) {
                zapiszAccessTokenDoSharedPreferences(wynik.getAccessToken());
                zapiszRefreshTokenDoSharedPreferences(wynik.getRefreshToken());
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void zapiszSocialAccessTokenDoSharedPreferences(String socialAccessToken) {
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        preferencesEditor.putString("socialAccessToken", socialAccessToken);
        preferencesEditor.apply();
        preferencesEditor.commit();
    }

    public void zapiszDaneLogowaniaDoSharedPreferences(DaneLogowania daneLogowania) {
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        if (daneLogowania.getMetodaLogowania() != null) {
            preferencesEditor.putString("metodaLogowania", daneLogowania.getMetodaLogowania().toString());
        }
        if (daneLogowania.getEmail() != null) {
            preferencesEditor.putString("email", daneLogowania.getEmail());
        }
        if (daneLogowania.getImie() != null) {
            preferencesEditor.putString("imie", daneLogowania.getImie());
        }
        if (daneLogowania.getNazwisko() != null) {
            preferencesEditor.putString("nazwisko", daneLogowania.getNazwisko());
        }
        if (daneLogowania.getUserID() != null) {
            preferencesEditor.putString("userId", daneLogowania.getUserID());
        }
        if (daneLogowania.getSocialUserID() != null) {
            preferencesEditor.putString("socialUserId", daneLogowania.getSocialUserID());
        }
        if (daneLogowania.getSocialAccessToken() != null) {
            preferencesEditor.putString("socialAccessToken", daneLogowania.getSocialAccessToken());
        }
        if (daneLogowania.getZdjecieProfiloweUrl() != null) {
            preferencesEditor.putString("zdjecieProfiloweUrl", daneLogowania.getZdjecieProfiloweUrl());
        }
        preferencesEditor.apply();
        preferencesEditor.commit();
    }

    public DaneLogowania pobierzDaneLogowania() {
        DaneLogowania daneLogowania = new DaneLogowania();
        daneLogowania.setZdjecieProfiloweUrl(sharedPreferences.getString("zdjecieProfiloweUrl", null));
        daneLogowania.setUserID(sharedPreferences.getString("userId", null));
        daneLogowania.setSocialUserID(sharedPreferences.getString("socialUserId", null));
        daneLogowania.setSocialAccessToken(sharedPreferences.getString("socialAccessToken", null));
        daneLogowania.setMetodaLogowania(MetodaLogowania.valueOf(sharedPreferences.getString("metodaLogowania", "Brak")));
        daneLogowania.setNazwisko(sharedPreferences.getString("nazwisko", null));
        daneLogowania.setEmail(sharedPreferences.getString("email", null));
        daneLogowania.setImie(sharedPreferences.getString("imie", "nieznajomy"));
        return daneLogowania;
    }

    public UserDTO pobierzDanePotrzebneDoLogowaniaLubRejestracji() {
        DaneLogowania daneLogowania = pobierzDaneLogowania();
        UserDTO dto = new UserDTO();
        dto.setSocialId(daneLogowania.getSocialUserID());
        dto.setEmail(daneLogowania.getEmail());
        dto.setMetodaLogowania(daneLogowania.getMetodaLogowania().toString());
        dto.setSocialAccessToken(daneLogowania.getSocialAccessToken());
        return dto;
    }

    public boolean czyUzytkownikZalogowany() {
        String tokenString = pobierzAccessTokenSerwera();
        if (tokenString == null) {
            return false;
        }
        JWT token = new JWT(tokenString);
        return !token.isExpired(10);
    }

    public String pobierzAccessTokenSerwera() {
        return sharedPreferences.getString("accessToken", null);
    }

    public String pobierzRefreshTokenSerwera() {
        return sharedPreferences.getString("refreshToken", null);
    }

    public void wyloguj() {
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.commit();
    }

    public void wylogujSocial() {
        if (pobierzDaneLogowania().getMetodaLogowania() != null) {
            switch(pobierzDaneLogowania().getMetodaLogowania()) {
                case Facebook:
                    LoginManager.getInstance().logOut();
                    break;
                case Google:
                    mGoogleSignInClient.signOut();
                    break;
            }
        }
    }

}
