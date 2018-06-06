package com.example.lenovo.string_color;

public class generatorList {
    String nazwaKoloruMnemoniczna = null;
    String kolorFałszywyHex = null;
    String nazwaFalszywegoKoloru = null;
    String falszywyHexButton = null;
    String falszywyHexButtonMnemoniczna = null;


    generatorList(String nazwaKoloruMnemoniczna, String kolorFałszywyHex, String nazwaFalszywegoKoloru, String falszywyHexButton, String falszywyHexButtonMnemoniczna) {
        this.nazwaKoloruMnemoniczna = nazwaKoloruMnemoniczna;
        this.kolorFałszywyHex = kolorFałszywyHex;
        this.nazwaFalszywegoKoloru = nazwaFalszywegoKoloru;
        this.falszywyHexButton = falszywyHexButton;
        this.falszywyHexButtonMnemoniczna = falszywyHexButtonMnemoniczna;

    }

    public String getStringColor() {
        return nazwaKoloruMnemoniczna;
    }

    public String getFalszywyHex() {
        return kolorFałszywyHex;
    }

    public String getNazwaFalszywegoKoloru() {
        return nazwaFalszywegoKoloru;
    }

    public String getFalszywyHexButton() { return falszywyHexButton; }

    public String getFalszywyHexButtonMnemoniczna() { return falszywyHexButtonMnemoniczna; }


    /*generatorList kolor_1 = new generatorList("niebieski", "#ff0000", "czerwony");*/

}
