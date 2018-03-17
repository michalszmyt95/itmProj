package com.uwm.wmii.student.michal.itmproj;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static final String PREFERENCES_NAME = "userPreferences";

    private CallbackManager callbackManager;
    private ProgressDialog mDialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // W shared preferences beda przechowywane dane zalogowanego użytkownika:
        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);

        callbackManager = CallbackManager.Factory.create();
        ustawLogowaniePrzezFacebooka();
    }

    @Override // Tworzenie menu:
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String ktoryElement = "";

        switch (item.getItemId()) {

            case R.id.item1:
                ktoryElement = "pierwszy";
                break;
            case R.id.item2:
                ktoryElement = "drugi";
                break;
            case R.id.item3:
                ktoryElement = "trzeci";
                break;
            default:
                ktoryElement = "żaden";

        }

        Toast.makeText(getApplicationContext(), "Element: " + ktoryElement,
                Toast.LENGTH_LONG).show();

        return true;
    }

    private void ustawLogowaniePrzezFacebooka() {
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));

        //Jeśli użytkownik już zalogowany:
        if(AccessToken.getCurrentAccessToken() != null) {
            // Po prostu przechodzimy do danych użytkownika:
            startActivity(new Intent(MainActivity.this, UserDataActivity.class));
            return;
        }

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mDialog = new ProgressDialog(MainActivity.this);
                mDialog.setMessage("Wczytywanie...");
                mDialog.show();

                String accessToken = loginResult.getAccessToken().getToken();

                SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
                preferencesEditor.putString("loginMethod", "facebook");
                preferencesEditor.putString("accessToken", accessToken);
                preferencesEditor.apply();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject userDTO, GraphResponse response) {
                        mDialog.dismiss();
                        Log.d("response", response.toString());
                        //Zachowujemy dane z fejsa do sharedPreferences:
                        persistUserDataFromFacebook(userDTO);
                        //Przechodzimy do aktywności pozwalającej na wpisywanie danych użytkownika:
                        startActivity(new Intent(MainActivity.this, UserDataActivity.class));
                    }
                });

                //Request Graph API
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,first_name,birthday,friends");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void persistUserDataFromFacebook(JSONObject userDTO) {
        try {
            URL profilePictureURL = new URL("https://graph.facebook.com/" + userDTO.getString("id") + "/picture?width=250&height=250");
            //Picasso.get().load(profilePictureURL.toString()).into(imgAvatar);
            //txtEmail.setText(object.getString("email"));
            //txtBirthday.setText(object.getString("birthday"));
            //String friendsTxt = "Friends " + object.getJSONObject("friends").getJSONObject("summary").getString("total_count");
            //txtFriends.setText(friendsTxt);
            Log.d("FIRST NAME: ", userDTO.getString("first_name"));
            SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
            preferencesEditor.putString("email", userDTO.getString("email"));
            preferencesEditor.putString("birthday", userDTO.getString("birthday"));
            preferencesEditor.putString("firstName", userDTO.getString("first_name"));
            preferencesEditor.putString("id", userDTO.getString("id"));
            preferencesEditor.putString("profilePictureURL", profilePictureURL.toString());
            preferencesEditor.commit();




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
