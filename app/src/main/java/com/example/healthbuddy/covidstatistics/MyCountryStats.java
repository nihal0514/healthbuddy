package com.example.healthbuddy.covidstatistics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.healthbuddy.Api.JsonPlaceHolderApi;
import com.example.healthbuddy.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyCountryStats extends AppCompatActivity {
    List<Statewise>statewiseList;
    RecyclerView recyclerView;
    mycountrystatsadapter mycountrystatsadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_country_stats);

        recyclerView=findViewById(R.id.mycountryrecy);
        statewiseList=new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mycountrystatsadapter=new mycountrystatsadapter(statewiseList,this);
        recyclerView.setAdapter(mycountrystatsadapter);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.rootnet.in/covid19-in/unofficial/covid19india.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi=retrofit.create(JsonPlaceHolderApi.class);
        Call<mycountrystat>call=jsonPlaceHolderApi.getmycountrystats();
        call.enqueue(new Callback<mycountrystat>() {
            @Override
            public void onResponse(Call<mycountrystat> call, Response<mycountrystat> response) {
                statewiseList.clear();
                statewiseList.addAll(response.body().getData().getStatewise());
                mycountrystatsadapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<mycountrystat> call, Throwable t) {

            }
        });



    }
}