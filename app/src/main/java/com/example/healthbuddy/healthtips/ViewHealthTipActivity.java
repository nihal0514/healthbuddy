package com.example.healthbuddy.healthtips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.healthbuddy.R;

import java.util.List;

public class ViewHealthTipActivity extends AppCompatActivity implements HealthAdapter.IDeleteHealths {
    private RecyclerView recyclerView;
    private HealthAdapter healthAdapter;
    private  DbHelper dbHelper;
    private List<Health> healthList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_health_tip);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        dbHelper = new DbHelper(this);

        healthList = dbHelper.getHealth();

        healthAdapter = new HealthAdapter(healthList,this);

        recyclerView.setAdapter(healthAdapter);
    }

    @Override
    public void delete(int position, int studentId) {
        if(dbHelper.deleteStudent(studentId)==1){
            Toast.makeText(this, "Deletion is successful", Toast.LENGTH_SHORT).show();
            healthList.remove(position);
            healthAdapter.notifyItemRemoved(position);
        }
    }
}