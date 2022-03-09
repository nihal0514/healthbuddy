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

import java.util.ArrayList;
import java.util.List;

public class DoctorList extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView doctor_list_tv;
    Button btn1;
    Button btn2;
    private DoctorListAdapter.RecyclerViewClickListener listener;
    EditText searchdoctor;
    List<DoctorsList> doctorsLists = new ArrayList<>();
    DoctorListAdapter doctorListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        recyclerView = findViewById(R.id.recyv);
        searchdoctor=findViewById(R.id.searchdoctor);
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


        doctorListAdapter = new DoctorListAdapter(doctorsLists, this, listener);
        recyclerView.setAdapter(doctorListAdapter);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Doctors");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                doctorsLists.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    doctorsLists.add(new DoctorsList(dataSnapshot1.child("fullname").getValue().toString(),
                            dataSnapshot1.child("about").getValue().toString(),
                            dataSnapshot1.getKey(),
                            dataSnapshot1.child("profileimage").getValue().toString()));
                }
                doctorListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
    void filter(String text){
        List<DoctorsList> temp = new ArrayList();
        for(DoctorsList d:doctorsLists){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getName().toLowerCase().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        doctorListAdapter.updateList1(temp);
    }

}




