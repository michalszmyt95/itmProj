package com.uwm.wmii.student.michal.itmproj.model;

import com.uwm.wmii.student.michal.itmproj.model.enumy.MetodaLogowania;

/**
 * Created by Michał on 20.03.2018.
 */

public class DaneLogowania {
    private String email;
    private String imie;
    private String nazwisko;
    private String userID;
    private String token;
    private String zdjecieProfiloweUrl;
    private MetodaLogowania metodaLogowania;

    public DaneLogowania() {
    }

    public DaneLogowania(String email, String imie, String nazwisko, String userID, String token, String zdjecieProfiloweUrl, MetodaLogowania metodaLogowania) {
        this.email = email;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.userID = userID;
        this.token = token;
        this.metodaLogowania = metodaLogowania;
        this.zdjecieProfiloweUrl = zdjecieProfiloweUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getZdjecieProfiloweUrl() {
        return zdjecieProfiloweUrl;
    }

    public void setZdjecieProfiloweUrl(String zdjecieProfiloweUrl) {
        this.zdjecieProfiloweUrl = zdjecieProfiloweUrl;
    }

    public MetodaLogowania getMetodaLogowania() {
        return metodaLogowania;
    }

    public void setMetodaLogowania(MetodaLogowania metodaLogowania) {
        this.metodaLogowania = metodaLogowania;
    }
}
