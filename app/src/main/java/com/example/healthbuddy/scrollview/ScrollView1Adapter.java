package com.example.healthbuddy.scrollview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthbuddy.R;
import com.example.healthbuddy.consult.DoctorList;
import com.example.healthbuddy.covidstatistics.CovidDataNew;
import com.example.healthbuddy.covidstatistics.Coviddata;
import com.example.healthbuddy.demo.LoginUiDemo;
import com.example.healthbuddy.healthtips.HealthTipActivity;
import com.example.healthbuddy.labtest.LabTestDisplay;
import com.example.healthbuddy.labtest.Lab_Test;
import com.example.healthbuddy.shop.shop;
import com.example.healthbuddy.signin.LoginActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ScrollView1Adapter extends RecyclerView.Adapter<ScrollView1Adapter.ViewHolder> {
    public ScrollView1Adapter(Context context, List<ScrollView1> scrollView1List, RecyclerViewClickListener listener) {
        this.context = context;
        this.scrollView1List = scrollView1List;
        this.listener = listener;
    }

    private Context context;
    private List<ScrollView1>scrollView1List;
    private RecyclerViewClickListener listener;
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_shop,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ScrollView1Adapter.ViewHolder holder, int position) {
        ScrollView1 scrollView1= scrollView1List.get(position);
        holder.name.setText(scrollView1.getName());
        Picasso.get().load(scrollView1.getImageview()).placeholder(R.drawable.profile).into(holder.scroll1image);
        holder.itemView.setOnClickListener(l_->{
            switch(scrollView1.getName()){
                case "Medicines":
                    Intent intent=new Intent(context, shop.class);
                    context.startActivity(intent);
                    break;
                case "Consultation":
                    Intent intent1=new Intent(context, DoctorList.class);
                    context.startActivity(intent1);
                    break;
                case "Covid Analysis":
                    Intent intent2=new Intent(context, CovidDataNew.class);
                    context.startActivity(intent2);
                    break;

                case "Health Tips":
                    Intent intent3=new Intent(context, HealthTipActivity.class);
                    context.startActivity(intent3);
                    break;
                case "Lab Test":
                    Intent intent4=new Intent(context, LabTestDisplay.class);
                    context.startActivity(intent4);
                    break;

            }
        });

    }

    @Override
    public int getItemCount() {
        return scrollView1List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView scroll1image;
        TextView name;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.tv_medicines);
            scroll1image=itemView.findViewById(R.id.medicines);
        }
    }
    public interface RecyclerViewClickListener{
        void onClick(View v,int position);

    }
}
