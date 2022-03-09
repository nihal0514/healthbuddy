package com.example.healthbuddy.shop;

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
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MedicinesAdapter extends RecyclerView.Adapter<MedicinesAdapter.ViewHolder> {
    public MedicinesAdapter(List<MedicinesList> medicinesLists,Context context, RecyclerViewClickListener listener) {
        this.medicinesLists = medicinesLists;
        this.listener = listener;
        this.context=context;
    }

    private List<MedicinesList>medicinesLists;
    private RecyclerViewClickListener listener;
    private Context context;
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.revymedicine_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MedicinesAdapter.ViewHolder holder, int position) {
        MedicinesList medicinesList=medicinesLists.get(position);
       // holder.MediImage.setText(medicinesList.getMediImage());
        holder.MediName.setText(medicinesList.getMediName());
        holder.pils.setText(String.valueOf(medicinesList.getPils()));
        holder.finalprice.setText(String.valueOf(medicinesList.getFinalprice()));
        holder.actualprice.setText(String.valueOf(medicinesList.getActualprice()));
        Picasso.get().load(medicinesList.getMediImage()).placeholder(R.drawable.profile).into(holder.mediImage);
        holder.itemView.setOnClickListener(l_->{
            Intent intent=new Intent(context,shopdetail.class);
            intent.putExtra("key",medicinesList.getKey());
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return medicinesLists.size();
    }
    public void updateList(List<MedicinesList> list){
        medicinesLists = list;
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
      //  TextView MediImage;
        TextView MediName;
        TextView pils;
        TextView finalprice;
        TextView actualprice;
        ImageView mediImage;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        //    MediImage=itemView.findViewById(R.id.img_product_shop);
            MediName=itemView.findViewById(R.id.tv_name_product_shop);
            pils=itemView.findViewById(R.id.tv_pills_product_shop);
            finalprice=itemView.findViewById(R.id.tv_final_price_product_shop);
            actualprice=itemView.findViewById(R.id.tv_price_product_shop);
            mediImage=itemView.findViewById(R.id.img_product_shop);
        }
    }
    public interface RecyclerViewClickListener{
        void onClick(View v,int position);

    }

}