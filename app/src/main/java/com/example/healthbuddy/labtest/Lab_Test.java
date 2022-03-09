package com.example.healthbuddy.labtest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthbuddy.R;
import com.example.healthbuddy.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Lab_Test extends AppCompatActivity {
    private Button labselect,labpredict;
    private ImageView labimage;
    private TextView outptut_text;
    private Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        labselect= findViewById(R.id.select);
        labpredict= findViewById(R.id.predict);
        labimage= findViewById(R.id.labimagescan);
        outptut_text= findViewById(R.id.output_text);

        labselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,100);
            }
        });
        labpredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img=Bitmap.createScaledBitmap(img,64,64,true);
                if(img!=null){
                    try {
                        Model model = Model.newInstance(getApplicationContext());

                        // Creates inputs for reference.
                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 64, 64, 3}, DataType.FLOAT32);

                        TensorImage tensorImage= new TensorImage(DataType.FLOAT32);
                        tensorImage.load(img);
                        ByteBuffer byteBuffer= tensorImage.getBuffer();

                        inputFeature0.loadBuffer(byteBuffer);

                        // Runs model inference and gets result.
                        Model.Outputs outputs = model.process(inputFeature0);
                        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                        if(outputFeature0.getFloatArray()[0]==1.0){
                            outptut_text.setText("Yes Brain Tumor");
                        }else{
                            outptut_text.setText("No Brain Tumor detected");
                        }

                        // Releases model resources if no longer used.
                        model.close();
                    } catch (IOException e) {
                        // TODO Handle the exception
                    }
                }else{
                    Toast.makeText(Lab_Test.this, "Please select any image", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            labimage.setImageURI(data.getData());

            Uri uri= data.getData();
            try{
                img=MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}