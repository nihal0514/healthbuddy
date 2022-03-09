package com.example.healthbuddy.consult;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthbuddy.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder>  {
    private List<DoctorsList>doctorsLists;
    private RecyclerViewClickListener listener;

    public DoctorListAdapter(List<DoctorsList> doctorsLists, Context context,RecyclerViewClickListener listener) {
        this.doctorsLists = doctorsLists;
        this.context = context;
        this.listener=listener;
    }

    private Context context;

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyvdoctor_list,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DoctorListAdapter.ViewHolder holder, int position) {
        DoctorsList doctorsList=doctorsLists.get(position);
        holder.textView.setText(doctorsList.getName());
        holder.itemView.setOnClickListener(l_->{
            Intent intent=new Intent(context,DoctorDetails.class);
            intent.putExtra("key",doctorsList.getKey());
            context.startActivity(intent);
        });
        Picasso.get().load(doctorsList.getProfileimage()).placeholder(R.drawable.doctor).into(holder.doctorimage);
    }

    @Override
    public int getItemCount() {
        return doctorsLists.size() ;
    }

    public void updateList1(List<DoctorsList> list){
        doctorsLists = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView doctorimage;
        Button btn1;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.doctorlist_tv);
            doctorimage= itemView.findViewById(R.id.doctor);
        }
/*
        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());

        }*/
    }
    public interface RecyclerViewClickListener{
        void onClick(View v,int position);

    }

}
