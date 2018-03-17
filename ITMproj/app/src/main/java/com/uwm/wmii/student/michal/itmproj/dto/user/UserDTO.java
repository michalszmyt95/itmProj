package com.uwm.wmii.student.michal.itmproj.dto.user;

/**
 * Created by Michał on 13.03.2018.
 */

public class UserDTO {
    private String firstname;
    private String email;
    private Integer wiek;
    private Float wzrost;
    private Float waga;

    public UserDTO() {}

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getWiek() {
        return wiek;
    }

    public void setWiek(Integer wiek) {
        this.wiek = wiek;
    }

    public Float getWzrost() {
        return wzrost;
    }

    public void setWzrost(Float wzrost) {
        this.wzrost = wzrost;
    }

    public Float getWaga() {
        return waga;
    }

    public void setWaga(Float waga) {
        this.waga = waga;
    }
}
