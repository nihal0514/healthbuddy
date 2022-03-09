package com.example.healthbuddy.covidstatistics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthbuddy.Api.JsonPlaceHolderApi;
import com.example.healthbuddy.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Global extends AppCompatActivity {
    List<globalclass> globalclasses;
    RecyclerView recyclerView;
    GlobalClassAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);
        recyclerView=findViewById(R.id.covidstatsrecy);
        globalclasses=new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter=new GlobalClassAdapter(globalclasses,this);
        recyclerView.setAdapter(recyclerAdapter);

        TextView countrynametv=findViewById(R.id.country_name);
        TextView countrycase=findViewById(R.id.country_cases);
        ImageView flag=findViewById(R.id.flag);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://corona.lmao.ninja/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi=retrofit.create(JsonPlaceHolderApi.class);

        Call<List<globalclass>>call=jsonPlaceHolderApi.getcountrylists();
        call.enqueue(new Callback<List<globalclass>>() {
            @Override
            public void onResponse(Call<List<globalclass>> call, Response<List<globalclass>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Global.this, ""+response.code(), Toast.LENGTH_SHORT).show();

                }
                //globalclasses=response.body();
                globalclasses.clear();
                globalclasses.addAll(response.body());
              //  recyclerAdapter.setCovidCase(globalclasses);
                recyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<globalclass>> call, Throwable t) {

            }
        });
    }
}