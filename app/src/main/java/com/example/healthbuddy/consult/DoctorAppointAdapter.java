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

public class DoctorAppointAdapter extends RecyclerView.Adapter<DoctorAppointAdapter.ViewHolder> {
    private List<DoctorAppint>doctorAppintList;
    private Context context;
    private DoctorListAdapter.RecyclerViewClickListener listener;

    public DoctorAppointAdapter(List<DoctorAppint> doctorAppintList, Context context, DoctorListAdapter.RecyclerViewClickListener listener) {
        this.doctorAppintList = doctorAppintList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_appointment_ite,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DoctorAppointAdapter.ViewHolder holder, int position) {
        DoctorAppint doctorAppint=doctorAppintList.get(position);
        holder.textView.setText(doctorAppint.getName());
        holder.btn.setOnClickListener(l_->{
            Intent intent=new Intent(context,DoctorAppointmentDetail.class);
            intent.putExtra("key",doctorAppint.getKey());
            context.startActivity(intent);
        });



    }

    @Override
    public int getItemCount() {
        return doctorAppintList.size();
    }
    public void updateList1(List<DoctorAppint> list){
        doctorAppintList = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        Button btn;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.doctorlist_tv_appoint);
            btn=itemView.findViewById(R.id.bookappointment);

        }
    }
    public interface RecyclerViewClickListener{
        void onClick(View v,int position);

    }
}
