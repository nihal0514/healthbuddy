package com.example.healthbuddy.shop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthbuddy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.List;

public class MedicinesActivity extends AppCompatActivity {
    ImageView imageView,copymedicines;
    TextView textview;
    Button button;
    Button reset;
    public static final int PIC_IMAGE = 121;
    String currentUserID;
    private FirebaseAuth mAuth;
    final static int Gallery_Pick = 1;
    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines);
        imageView = findViewById(R.id.imageview);
        textview = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        copymedicines=findViewById(R.id.copymedicines);
        reset=findViewById(R.id.reset);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.startPickImageActivity(MedicinesActivity.this);

            }
        });
        copymedicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard=(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip=ClipData.newPlainText("CouponText",textview.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MedicinesActivity.this, "copied", Toast.LENGTH_SHORT).show();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textview.setText(null);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode== Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                uri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

            } else {
                startCrop(imageUri);
            }
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(requestCode==RESULT_OK){
                imageView.setImageURI(result.getUri());
                Toast.makeText(this, "Updated Suceessfully", Toast.LENGTH_SHORT).show();
            }

            FirebaseVisionImage image;

            try {
                image = FirebaseVisionImage.fromFilePath(getApplicationContext(),result.getUri());


                FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance()
                        .getOnDeviceTextRecognizer();
                textRecognizer.processImage(image)
                        .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText result) {
                                String resultText = result.getText();
                                for (FirebaseVisionText.TextBlock block : result.getTextBlocks()) {
                                    String blockText = block.getText();
                                    Float blockConfidence = block.getConfidence();
                                    List<RecognizedLanguage> blockLanguages = block.getRecognizedLanguages();
                                    Point[] blockCornerPoints = block.getCornerPoints();
                                    Rect blockFrame = block.getBoundingBox();
                                    for (FirebaseVisionText.Line line : block.getLines()) {
                                        String lineText = line.getText();
                                        Float lineConfidence = line.getConfidence();
                                        List<RecognizedLanguage> lineLanguages = line.getRecognizedLanguages();
                                        Point[] lineCornerPoints = line.getCornerPoints();
                                        Rect lineFrame = line.getBoundingBox();
                                        for (FirebaseVisionText.Element element : line.getElements()) {
                                            textview.append(element.getText() + " ");
                                            String elementText = element.getText();
                                            Float elementConfidence = element.getConfidence();
                                            List<RecognizedLanguage> elementLanguages = element.getRecognizedLanguages();
                                            Point[] elementCornerPoints = element.getCornerPoints();
                                            Rect elementFrame = element.getBoundingBox();
                                        }
                                        textview.append("\n");
                                    }
                                    textview.append("\n\n");
                                }
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                    }
                                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void startCrop(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }
    }
