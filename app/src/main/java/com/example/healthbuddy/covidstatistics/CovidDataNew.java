package com.example.healthbuddy.covidstatistics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.healthbuddy.R;

public class CovidDataNew extends AppCompatActivity {
    Button covidNext_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.covid_data_new);

        covidNext_btn= findViewById(R.id.covid_next_btn);
        covidNext_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CovidDataNew.this,Coviddata.class));
            }
        });
    }
}