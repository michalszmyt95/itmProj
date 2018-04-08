package com.uwm.wmii.student.michal.itmproj.model;

import com.uwm.wmii.student.michal.itmproj.model.enumy.MetodaLogowania;

/**
 * Created by Michał on 20.03.2018.
 */

public class DaneLogowania {
    private String email;
    private String imie;
    private String nazwisko;
    private String socialUserID;
    private String userID;
    private String socialAccessToken;
    private String socialRefreshToken;
    private String zdjecieProfiloweUrl;
    private MetodaLogowania metodaLogowania;

    public DaneLogowania() {
    }

    public DaneLogowania(String email, String imie, String nazwisko, String socialUserID, String userID, String socialAccessToken, String socialRefreshToken, String zdjecieProfiloweUrl, MetodaLogowania metodaLogowania) {
        this.email = email;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.socialUserID = socialUserID;
        this.userID = userID;
        this.socialAccessToken = socialAccessToken;
        this.socialRefreshToken = socialRefreshToken;
        this.zdjecieProfiloweUrl = zdjecieProfiloweUrl;
        this.metodaLogowania = metodaLogowania;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSocialAccessToken() {
        return socialAccessToken;
    }

    public void setSocialAccessToken(String socialAccessToken) {
        this.socialAccessToken = socialAccessToken;
    }

    public String getSocialRefreshToken() {
        return socialRefreshToken;
    }

    public void setSocialRefreshToken(String socialRefreshToken) {
        this.socialRefreshToken = socialRefreshToken;
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

    public String getSocialUserID() {
        return socialUserID;
    }

    public void setSocialUserID(String socialUserID) {
        this.socialUserID = socialUserID;
    }
}
