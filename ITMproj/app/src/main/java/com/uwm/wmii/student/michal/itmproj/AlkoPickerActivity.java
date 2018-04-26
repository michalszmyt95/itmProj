package com.uwm.wmii.student.michal.itmproj;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.uwm.wmii.student.michal.itmproj.Fragments.HomeFragment;
import com.uwm.wmii.student.michal.itmproj.alkoninja.AlkoNinjaLauncher;
import com.uwm.wmii.student.michal.itmproj.api.dto.AlkoholeDTO;
import com.uwm.wmii.student.michal.itmproj.api.service.AlkoRestService;
import com.uwm.wmii.student.michal.itmproj.singletons.AppRestManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlkoPickerActivity extends AppCompatActivity   {

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
    AppRestManager appRestManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle b = getIntent().getExtras();
        final boolean fromTest = b.getBoolean("fromTest");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alko_picker);
        appRestManager = AppRestManager.getInstance(getApplicationContext());

        current = getResources().getConfiguration().locale;
        dateFormatter = new SimpleDateFormat("dd.MM.yyyy", current);
        timeFormatter = new SimpleDateFormat("HH:mm", current);
        dateAndTimeFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", current);
        today = new Date();
        time = (TextView) findViewById(R.id.time);
        date = (TextView) findViewById(R.id.date);
        time.setText(timeFormatter.format(today));
        date.setText(dateFormatter.format(today));
        disableEditText((EditText) time);
        disableEditText((EditText) date);

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
        beerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker("beer");
            }
        });

        wineCounter = (TextView) findViewById(R.id.winecounter);
        wineButton = (ImageButton) findViewById(R.id.winebutton);
        wineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker("wine");
            }
        });

        vodkaCounter = (TextView) findViewById(R.id.vodkacounter);
        vodkaButton = (ImageButton) findViewById(R.id.vodkabutton);
        vodkaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker("vodka");
            }
        });

        cocktailCounter = (TextView) findViewById(R.id.cocktailcounter);
        cocktailButton = (ImageButton) findViewById(R.id.cocktailbutton);
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

                Integer iloscPiwa = Integer.parseInt(beerCounter.getText().toString());
                Integer iloscWodki = Integer.parseInt(vodkaCounter.getText().toString());
                Integer iloscWina = Integer.parseInt(wineCounter.getText().toString());
                Integer iloscDrinki = Integer.parseInt(cocktailCounter.getText().toString());

                AlkoholeDTO alkoholeDTO = new AlkoholeDTO();
                alkoholeDTO.setIloscPiwo(Integer.parseInt(beerCounter.getText().toString()));
                alkoholeDTO.setIloscWodka(Integer.parseInt(vodkaCounter.getText().toString()));
                alkoholeDTO.setIloscWino(Integer.parseInt(wineCounter.getText().toString()));
                alkoholeDTO.setIloscCocktail(Integer.parseInt(cocktailCounter.getText().toString()));
                try {

                    String  wybranaData = String.valueOf(date.getText()) + " " + String.valueOf(time.getText());
                    dataICzasWypicia =  dateAndTimeFormatter.parse(wybranaData);
                    alkoholeDTO.setDataCzasWypicia(dataICzasWypicia);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                zapiszAlkohole(alkoholeDTO);

                if(fromTest == true) {
                    rozpocznijTest();
                }
                else {
                    startActivity(new Intent(AlkoPickerActivity.this, MainActivity.class));
                }
            }
        });

    }

    private int iloscGier = 2;
    public int wylosujNumerGry() {
        return Double.valueOf(Math.random() * iloscGier).intValue();
    }
    public void rozpocznijTest() {
        switch(wylosujNumerGry()) {
            case 0:
                startActivity(new Intent(AlkoPickerActivity.this, ButtonGameActivity.class));
                break;
            case 1:
                startActivity(new Intent(AlkoPickerActivity.this,  AlkoNinjaLauncher.class));
                break;
        }
    }

    private void zapiszAlkohole(AlkoholeDTO alkohole) {
        AlkoRestService alkoService = appRestManager.dodajAlkohole();

        alkoService.dodajAlkohole(alkohole).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("RES:", response.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setCursorVisible(false);
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
        np.setMaxValue(20);
        np.setMinValue(0);
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
    public void onBackPressed() {
        startActivity(new Intent(AlkoPickerActivity.this, MainActivity.class));
    }


}
