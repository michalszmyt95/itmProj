package com.uwm.wmii.student.michal.itmproj.api.dto;

/**
 * Created by Michał on 04.04.2018.
 */

public class WynikOdswiezeniaTokenaDTO {
    String accessToken;
    String refreshToken;
    Boolean odswiezonoPoprawnie;

    public WynikOdswiezeniaTokenaDTO() {}

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

    public Boolean getOdswiezonoPoprawnie() {
        return odswiezonoPoprawnie;
    }

    public void setOdswiezonoPoprawnie(Boolean odswiezonoPoprawnie) {
        this.odswiezonoPoprawnie = odswiezonoPoprawnie;
    }
}
