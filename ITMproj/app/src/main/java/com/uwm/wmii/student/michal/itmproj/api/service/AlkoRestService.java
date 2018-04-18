package com.uwm.wmii.student.michal.itmproj.api.service;

import com.uwm.wmii.student.michal.itmproj.api.dto.AlkoholeDTO;
import com.uwm.wmii.student.michal.itmproj.api.dto.WynikTestuDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Michał on 18.03.2018.
 */

public interface AlkoRestService {

    @POST("/alkohole/buttonAlkohole")
    Call<String> dodajAlkohole(@Body AlkoholeDTO alkohole);

}
