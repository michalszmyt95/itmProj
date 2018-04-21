package com.uwm.wmii.student.michal.itmproj;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.uwm.wmii.student.michal.itmproj.Fragments.ProfileFragment;
import com.uwm.wmii.student.michal.itmproj.Fragments.StatisticsFragment;
import com.uwm.wmii.student.michal.itmproj.Fragments.HomeFragment;
import com.uwm.wmii.student.michal.itmproj.Fragments.PartyFragment;
import com.uwm.wmii.student.michal.itmproj.alkoninja.AlkoNinjaLauncher;
import com.uwm.wmii.student.michal.itmproj.singletons.AppLoginManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppLoginManager appLoginManager;
    private Dialog addAlkoholDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.appLoginManager = AppLoginManager.getInstance(getApplicationContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //domyslny fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flMain,new HomeFragment());
        ft.commit();
        navigationView.setCheckedItem(R.id.nav_home);
        addAlkoholDialog = new Dialog(MainActivity.this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            this.moveTaskToBack(true);
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            appLoginManager.wyloguj();
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain,new HomeFragment());
            ft.commit();
        } else if (id == R.id.nav_profile) {
            if (appLoginManager.czyUzytkownikZalogowany()) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new ProfileFragment());
                ft.commit();
            } else {
                appLoginManager.przejdzDoEkranuLogowaniaZKomunikatem();
            }
        } else if (id == R.id.nav_party) {
            if (appLoginManager.czyUzytkownikZalogowany()) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new PartyFragment());
                ft.commit();
            } else {
                appLoginManager.przejdzDoEkranuLogowaniaZKomunikatem();
            }
        } else if (id == R.id.nav_statistics) {
            if (appLoginManager.czyUzytkownikZalogowany()) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new StatisticsFragment());
                ft.commit();
            } else {
                appLoginManager.przejdzDoEkranuLogowaniaZKomunikatem();
            }
        } else if (id == R.id.nav_settings) {
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void showAddAlkoholDialog() {

        addAlkoholDialog.setTitle("AddAlkohol");
        addAlkoholDialog.setContentView(R.layout.add_alkohol_dialog);
        Button ok = (Button) addAlkoholDialog.findViewById(R.id.ok);
        Button anuluj = (Button) addAlkoholDialog.findViewById(R.id.anuluj);

      ok.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i = new Intent(MainActivity.this, AlkoPickerActivity.class);
              i.putExtra("fromTest", true);

              startActivity(i);

              addAlkoholDialog.dismiss();
          }
      });

      anuluj.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              addAlkoholDialog.dismiss();
              rozpocznijTest();
          }
      });
      addAlkoholDialog.show();

    }


    private int iloscGier = 2;
    public int wylosujNumerGry() {
        return Double.valueOf(Math.random() * iloscGier).intValue();
    }
    public void rozpocznijTest() {
        switch(wylosujNumerGry()) {
            case 0:
                startActivity(new Intent(MainActivity.this, ButtonGameActivity.class));
                break;
            case 1:
                startActivity(new Intent(MainActivity.this,  AlkoNinjaLauncher.class));
                break;
        }
    }


}
