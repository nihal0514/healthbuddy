package com.example.healthbuddy.healthtips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthbuddy.R;

public class HealthTipActivity extends AppCompatActivity {
    private EditText edtName, edtInstructions, edtFrequency, edtDate;
    private Button btnInsert, btnGellAllStudnets;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tip);
        dbHelper = new DbHelper(this);

        final boolean isEditData = getIntent().getBooleanExtra("isEditData",false);


        edtName = findViewById(R.id.edt_name);
        edtInstructions = findViewById(R.id.edt_instructions);
        edtFrequency = findViewById(R.id.edt_frequency);
        edtDate = findViewById(R.id.edt_date);
        btnInsert = findViewById(R.id.btn_insert);
        btnGellAllStudnets = findViewById(R.id.btn_get_all_students);


        if(isEditData){
            int stdId = getIntent().getIntExtra("stdId",0);

            Health health = dbHelper.getHealths(stdId+"");
            edtName.setText(health.getName());
            edtInstructions.setText(health.getInstructions());
            edtFrequency.setText(health.getFrequency());
            edtDate.setText(health.getDate());
            btnInsert.setText("Update");
        }
        btnGellAllStudnets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HealthTipActivity.this, ViewHealthTipActivity.class));
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edtName.getText().toString();
                String instructions = edtInstructions.getText().toString();
                String frequency = edtFrequency.getText().toString();
                String date = edtDate.getText().toString();
                Health health = new Health(name,instructions,frequency,date);

                 if(isEditData){
                                    // update Data
                     health.setId(getIntent().getIntExtra("stdId",0));
                     if(dbHelper.updateStudent(health)==1){
                         clearData();
                         Toast.makeText(HealthTipActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                     }else{
                         Toast.makeText(HealthTipActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                     }
                 }else{
                                    // insert data
                     if(dbHelper.inserthealth(health)!=-1){
                         clearData();
                         Toast.makeText(HealthTipActivity.this, "Insertion Successful", Toast.LENGTH_SHORT).show();
                     }else{
                         Toast.makeText(HealthTipActivity.this, "Insertion Failed", Toast.LENGTH_SHORT).show();
                     }
                 }

            }
        });
    }

    private void clearData() {
        edtName.setText("");
        edtDate.setText("");
        edtFrequency.setText("");
        edtInstructions.setText("");

    }
};
