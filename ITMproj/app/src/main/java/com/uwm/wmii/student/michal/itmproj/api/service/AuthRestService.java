package com.uwm.wmii.student.michal.itmproj.api.service;

import com.uwm.wmii.student.michal.itmproj.api.dto.UserDTO;
import com.uwm.wmii.student.michal.itmproj.api.dto.WynikOdswiezeniaTokenaDTO;
import com.uwm.wmii.student.michal.itmproj.api.dto.WynikRejestracjiDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Michał on 04.04.2018.
 */

public interface AuthRestService {

    @POST("/auth/rejestruj-lub-zaloguj")
    Call<WynikRejestracjiDTO> zalogujLubZarejestruj(@Body UserDTO user);

    @GET("/auth/odswiez-token")
    Call<WynikOdswiezeniaTokenaDTO> odswiezToken();

    @POST("/auth/hello")
    Call<String> helloWorld(@Body String imie);

}
