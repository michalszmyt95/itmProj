package com.example.lenovo.string_color;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "task";

    int odliczanie = 60;
    private int mProgressBarStatus = 100;
    public boolean stopTimer = false;
    TextView licznik, napis, licznikRuchow, licznikTrafien;
    Button button_odp_1, button_odp_2, button_odp_3;
    ProgressBar progressBar;
    int score = 0;
    Random random = new Random();
    static int i = 0;
    private static int x_1, y_1;
    static boolean isRunning = false;
    public static int iloscRuchow = 0;
    public static int liczbaPunktow = 0;
    generatorList gen;
    List<String> napisy_na_przycisku = new ArrayList();



    String[][] koloryHex = new String[][]{
            {"pomarańczowy", "niebieski", "żółty", "żodyn kurwa", "sraczkowaty", "karmazynowy", "fioletowy", "biały", "czarny", "błękitny", "brązowy", "szary"},
            {"#FFA500", "#0000FF", "#FFFF00", "#00b8c7", "#8b4500", "#008b00", "#a50e2c", "#8A2BE2", "#FFFFFF", "#000000", "#2FDAFF", "#742323"}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        licznik = findViewById(R.id.licznikCzasu);
        napis = findViewById(R.id.kolorowyNapis);
        button_odp_1 = findViewById(R.id.button_odp1);
        button_odp_2 = findViewById(R.id.button_odp2);
        button_odp_3 = findViewById(R.id.button_odp3);
        progressBar = findViewById(R.id.progressBar);
        licznikRuchow = findViewById(R.id.textView_licznikRuchów);
        licznikTrafien = findViewById(R.id.textView_licznikPunktów);
        final MediaPlayer playTrue = MediaPlayer.create(this, R.raw.true_sound);
        final MediaPlayer playFalse = MediaPlayer.create(this, R.raw.false_sound);


        generatorList kolor_1 = new generatorList("niebieski", "#ff0000", "czerwony", "#ff0000", "niebieski");
        generatorList kolor_2 = new generatorList("zielony", "#FFA500", "pomarańczowy", "#FFA500", "różowy");
        generatorList kolor_3 = new generatorList("granatowy", "#1dff00", "zielony", "#1dff00", "niebieski");
        generatorList kolor_4 = new generatorList("pomarańczowy", "#f6fc3c", "żółty", "#f6fc3c", "niebieski");
        generatorList kolor_5 = new generatorList("czarny", "#ffffff", "bialy", "#ffffff", "niebieski");
        generatorList kolor_6 = new generatorList("biały", "#000000", "czarny", "#000000", "pomarańczowy");
        generatorList kolor_7 = new generatorList("różowy", "#08ff00", "zielony", "#ff00d4", "oliwkowy");
        generatorList kolor_8 = new generatorList("brązowy", "#604805", "brązowy", "#604805", "jasny niebieski");
        generatorList kolor_9 = new generatorList("siwy", "#00e5ff", "błękitny", "#00e5ff", "szary");
        generatorList kolor_10 = new generatorList("błękitny", "#b7b7b7", "szary", "#b7b7b7", "fioletowy");


        final ArrayList<generatorList> lista = new ArrayList();
        lista.add(kolor_1);
        lista.add(kolor_2);
        lista.add(kolor_3);
        lista.add(kolor_4);
        lista.add(kolor_5);
        lista.add(kolor_6);
        lista.add(kolor_7);
        lista.add(kolor_8);
        lista.add(kolor_9);
        lista.add(kolor_10);


        CountDown();
        UpdateProgressBar();
        updateData(lista);

        button_odp_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gen.getNazwaFalszywegoKoloru() == button_odp_1.getText()) {
                    iloscRuchow++;
                    liczbaPunktow++;

                    licznikRuchow.setText(String.valueOf(iloscRuchow));
                    licznikTrafien.setText(String.valueOf(liczbaPunktow));
                    playTrue.start();

                } else {
                    iloscRuchow++;
                    licznikRuchow.setText(String.valueOf(iloscRuchow));
                    playFalse.start();
                }
                updateData(lista);
            }
        });

        button_odp_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gen.getNazwaFalszywegoKoloru() == button_odp_2.getText()) {
                    iloscRuchow++;
                    liczbaPunktow++;

                    licznikRuchow.setText(String.valueOf(iloscRuchow));
                    licznikTrafien.setText(String.valueOf(liczbaPunktow));
                    playTrue.start();
                } else {
                    iloscRuchow++;
                    licznikRuchow.setText(String.valueOf(iloscRuchow));
                    playFalse.start();
                }
                updateData(lista);
            }
        });

        button_odp_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gen.getNazwaFalszywegoKoloru() == button_odp_3.getText()) {
                    iloscRuchow++;
                    liczbaPunktow++;

                    licznikRuchow.setText(String.valueOf(iloscRuchow));
                    licznikTrafien.setText(String.valueOf(liczbaPunktow));
                    playTrue.start();
                } else {
                    iloscRuchow++;
                    licznikRuchow.setText(String.valueOf(iloscRuchow));
                    playFalse.start();
                }
                updateData(lista);
            }
        });
    }

    public void CountDown() {
        isRunning = true;
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (odliczanie < 0) {
                    stopTimer = true;
                    isRunning = false;
                    Intent intent = new Intent(getApplicationContext(), gameOver.class);
                    intent.putExtra("liczba trafien", liczbaPunktow);
                    intent.putExtra("liczba ruchow", iloscRuchow);
                    startActivity(intent);


                }
                if (stopTimer == false) {
                    licznik.setText(String.valueOf(odliczanie));
                    odliczanie--;
                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    public void UpdateProgressBar() {
        final Timer time = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                mProgressBarStatus--;
                progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#FFC90306"), PorterDuff.Mode.MULTIPLY);
                progressBar.setProgress(mProgressBarStatus);
                if (mProgressBarStatus == 0) {
                    time.cancel();
                }
            }
        };
        time.schedule(tt, 300, 600);
    }


    public static boolean contains(final int[] array, final int v) {
        boolean result = false;
        for (int i : array) {
            if (i == v) {
                result = true;
                break;
            }
        }
        return result;
    }


    public static boolean containsThreePositiveDigit(final int[] array) {
        boolean result = true;
        int counter = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] >= 0) {
                counter++;
            }
        }
        if (counter == 3) {
            result = false;
        }
        return result;
    }

    public static boolean hasThreePositiveDigit(final int[] array) {
        boolean result = false;
        int counter = 0;
        for (int j = 0; j < array.length; j++) {
            if (array[j] >= 0) {
                counter++;
            }
            if (counter == 3) {
                result = true;
            }
        }
        return result;
    }


    public void updateData(final ArrayList<generatorList> lista) {
        Button tablica_przyciskow[] = new Button[3];
        tablica_przyciskow[0] = findViewById(R.id.button_odp1);
        tablica_przyciskow[1] = findViewById(R.id.button_odp2);
        tablica_przyciskow[2] = findViewById(R.id.button_odp3);

        int tmp;
        int x = random.nextInt(12);
        int y = random.nextInt(12);
        int z = random.nextInt(10);
        x_1 = x;
        y_1 = y;
        int[] tab_indeksy = {-1, -1, -1};
        i = 0;
        while (containsThreePositiveDigit(tab_indeksy) && i <= 2) {
            int wylosowany_indeks = random.nextInt(tablica_przyciskow.length);
            if (contains(tab_indeksy, wylosowany_indeks)) {
                continue;
            }
            tab_indeksy[i] = wylosowany_indeks;
            if (hasThreePositiveDigit(tab_indeksy)) {
                i = 0;
                break;
            }
            i++;
        }

        gen = lista.get(z);
        napis.setText(gen.getStringColor());
        napis.setTextColor(Color.parseColor(gen.getFalszywyHex()));


        for (int i = 0; i < tab_indeksy.length; i++) {

            if (i == 0) {
                tablica_przyciskow[tab_indeksy[i]].setText(gen.getNazwaFalszywegoKoloru());
                tablica_przyciskow[tab_indeksy[i]].setTextColor(Color.parseColor(koloryHex[1][x]));
            }

            if (i == 1) {
                tablica_przyciskow[tab_indeksy[i]].setText(koloryHex[0][x]);
                tablica_przyciskow[tab_indeksy[i]].setTextColor(Color.parseColor(gen.getFalszywyHexButton()));
            }

            if (i == 2) {
                tmp = generateUniqueInt();
                napisy_na_przycisku.add(koloryHex[0][x]);
                napisy_na_przycisku.add(gen.getNazwaFalszywegoKoloru());

                if (napisy_na_przycisku.contains(koloryHex[0][tmp])) {
                    tablica_przyciskow[tab_indeksy[i]].setText(koloryHex[0][tmp % 3]);
                    tablica_przyciskow[tab_indeksy[i]].setTextColor(Color.parseColor(koloryHex[1][tmp]));

                } else {
                    tmp = generateUniqueInt();
                    tablica_przyciskow[tab_indeksy[i]].setText(koloryHex[0][tmp]);
                    tablica_przyciskow[tab_indeksy[i]].setTextColor(Color.parseColor(koloryHex[1][tmp]));
                }
                napisy_na_przycisku.clear();
            }
        }
    }

    public static int generateUniqueInt() {
        Random rnd = new Random();
        List<Integer> previous = new ArrayList<>();
        previous.add(x_1);
        int d = rnd.nextInt(12);
        if (previous.contains(Double.valueOf(d))) {
            return generateUniqueInt();
        }
        return d;
    }
}



