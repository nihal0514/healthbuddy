package com.example.healthbuddy.consult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.healthbuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class DoctorAppointmentDetail extends AppCompatActivity {
    Button btndoctorappoint;
    MaterialTextView nametext,doctorphone,doctoremail,doctoraddress,doctorabout;
    ImageView imageView;

    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;
    FirebaseFirestore firestore;
    private StorageReference DoctorAppointmentRef;
    EditText chooseTime;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment_detail);
        nametext=findViewById(R.id.doctor_name_appoint);
        doctorphone=findViewById(R.id.doctor_phone_appoint);
        doctoremail=findViewById(R.id.doctor_email_appoint);
        doctoraddress=findViewById(R.id.doctor_address_appoint);
        doctorabout=findViewById(R.id.doctor_about_appoint);
        imageView=findViewById(R.id.imageView3_appoint);
        chooseTime = findViewById(R.id.etChooseTime);

        mAuth = FirebaseAuth.getInstance();

        firestore=FirebaseFirestore.getInstance();
        DoctorAppointmentRef= FirebaseStorage.getInstance().getReference().child("ProductImage");

        btndoctorappoint=findViewById(R.id.btn_doctor_appoint);
        btndoctorappoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAppointmentDetails();
            }
        });






        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(DoctorAppointmentDetail.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        chooseTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
                btndoctorappoint.setVisibility(View.VISIBLE);
            }
        });

        Intent dintent=getIntent();
        String key=dintent.getStringExtra("key");

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
                if (snapshot.hasChild("profileimage")) {
                    String image = snapshot.child("profileimage").getValue().toString();
                    Picasso.get().load(image).placeholder(R.drawable.profile).into(imageView);

                } else {
                    Toast.makeText(DoctorAppointmentDetail.this, "Profile name do not exists...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void setAppointmentDetails() {
        final int min = 20;
        final int max = 80;
        final int random = new Random().nextInt((max - min) + 1) + min;
        String savecurrentdate,savecurrenttime;
        Calendar calfordate=Calendar.getInstance();

        SimpleDateFormat currentdate=new SimpleDateFormat("MM dd,yyyy");
        savecurrentdate=currentdate.format(calfordate.getTime());

        SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime=currenttime.format(calfordate.getTime());

        final HashMap<String,Object> AppointMap=new HashMap<>();
        AppointMap.put("DoctorName", nametext.getText().toString());
        AppointMap.put("DoctorPhone",doctorphone.getText().toString());
        AppointMap.put("DoctorEmail",doctoremail.getText().toString());
        AppointMap.put("DoctorAddress",doctoraddress.getText().toString());
        AppointMap.put("Time",chooseTime.getText().toString());
        AppointMap.put("MeetingID",mAuth.getCurrentUser().getUid()+random);
        firestore.collection("DoctorAppointment").document(mAuth.getCurrentUser().getUid())
                .collection("CurrentUser").add(AppointMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                Toast.makeText(DoctorAppointmentDetail.this, "Appointment Booked", Toast.LENGTH_SHORT).show();
                btndoctorappoint.setVisibility(View.INVISIBLE);
                finish();
            }
        });

    }
}