package com.example.sampark.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampark.Models.message;
import com.example.sampark.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Chatadapter extends  RecyclerView.Adapter{
    ArrayList<com.example.sampark.Models.message> message;
    Context context;

    public Chatadapter(ArrayList<com.example.sampark.Models.message> message, Context context) {
        this.message = message;
        this.context = context;
    }
   int Sender_view_type=1;
    int Reciever_view_type=0;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      if(viewType == Sender_view_type){
          View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
          return new SenderViewholder(view);
      }
      else{
          View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
          return new RecieverViewholder(view);
      }
    }

    @Override
    public int getItemViewType(int position) {
     if(message.get(position).getUid().equals(FirebaseAuth.getInstance().getUid())){
         return Sender_view_type;
     }
     else{
         return  Reciever_view_type;
     }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      message message1 = message.get(position);
      if(holder.getClass()== SenderViewholder.class){
          ((SenderViewholder) holder).sendermsg.setText(message1.getTextmessage());

      }
      else{
          ((RecieverViewholder)holder).recievermsg.setText(message1.getTextmessage());

      }
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    public class RecieverViewholder extends RecyclerView.ViewHolder{
        TextView recievermsg, recievertime;
        public RecieverViewholder(@NonNull View itemView) {
            super(itemView);
            recievermsg=itemView.findViewById(R.id.recieverText);
            recievertime=itemView.findViewById(R.id.recieverTime);
        }
    }
    public class SenderViewholder extends  RecyclerView.ViewHolder{
           TextView sendermsg, sendertime;
        public SenderViewholder(@NonNull View itemView) {
            super(itemView);
            sendermsg=itemView.findViewById(R.id.sendertext);
            sendertime=itemView.findViewById(R.id.sendertime);
        }
    }
}
