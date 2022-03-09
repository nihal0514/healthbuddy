package com.example.healthbuddy.consult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthbuddy.Home.MainActivity;
import com.example.healthbuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Doctor_setup_activity extends AppCompatActivity {
    private EditText doctorfullname, doctorphonenumber;
    private EditText doctoremailaddress,doctormanaddress,doctormanabout;
    private Button SaveInformationbuttion;
    private CircleImageView ProfileImage;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;
    private DatabaseReference DoctorsRef;
    private StorageReference UserProfileImageRef;

    String currentUserID;
    final static int Gallery_Pick = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_setup);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        DoctorsRef = FirebaseDatabase.getInstance().getReference().child("Doctors").child(currentUserID);
        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("Doctor Profile Images");


        doctorfullname = (EditText) findViewById(R.id.doctor_setup_fullname);
        doctorphonenumber = (EditText) findViewById(R.id.doctor_setup_phone);
        doctoremailaddress = (EditText) findViewById(R.id.doctor_setup_email);
        doctormanaddress = (EditText) findViewById(R.id.doctor_setup_address);
        doctormanabout = (EditText) findViewById(R.id.doctor_about_tv);
        SaveInformationbuttion = (Button) findViewById(R.id.btn_save);
        ProfileImage = (CircleImageView) findViewById(R.id.doctor_setup_image);
        loadingBar = new ProgressDialog(this);
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Gallery_Pick);
            }
        });
        DoctorsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.hasChild("profileimage")) {
                        String image = snapshot.child("profileimage").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.profile).into(ProfileImage);

                    } else {
                        Toast.makeText(Doctor_setup_activity.this, "no image", Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        SaveInformationbuttion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveAccountSetupInformation();
            }
        });
    }


    private void SaveAccountSetupInformation() {
        String doctorname = doctorfullname.getText().toString();
        String doctorphone = doctorphonenumber.getText().toString();
        String doctoremail = doctoremailaddress.getText().toString();
        String doctoraddress = doctormanaddress.getText().toString();
        String doctorabout= doctormanabout.getText().toString();
        if (TextUtils.isEmpty(doctorname)) {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(doctorphone)) {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(doctoremail)) {
            Toast.makeText(this, "Please write your email address...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Saving Information");
            loadingBar.setMessage("Please wait, while we are creating your new Account...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);


            HashMap doctorMap = new HashMap();
            doctorMap.put("fullname", doctorname);
            doctorMap.put("phone", doctorphone);
            doctorMap.put("email", doctoremail);
            doctorMap.put("address",doctoraddress);
            doctorMap.put("about",doctorabout);
            doctorMap.put("gender", "none");
            doctorMap.put("dob", "none");
            doctorMap.put("id",currentUserID);
            doctorMap.put("profileimage","https://firebasestorage.googleapis.com/v0/b/comparecall-d2037.appspot.com/o/Profile%20Images%2Fprofile.png?alt=media&token=671959dd-8ab7-4e55-a44c-d92c892b594f");
            DoctorsRef.updateChildren(doctorMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {

                        SendUserToMainActivity();
                        Toast.makeText(Doctor_setup_activity.this, "your Account is created Successfully.", Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();
                    } else {
                        String message = task.getException().getMessage();
                        Toast.makeText(Doctor_setup_activity.this, "Error Occured: " + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });

        }
    }


    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(Doctor_setup_activity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // some conditions for the picture
        if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {
            Uri ImageUri = data.getData();
            // crop the image
            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        // Get the cropped image
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {       // store the cropped image into result
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                loadingBar.setTitle("Profile Image");
                loadingBar.setMessage("Please wait, while we updating your profile image...");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);

                Uri resultUri = result.getUri();

                final StorageReference filePath = UserProfileImageRef.child(currentUserID + ".jpg");

                filePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final String downloadUrl = uri.toString();
                                DoctorsRef.child("profileimage").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {


                                            Toast.makeText(Doctor_setup_activity.this, "Image Stored", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                        } else {
                                            String message = task.getException().getMessage();
                                            Toast.makeText(Doctor_setup_activity.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                        }
                                    }
                                });
                            }

                        });

                    }

                });
            } else {
                Toast.makeText(this, "Error Occured: Image can not be cropped. Try Again.", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }


    }
}