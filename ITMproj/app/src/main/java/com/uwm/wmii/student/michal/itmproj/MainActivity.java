package com.uwm.wmii.student.michal.itmproj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.uwm.wmii.student.michal.itmproj.alkoninja.AlkoNinjaLauncher;
import com.uwm.wmii.student.michal.itmproj.singletons.AppLoginManager;

public class MainActivity extends AppCompatActivity {

    private AppLoginManager appLoginManager;

    Button testyBtn;
    Button testyBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.appLoginManager = AppLoginManager.getInstance(getApplicationContext());

        ustawPrzyciski();
    }

    @Override //Nadpisuję back button, żeby nie cofać się spowrotem do ekranu logowania.
    public void onBackPressed() {

    }

    @Override // Tworzenie menu:
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // ustawienie logout buttonu
        switch (item.getItemId()) {

            case R.id.logout:
                appLoginManager.wyloguj();
                super.onBackPressed(); // powrót do poprzedniej aktywności.
                break;
        }

        return true;
    }

    private void ustawPrzyciski() {
       testyBtn = findViewById(R.id.testyBtn);
        testyBtn.setOnClickListener(new View.OnClickListener() {
            @Override //TODO: Tutaj powinno być przejście do widoku wyboru testów. W tej chwili mamy jeden, więc przechodzimy od razu do niego.
            public void onClick(View v) { // Przejście do testu.
                startActivity(new Intent(MainActivity.this, ButtonGameActivity.class));
            }
        });
       testyBtn2 = findViewById(R.id.testyBtn2);
        testyBtn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AlkoNinjaLauncher.class));
            }
        });
    }


}
