package com.uwm.wmii.student.michal.itmproj.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.uwm.wmii.student.michal.itmproj.AlkoPickerActivity;
import com.uwm.wmii.student.michal.itmproj.ButtonGameActivity;
import com.uwm.wmii.student.michal.itmproj.GamesActivity;
import com.uwm.wmii.student.michal.itmproj.R;
import com.uwm.wmii.student.michal.itmproj.alkoninja.AlkoNinjaLauncher;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button homeButtonTest = (Button) view.findViewById(R.id.homeButton1);
        Button homeButtonAl = (Button) view.findViewById(R.id.homeButton2);
        Button homeButtonGames = (Button) view.findViewById(R.id.homeButton3);

        homeButtonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo: sprawdzac stan uzytkownika (czy zalogowany)
                startActivity(new Intent(getActivity(), GamesActivity.class));
            }
        });
        homeButtonAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AlkoPickerActivity.class));
                //Todo: zrobic ten widok i przypisac click , sprawdzic stan uzytkownika
            }
        });
        homeButtonGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), GamesActivity.class));
            }
        });
        return view;
    }

}
