package com.example.healthbuddy.covidstatistics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.healthbuddy.R;

public class Coviddata extends AppCompatActivity {
    Button covidstatistics;
    ImageView flags;
    Spinner spinner;
    Button stat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.covid_data);

        covidstatistics=findViewById(R.id.stat);
        covidstatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Coviddata.this,CovidStatistics.class);
                startActivity(intent);
            }
        });
        flags = findViewById(R.id.flag);
        stat = findViewById(R.id.stat);


    }
}