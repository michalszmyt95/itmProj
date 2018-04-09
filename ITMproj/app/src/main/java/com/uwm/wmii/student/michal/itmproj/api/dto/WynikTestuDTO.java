package com.uwm.wmii.student.michal.itmproj.api.dto;

import java.util.Date;

/**
 * Created by Michał on 09.04.2018.
 */

public class WynikTestuDTO {
    private Object wynik;
    private Date data;
    private Boolean czyTest;

    public WynikTestuDTO() {

    }

    public WynikTestuDTO(Object wynik, Date data, Boolean czyTest) {
        this.wynik = wynik;
        this.data = data;
        this.czyTest = czyTest;
    }

    public Object getWynik() {
        return wynik;
    }

    public void setWynik(Object wynik) {
        this.wynik = wynik;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Boolean getCzyTest() {
        return czyTest;
    }

    public void setCzyTest(Boolean czyTest) {
        this.czyTest = czyTest;
    }

}
