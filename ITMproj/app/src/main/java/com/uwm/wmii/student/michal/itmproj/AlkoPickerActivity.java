package com.uwm.wmii.student.michal.itmproj;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.TimePicker;

import com.uwm.wmii.student.michal.itmproj.alkoninja.AlkoNinjaLauncher;
import com.uwm.wmii.student.michal.itmproj.api.dto.AlkoholeDTO;

import java.sql.Time;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class AlkoPickerActivity extends AppCompatActivity  implements NumberPicker.OnValueChangeListener {


    static Dialog numberDialog;
    private TextView beerCounter;
    private TextView wineCounter;
    private TextView vodkaCounter;
    private TextView cocktailCounter;
    private int mHour;
    private int mMinute;
    private int mMonth;
    private int mDay;
    private int mYear;
    private TextView currentDate;
    private TextView currentTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alko_picker);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);


        currentTime = (TextView) findViewById(R.id.currentTime);
        currentDate = (TextView) findViewById(R.id.currentDate);

        currentDate.setText(String.valueOf(mDay) + "." + String.valueOf(mMonth+1) + "." + String.valueOf(mYear));

        if(mMinute < 10) {
            currentTime.setText(String.valueOf(mHour) + ":0" + String.valueOf(mMinute));
        }
        else {
            currentTime.setText(String.valueOf(mHour) + ":" + String.valueOf(mMinute));
        }

        beerCounter = (TextView) findViewById(R.id.beercounter);
        ImageButton beerButton = (ImageButton) findViewById(R.id.beerbutton);

        wineCounter = (TextView) findViewById(R.id.winecounter);
        ImageButton wineButton = (ImageButton) findViewById(R.id.winebutton);

        vodkaCounter = (TextView) findViewById(R.id.vodkacounter);
        ImageButton vodkaButton = (ImageButton) findViewById(R.id.vodkabutton);

        cocktailCounter = (TextView) findViewById(R.id.cocktailcounter);
        ImageButton cocktailButton = (ImageButton) findViewById(R.id.cocktailbutton);

        Button okButton = (Button) findViewById(R.id.okbutton);
        Button anulujButton = (Button) findViewById(R.id.abulujbutton);

        currentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });


        currentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();

            }
        });
        beerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker("beer");
            }
        });

        wineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker("wine");
            }
        });

        vodkaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker("vodka");
            }
        });

        cocktailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker("cocktail");
            }
        });



        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlkoholeDTO alkoholeDTO = new AlkoholeDTO();
                alkoholeDTO.setIloscPiwo(Integer.parseInt(beerCounter.getText().toString()));
                alkoholeDTO.setIloscWodka(Integer.parseInt(vodkaCounter.getText().toString()));
                alkoholeDTO.setIloscWino(Integer.parseInt(wineCounter.getText().toString()));
                alkoholeDTO.setIloscCocktail(Integer.parseInt(cocktailCounter.getText().toString()));
                //TODO implement DateTime Parsing
                startActivity(new Intent(AlkoPickerActivity.this, ButtonGameActivity.class));

            }
        });

        anulujButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlkoPickerActivity.this, AlkoNinjaLauncher.class));

            }
        });

    }

    public void showDatePicker(){


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        currentDate.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void showTimePicker(){
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        if(minute < 10) {
                            currentTime.setText(hourOfDay + ":0" + minute);
                        }
                        else {
                            currentTime.setText(hourOfDay + ":" + minute);
                        }

                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }



    public void showNumberPicker(final String buttonName)
    {
        final Dialog numberDialog = new Dialog(AlkoPickerActivity.this);
        numberDialog.setTitle("NumberPicker");
        numberDialog.setContentView(R.layout.number_dialog);
        Button ok = (Button)numberDialog.findViewById(R.id.ok);
        Button anuluj = (Button) numberDialog.findViewById(R.id.anuluj);
        final NumberPicker np = (NumberPicker) numberDialog.findViewById(R.id.alkoPicker);
        np.setMaxValue(20); // max value 100
        np.setMinValue(0);   // min value 0
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener((NumberPicker.OnValueChangeListener) this);
        ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
            if(buttonName == "beer") {
                beerCounter.setText(String.valueOf(np.getValue())); //set the value to textview

            }
            if(buttonName == "wine") {
                wineCounter.setText(String.valueOf(np.getValue())); //set the value to textview

            }
            if(buttonName == "vodka") {
                vodkaCounter.setText(String.valueOf(np.getValue())); //set the value to textview

            }
            if(buttonName == "cocktail") {
                cocktailCounter.setText(String.valueOf(np.getValue())); //set the value to textview

            }
                    numberDialog.dismiss();


            }
        });
        anuluj.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                numberDialog.dismiss(); // dismiss the dialog
            }
        });
        numberDialog.show();

    }


    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        Log.i("value is",""+newVal);

    }
}
