package com.example.healthbuddy.Api;

import com.example.healthbuddy.covidstatistics.globalclass;
import com.example.healthbuddy.covidstatistics.mycountrystat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("countries")
    Call<List<globalclass>> getcountrylists();
    @GET("statewise")
    Call<mycountrystat> getmycountrystats();


}
