package com.uwm.wmii.student.michal.itmproj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.uwm.wmii.student.michal.itmproj.model.DaneLogowania;
import com.uwm.wmii.student.michal.itmproj.model.enumy.MetodaLogowania;
import com.uwm.wmii.student.michal.itmproj.singletons.AppLoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;
    private ProgressDialog mDialog;
    private AppLoginManager appLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // AppLoginManager pośredniczy w dostępie do danych użytkownika w shared preferences
        this.appLoginManager = AppLoginManager.getInstance(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        //Jeśli użytkownik już zalogowany:
        if(appLoginManager.czyUzytkownikZalogowany()) {
            // Po prostu przechodzimy do ekranu głównego:
            przejdzDoMainActivity();
            return;
        }

        ustawLogowanieGoogle();
        ustawLogowaniePrzezFacebooka();
    }

    private void przejdzDoMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private void ustawLogowanieGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        appLoginManager.setGoogleApiClient(mGoogleApiClient);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoogleApiClient != null) {
                    mGoogleApiClient.disconnect();
                }
                int i = v.getId();
                if (i == R.id.sign_in_button) {
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if(result.isSuccess()) {
                GoogleSignInAccount user = result.getSignInAccount();

                try {
                    AppLoginManager appLoginManager = AppLoginManager.getInstance(getApplicationContext());
                    DaneLogowania daneLogowania = new DaneLogowania();
                    daneLogowania.setImie(user.getDisplayName());
                    daneLogowania.setEmail(user.getEmail());
                    daneLogowania.setNazwisko(user.getFamilyName());
                    daneLogowania.setMetodaLogowania(MetodaLogowania.Google);
                    daneLogowania.setToken(user.getIdToken());
                    daneLogowania.setUserID(user.getId());
                    if (user.getPhotoUrl() != null) {
                        daneLogowania.setZdjecieProfiloweUrl(user.getPhotoUrl().toString());
                    }
                    appLoginManager.zapiszDaneLogowaniaDoSharedPreferences(daneLogowania);
                    this.przejdzDoMainActivity();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void ustawLogowaniePrzezFacebooka() {
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Wczytywanie...");
                mDialog.show();

                final DaneLogowania daneLogowania = new DaneLogowania();
                daneLogowania.setToken(loginResult.getAccessToken().getToken());
                daneLogowania.setMetodaLogowania(MetodaLogowania.Facebook);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject userDTO, GraphResponse response) {
                        mDialog.dismiss();
                        Log.d("response", response.toString());
                        //Zachowujemy dane z fejsa do sharedPreferences:
                        try {
                            daneLogowania.setImie(userDTO.getString("first_name"));
                            daneLogowania.setNazwisko(userDTO.getString("last_name"));
                            daneLogowania.setEmail(userDTO.getString("email"));
                            daneLogowania.setZdjecieProfiloweUrl("https://graph.facebook.com/" + userDTO.getString("id") + "/picture?width=250&height=250");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        appLoginManager.zapiszDaneLogowaniaDoSharedPreferences(daneLogowania);
                        //Przechodzimy do aktywności pozwalającej na wpisywanie danych użytkownika:
                        przejdzDoMainActivity();
                    }
                });
                //Request Graph API
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync(); // <-- ta metoda tak naprawdę wykonuje metodę asynchroniczną, tzw. callback opisany wyżej funkcją lambda (userDTO, response) -> {...}
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }
}
