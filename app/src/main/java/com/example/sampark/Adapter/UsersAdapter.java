package com.example.sampark.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampark.Chatdetailactivity;
import com.example.sampark.Models.Users;
import com.example.sampark.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class UsersAdapter extends  RecyclerView.Adapter<UsersAdapter.ViewHolder> {

     ArrayList<Users> list;
     Context context;

    public UsersAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Users users =list.get(position);
        Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.userr).into(holder.image);
        holder.username.setText(users.getUsername());
        FirebaseDatabase.getInstance().getReference().child("chats")
                        .child(FirebaseAuth.getInstance().getUid()+ users.getUserid())
                                .orderByChild("timestamp")
                                        .limitToLast(1)
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if(snapshot.hasChildren()){
                                                            for (DataSnapshot snapshot1:snapshot.getChildren()
                                                                 ) {
                                                                holder.lastmessage.setText(snapshot1.child("textmessage")
                                                                        .getValue().toString());
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Chatdetailactivity.class);
                intent.putExtra("userId",users.getUserid());
                intent.putExtra("profilePic",users.getProfilepic());
                intent.putExtra("userName",users.getUsername());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView username,lastmessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.profile_image);
            username= itemView.findViewById(R.id.Username);
            lastmessage= itemView.findViewById(R.id.lmeassage);

        }
    }
}
