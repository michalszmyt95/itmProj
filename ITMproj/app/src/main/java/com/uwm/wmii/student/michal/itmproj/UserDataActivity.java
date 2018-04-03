package com.uwm.wmii.student.michal.itmproj;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.uwm.wmii.student.michal.itmproj.api.dto.UserDTO;
import com.uwm.wmii.student.michal.itmproj.api.dto.WynikOperacjiDTO;
import com.uwm.wmii.student.michal.itmproj.api.service.UserRestService;
import com.uwm.wmii.student.michal.itmproj.singletons.AppLoginManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDataActivity extends AppCompatActivity {

    private Button przyciskAkceptacji;

    private TextInputEditText wiekInput;
    private TextInputEditText wzrostInput;
    private TextInputEditText wagaInput;

    private AppLoginManager appLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.appLoginManager = AppLoginManager.getInstance(getApplicationContext());
        setContentView(R.layout.activity_user_data);

        ustawAktywnosc();
    }

    private void ustawAktywnosc() {
        ustawInputy();
        ustawPrzyciskAkceptacji();
        TextView header = findViewById(R.id.header_user_data);
        String text = "Witaj ";
        text += appLoginManager.pobierzDaneLogowania().getImie() + "!";
        header.setText(text);
    }

    private void ustawInputy() {
        wiekInput = findViewById(R.id.wiek_input);
        wzrostInput = findViewById(R.id.wzrost_input);
        wagaInput = findViewById(R.id.waga_input);
    }

    private void ustawPrzyciskAkceptacji() {
        przyciskAkceptacji = findViewById(R.id.akceptuj_dane_btn);
        przyciskAkceptacji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (walidujWprowadzoneDane()) {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setEmail(appLoginManager.pobierzDaneLogowania().getEmail());
                    userDTO.setId("123124");
                    userDTO.setSocialId(appLoginManager.pobierzDaneLogowania().getUserID());
                    userDTO.setToken(appLoginManager.pobierzDaneLogowania().getToken());
                    userDTO.setMetodaLogowania(appLoginManager.pobierzDaneLogowania().getMetodaLogowania().toString());
                    userDTO.setWiek(Integer.parseInt(wiekInput.getText().toString()));
                    userDTO.setWzrost(Float.parseFloat(wzrostInput.getText().toString()));
                    userDTO.setWaga(Float.parseFloat(wagaInput.getText().toString()));
                    Log.d("USER ID:", userDTO.getSocialId());
                    dodajUzytkownikaDoBazyDanych(userDTO);
                }
            }
        });
    }

    private Boolean walidujWprowadzoneDane() {
        Log.d("WALIDUJE DANE!", "");
        Toast komunikat;

        if (wiekInput.getText().toString().equals("")) {
            komunikat = Toast.makeText(getApplicationContext(), "Należy podać wiek", Toast.LENGTH_LONG);
            komunikat.setGravity(Gravity.TOP, 0, 230);
            komunikat.show();
            return false;
        } else if (wzrostInput.getText().toString().equals("")) {
            komunikat = Toast.makeText(getApplicationContext(), "Należy podać wzrost", Toast.LENGTH_LONG);
            komunikat.setGravity(Gravity.TOP, 0, 230);
            komunikat.show();
            return false;
        } else if (wagaInput.getText().toString().equals("")) {
            komunikat = Toast.makeText(getApplicationContext(), "Należy podać wagę", Toast.LENGTH_LONG);
            komunikat.setGravity(Gravity.TOP, 0, 230);
            komunikat.show();
            return false;
        }
        return true;
    }

    /*
    ** Przykładowa metoda rest serwisu z androida. Korzysta z zadeklarowanego interfejsu UserRestService.
     */
    private void dodajUzytkownikaDoBazyDanych(UserDTO userDTO) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getApplicationContext().getString(R.string.restApiUrl))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        UserRestService service = retrofit.create(UserRestService.class);

        Call<WynikOperacjiDTO> call = service.dodajUzytkownika(userDTO);

        call.enqueue(new Callback<WynikOperacjiDTO>() { // Akcje które dzieją się przy zwróceniu wartości z serwera:
            @Override
            public void onResponse(Call<WynikOperacjiDTO> call, Response<WynikOperacjiDTO> response) {
                Toast.makeText(UserDataActivity.this, "UDALO SIE!", Toast.LENGTH_LONG).show();
                if (response.isSuccessful()) {
                    try {
                        Log.d("USER ID: ", response.body().getId());
                    } catch (NullPointerException e) {
                        Log.d("BRAK USER ID: ", "BRAK USER ID.");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<WynikOperacjiDTO> call, Throwable t) {
                Toast.makeText(UserDataActivity.this, "NIE UDALO SIE :(", Toast.LENGTH_LONG).show();
                Log.d("BLAD RESTA: ", t.toString());
            }
        });
    }
}
