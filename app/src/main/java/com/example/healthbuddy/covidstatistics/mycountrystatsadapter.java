package com.example.healthbuddy.covidstatistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthbuddy.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class mycountrystatsadapter extends RecyclerView.Adapter<mycountrystatsadapter.ViewHolder> {
    List<Statewise> statewiseList;
    Context context;

    public mycountrystatsadapter(List<Statewise> statewiseList, Context context) {
        this.statewiseList = statewiseList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mycountrystats,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull mycountrystatsadapter.ViewHolder holder, int position) {
        Statewise statewise=statewiseList.get(position);
        holder.statename.setText(statewise.getState());
        holder.stateconfirmedcase.setText(String.valueOf(statewise.getConfirmed()));
        holder.staterecoveredcase.setText(String.valueOf(statewise.getRecovered()));
        holder.statedeathcase.setText(String.valueOf(statewise.getDeaths()));
        holder.stateactivecase.setText(String.valueOf(statewise.getActive()));


    }

    @Override
    public int getItemCount() {
        return statewiseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView statename;
        TextView stateconfirmedcase;
        TextView staterecoveredcase;
        TextView statedeathcase;
        TextView stateactivecase;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            statename=itemView.findViewById(R.id.state_name);
            stateconfirmedcase=itemView.findViewById(R.id.state_confirmed_cases);
            staterecoveredcase=itemView.findViewById(R.id.state_recovered_cases);
            statedeathcase=itemView.findViewById(R.id.state_deaths_cases);
            stateactivecase=itemView.findViewById(R.id.state_active_cases);
        }
    }
}
