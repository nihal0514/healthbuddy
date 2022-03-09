package com.example.healthbuddy.chats.chatfragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthbuddy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PrivateEmptyActivity extends AppCompatActivity {
   // CircleImageView profileimage;
    ImageView profileimage,backbtn;
    TextView fullname;
    private FirebaseAuth mAuth;
    private DatabaseReference alluserRef;
    String currentUserID;

    private EditText textsend;
  //  private ImageButton btnsend;
   private ImageView btnsend;
    MessageAdapter.RecyclerViewClickListener listener;

    String key;
    MessageAdapter messageAdapter;
    List<Chats> chats;
    RecyclerView recyclerView;
    private static String alphabet_string="abcdefghijklmnopqrstuvwxyz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_ui);

       /* Toolbar toolbar= findViewById(R.id.private_message_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

        recyclerView=findViewById(R.id.message_recy);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        Intent dintent = getIntent();
        key = dintent.getStringExtra("key");

        textsend=findViewById(R.id.text_send);
        btnsend=findViewById(R.id.btn_send);
        backbtn= findViewById(R.id.backbtn);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg= encrypt12(textsend.getText().toString(),11);
                if(!msg.equals(" ")){
                    SendMessage(currentUserID,key,msg);
                }else{
                    Toast.makeText(PrivateEmptyActivity.this, "you cant send messages", Toast.LENGTH_SHORT).show();
                }
                textsend.setText("");
            }
        });

        profileimage=findViewById(R.id.private_profile_image);
        fullname=findViewById(R.id.private_message_username);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        alluserRef = database.getReference("Doctors").child(key);
      // alluserRef = database.getReference("PList").child(key);
        alluserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    alldoctors alldoctors1 = snapshot.getValue(alldoctors.class);
                    fullname.setText(alldoctors1.getFullname());
                    Picasso.get().load(alldoctors1.getProfileimage()).placeholder(R.drawable.profile).into(profileimage);
                    ReadMessage(currentUserID,key,alldoctors1.getProfileimage());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void SendMessage(String sender,String receiver,String message){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);

        reference.child("Chats").push().setValue(hashMap);

        final DatabaseReference chatRef=FirebaseDatabase.getInstance().getReference("chatlist")
                .child(currentUserID)
                .child(key);
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    chatRef.child("username").setValue(key);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ReadMessage(String myid,String userid,String imageUrl){
        chats=new ArrayList<>();
        alluserRef=FirebaseDatabase.getInstance().getReference("Chats");
        alluserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chats.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Chats chat1=snapshot.getValue(Chats.class);
                    if(chat1.getReceiver().equals(myid) && chat1.getSender().equals(key) ||
                            chat1.getReceiver().equals(key) && chat1.getSender().equals(myid)){
                        chats.add(chat1);
                    }

                    messageAdapter=new MessageAdapter(chats,PrivateEmptyActivity.this,listener,imageUrl);
                    recyclerView.setAdapter(messageAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public String encrypt12(String message,int shiftkey){
        message=message.toLowerCase();
        String ciphertext="";
        for(int i=0;i<message.length();i++) {
            int charPosition = alphabet_string.indexOf(message.charAt(i));
            int keyVal = (shiftkey + charPosition) % 26;
            char replaceVal = alphabet_string.charAt(keyVal);
            ciphertext = ciphertext + replaceVal;
        }
        return ciphertext;
    }
}