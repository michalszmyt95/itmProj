package com.uwm.wmii.student.michal.itmproj.model.enumy;

/**
 * Created by Michał on 20.03.2018.
 */

public enum MetodaLogowania {
    Facebook("Facebook"), Google("Google"), Brak("Brak");

    private String metodaLogowania;

    MetodaLogowania(String metodaLogowania) {
        this.metodaLogowania = metodaLogowania;
    }

    @Override
    public String toString() {
        return this.metodaLogowania;
    }
}
