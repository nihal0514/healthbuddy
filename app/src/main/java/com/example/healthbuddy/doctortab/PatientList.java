package com.example.healthbuddy.doctortab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.healthbuddy.R;
import com.example.healthbuddy.consult.Appointmentadapter;
import com.example.healthbuddy.consult.DoctorAppointment;
import com.example.healthbuddy.consult.PatientAppointment;
import com.example.healthbuddy.consult.appointmentlist;
import com.example.healthbuddy.menu.Appointment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PatientList extends AppCompatActivity {
    RecyclerView recyclerView;
    private patientAdapter patientsadap;
    List<patientmodel> patientmod = new ArrayList<>();

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        recyclerView = findViewById(R.id.patientlist_recyv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        patientsadap = new patientAdapter(patientmod, this);
        recyclerView.setAdapter(patientsadap);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        db.collection("PatientList").document(mAuth.getCurrentUser().getUid())
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        patientmodel appointment1 = documentSnapshot.toObject(patientmodel.class);
                        patientmod.add(appointment1);
                        patientsadap.notifyDataSetChanged();
                    }
                }

            }
        });


    }
}