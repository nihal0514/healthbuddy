package com.example.healthbuddy.signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthbuddy.Home.MainActivity;
import com.example.healthbuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class RegisterActivity extends AppCompatActivity {
    private EditText registeremail,registerpassword,registerconfirmpassword;
    private CircularProgressButton registercreateaccount;
    private FirebaseAuth mAuth;
    private TextView already_account_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_ui_demo);
        registeremail=findViewById(R.id.register_email);
        registerpassword=findViewById(R.id.register_password);
        registerconfirmpassword=findViewById(R.id.register_confirmpassword);
        registercreateaccount=findViewById(R.id.register_create_account);
        already_account_tv= findViewById(R.id.already_accout);

        mAuth= FirebaseAuth.getInstance();

        registercreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createnewAccount();
            }
        });

        already_account_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createnewAccount() {
        String email=registeremail.getText().toString();
        String password=registerpassword.getText().toString();
        String confirmpassword=registerconfirmpassword.getText().toString();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "please write email", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "please write password", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(confirmpassword)){
            Toast.makeText(this, "please write confirm password", Toast.LENGTH_SHORT).show();
        }else if(!password.equals(confirmpassword)){
            Toast.makeText(this, "password not match", Toast.LENGTH_SHORT).show();
        }else{

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendUsertoSetupActivity();
                                Toast.makeText(RegisterActivity.this, "registered Successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                String message=task.getException().getMessage();
                                Toast.makeText(RegisterActivity.this, "Error Occured"+ message, Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser!=null){
            sendUserToMainActivity();

        }


    }

    private void sendUserToMainActivity() {
        Intent mainIntent=new Intent(RegisterActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);

        finish();
    }


    private void sendUsertoSetupActivity() {
        Intent setUpIntent=new Intent(RegisterActivity.this, SetupActivity.class);
        setUpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setUpIntent);
        finish();
    }
}