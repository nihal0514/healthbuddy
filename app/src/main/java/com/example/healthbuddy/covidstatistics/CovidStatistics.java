package com.example.healthbuddy.covidstatistics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.healthbuddy.R;

public class CovidStatistics extends AppCompatActivity {
    Button global;
    Button mycountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_statistics);
        global=findViewById(R.id.button4);
        mycountry=findViewById(R.id.button3);
        global.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CovidStatistics.this, Global.class);
                startActivity(intent);

            }
        });
        mycountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(CovidStatistics.this, MyCountryStats.class);
                startActivity(intent1);
            }
        });
    }
}