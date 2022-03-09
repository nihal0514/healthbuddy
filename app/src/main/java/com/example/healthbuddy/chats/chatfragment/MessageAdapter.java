package com.example.healthbuddy.chats.chatfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthbuddy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    @NonNull
    FirebaseUser fUser;
    private static final int MSG_TYPE_LEFT=0;
    private static final int MSG_TYPE_RIGHT=1;


    private List<Chats> ChatsList;
    private Context context;
    private RecyclerViewClickListener listener;
    private String imageurl;

    public MessageAdapter(@NonNull List<Chats> ChatsList, Context context, RecyclerViewClickListener listener,String imageurl) {
        this.ChatsList = ChatsList;
        this.context = context;
        this.listener = listener;
        this.imageurl=imageurl;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RIGHT){
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right,parent,false));

        }else{
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left,parent,false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chats chats=ChatsList.get(position);
        holder.show_message.setText((Utility.Decrypt1(chats.getMessage(),11)));
        Picasso.get().load(imageurl).placeholder(R.drawable.profile).into(holder.profile_image);
    }

    @Override
    public int getItemCount() {
        return ChatsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile_image;
        TextView show_message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image=itemView.findViewById(R.id.profile_image);
            show_message=itemView.findViewById(R.id.show_message);

        }
    }

    @Override
    public int getItemViewType(int position) {
        fUser= FirebaseAuth.getInstance().getCurrentUser();
        if(ChatsList.get(position).getSender().equals(fUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }

    }

    public interface RecyclerViewClickListener{
        void onClick(View v,int position);

    }
}

