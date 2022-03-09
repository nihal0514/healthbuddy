package com.example.healthbuddy.labtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.healthbuddy.R;
import com.example.healthbuddy.covidstatistics.GlobalData;
import com.example.healthbuddy.scrollview.ScrollView1;
import com.example.healthbuddy.scrollview.ScrollView1Adapter;
import com.example.healthbuddy.shop.MedicinesList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LabTestDisplay extends AppCompatActivity {

    LabTestSVAdapter labTestSVAdapter;
    List<LabtestSV> labtestSVList= new ArrayList<>();
    RecyclerView recyclerView;
    DatabaseReference labtestsv1ref;

    LabTestSV2Adapter labtestsv2adapter;
    List<LabtestSV2> labtestsv2list= new ArrayList<>();
    RecyclerView recyclerView2;
    DatabaseReference labtestsv2ref;
    EditText searchtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_display);

        recyclerView2 = findViewById(R.id.labtest_sv2_recyv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(linearLayoutManager);
        labtestsv2adapter = new LabTestSV2Adapter(labtestsv2list,this);
        recyclerView2.setAdapter(labtestsv2adapter);

        searchtext = findViewById(R.id.search_lab);
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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        labtestsv2ref = database.getReference("LabTestSV2");

        labtestsv2ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                labtestsv2list.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    if(snapshot.exists()){
                        labtestsv2list.add(new LabtestSV2(
                                snapshot1.child("image").getValue().toString(),
                                snapshot1.child("name").getValue().toString(),
                                snapshot1.child("totaltests").getValue().toString(),
                                snapshot.getKey()));
                    }
                }

                labtestsv2adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = findViewById(R.id.labtest_sv1_recyv);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager1);
        labTestSVAdapter = new LabTestSVAdapter(this, labtestSVList);
        recyclerView.setAdapter(labTestSVAdapter);

        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        labtestsv1ref = database1.getReference("LabtestSV");

        labtestsv1ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                labtestSVList.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    if(snapshot.exists()){
                        labtestSVList.add(new LabtestSV(
                                snapshot1.child("image").getValue().toString(),
                                snapshot1.child("name").getValue().toString(),
                                snapshot.getKey()));
                    }
                }

                labTestSVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void filter(String text) {
        List<LabtestSV> temp = new ArrayList();
        for (LabtestSV d :labtestSVList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getName().toLowerCase().contains(text)) {
                temp.add(d);
            }
        }
        //update recyclerview
        labTestSVAdapter.updateList(temp);
    }
}