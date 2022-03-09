package com.example.healthbuddy.healthtips;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthbuddy.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HealthAdapter extends RecyclerView.Adapter<HealthAdapter.ViewHolder> {
    private List<Health>healthList;
    private Context context;
    private IDeleteHealths deleteInterface;

    public HealthAdapter(List<Health> healthList, Context context) {
        this.healthList = healthList;
        this.context = context;
        deleteInterface = (IDeleteHealths) context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_health, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HealthAdapter.ViewHolder holder, int position) {
        Health health=healthList.get(position);
        holder.stdName.setText(health.getName());
        holder.stdInstructions.setText(health.getInstructions());
        holder.stdFrequency.setText(health.getFrequency());
        holder.stdDate.setText(health.getDate());


    }

    @Override
    public int getItemCount() {
        return healthList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stdName, stdInstructions, stdFrequency, stdDate;
        Button btnEdit, btnDelete;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            stdName = itemView.findViewById(R.id.txt_medicine_name);
            stdInstructions = itemView.findViewById(R.id.txt_instructions);
            stdFrequency = itemView.findViewById(R.id.txt_frequency);
            stdDate = itemView.findViewById(R.id.txt_start_day);
            btnEdit=itemView.findViewById(R.id.btn_edt);
            btnDelete=itemView.findViewById(R.id.btn_delete);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,HealthTipActivity.class);
                    intent.putExtra("isEditData",true);
                    intent.putExtra("stdId",healthList.get(getAdapterPosition()).getId());
                    context.startActivity(intent);

                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to delete?")
                            .setTitle("wARning")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    deleteInterface.delete(getAdapterPosition(),healthList.get(getAdapterPosition()).getId());
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }

            });

        }

    }
    public interface IDeleteHealths {
        void delete(int position, int healthId);
    }
}
