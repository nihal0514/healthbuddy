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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private allDoctorsAdapter adapter;
    private List<alldoctors> allusersList;
    FirebaseUser fUser;
    DatabaseReference reference;
    //  private List<Chatlist>usersList;
    private List<String> usersList;
    private allDoctorsAdapter.RecyclerViewClickListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.fragments_chats_recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        usersList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");


        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //  usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chats chats = snapshot.getValue(Chats.class);
                    if (chats.getSender().equals(fUser.getUid())) {
                        usersList.add(chats.getReceiver());

                    }
                    if (chats.getReceiver().equals(fUser.getUid())) {
                        usersList.add(chats.getSender());

                    }
                }
                readChats();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void readChats() {
        allusersList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Doctors");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datsSnapshot) {
                allusersList.clear();
                for (DataSnapshot snapshot : datsSnapshot.getChildren()) {
                    alldoctors allusers1 = snapshot.getValue(alldoctors.class);
                    for (String id : usersList) {
                        if (allusers1.getId().equals(id)) {
                            if (allusersList.size() != 0) {
                                for (alldoctors allusers2 : allusersList) {
                                    if (!allusers1.getId().equals(allusers2.getId())) {
                                        allusersList.add(allusers1);
                                    }
                                }
                            } else {
                                allusersList.add(allusers1);
                            }
                        }
                    }
                }
                adapter = new allDoctorsAdapter(allusersList, getContext(), listener);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}