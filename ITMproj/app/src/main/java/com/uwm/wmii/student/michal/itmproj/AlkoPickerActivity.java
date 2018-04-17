package com.uwm.wmii.student.michal.itmproj;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
    private ImageButton beerButton;
    private ImageButton wineButton;
    private ImageButton vodkaButton;
    private ImageButton cocktailButton;
    private TextView time;
    private TextView date;
    private SimpleDateFormat dateFormatter;
    private Date today;
    private SimpleDateFormat timeFormatter;
    private Locale current;
    private SimpleDateFormat dateAndTimeFormatter;
    private Button zatwierdzAlko;
    private Date chosenDate;
    private Date chosenTime;
    private Date dataICzasWypicia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alko_picker);

        current = getResources().getConfiguration().locale;
        dateFormatter = new SimpleDateFormat("dd.MM.yyyy", current);
        timeFormatter = new SimpleDateFormat("HH:mm", current);
        dateAndTimeFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", current);
        today = new Date();
        time = (TextView) findViewById(R.id.time);
        date = (TextView) findViewById(R.id.date);
        time.setText(timeFormatter.format(today));
        date.setText(dateFormatter.format(today));

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showTimePicker();
             }
        });

        beerCounter = (TextView) findViewById(R.id.beercounter);
        beerButton = (ImageButton) findViewById(R.id.beerbutton);
        wineCounter = (TextView) findViewById(R.id.winecounter);
        wineButton = (ImageButton) findViewById(R.id.winebutton);
        vodkaCounter = (TextView) findViewById(R.id.vodkacounter);
        vodkaButton = (ImageButton) findViewById(R.id.vodkabutton);
        cocktailCounter = (TextView) findViewById(R.id.cocktailcounter);
        cocktailButton = (ImageButton) findViewById(R.id.cocktailbutton);
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

        zatwierdzAlko = (Button) findViewById(R.id.zatwierdzalko);

        zatwierdzAlko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlkoholeDTO alkoholeDTO = new AlkoholeDTO();
                alkoholeDTO.setIloscPiwo(Integer.parseInt(beerCounter.getText().toString()));
                alkoholeDTO.setIloscWodka(Integer.parseInt(vodkaCounter.getText().toString()));
                alkoholeDTO.setIloscWino(Integer.parseInt(wineCounter.getText().toString()));
                alkoholeDTO.setIloscCocktail(Integer.parseInt(cocktailCounter.getText().toString()));
                try {
                    dataICzasWypicia =
                            dateAndTimeFormatter.parse(dateAndTimeFormatter.format(String.valueOf(date.getText()) + String.valueOf(time.getText())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                alkoholeDTO.setDataCzasWypicia(dataICzasWypicia);
                startActivity(new Intent(AlkoPickerActivity.this, ButtonGameActivity.class));
            }
        });

    }



    public void showDatePicker(){
        final Dialog dateDialog = new Dialog(AlkoPickerActivity.this);
        dateDialog.setTitle("DatePicker");
        dateDialog.setContentView(R.layout.date_picker);
        ImageButton okdp = (ImageButton) dateDialog.findViewById(R.id.okdp);
        ImageButton anulujdp = (ImageButton) dateDialog.findViewById(R.id.anulujdp);
        final DatePicker dp = (DatePicker) dateDialog.findViewById(R.id.datePicker);
        dp.setMaxDate(new Date().getTime());

        okdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                   chosenDate = dateFormatter.parse(String.valueOf(dp.getDayOfMonth()) + "." + String.valueOf(dp.getMonth() + 1) + "." + String.valueOf(dp.getYear()));
                   date.setText(dateFormatter.format(chosenDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dateDialog.dismiss();
            }

        });

        anulujdp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dateDialog.dismiss(); // dismiss the dialog
            }
        });
        dateDialog.show();




    }

  public void showTimePicker(){
      final Dialog timeDialog = new Dialog(AlkoPickerActivity.this);
      timeDialog.setTitle("TimePicker");
      timeDialog.setContentView(R.layout.time_picker);
      ImageButton oktp = (ImageButton) timeDialog.findViewById(R.id.oktp);
      ImageButton anulujtp = (ImageButton) timeDialog.findViewById(R.id.anulujtp);
      final TimePicker tp = (TimePicker) timeDialog.findViewById(R.id.timePicker);
      tp.setIs24HourView(true);



      oktp.setOnClickListener(new View.OnClickListener() {

          @RequiresApi(api = Build.VERSION_CODES.M)
          @Override
          public void onClick(View v) {
              try {
                  chosenTime = timeFormatter.parse(String.valueOf(tp.getHour()) + ":"+ String.valueOf(tp.getMinute()));
                  time.setText(timeFormatter.format(chosenTime));
              } catch (ParseException e) {
                  e.printStackTrace();
              }
              timeDialog.dismiss();
          }

      });

      anulujtp.setOnClickListener(new View.OnClickListener()
      {
          @Override
          public void onClick(View v) {
              timeDialog.dismiss();
          }
      });
      timeDialog.show();


  }



    public void showNumberPicker(final String buttonName)
    {
        final Dialog numberDialog = new Dialog(AlkoPickerActivity.this);
        numberDialog.setTitle("NumberPicker");
        numberDialog.setContentView(R.layout.number_dialog);

        ImageButton oknp = numberDialog.findViewById(R.id.oknp);
        ImageButton anulujnp = numberDialog.findViewById(R.id.anulujnp);
        TextView title = numberDialog.findViewById(R.id.title);

        switch (buttonName) {

            case "beer" : title.setText("Browarki" );
                break;
            case "vodka" : title.setText("Wódeczki" );
                break;
            case "wine" : title.setText("Winka" );
                break;
            case "cocktail" : title.setText("Drineczki" );
                break;
        }


        final NumberPicker np = numberDialog.findViewById(R.id.alkoPicker);
        np.setMaxValue(20); // max value 100
        np.setMinValue(0);   // min value 0
        np.setValue(Integer.parseInt(vodkaCounter.getText().toString()));
        np.setWrapSelectorWheel(false);

        oknp.setOnClickListener(new View.OnClickListener()
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

         anulujnp.setOnClickListener(new View.OnClickListener()
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
