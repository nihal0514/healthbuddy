package com.example.healthbuddy.covidstatistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.healthbuddy.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GlobalClassAdapter extends RecyclerView.Adapter<GlobalClassAdapter.ViewHolder> {
    List<globalclass>globalclasses;
    Context context;

    public GlobalClassAdapter(List<globalclass> globalclasses, Context context) {
        this.globalclasses = globalclasses;
        this.context = context;
    }
   /* public void setCovidCase(List<globalclass> globalclasses) {
        this.globalclasses = globalclasses;
        notifyDataSetChanged();
    }
*/
    @NonNull
    @NotNull
    @Override
    public GlobalClassAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.covid_list_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GlobalClassAdapter.ViewHolder holder, int position) {
        globalclass globalclass1=globalclasses.get(position);
        holder.countryname.setText(globalclass1.getCountry());
        holder.countrycase.setText(String.valueOf(globalclass1.getUpdated()));
      //  Picasso.get().load(globalclass1.getFlag()).placeholder(R.drawable.profile).into(holder.flag);
        Glide.with(context).load(globalclasses.get(position).getCountryInfo().getFlag()).apply(RequestOptions.centerCropTransform()).into(holder.flag);

    }

    @Override
    public int getItemCount() {
        return globalclasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView countryname;
        TextView countrycase;
        ImageView flag;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            countryname=itemView.findViewById(R.id.country_name);
            countrycase=itemView.findViewById(R.id.country_cases);
            flag=itemView.findViewById(R.id.flag);
        }
    }
}
