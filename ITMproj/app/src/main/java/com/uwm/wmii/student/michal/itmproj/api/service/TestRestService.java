package com.uwm.wmii.student.michal.itmproj.api.service;

import com.uwm.wmii.student.michal.itmproj.api.dto.UserDTO;
import com.uwm.wmii.student.michal.itmproj.api.dto.WynikOperacjiDTO;
import com.uwm.wmii.student.michal.itmproj.api.dto.WynikTestuDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Michał on 18.03.2018.
 */

public interface TestRestService {

    @POST("/tests/buttonTest")
    Call<String> dodajWynikButtonTestu(@Body WynikTestuDTO wynikTestu);

    @POST("/tests/ninjaTest")
    Call<String> dodajWynikNinjaTestu(@Body WynikTestuDTO wynikTestu);

}
