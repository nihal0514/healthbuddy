package com.example.healthbuddy.consult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthbuddy.R;
import com.example.healthbuddy.signin.SetupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetails extends AppCompatActivity {
    Button consultbtn;
    MaterialTextView nametext,doctorphone,doctoremail,doctoraddress,doctorabout;
    ImageView imageView;
    Button message;
    FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    String PatientId,Patientname,Patientusername,PatientprofileImage;
    String DoctorId,Doctorname,Doctorusername,Doctorprofileimage;
    DatabaseReference reference;
    FirebaseUser fUser;
    private DatabaseReference UsersRef,PatientsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        nametext = findViewById(R.id.doctor_name);
        doctorphone = findViewById(R.id.doctor_phone);
        doctoremail = findViewById(R.id.doctor_email);
        doctoraddress = findViewById(R.id.doctor_address);
        doctorabout = findViewById(R.id.doctor_about);
        imageView = findViewById(R.id.imageView3);
        message = findViewById(R.id.btnmessage);

        Intent dintent = getIntent();
        String key = dintent.getStringExtra("key");

        mAuth = FirebaseAuth.getInstance();
        PatientId = mAuth.getCurrentUser().getUid();
        firestore = FirebaseFirestore.getInstance();

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(PatientId);
        PatientsRef = FirebaseDatabase.getInstance().getReference().child("PList").child(PatientId);

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Chats");

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Patientname= snapshot.child("username").getValue().toString();
                    if (snapshot.hasChild("profileimage")) {
                        String image = snapshot.child("profileimage").getValue().toString();
                        PatientprofileImage = snapshot.child("profileimage").getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.setText("Sent");
              //  setup();
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Doctors").child(key);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                nametext.setText(snapshot.child("fullname").getValue().toString());
                doctorphone.setText(snapshot.child("phone").getValue().toString());
                doctoremail.setText(snapshot.child("email").getValue().toString());
                doctoraddress.setText(snapshot.child("address").getValue().toString());
                doctorabout.setText(snapshot.child("about").getValue().toString());

                Doctorname = snapshot.child("fullname").getValue().toString();
                Doctorprofileimage = snapshot.child("profileimage").getValue().toString();
                DoctorId = snapshot.child("id").getValue().toString();

                if (snapshot.hasChild("profileimage")) {
                    String image = snapshot.child("profileimage").getValue().toString();
                    Picasso.get().load(image).placeholder(R.drawable.profile).into(imageView);

                } else {
                    Toast.makeText(DoctorDetails.this, "Profile name do not exists...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        consultbtn = findViewById(R.id.btn_doctor);
        consultbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorDetails.this, consultactivity.class);
                startActivity(intent);
            }
        });
    }



   /* private void setup() {
        HashMap userMap = new HashMap();
        userMap.put("doctorname",Doctorname);
        userMap.put("patientname",Patientname);
        userMap.put("doctorprofileimage",Doctorprofileimage);
        if(PatientprofileImage==null){
            userMap.put("patientprofileimage","https://firebasestorage.googleapis.com/v0/b/healthbuddy-ce8f6.appspot.com/o/Profile%20Images%2Fprofile.png?alt=media&token=f8300a09-1d49-4299-8835-a9fdd7b2f087");
        }else{
            userMap.put("patientprofileimage",PatientprofileImage);
        }
        userMap.put("patientprofileimage",PatientprofileImage);
        userMap.put("patientId",PatientId);
        userMap.put("doctorId", DoctorId);

        PatientsRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {

                    Toast.makeText(DoctorDetails.this, "Done", Toast.LENGTH_LONG).show();
                }
            }
        });
    }*/


}