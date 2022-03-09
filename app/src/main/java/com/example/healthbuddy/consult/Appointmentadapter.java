package com.example.healthbuddy.consult;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthbuddy.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Appointmentadapter extends RecyclerView.Adapter<Appointmentadapter.ViewHolder> {
    private List<appointmentlist> appointmentlistList;
    private Context context;
    private RecyclerViewClickListener listener;

    public Appointmentadapter(List<appointmentlist> appointmentlistList, Context context, RecyclerViewClickListener listener) {
        this.appointmentlistList = appointmentlistList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public Appointmentadapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.appointmentlist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Appointmentadapter.ViewHolder holder, int position) {
        appointmentlist appointmentlist=appointmentlistList.get(position);
        holder.doctorname.setText(appointmentlist.getDoctorName());
        holder.doctorphone.setText(appointmentlist.getDoctorPhone());
        holder.doctortime.setText(appointmentlist.getTime());
        holder.meetingid.setText(appointmentlist.getMeetingID());
        holder.meetinglinkcopy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard=(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip=ClipData.newPlainText("CouponText", appointmentlist.getMeetingID().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context.getApplicationContext(), "copied", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return appointmentlistList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView doctorname;
        TextView meetingid;
        TextView doctorphone;
        TextView doctortime;
        Button meetinglinkcopy_btn;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            doctorname= itemView.findViewById(R.id.doctorappointname_tv);
            meetingid= itemView.findViewById(R.id.doctor_meeting_id_tv);
            doctorphone= itemView.findViewById(R.id.doctorappointphonetv);
            doctortime= itemView.findViewById(R.id.doctortime_tv);
            meetinglinkcopy_btn= itemView.findViewById(R.id.meeting_link_copy_button);
        }
    }
    public interface RecyclerViewClickListener{
        void onClick(View v,int position);

    }
}
