package com.uwm.wmii.student.michal.itmproj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class SettingsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_settings);

        Button theme1 = findViewById(R.id.themeButton1);
        Button theme2 = findViewById(R.id.themeButton2);
        Button theme3 = findViewById(R.id.themeButton3);
        theme1.setOnClickListener(myHandler);
        theme2.setOnClickListener(myHandler);
        theme3.setOnClickListener(myHandler);


    }

    View.OnClickListener myHandler = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.themeButton1:
                    Utils.changeToTheme(SettingsActivity.this, Utils.THEME_DEFAULT);
                    break;
                case R.id.themeButton2:
                    Utils.changeToTheme(SettingsActivity.this, Utils.THEME_WHITE);
                    break;
                case R.id.themeButton3:
                    Utils.changeToTheme(SettingsActivity.this, Utils.THEME_BLUE);
                    break;
            }
        }
    };
}
