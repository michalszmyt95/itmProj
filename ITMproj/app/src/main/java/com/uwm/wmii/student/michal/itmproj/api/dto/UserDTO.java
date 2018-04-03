package com.uwm.wmii.student.michal.itmproj.api.dto;

/**
 * Created by Michał on 13.03.2018.
 */

public class UserDTO extends SocialDTO {
    private String id;
    private String imie;
    private String nazwisko;
    private String email;
    private Integer wiek;
    private Float wzrost;
    private Float waga;

    public UserDTO() {}

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

    public String getId() {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

}
