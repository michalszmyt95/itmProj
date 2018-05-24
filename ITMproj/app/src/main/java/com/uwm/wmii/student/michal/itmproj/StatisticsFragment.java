/*
 __          __        _       _____          _____
 \ \        / /       | |     |_   _|        |  __ \
  \ \  /\  / /__  _ __| | __    | |  _ __    | |__) | __ ___   __ _ _ __ ___  ___ ___
   \ \/  \/ / _ \| '__| |/ /    | | | '_ \   |  ___/ '__/ _ \ / _` | '__/ _ \/ __/ __|
    \  /\  / (_) | |  |   <    _| |_| | | |  | |   | | | (_) | (_| | | |  __/\__ \__ \
     \/  \/ \___/|_|  |_|\_\  |_____|_| |_|  |_|   |_|  \___/ \__, |_|  \___||___/___/
                                                               __/ |
                                                              |___/
*/
package com.uwm.wmii.student.michal.itmproj;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;

public class StatisticsFragment extends AppCompatActivity {



    TextView tv_score;
    Button b_dodaj_1, b_zakoncz;

    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_statistics);

        tv_score = (TextView)findViewById(R.id.tv_score);
        b_dodaj_1 = (Button)findViewById(R.id.b_dodaj_1);
        b_zakoncz = (Button)findViewById(R.id.b_zakoncz);

        tv_score.setText("Punkty: " + score);

        b_dodaj_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                score++;
                tv_score.setText("Punkty: " + score);
            }
        });

        b_zakoncz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }



}
