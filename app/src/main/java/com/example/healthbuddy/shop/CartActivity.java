package com.example.healthbuddy.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.healthbuddy.Home.MainActivity;
import com.example.healthbuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements PaymentResultListener {
    RecyclerView recyclerView;
    private my_cart_adapter cartAdapter;
    private my_cart_adapter.RecyclerViewClickListener listener;
    List<mycart>mycartList=new ArrayList<>();

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    Button btPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView=findViewById(R.id.mycartrecy);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        cartAdapter=new my_cart_adapter(mycartList,this,listener);
        recyclerView.setAdapter(cartAdapter);

        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();

        btPay= findViewById(R.id.buy_shopping_shop);

        db.collection("AddtoCart").document(mAuth.getCurrentUser().getUid())
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        mycart mycart1=documentSnapshot.toObject(mycart.class);
                        mycartList.add(mycart1);
                        cartAdapter.notifyDataSetChanged();
                    }
                }

            }
        });

        String sAmount= "100";
        int amount= Math.round(Float.parseFloat(sAmount)*100);

        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_h9bhx7TDJfLDqV");

                checkout.setImage(R.drawable.logo);

                try {
                    JSONObject options = new JSONObject();

                    options.put("name", "ALGORIAL EDCUARE");
                    options.put("description", "Reference No. #123456");
                    options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
                    // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
                    options.put("theme.color", "#3399cc");
                    options.put("currency", "INR");
                    options.put("amount", "30000");//300 X 100
                    options.put("prefill.email", "gaurav.kumar@example.com");
                    options.put("prefill.contact","7864945278");
                    checkout.open(CartActivity.this, options);
                } catch(Exception e) {
                    Log.e("TAG", "Error in starting Razorpay Checkout", e);
                }
            }
        });

    }

    @Override
    public void onPaymentSuccess(String s) {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Payment ID");
        builder.setMessage(s);
        builder.show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(CartActivity.this, s, Toast.LENGTH_SHORT).show();

    }
}