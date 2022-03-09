package com.example.healthbuddy.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthbuddy.R;
import com.example.healthbuddy.chats.chatfragment.MessageActivity;
import com.example.healthbuddy.consult.consultactivity;
import com.example.healthbuddy.covidstatistics.GlobalData;
import com.example.healthbuddy.doctortab.PatientList;
import com.example.healthbuddy.labtest.LabTestDisplay;
import com.example.healthbuddy.menu.Appointment;
import com.example.healthbuddy.menu.FAQS;
import com.example.healthbuddy.menu.TestBookings;
import com.example.healthbuddy.scrollview.ScrollView1;
import com.example.healthbuddy.scrollview.ScrollView1Adapter;
import com.example.healthbuddy.scrollview.ScrollView2;
import com.example.healthbuddy.scrollview.ScrollView2Adapter;
import com.example.healthbuddy.shop.CartActivity;
import com.example.healthbuddy.shop.MedicinesActivity;
import com.example.healthbuddy.signin.LoginActivity;
import com.example.healthbuddy.signin.SetupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, PostsRef, DoctorsRef, Scroll1Ref;
    private FirebaseUser currentUser;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView NavProfileUserName;
    private CircleImageView NavProfileImage;
    private Toolbar toolbar;

    RecyclerView recyclerView;
    ScrollView1Adapter scrollView1Adapter;
    ArrayList<ScrollView1> scrollView1List = new ArrayList<>();
    private ScrollView1Adapter.RecyclerViewClickListener listener;

    RecyclerView recyclerView1;
    ScrollView2Adapter scrollView2Adapter;
    ArrayList<ScrollView2> scrollView2List = new ArrayList<>();

    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        recyclerView = findViewById(R.id.scrollview1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        scrollView1Adapter = new ScrollView1Adapter(this, scrollView1List, listener);
        recyclerView.setAdapter(scrollView1Adapter);

        recyclerView1 = findViewById(R.id.scrollview2);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(linearLayoutManager1);
        scrollView2Adapter = new ScrollView2Adapter(scrollView2List, this);
        recyclerView1.setAdapter(scrollView2Adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference scrollview1ref = database.getReference("ScrollView1");

        DatabaseReference scrollview2ref = database.getReference("ScrollView2");

        if (GlobalData.scrollView1List != null && !GlobalData.scrollView1List.isEmpty()) {
            scrollView1List.clear();
            scrollView1List.addAll(GlobalData.scrollView1List);
            scrollView1Adapter.notifyDataSetChanged();
        } else {
            scrollview1ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    scrollView1List.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.exists()) {
                            scrollView1List.add(new ScrollView1(
                                    dataSnapshot.child("name").getValue().toString(),
                                    dataSnapshot.child("scrollimage").getValue().toString(),
                                    dataSnapshot.getKey()));
                        }


                    }
                    GlobalData.scrollView1List.clear();
                    GlobalData.scrollView1List.addAll(scrollView1List);
                    scrollView1Adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {


                }
            });

        }
        scrollview2ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                scrollView2List.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.exists()) {
                        scrollView2List.add(new ScrollView2(
                                dataSnapshot.child("ProductImage").getValue().toString(),
                                dataSnapshot.child("ProductName").getValue().toString(),
                                (long) dataSnapshot.child("FinalPrice").getValue(),
                                (long) dataSnapshot.child("Pils").getValue(),
                                (long) dataSnapshot.child("Price").getValue(),
                                dataSnapshot.getKey()));
                    }


                }
                GlobalData.scrollView2List.clear();
                GlobalData.scrollView2List.addAll(scrollView2List);
                scrollView2Adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {


            }
        });

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        currentUser = mAuth.getCurrentUser();
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        DoctorsRef = FirebaseDatabase.getInstance().getReference().child("Doctors");


        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawable_layout);
        toolbar = findViewById(R.id.main_page_toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        View navView = navigationView.inflateHeaderView(R.layout.navigation_layout);
        NavProfileImage = navView.findViewById(R.id.nav_profile_image);
        NavProfileUserName = navView.findViewById(R.id.nav_user_full_name);
        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String fullname = snapshot.child("fullname").getValue().toString();
                    NavProfileUserName.setText(fullname);
                }
                if (snapshot.hasChild("profileimage")) {
                    String image = snapshot.child("profileimage").getValue().toString();
                    Picasso.get().load(image).placeholder(R.drawable.profile).into(NavProfileImage);

                } else {
                    Toast.makeText(MainActivity.this, "Profile name do not exists...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {


            }
        });
        DoctorsRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String fullname = snapshot.child("fullname").getValue().toString();
                    NavProfileUserName.setText(fullname);
                }
                if (snapshot.hasChild("profileimage")) {
                    String image = snapshot.child("profileimage").getValue().toString();
                    Picasso.get().load(image).placeholder(R.drawable.profile).into(NavProfileImage);

                } else {
                    Toast.makeText(MainActivity.this, "Profile name do not exists...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {


            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                userMenuSelector(item);
                return false;
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //      Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.nav_chats:
                            Intent intent1 = new Intent(MainActivity.this, MessageActivity.class);
                            startActivity(intent1);
                            break;
                    }

                    return false;
                }
            };

    private void sendUsertoLoginActivity() {
        Intent loginintent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginintent);
    }

    private void userMenuSelector(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.appointments:
                Intent intent = new Intent(MainActivity.this, Appointment.class);
                startActivity(intent);
                break;

            case R.id.my_cart:
                Intent intentc = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intentc);
                break;

            case R.id.nav_profile:
                Intent intent1 = new Intent(MainActivity.this, SetupActivity.class);
                startActivity(intent1);
                break;
            case R.id.medicines:
                Intent intent9 = new Intent(MainActivity.this, MedicinesActivity.class);
                startActivity(intent9);
                break;
            case R.id.faq:
                Intent intent2 = new Intent(MainActivity.this, FAQS.class);
                startActivity(intent2);
                break;

            case R.id.test_bookings:
                Intent intent3 = new Intent(MainActivity.this, LabTestDisplay.class);
                startActivity(intent3);
                break;

            case R.id.consultations:
                Intent intent4 = new Intent(MainActivity.this, consultactivity.class);
                startActivity(intent4);
                break;

            case R.id.chat:
                Intent intent5 = new Intent(MainActivity.this,MessageActivity.class);
                startActivity(intent5);
                break;

            case R.id.log_out:
                mAuth.signOut();
                sendUsertoLoginActivity();
                break;


        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        UsersRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().hasChild(currentUserID)) {
                        sendUsertoSetUpActivity();
                    }

                }

            }
        });

    }

    private void sendUsertoSetUpActivity() {
        Intent setupintent = new Intent(MainActivity.this, SetupActivity.class);
        startActivity(setupintent);
    }

}




