package com.example.healthbuddy.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.healthbuddy.consult.Appointmentadapter;
import com.example.healthbuddy.consult.DoctorAppointment;
import com.example.healthbuddy.R;
import com.example.healthbuddy.consult.appointmentlist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Appointment extends AppCompatActivity {
    TextView myappointment;
    RecyclerView recyclerView;
    private Appointmentadapter appointmentAdapter;
    private Appointmentadapter.RecyclerViewClickListener listener;
    List<appointmentlist> myappointList=new ArrayList<>();

    FirebaseFirestore db;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);


        myappointment=findViewById(R.id.myappointments_tv);
        myappointment.setVisibility(View.GONE);
        myappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Appointment.this, DoctorAppointment.class);
                startActivity(intent);
            }
        });
        recyclerView=findViewById(R.id.myappointments);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        appointmentAdapter=new Appointmentadapter(myappointList,this,listener);
        recyclerView.setAdapter(appointmentAdapter);

        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();

        db.collection("DoctorAppointment").document(mAuth.getCurrentUser().getUid())
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        appointmentlist appointment1=documentSnapshot.toObject(appointmentlist.class);
                        myappointList.add(appointment1);
                        appointmentAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
        if(myappointList.isEmpty()){
            myappointment.setVisibility(View.VISIBLE);
        }


    }
}