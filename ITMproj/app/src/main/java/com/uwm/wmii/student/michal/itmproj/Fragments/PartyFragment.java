package com.uwm.wmii.student.michal.itmproj.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.ViewGroup;
import com.uwm.wmii.student.michal.itmproj.R;
import com.uwm.wmii.student.michal.itmproj.ButtonGameActivity;
import com.uwm.wmii.student.michal.itmproj.MainActivity;
import com.uwm.wmii.student.michal.itmproj.R;
import com.uwm.wmii.student.michal.itmproj.alkoninja.AlkoNinjaLauncher;
import com.uwm.wmii.student.michal.itmproj.Fragments.TestsFragment;
import com.uwm.wmii.student.michal.itmproj.singletons.AppLoginManager;
import android.view.View;

public class PartyFragment extends Fragment {

    Button testyBtn;
    Button testyBtn2;

    public PartyFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_party, container, false);
       // testyBtn = (Button) getView().findViewById(R.id.testyBtn);
        //testyBtn2 = (Button)getView().findViewById(R.id.testyBtn2);

        //return View
    }

}
