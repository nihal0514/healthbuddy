package com.example.healthbuddy.chats.chatfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthbuddy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorsFragment extends Fragment {
    private List<alldoctors> allusersList = new ArrayList<>();
    private allDoctorsAdapter alldoctorsAdapter1;
    private allDoctorsAdapter.RecyclerViewClickListener listener;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctors, container, false);

        recyclerView = view.findViewById(R.id.fragment_users_recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        alldoctorsAdapter1 = new allDoctorsAdapter(allusersList, getContext(), listener);
        recyclerView.setAdapter(alldoctorsAdapter1);

        readUsers();
        return view;
    }

    private void readUsers() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference alluserRef = database.getReference("Doctors");
        alluserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allusersList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    allusersList.add(new alldoctors(
                            dataSnapshot.child("profileimage").getValue().toString(),
                            dataSnapshot.child("fullname").getValue().toString(),
                            dataSnapshot.getKey(),
                            dataSnapshot.child("id").getValue().toString()
                    ));
                }
                alldoctorsAdapter1.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
