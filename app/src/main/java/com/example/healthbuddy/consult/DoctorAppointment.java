package com.example.healthbuddy.consult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.healthbuddy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DoctorAppointment extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView doctor_list_tv;
    Button btn1;
    Button btn2;
    private DoctorListAdapter.RecyclerViewClickListener listener;
    EditText searchdoctor;
    List<DoctorAppint> doctorAppintList = new ArrayList<>();
    DoctorAppointAdapter doctorAppointAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment);
        recyclerView = findViewById(R.id.recyclerDoctorAppointement);
        searchdoctor=findViewById(R.id.searchdoctor1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        searchdoctor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });

        // setOnClickListener();


        doctorAppointAdapter = new DoctorAppointAdapter(doctorAppintList, this, listener);
        recyclerView.setAdapter(doctorAppointAdapter);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Doctors");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                doctorAppintList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    doctorAppintList.add(new DoctorAppint(dataSnapshot1.child("fullname").getValue().toString(),
                            dataSnapshot1.child("about").getValue().toString(),
                            dataSnapshot1.getKey()
                    ));


                }
                doctorAppointAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
    void filter(String text){
        List<DoctorAppint> temp = new ArrayList();
        for(DoctorAppint d:doctorAppintList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getName().toLowerCase().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        doctorAppointAdapter.updateList1(temp);
    }
    }
