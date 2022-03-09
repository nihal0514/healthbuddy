package com.example.healthbuddy.scrollview;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthbuddy.R;
import com.example.healthbuddy.shop.shopdetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ScrollView2Adapter extends RecyclerView.Adapter<ScrollView2Adapter.ViewHolder> {
    private List<ScrollView2> scrollView2List;
    private Context context;

    public ScrollView2Adapter(List<ScrollView2> scrollView2List, Context context) {
        this.scrollView2List = scrollView2List;
        this.context = context;
    }

    @NonNull
    @Override
    public ScrollView2Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScrollView2Adapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScrollView2Adapter.ViewHolder holder, int position) {
        ScrollView2 scrollView2= scrollView2List.get(position);
        holder.nameproduct.setText(scrollView2.getProductName());
        holder.priceproduct.setText(String.valueOf(scrollView2.getPrice()));
        holder.finalprice.setText(String.valueOf(scrollView2.getFinalPrice()));
        holder.pilsproduct.setText(String.valueOf(scrollView2.getPils()));
        Picasso.get().load(scrollView2.getProductImage()).placeholder(R.drawable.profile).into(holder.productimage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, shopdetail.class);
                intent.putExtra("newkey",scrollView2.getKey());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return scrollView2List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productimage;
        TextView nameproduct,pilsproduct,priceproduct,finalprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productimage= itemView.findViewById(R.id.image_product);
            nameproduct= itemView.findViewById(R.id.tv_name_product);
            pilsproduct= itemView.findViewById(R.id.tv_pills_product);
            priceproduct= itemView.findViewById(R.id.tv_price_product);
            finalprice= itemView.findViewById(R.id.tv_final_price_product);

        }
    }
}
