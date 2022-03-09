package com.example.healthbuddy.shop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthbuddy.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class my_cart_adapter extends RecyclerView.Adapter<my_cart_adapter.ViewHolder> {
    public my_cart_adapter(List<mycart> mycartList, Context context, RecyclerViewClickListener listener) {
        this.mycartList = mycartList;
        this.context = context;
        this.listener = listener;
    }

    private List<mycart>mycartList;
    private Context context;
    private RecyclerViewClickListener listener;



    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mycartnew,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull my_cart_adapter.ViewHolder holder, int position) {
        mycart mycart1 = mycartList.get(position);
        holder.productname.setText(mycart1.getProductName());
        holder.priceperquantity.setText(mycart1.getPriceperquantity());
        holder.quantity.setText(mycart1.getQuantity());
        holder.totalprice.setText(String.valueOf(mycart1.getTotalPrice()));
          Picasso.get().load(mycart1.getMedicineimage()).placeholder(R.drawable.medicina_4).into(holder.medicineimage);
    }

    @Override
    public int getItemCount() {
        return mycartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productname,priceperquantity,quantity,totalprice;
        ImageView medicineimage;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            productname=itemView.findViewById(R.id.tv_name_product_shop_1);
            priceperquantity=itemView.findViewById(R.id.tv_price_quantity);
            quantity=itemView.findViewById(R.id.tv_total_quantity);
            totalprice=itemView.findViewById(R.id.tv_total_price_);
            medicineimage= itemView.findViewById(R.id.medicine_image);
        }
    }
    public interface RecyclerViewClickListener{
        void onClick(View v,int position);

    }
}
