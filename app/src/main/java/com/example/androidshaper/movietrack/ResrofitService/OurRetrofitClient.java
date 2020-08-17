package com.example.androidshaper.movietrack.ResrofitService;

import com.example.androidshaper.movietrack.RetrofitModal.MainObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OurRetrofitClient {

    @GET("popular")
    Call<MainObject> getObject(@Query("api_key") String api_key);
}
