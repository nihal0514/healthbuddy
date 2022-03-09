package com.example.healthbuddy.chats.chatfragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthbuddy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class allDoctorsAdapter extends RecyclerView.Adapter<allDoctorsAdapter.ViewHolder> {
    @NonNull
    private List<alldoctors> alldoctorsList;
    private Context context;
    private RecyclerViewClickListener listener;

    public allDoctorsAdapter(@NonNull List<alldoctors> alldoctorsList, Context context, RecyclerViewClickListener listener) {
        this.alldoctorsList = alldoctorsList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.doctors_item_ui,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull allDoctorsAdapter.ViewHolder holder, int position) {
        alldoctors allusers1=alldoctorsList.get(position);
        holder.username.setText(allusers1.getFullname());
        Picasso.get().load(allusers1.getProfileimage()).placeholder(R.drawable.profile).into(holder.profileimage);
        holder.itemView.setOnClickListener(l_->{
            Intent intent=new Intent(context, PrivateEmptyActivity.class);
            intent.putExtra("key",allusers1.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return alldoctorsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileimage;
        TextView username;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileimage=itemView.findViewById(R.id.user_profile_image);
            username=itemView.findViewById(R.id.User_username);

        }
    }
    public interface RecyclerViewClickListener{
        void onClick(View v,int position);

    }
}
