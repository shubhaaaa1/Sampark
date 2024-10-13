package com.example.sampark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sampark.Adapter.Chatadapter;
import com.example.sampark.Models.message;
import com.example.sampark.databinding.ActivityChatdetailactivityBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class Chatdetailactivity extends AppCompatActivity {
    ActivityChatdetailactivityBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityChatdetailactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database= FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

       final  String sender = auth.getUid();
        String reciever = getIntent().getStringExtra("userId");
        String username = getIntent().getStringExtra("userName");
        String profilepic =getIntent().getStringExtra("profilePic");

        binding.username.setText(username);
        Picasso.get().load(profilepic).placeholder(R.drawable.userr).into(binding.profilepic);

        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chatdetailactivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        final ArrayList<message> message= new ArrayList<>();
        final Chatadapter chatadapter= new Chatadapter(message,this);
        binding.chatrecycler.setAdapter(chatadapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatrecycler.setLayoutManager(layoutManager);
        final String senderroom = sender+ reciever;
        final String recieverroom = reciever+ sender;


        database.getReference().child("chats").child(senderroom).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        message.clear();
                        for ( DataSnapshot snapshot1: snapshot.getChildren()
                             ) {
                            message model = snapshot1.getValue(message.class);
                            message.add(model);

                        }
                        chatadapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

        getSupportActionBar().hide();


        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.etmessage.getText().toString();
                final message model=new message(sender,message);
                model.setTimestamp(new Date().getTime());
                binding.etmessage.setText("");

                database.getReference().child("chats")
                        .child(senderroom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                database.getReference().child("chats").child(recieverroom)
                                        .push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        });
                            }
                        });
            }
        });





    }
}