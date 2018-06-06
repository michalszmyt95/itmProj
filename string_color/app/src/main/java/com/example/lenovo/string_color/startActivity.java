package com.example.lenovo.string_color;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class startActivity extends AppCompatActivity {

    int odliczanie = 3;
    boolean stopTimer = false;
    TextView licznik, textView_opis, textView_przyklad, textView_przyklad_odp;
    Button button_rozpocznij;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);

        // getSupportActionBar().hide();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_start);


        licznik = findViewById(R.id.licznik);
        textView_opis = findViewById(R.id.textView_opis);
        textView_przyklad = findViewById(R.id.textView_przyklad);
        textView_przyklad_odp = findViewById(R.id.textView_przyklad_odp);
        button_rozpocznij = findViewById(R.id.startButton);

        textView_opis.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        licznik.setVisibility(View.INVISIBLE);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_wiki:
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                startActivity(intent);
                return true;
            case R.id.o_nas:
                Intent intentp = new Intent(getApplicationContext(), Autorzy.class);
                startActivity(intentp);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void RunCounter(View view) {

        button_rozpocznij.setVisibility(View.INVISIBLE);
        textView_opis.setVisibility(View.INVISIBLE);
        textView_przyklad.setVisibility(View.INVISIBLE);
        textView_przyklad_odp.setVisibility(View.INVISIBLE);
        licznik.setVisibility(View.VISIBLE);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (odliczanie == 0) {
                    licznik.setText("Zaczynamy!");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    stopTimer = true;
                    Intent mIntent = new Intent(getApplicationContext(), MainActivity.class);
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mIntent);
                }
                if (stopTimer == false) {
                    licznik.setText(String.valueOf(odliczanie));
                    odliczanie--;
                    handler.postDelayed(this, 1000);
                }
            }
        });
    }
}
