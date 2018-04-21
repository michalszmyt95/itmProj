package com.uwm.wmii.student.michal.itmproj;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.uwm.wmii.student.michal.itmproj.AlkoPickerActivity;


import com.uwm.wmii.student.michal.itmproj.ButtonGameActivity;
import com.uwm.wmii.student.michal.itmproj.R;
import com.uwm.wmii.student.michal.itmproj.alkoninja.AlkoNinjaLauncher;
public class GamesActivity extends AppCompatActivity {

    Button testyBtn;
    Button testyBtn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        Button testyBtn = (Button) findViewById(R.id.games_button_test);
        Button testyBtn2 = (Button) findViewById(R.id.games_button_ninja);


        testyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GamesActivity.this ,ButtonGameActivity.class));
            }
        });
        testyBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GamesActivity.this,AlkoNinjaLauncher.class));
            }
        });
    }

}