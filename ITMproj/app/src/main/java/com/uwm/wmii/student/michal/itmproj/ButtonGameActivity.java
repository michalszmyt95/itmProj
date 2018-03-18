package com.uwm.wmii.student.michal.itmproj;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class ButtonGameActivity extends AppCompatActivity {

    RelativeLayout tlo;
    Button btn;
    Button testStartBtn;
    Float wysokoscEkranu;
    Float szerokoscEkranu;
    Integer iloscKlikniec;
    TextView ileKlikniec;
    TextView czas;
    TextView odliczanie;
    int iloscSekund = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_game);
        ustawDaneEkranu();
        ustawPrzyciski();
    }

    private void ustawDaneEkranu() {
        ileKlikniec = findViewById(R.id.ile_klikniec);

        czas = findViewById(R.id.czas);
        odliczanie = findViewById(R.id.odliczanie_do_startu);
        odliczanie.setVisibility(View.GONE);

        tlo = findViewById(R.id.tlo);
        tlo.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                wysokoscEkranu = (float) tlo.getHeight();
                szerokoscEkranu = (float) tlo.getWidth();
            }
        });
    }

    private void ustawPrzyciski() {
        btn = findViewById(R.id.btn);
        btn.setVisibility(View.GONE);
        testStartBtn = findViewById(R.id.test_start);

        testStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iloscKlikniec = 0;
                String iloscKliniec =  getApplicationContext().getString(R.string.trafienia) + " " + iloscKlikniec;
                ileKlikniec.setText(iloscKliniec);
                String czasText = getApplicationContext().getString(R.string.pozostalo_sekund) + " " + iloscSekund;
                czas.setText(czasText);
                testStartBtn.setVisibility(View.GONE);
                odliczanie.setVisibility(View.VISIBLE);

                new CountDownTimer(4 * 1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        Animation a = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.powiekszanie);
                        a.reset();
                        odliczanie.clearAnimation();
                        odliczanie.startAnimation(a);
                        odliczanie.setText(String.valueOf(millisUntilFinished/1000));
                    }
                    public void onFinish() {
                        odliczanie.setVisibility(View.GONE);
                        wykonajGre();
                    }
                }.start();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setEnabled(false);
                iloscKlikniec++;
                String iloscKliniec = getApplicationContext().getString(R.string.trafienia) + " " + iloscKlikniec;
                ileKlikniec.setText(iloscKliniec);
            }
        });
    }

    private void wykonajGre() {
        new CountDownTimer(iloscSekund * 1000, 1000) { // Odliczanie czasu:
            public void onTick(long millisUntilFinished) {
                String czasText = getApplicationContext().getString(R.string.pozostalo_sekund) + " " + millisUntilFinished / 1000;
                czas.setText(czasText);
            }
            public void onFinish() {
                czas.setText("Koniec!");
                wywolajWynik();
            }
        }.start();

        new CountDownTimer(iloscSekund * 1000, 750) { // Timer przycisku. Max - 40 razy
            public void onTick(long millisUntilFinished) {
                btn.setY((float) (Math.random() * (wysokoscEkranu - btn.getHeight())));
                btn.setX((float) (Math.random() * (szerokoscEkranu - btn.getWidth())));
                btn.setVisibility(View.VISIBLE);
                btn.setEnabled(true);
            }
            public void onFinish() {
                btn.setVisibility(View.GONE);
                testStartBtn.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void wywolajWynik() {

    }

}
