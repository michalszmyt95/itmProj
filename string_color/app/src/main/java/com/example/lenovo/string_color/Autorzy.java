package com.example.lenovo.string_color;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class Autorzy extends AppCompatActivity {

    TextView cezary, adam;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_autorzy);

        cezary = findViewById(R.id.cezary_opis);
        adam = findViewById(R.id.adam_opis);

        adam.setText("Adam Kamil Sypnik - Legenda mówi, że miał 100% z testu przy 3 promilach. " +
                "Człowiek o 2 wątrobach, Prezes koła naukowego robotyki - ogólnie człowiek orkiestra, full serwis bez anala. " +
                "Nie pijcie z nim, przepije ruskich. W technikum złapany na bolszewickiej granicy, gdzie kazano mu wypalić 2000 fajek");

        cezary.setText("Cezary Morawski - ciepły miś, lubi opierdolić kebaba. Pionier w dziedzinie znakowania terenu. " +
                "Ulepszył metodę Jasia i Małgosi. Gdyby był postacią pierwszoplanową w baśniach braci Grimm, " +
                "zamiast okruszków za sobą zostawiałby foremki po kebsalonikach.");


        cezary.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        adam.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

    }
}
