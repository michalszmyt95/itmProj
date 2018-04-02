package com.uwm.wmii.student.michal.itmproj.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.uwm.wmii.student.michal.itmproj.ButtonGameActivity;
import com.uwm.wmii.student.michal.itmproj.MainActivity;
import com.uwm.wmii.student.michal.itmproj.R;
import com.uwm.wmii.student.michal.itmproj.alkoninja.AlkoNinjaLauncher;
import com.uwm.wmii.student.michal.itmproj.singletons.AppLoginManager;

public class TestsFragment extends Fragment {

    Button testyBtn;
    Button testyBtn2;
    public TestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tests, container, false);
        Button testyBtn = (Button) view.findViewById(R.id.testyBtn);
        Button testyBtn2 = (Button)view.findViewById(R.id.testyBtn2);
        testyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ButtonGameActivity.class));
            }
        });
        testyBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AlkoNinjaLauncher.class));
            }
        });

        return view;
    }

}
