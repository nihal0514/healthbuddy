package com.example.healthbuddy.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.healthbuddy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class shop extends AppCompatActivity {

    ImageButton backbutton;
    ImageButton imageshoppingshop;
    RecyclerView recyclerView;
    private MedicinesAdapter medicinesAdapter;
    private MedicinesAdapter.RecyclerViewClickListener listener;
    ArrayList<MedicinesList> medicinesLists = new ArrayList<>();
    EditText searchtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        imageshoppingshop= findViewById(R.id.image_shopping_shop);
        backbutton= findViewById(R.id.image_btn_arrow_shop);

        imageshoppingshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(shop.this, CartActivity.class);
                startActivity(intent);
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        searchtext = findViewById(R.id.editshop);

        searchtext.addTextChangedListener(new TextWatcher() {
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


        recyclerView = findViewById(R.id.rvprogram1);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        // List<MedicinesList> medicinesLists = new ArrayList<>();
        medicinesAdapter = new MedicinesAdapter(medicinesLists, this, listener);
        recyclerView.setAdapter(medicinesAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Medicines");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                medicinesLists.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    medicinesLists.add(new MedicinesList(
                            dataSnapshot.child("MediImage").getValue().toString(),
                            dataSnapshot.child("MediName").getValue().toString(),
                            (long) dataSnapshot.child("ActualPrice").getValue(),
                            (long) dataSnapshot.child("FinalPrice").getValue(),
                            (long) dataSnapshot.child("pils").getValue(),
                            dataSnapshot.getKey()));
                }
                medicinesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

    void filter(String text) {
        List<MedicinesList> temp = new ArrayList();
        for (MedicinesList d : medicinesLists) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getMediName().toLowerCase().contains(text)) {
                temp.add(d);
            }
        }
        //update recyclerview
        medicinesAdapter.updateList(temp);
    }
}
