package com.uwm.wmii.student.michal.itmproj.api.dto;

/**
 * Created by Michał on 03.04.2018.
 */

public class SocialDTO {
    private String token;
    private String metodaLogowania;
    private String socialId;

    public SocialDTO() {
    }

    public SocialDTO(String token, String metodaLogowania, String socialId) {
        this.token = token;
        this.metodaLogowania = metodaLogowania;
        this.socialId = socialId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
