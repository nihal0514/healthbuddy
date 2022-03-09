package com.example.healthbuddy.labtest;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthbuddy.R;
import com.example.healthbuddy.scrollview.ScrollView1;
import com.example.healthbuddy.scrollview.ScrollView1Adapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LabTestSVAdapter extends RecyclerView.Adapter<LabTestSVAdapter.ViewHolder> {
    private Context context;
    private List<LabtestSV> labtestSV;

    public LabTestSVAdapter(Context context, List<LabtestSV> labtestSV) {
        this.context = context;
        this.labtestSV = labtestSV;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LabTestSVAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.labtest_item_sv,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LabtestSV labtestSV1= labtestSV.get(position);
        holder.labtestcategoryname.setText(labtestSV1.getName());
        Picasso.get().load(labtestSV1.getImage()).placeholder(R.drawable.profile).into(holder.labtestcategoryimage);
    }

    @Override
    public int getItemCount() {
        return labtestSV.size();
    }
    public void updateList(List<LabtestSV> list){
        labtestSV = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView labtestcategoryimage;
        TextView labtestcategoryname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            labtestcategoryname= itemView.findViewById(R.id.labtest_text_sv1);
            labtestcategoryimage= itemView.findViewById(R.id.labtest_image_sv1);
        }
    }
}
