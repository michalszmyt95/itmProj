package com.uwm.wmii.student.michal.itmproj.singletons;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.uwm.wmii.student.michal.itmproj.model.DaneLogowania;
import com.uwm.wmii.student.michal.itmproj.model.enumy.MetodaLogowania;

/**
 * Created by Michał on 20.03.2018.
 */

public class AppLoginManager {
    private static final AppLoginManager ourInstance = new AppLoginManager();

    private static Context context;
    private static SharedPreferences sharedPreferences;

    private GoogleApiClient mGoogleApiClient;

    public static AppLoginManager getInstance(Context context) {
        if (AppLoginManager.context == null) {
            AppLoginManager.context = context;
            AppLoginManager.sharedPreferences = AppLoginManager.context.getSharedPreferences("LOGIN", Activity.MODE_PRIVATE);
        }
        return ourInstance;
    }

    private AppLoginManager() {
    }

    public void setGoogleApiClient(GoogleApiClient mGoogleApiClient) {
        this.mGoogleApiClient = mGoogleApiClient;
    }

    public GoogleApiClient getGoogleApiClient() {
        return this.mGoogleApiClient;
    }

    public void zapiszDaneLogowaniaDoSharedPreferences(DaneLogowania daneLogowania) {
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        if (daneLogowania.getMetodaLogowania() != null) {
            preferencesEditor.putString("metodaLogowania", daneLogowania.getMetodaLogowania().toString());
        }
        preferencesEditor.putString("email", daneLogowania.getEmail());
        preferencesEditor.putString("imie", daneLogowania.getImie());
        preferencesEditor.putString("nazwisko", daneLogowania.getNazwisko());
        preferencesEditor.putString("userId", daneLogowania.getUserID());
        preferencesEditor.putString("token", daneLogowania.getToken());
        preferencesEditor.putString("zdjecieProfiloweUrl", daneLogowania.getZdjecieProfiloweUrl());

        preferencesEditor.apply();
        preferencesEditor.commit();
    }

    public DaneLogowania pobierzDaneLogowania() {
        DaneLogowania daneLogowania = new DaneLogowania();
        daneLogowania.setZdjecieProfiloweUrl(sharedPreferences.getString("zdjecieProfiloweUrl", null));
        daneLogowania.setUserID(sharedPreferences.getString("userId", null));
        daneLogowania.setToken(sharedPreferences.getString("token", null));
        daneLogowania.setMetodaLogowania(MetodaLogowania.valueOf(sharedPreferences.getString("metodaLogowania", "Brak")));
        daneLogowania.setNazwisko(sharedPreferences.getString("nazwisko", null));
        daneLogowania.setEmail(sharedPreferences.getString("email", null));
        daneLogowania.setImie(sharedPreferences.getString("imie", "nieznajomy"));
        return daneLogowania;
    }

    public boolean czyUzytkownikZalogowany() {
        DaneLogowania dane = pobierzDaneLogowania();
        if (dane.getToken() == null) {
            return false;
        }
        return true;
    }

    public void wyloguj() {
        if (pobierzDaneLogowania().getMetodaLogowania() != null) {
            switch(pobierzDaneLogowania().getMetodaLogowania()) {
                case Facebook:
                    LoginManager.getInstance().logOut();
                    break;
                case Google:
                    if (mGoogleApiClient.isConnected())
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                    break;
            }
        }

        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.commit();
    }


}
