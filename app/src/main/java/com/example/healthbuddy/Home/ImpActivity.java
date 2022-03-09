package com.example.healthbuddy.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import com.example.healthbuddy.R;
import com.example.healthbuddy.signin.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ImpActivity extends AppCompatActivity {
    Button getStarted;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imp);

        mAuth = FirebaseAuth.getInstance();
        getStarted= findViewById(R.id.getstarted);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ImpActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null)
        {
            sendUsertoMainActivity();
        }
   }

    private void sendUsertoMainActivity() {
        startActivity(new Intent(ImpActivity.this, MainActivity.class));
    }
}