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

import com.uwm.wmii.student.michal.itmproj.api.dto.WynikTestuDTO;
import com.uwm.wmii.student.michal.itmproj.api.service.TestRestService;
import com.uwm.wmii.student.michal.itmproj.singletons.AppRestManager;
import com.uwm.wmii.student.michal.itmproj.utils.FunkcjePomocnicze;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    TextView wynikText;
    Integer iloscSekund = 30;
    Double okres = 0.81; // wzgledem sekund
    Integer maksIloscKlikniec;

    AppRestManager appRestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_game);
        appRestManager = AppRestManager.getInstance(getApplicationContext());
        ustawZmienne();
        ustawDaneEkranu();
        ustawPrzyciski();
    }

    private void ustawZmienne() {
        maksIloscKlikniec = ((Double)(iloscSekund / okres)).intValue();
    }

    private void ustawDaneEkranu() {
        ileKlikniec = findViewById(R.id.ile_klikniec);

        czas = findViewById(R.id.czas);
        odliczanie = findViewById(R.id.odliczanie_do_startu);
        odliczanie.setVisibility(View.GONE);

        wynikText = findViewById(R.id.wynik_text);
        wynikText.setVisibility(View.GONE);

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
                wynikText.setVisibility(View.GONE);
                iloscKlikniec = 0;
                String kliknieciaString =  getApplicationContext().getString(R.string.trafienia) + " " + iloscKlikniec;
                ileKlikniec.setText(kliknieciaString);
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
                if (view.isEnabled()) {
                    view.setEnabled(false);
                    doliczKlikniecie();
                }
            }
        });
    }

    private void wykonajGre() {
        //Wstępne ustawienie położenia przycisku:
        btn.setY((float) (Math.random() * (wysokoscEkranu - btn.getHeight())));
        btn.setX((float) (Math.random() * (szerokoscEkranu - btn.getWidth())));

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
        new CountDownTimer(iloscSekund * 1000, ((Double)(1000 * okres)).intValue()) { // Timer przycisku.
            public void onTick(long millisUntilFinished) {
                btn.setY((float) (Math.random() * (wysokoscEkranu - btn.getHeight())));
                btn.setX((float) (Math.random() * (szerokoscEkranu - btn.getWidth())));
                btn.setVisibility(View.VISIBLE);
                btn.setEnabled(true);
            }
            public void onFinish() {
                btn.setVisibility(View.GONE);
            }
        }.start();
    }

    private void doliczKlikniecie() {
        iloscKlikniec++;
        String iloscKliniec = getApplicationContext()
                .getString(R.string.trafienia) + " " + iloscKlikniec + "/" + maksIloscKlikniec;
        ileKlikniec.setText(iloscKliniec);
    }

    private void wywolajWynik() {
        // zapis danych do bazy:
        TestRestService testService = appRestManager.podajTestService();
        double wynikWProcentach = (iloscKlikniec * 100) / maksIloscKlikniec;



        WynikTestuDTO wynikTestu = new WynikTestuDTO(wynikWProcentach, new Date(), true);
        testService.dodajWynikButtonTestu(wynikTestu).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("RES:", response.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        testStartBtn.setVisibility(View.VISIBLE);
        wynikText.setText(FunkcjePomocnicze.truncateDecimal(wynikWProcentach, 0).toString() + "% trafień!");
        wynikText.setVisibility(View.VISIBLE);
    }

}
