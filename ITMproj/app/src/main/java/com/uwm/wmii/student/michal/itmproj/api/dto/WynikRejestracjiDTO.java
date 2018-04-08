package com.uwm.wmii.student.michal.itmproj.api.dto;

/**
 * Created by Michał on 04.04.2018.
 */

public class WynikRejestracjiDTO {
    String accessToken;
    String refreshToken;
    Boolean dodanoUzytkownika;

    public WynikRejestracjiDTO() {}

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Boolean getDodanoUzytkownika() {
        return dodanoUzytkownika;
    }

    public void setDodanoUzytkownika(Boolean dodanoUzytkownika) {
        this.dodanoUzytkownika = dodanoUzytkownika;
    }
}
