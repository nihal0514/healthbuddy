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

public class programAdapter1 extends RecyclerView.Adapter<programAdapter1.ViewHolder> {
    Context context;
    String[] product;
    String[] pils;
    String[] finalprice;
    String[] price;
    int[] prodimage;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView proddimage;
        TextView productt;
        TextView pilss;
        TextView finalpricee;
        TextView pricee;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            proddimage=itemView.findViewById(R.id.img_product_shop);
            productt=itemView.findViewById(R.id.tv_name_product_shop);
            pilss=itemView.findViewById(R.id.tv_pills_product_shop);
            finalpricee=itemView.findViewById(R.id.tv_final_price_product_shop);
            pricee=itemView.findViewById(R.id.tv_price_product_shop);
        }
    }
    public programAdapter1(Context context,  String[] product,String[] pils,String[] finalprice,String[] price,int[] prodimage){
        this.context=context;
        this.product=product;
        this.pils=pils;
        this.finalprice=finalprice;
        this.price=price;
        this.prodimage=prodimage;
    }
    @NonNull
    @Override
    public programAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.revymedicine_list,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull programAdapter1.ViewHolder holder, int position) {
        holder.productt.setText(product[position]);
        holder.pilss.setText(pils[position]);
        holder.finalpricee.setText(finalprice[position]);
        holder.pricee.setText(price[position]);
        holder.proddimage.setImageResource(prodimage[position]);

    }



    @Override
    public int getItemCount() {
        return product.length;
    }
}
