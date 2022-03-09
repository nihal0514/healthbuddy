package com.example.healthbuddy.scrollview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthbuddy.R;

public class programAdapter extends RecyclerView.Adapter<programAdapter.ViewHolder> {
    Context context;
    String[] shop;
    int[] shopimage;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView shopimagee;
        TextView shopp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopimagee=itemView.findViewById(R.id.medicines);
            shopp=itemView.findViewById(R.id.tv_medicines);
        }
    }
    public programAdapter(Context context, String[] shop,int[] shopimage){
        this.context=context;
        this.shop=shop;
        this.shopimage=shopimage;
    }
    @NonNull
    @Override
    public programAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.single_shop,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull programAdapter.ViewHolder holder, int position) {
        holder.shopp.setText(shop[position]);
        holder.shopimagee.setImageResource(shopimage[position]);

    }
    @Override
    public int getItemCount() {
        return shop.length;
    }
}
