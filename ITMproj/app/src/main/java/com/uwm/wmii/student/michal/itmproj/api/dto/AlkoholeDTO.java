package com.uwm.wmii.student.michal.itmproj.api.dto;

import java.util.Date;

/**
 * Created by Paulinka on 08.04.2018.
 */

public class AlkoholeDTO {
    private int iloscPiwo;
    private int iloscWodka;
    private int iloscCocktail;
    private int iloscWino;
    private Date dataCzasWypicia;

    public AlkoholeDTO() {
    }

    public AlkoholeDTO(int iloscPiwo,
                        int iloscWodka,
                        int iloscCocktail,
                        int iloscWino,
                        Date dataCzasWypicia) {
        this.iloscPiwo = iloscPiwo;
        this.iloscWodka = iloscWodka;
        this.iloscCocktail = iloscCocktail;
        this.iloscWino = iloscWino;
        this.dataCzasWypicia =  dataCzasWypicia;
    }

    public int getIloscPiwo() {
        return iloscPiwo;
    }

    public void setIloscPiwo(int iloscPiwo) {
        this.iloscPiwo = iloscPiwo;
    }

    public int getIloscWodka() {
        return iloscWodka;
    }

    public void setIloscWodka(int iloscWodka) {
        this.iloscWodka = iloscWodka;
    }

    public int getIloscWino() {
        return iloscWino;
    }

    public void setIloscWino(int iloscWino) {
        this.iloscWino = iloscWino;
    }

    public int getIloscCocktail() {
        return iloscCocktail;
    }

    public void setIloscCocktail(int iloscCocktail) {
        this.iloscCocktail = iloscCocktail;
    }

    public Date getDataCzasWypicia() {
        return dataCzasWypicia;
    }

    public void setDataCzasWypicia(Date dataCzasWypicia) {
        this.dataCzasWypicia = dataCzasWypicia;
    }
}
