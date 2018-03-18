package com.uwm.wmii.student.michal.itmproj.api.service;

import com.uwm.wmii.student.michal.itmproj.api.dto.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Michał on 18.03.2018.
 */

public interface UserRestService {

    // TODO: potrzebna zmiana, by nie trzeba było zawsze dodawać domyślnego @Header(). Sprawę załatwi dodanie interceptora.

    @Headers({
            "Accept: application/json",
    })
    @POST("/uzytkownik")
    Call<UserDTO> dodajUzytkownika(@Body UserDTO user);

    @Headers({
            "Accept: application/json",
    })
    @GET("/uzytkownik/{id}")
    Call<UserDTO> podajUzytkownika(@Path("id") Integer _id);

}
