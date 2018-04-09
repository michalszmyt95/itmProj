package com.uwm.wmii.student.michal.itmproj;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.uwm.wmii.student.michal.itmproj.api.dto.ProfilDTO;
import com.uwm.wmii.student.michal.itmproj.api.dto.UserDTO;
import com.uwm.wmii.student.michal.itmproj.api.dto.WynikOperacjiDTO;
import com.uwm.wmii.student.michal.itmproj.api.interceptor.InterceptorJWT;
import com.uwm.wmii.student.michal.itmproj.api.service.UserRestService;
import com.uwm.wmii.student.michal.itmproj.singletons.AppLoginManager;
import com.uwm.wmii.student.michal.itmproj.singletons.AppRestManager;

import okhttp3.OkHttpClient;
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
    private AppRestManager appRestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.appLoginManager = AppLoginManager.getInstance(getApplicationContext());
        this.appRestManager = AppRestManager.getInstance(getApplicationContext());
        setContentView(R.layout.activity_user_data);
       // ustawAktywnosc();
    }
    /*
    private void ustawAktywnosc() {
        ustawInputy();
        ustawPrzyciskAkceptacji();
        TextView header = findViewById(R.id.header_user_data);
        String text = "Witaj ";
        text += appLoginManager.pobierzDaneLogowania().getImie() + "!";
        header.setText(text);
    }
    */
}
