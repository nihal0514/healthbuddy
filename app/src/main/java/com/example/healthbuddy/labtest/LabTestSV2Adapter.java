package com.example.healthbuddy.labtest;

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
import com.example.healthbuddy.shop.MedicinesList;
import com.example.healthbuddy.shop.shop;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LabTestSV2Adapter extends RecyclerView.Adapter<LabTestSV2Adapter.ViewHolder> {

    private List<LabtestSV2> labtestSV2s;
    private Context context;

    public LabTestSV2Adapter(List<LabtestSV2> labtestSV2s, Context context) {
        this.labtestSV2s = labtestSV2s;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LabTestSV2Adapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.labtest_item_sv2,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LabtestSV2 labtestSV2= labtestSV2s.get(position);
        holder.labsv2name.setText(labtestSV2.getName());
        holder.labsv2tests.setText(labtestSV2.getTotaltests());
        Picasso.get().load(labtestSV2.getImage()).placeholder(R.drawable.profile).into(holder.labsv2image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (labtestSV2.getName()){
                    case "MRI":
                        Intent intent=new Intent(context,Lab_Test.class);
                        context.startActivity(intent);
                        break;


                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return labtestSV2s.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView labsv2image;
        TextView labsv2name;
        TextView labsv2tests;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            labsv2image= itemView.findViewById(R.id.labtest_sv2_image);
            labsv2name= itemView.findViewById(R.id.labtest_sv2_name);
            labsv2tests= itemView.findViewById(R.id.labtest_sv2_tests);
        }
    }
}
