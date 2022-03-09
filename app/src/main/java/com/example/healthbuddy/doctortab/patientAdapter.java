package com.example.healthbuddy.doctortab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthbuddy.R;
import com.example.healthbuddy.consult.Appointmentadapter;
import com.example.healthbuddy.consult.appointmentlist;

import java.util.List;

public class patientAdapter extends RecyclerView.Adapter<patientAdapter.ViewHolder>{
    private List<patientmodel> patientList;
    private Context context;

    public patientAdapter(List<patientmodel> patientList, Context context) {
        this.patientList = patientList;
        this.context = context;
    }

    @NonNull
    @Override
    public patientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new patientAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull patientAdapter.ViewHolder holder, int position) {
        patientmodel patientList1= patientList.get(position);
        holder.patientname.setText(patientList1.getPatientId());
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView patientname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            patientname = itemView.findViewById(R.id.patient_name);
        }
    }
}
