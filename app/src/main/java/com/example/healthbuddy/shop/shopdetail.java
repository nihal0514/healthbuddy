package com.example.healthbuddy.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthbuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class shopdetail extends AppCompatActivity {
    private TextView txt, tvcanshopdetail, tvfinalproductshop, tvnameproductshop;
    private ImageButton button_plus, button_minus, shoppingcart, backbutton;
    ImageView mediimage;
    TextView mediname, pils, descp, finalprice, actualprice;
    Button addtocart;

    private StorageReference cartStorageRef;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private DatabaseReference myReference;
    String currentUserID;
    FirebaseFirestore firestore;
    long pricee;
    int counter;
    String image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopdetail);

        txt = findViewById(R.id.tv_can_shop_detail);
        button_plus = findViewById(R.id.add_shop_detail);
        button_minus = findViewById(R.id.remove_shop_detail);
        mediimage = findViewById(R.id.image_shop_detail);
        mediname = findViewById(R.id.tv_name_product_shop_detail);
        pils = findViewById(R.id.tv_pills_product_shop_detail);
        descp = findViewById(R.id.tv_product_info);
        finalprice = findViewById(R.id.tv_final_price_product_shop);
        actualprice = findViewById(R.id.tv_price_product);
        shoppingcart = findViewById(R.id.image_shopping_shop_detail);
        backbutton = findViewById(R.id.image_btn_arrow_shop_detail);
        addtocart = findViewById(R.id.add_to_basket);
        tvnameproductshop = findViewById(R.id.tv_name_product_shop_detail);
        tvfinalproductshop = findViewById(R.id.tv_final_price_product_shop);



        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        firestore = FirebaseFirestore.getInstance();
        cartStorageRef = FirebaseStorage.getInstance().getReference().child("ProductImage");

        counter = Integer.parseInt(txt.getText().toString());

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCartDetails();
            //    Toast.makeText(shopdetail.this, counter, Toast.LENGTH_SHORT).show();
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent dintent = getIntent();
        String key = dintent.getStringExtra("key");
        String mkey = dintent.getStringExtra("newkey");
        if (mkey == null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            myRef = database.getReference("Medicines").child(key);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    mediname.setText(snapshot.child("MediName").getValue().toString());
                    pils.setText(snapshot.child("pils").getValue().toString());
                    descp.setText(snapshot.child("Description").getValue().toString());
                    actualprice.setText(snapshot.child("ActualPrice").getValue().toString());
                    finalprice.setText(snapshot.child("FinalPrice").getValue().toString());
                    if (snapshot.hasChild("MediImage")) {
                        image = snapshot.child("MediImage").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.profile).into(mediimage);

                    } else {
                        Toast.makeText(shopdetail.this, "Profile name do not exists...", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

        }
        if (key == null) {
            FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
            myReference = mdatabase.getReference("ScrollView2").child(mkey);
            myReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    mediname.setText(snapshot.child("ProductName").getValue().toString());
                    pils.setText(snapshot.child("Pils").getValue().toString());
                    descp.setText(snapshot.child("Description").getValue().toString());
                    actualprice.setText(snapshot.child("Price").getValue().toString());
                    finalprice.setText(snapshot.child("FinalPrice").getValue().toString());
                    if (snapshot.hasChild("ProductImage")) {
                        image = snapshot.child("ProductImage").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.profile).into(mediimage);

                    } else {
                        Toast.makeText(shopdetail.this, "Profile name do not exists...", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }

        button_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            //    counter = Integer.parseInt(txt.getText().toString());
                counter++;
                txt.setText(counter + "");
                pricee = Integer.parseInt(tvfinalproductshop.getText().toString()) ;


             /*   int number=Integer.parseInt("1");

                if(txt.getText().toString()=="1"){
                    pricee=Integer.parseInt(tvfinalproductshop.getText().toString()) *number;


                }*/


            }
        });
        button_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  counter = Integer.parseInt(txt.getText().toString());
                if (counter != 1) {
                    counter--;
                    txt.setText(counter + "");
                    pricee = Integer.parseInt(tvfinalproductshop.getText().toString());
                }

            }
        });

        shoppingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(shopdetail.this, CartActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setCartDetails() {
        String savecurrentdate, savecurrenttime;
        Calendar calfordate = Calendar.getInstance();

        SimpleDateFormat currentdate = new SimpleDateFormat("MM dd,yyyy");
        savecurrentdate = currentdate.format(calfordate.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime = currenttime.format(calfordate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", tvnameproductshop.getText().toString());
        cartMap.put("priceperquantity", tvfinalproductshop.getText().toString());
        cartMap.put("quantity", txt.getText().toString());
        cartMap.put("medicineimage",image);
        if (counter == 1) {
            cartMap.put("TotalPrice", Integer.parseInt(tvfinalproductshop.getText().toString()));
        } else {
            cartMap.put("TotalPrice",pricee*counter);
        }
        // cartMap.put("TotalPrice",pricee);

        firestore.collection("AddtoCart").document(mAuth.getCurrentUser().getUid())
                .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                Toast.makeText(shopdetail.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void SendUsertoCartActivity() {
        Intent intent = new Intent(shopdetail.this, CartActivity.class);
        startActivity(intent);
    }
}
