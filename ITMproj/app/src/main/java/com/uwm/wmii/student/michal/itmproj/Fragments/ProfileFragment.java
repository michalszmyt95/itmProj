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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.uwm.wmii.student.michal.itmproj.R;
import com.uwm.wmii.student.michal.itmproj.api.dto.ProfilDTO;
import com.uwm.wmii.student.michal.itmproj.api.dto.WynikOperacjiDTO;
import com.uwm.wmii.student.michal.itmproj.api.service.UserRestService;
import com.uwm.wmii.student.michal.itmproj.singletons.AppLoginManager;
import com.uwm.wmii.student.michal.itmproj.singletons.AppRestManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextInputEditText wiekInput;
    private TextInputEditText wzrostInput;
    private TextInputEditText wagaInput;
    private Button przyciskAkceptacji;
    private AppLoginManager appLoginManager;
    private AppRestManager appRestManager;
    private RadioGroup plecRadioGroup;
    private View view;
    private String wybranaPlec;

    public ProfileFragment() { } // Potrzebny pusty publiczny konstruktor.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        ustawPrzyciskAkceptacji(view);

        this.appRestManager = AppRestManager.getInstance(getContext());
        this.appLoginManager = AppLoginManager.getInstance(getContext());

        UserRestService userService = appRestManager.podajUserService();
        userService.podajProfil().enqueue(new Callback<ProfilDTO>() {
            @Override
            public void onResponse(Call<ProfilDTO> call, Response<ProfilDTO> response) {
                ustawInputy(response.body());
            }

            @Override
            public void onFailure(Call<ProfilDTO> call, Throwable t) {
                ustawInputy(null);
            }
        });

        return view;
    }

    private void ustawInputy(ProfilDTO daneProfilu) {
        wiekInput = view.findViewById(R.id.wiek_input);
        wzrostInput = view.findViewById(R.id.wzrost_input);
        wagaInput = view.findViewById(R.id.waga_input);
        plecRadioGroup = view.findViewById(R.id.plec_radio_group);

        plecRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.man_radio:
                        wybranaPlec = "M";
                        break;
                    case R.id.woman_radio:
                        wybranaPlec = "K";
                        break;
                }
            }
        });

        if (daneProfilu != null) {
            if (daneProfilu.getWiek() != null) {
                wiekInput.setText(String.valueOf(daneProfilu.getWiek()));
            }
            if (daneProfilu.getWzrost() != null) {
                wzrostInput.setText(String.valueOf(daneProfilu.getWzrost()));
            }
            if (daneProfilu.getWaga() != null) {
                wagaInput.setText(String.valueOf(daneProfilu.getWaga()));
            }
            if (daneProfilu.getPlec() != null) {
                switch (daneProfilu.getPlec()) {
                    case "M":
                        plecRadioGroup.check(R.id.man_radio);
                        break;
                    case "K":
                        plecRadioGroup.check(R.id.woman_radio);
                        break;
                }
            }
        }
    }

    private void ustawPrzyciskAkceptacji(View view) {
        przyciskAkceptacji = view.findViewById(R.id.akceptuj_dane_btn);
        przyciskAkceptacji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (walidujWprowadzoneDane()) {
                    ProfilDTO profilDTO = new ProfilDTO();
                    profilDTO.setWiek(Integer.parseInt(wiekInput.getText().toString()));
                    profilDTO.setWzrost(Float.parseFloat(wzrostInput.getText().toString()));
                    profilDTO.setWaga(Float.parseFloat(wagaInput.getText().toString()));
                    profilDTO.setPlec(wybranaPlec);
                    aktualizujProfilWBazieDanych(profilDTO);
                }
            }
        });
    }

    private Boolean walidujWprowadzoneDane() {
        Log.d("WALIDUJE DANE!", "");
        Toast komunikat;

        if (wiekInput.getText().toString().equals("")) {
            komunikat = Toast.makeText(getContext(), "Należy podać wiek", Toast.LENGTH_LONG);
            komunikat.setGravity(Gravity.TOP, 0, 230);
            komunikat.show();
            return false;
        } else if (wzrostInput.getText().toString().equals("")) {
            komunikat = Toast.makeText(getContext(), "Należy podać wzrost", Toast.LENGTH_LONG);
            komunikat.setGravity(Gravity.TOP, 0, 230);
            komunikat.show();
            return false;
        } else if (wagaInput.getText().toString().equals("")) {
            komunikat = Toast.makeText(getContext(), "Należy podać wagę", Toast.LENGTH_LONG);
            komunikat.setGravity(Gravity.TOP, 0, 230);
            komunikat.show();
            return false;
        } else if (wybranaPlec == null) {
            komunikat = Toast.makeText(getContext(), "Należy określić płeć", Toast.LENGTH_LONG);
            komunikat.setGravity(Gravity.TOP, 0, 230);
            komunikat.show();
            return false;
        }
        return true;
    }

    /*
** Przykładowa metoda rest serwisu z androida. Korzysta z zadeklarowanego interfejsu UserRestService.
 */
    private void aktualizujProfilWBazieDanych(ProfilDTO profilDTO) {
        UserRestService userService = appRestManager.podajUserService();
        userService.aktualizujProfil(profilDTO).enqueue(new Callback<WynikOperacjiDTO>() {
            // Akcje które dzieją się przy  wartości z serwera:
            @Override
            public void onResponse(Call<WynikOperacjiDTO> call, Response<WynikOperacjiDTO> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.d("ID OPERACJI: ", response.body().getId());
                        Log.d("CZY SUKCES: ", response.body().getWynik().toString());
                    } catch (NullPointerException e) {
                        Log.d("BRAK ID: ", "BRAK ID OPERACJI.");
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<WynikOperacjiDTO> call, Throwable t) {
                Toast.makeText(getContext(), "Błąd serwera", Toast.LENGTH_LONG).show();
                Log.d("BLAD RESTA: ", t.toString());
            }
        });
    }


}
