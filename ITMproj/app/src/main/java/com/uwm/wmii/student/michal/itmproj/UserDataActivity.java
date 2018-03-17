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

import com.uwm.wmii.student.michal.itmproj.dto.user.UserDTO;

public class UserDataActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    private Button przyciskAkceptacji;

    private TextInputEditText wiekInput;
    private TextInputEditText wzrostInput;
    private TextInputEditText wagaInput;

    private UserDTO userDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        ustawAktywnosc();
    }

    private void ustawAktywnosc() {
        ustawInputy();
        ustawPrzyciskAkceptacji();
        TextView header = findViewById(R.id.header_user_data);
        String text = "Witaj ";
        sharedPreferences = getSharedPreferences(MainActivity.PREFERENCES_NAME, Activity.MODE_PRIVATE);
        text += sharedPreferences.getString("firstName", "nieznajomy") + "!";
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
                    userDTO = new UserDTO();
                    userDTO.setWiek(Integer.parseInt(wiekInput.getText().toString()));
                    userDTO.setWzrost(Float.parseFloat(wzrostInput.getText().toString()));
                    userDTO.setWaga(Float.parseFloat(wagaInput.getText().toString()));
                    Log.d("WPROWADZONE DANE", userDTO.toString());
                    startActivity(new Intent(UserDataActivity.this, ButtonGameActivity.class));
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
}
