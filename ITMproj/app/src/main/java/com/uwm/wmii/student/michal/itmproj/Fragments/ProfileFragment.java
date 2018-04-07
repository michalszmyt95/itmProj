package com.uwm.wmii.student.michal.itmproj.Fragments;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

import com.uwm.wmii.student.michal.itmproj.R;
import com.uwm.wmii.student.michal.itmproj.UserDataActivity;
import com.uwm.wmii.student.michal.itmproj.api.dto.UserDTO;
import com.uwm.wmii.student.michal.itmproj.api.service.UserRestService;
import com.uwm.wmii.student.michal.itmproj.singletons.AppLoginManager;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private TextInputEditText wiekInput;
    private TextInputEditText wzrostInput;
    private TextInputEditText wagaInput;
  //  private AppLoginManager appLoginManager = AppLoginManager.getInstance(getActivity().getApplicationContext());
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button acceptButton =  view.findViewById(R.id.akceptuj_dane_btn);
        final TextInputEditText wiekInput = view.findViewById(R.id.wiek_input);
        final TextInputEditText wzrostInput = view.findViewById(R.id.wzrost_input);
        final TextInputEditText wagaInput = view.findViewById(R.id.waga_input);
        //Todo sprawdzic czemu wywala apke
        /*
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (walidujWprowadzoneDane()) {
                    UserDTO userDTO = new UserDTO();
                    userDTO.set_id("123124");
                    userDTO.setWiek(Integer.parseInt(wiekInput.getText().toString()));
                    userDTO.setWzrost(Float.parseFloat(wzrostInput.getText().toString()));
                    userDTO.setWaga(Float.parseFloat(wagaInput.getText().toString()));
                    Log.d("WPROWADZONE DANE", userDTO.toString());
                    dodajUzytkownikaDoBazyDanych(userDTO);
                }
            }

        });
        */
        return view;
    }
/*
    private Boolean walidujWprowadzoneDane() {
        Log.d("WALIDUJE DANE!", "");
        Toast komunikat;

        if (wiekInput.getText().toString().equals("")) {
            komunikat = Toast.makeText(getActivity().getApplicationContext(), "Należy podać wiek", Toast.LENGTH_LONG);
            komunikat.setGravity(Gravity.TOP, 0, 230);
            komunikat.show();
            return false;
        } else if (wzrostInput.getText().toString().equals("")) {
            komunikat = Toast.makeText(getActivity().getApplicationContext(), "Należy podać wzrost", Toast.LENGTH_LONG);
            komunikat.setGravity(Gravity.TOP, 0, 230);
            komunikat.show();
            return false;
        } else if (wagaInput.getText().toString().equals("")) {
            komunikat = Toast.makeText(getActivity().getApplicationContext(), "Należy podać wagę", Toast.LENGTH_LONG);
            komunikat.setGravity(Gravity.TOP, 0, 230);
            komunikat.show();
            return false;
        }
        return true;
    }
    private void dodajUzytkownikaDoBazyDanych(UserDTO userDTO) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getActivity().getApplicationContext().getString(R.string.restApiUrl))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        UserRestService service = retrofit.create(UserRestService.class);

        Call<UserDTO> call = service.dodajUzytkownika(userDTO);

        call.enqueue(new Callback<UserDTO>() { // Akcje które dzieją się przy zwróceniu wartości z serwera:
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                Toast.makeText(getActivity(), "UDALO SIE!", Toast.LENGTH_LONG).show();
                if (response.isSuccessful()) {
                    try {
                        Log.d("USER ID: ", response.body().get_id());
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Toast.makeText(getActivity(), "NIE UDALO SIE :(", Toast.LENGTH_LONG).show();
                Log.d("BLAD RESTA: ", t.toString());
            }
        });
    }

    */
}
