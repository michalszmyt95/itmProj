package com.uwm.wmii.student.michal.itmproj.api.dto;

/**
 * Created by Michał on 03.04.2018.
 */

public class SocialDTO {
    private String socialAccessToken;
    private String socialRefreshToken;
    private String metodaLogowania;
    private String socialId;

    public SocialDTO() {
    }

    public SocialDTO(String socialAccessToken, String socialRefreshToken, String metodaLogowania, String socialId) {
        this.socialAccessToken = socialAccessToken;
        this.socialRefreshToken = socialRefreshToken;
        this.metodaLogowania = metodaLogowania;
        this.socialId = socialId;
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

    public String getMetodaLogowania() {
        return metodaLogowania;
    }

    public void setMetodaLogowania(String metodaLogowania) {
        this.metodaLogowania = metodaLogowania;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }
}
