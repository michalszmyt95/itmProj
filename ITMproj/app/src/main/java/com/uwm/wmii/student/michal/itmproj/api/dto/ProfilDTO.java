package com.uwm.wmii.student.michal.itmproj.api.dto;

/**
 * Created by Michał on 09.04.2018.
 */

public class ProfilDTO {
    private Integer wiek;
    private Float wzrost;
    private Float waga;
    private String plec; // K - kobieta, M - mężczyzna

    public ProfilDTO() {}

    public ProfilDTO(Integer wiek, Float wzrost, Float waga, String plec) {
        this.wiek = wiek;
        this.wzrost = wzrost;
        this.waga = waga;
        this.plec = plec;
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

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }
}
