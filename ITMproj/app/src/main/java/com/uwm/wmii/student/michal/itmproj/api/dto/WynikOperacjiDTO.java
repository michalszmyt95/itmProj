package com.uwm.wmii.student.michal.itmproj.api.dto;

/**
 * Created by Michał on 02.04.2018.
 */

public class WynikOperacjiDTO {
    String id;
    Boolean wynik;

    public WynikOperacjiDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getWynik() {
        return wynik;
    }

    public void setWynik(Boolean wynik) {
        this.wynik = wynik;
    }
}
