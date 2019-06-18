package com.example.creativejsontask.network;


import com.example.creativejsontask.model.Data;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DataRequest {

    @GET("users/square/repos")
    Call<ArrayList<Data>> getJson(
            @Query("page") int page
    );
}
