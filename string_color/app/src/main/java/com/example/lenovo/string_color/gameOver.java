package com.example.lenovo.string_color;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;

public class gameOver extends AppCompatActivity {
    private BarChart chart;
    private TextView percentage, opis;
    float barWidth = 0.3f;
    float barSpace = 0f;
    float groupSpace = 0.4f;
    static int liczba_ruchow, liczba_trafien;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateUpTo(new Intent(getBaseContext(), startActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game_over);

        liczba_trafien = getIntent().getExtras().getInt("liczba trafien");
        liczba_ruchow = getIntent().getExtras().getInt("liczba ruchow");

        chart = (BarChart) findViewById(R.id.chart1);
        percentage = (TextView) findViewById(R.id.procenty);
        opis = (TextView) findViewById(R.id.opis);

        percentage.setText("Procentowa ilość trafień wynosi " + getPercentage() + "%");
        opis.setText(setDescription());


        chart.setDescription(null);
        chart.setPinchZoom(false);
        chart.setScaleEnabled(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        int groupCount = 1;

        ArrayList yVals1 = new ArrayList();
        ArrayList yVals2 = new ArrayList();

        yVals1.add(new BarEntry(1, liczba_trafien));
        yVals2.add(new BarEntry(1, liczba_ruchow));


        BarDataSet set1, set2;
        set1 = new BarDataSet(yVals1, "Liczba Trafień");
        set1.setColor(Color.RED);
        set2 = new BarDataSet(yVals2, "Liczba Ruchów");
        set2.setColor(Color.BLUE);
        BarData data = new BarData(set1, set2);
        data.setValueFormatter(new LargeValueFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);
        chart.getBarData().setBarWidth(barWidth);
        chart.getXAxis().setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chart.groupBars(0, groupSpace, barSpace);
        chart.getData().setHighlightEnabled(false);
        chart.invalidate();
        chart.getLegend().setTextColor(Color.WHITE);


        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(20f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);
        l.setTextSize(15f);

        //X-axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(6);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//Y-axis
        chart.getAxisRight().setEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(35f);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMinimum(0f);
    }

    public int getPercentage() {
        double a = liczba_ruchow;
        double b = liczba_trafien;
        int percentage = (int) ((b / a) * 100);
        return percentage;
    }

    public String setDescription() {
        String napis = null;
        int proc = getPercentage();
        if (proc >= 70 && proc <= 100) {
            napis = "Super, pij dalej!";
        } else if (proc >= 40 && proc < 70) {
            napis = "Nooo, kolego.. Przydałaby się mała przerwa";
        } else if (proc > 0 && proc < 40) {
            napis = "Weź ty już odstaw przyjacielu tą butelkę :|";
        }
        else if (proc == 0){
            napis = "chyba nie zagrałeś :(";
        }
        return napis;
    }


}

