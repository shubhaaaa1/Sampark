package com.example.sampark;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sampark.Models.Users;
import com.example.sampark.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Settings extends AppCompatActivity {
    ActivitySettingsBinding binding;
    FirebaseDatabase database;
    FirebaseStorage storage;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();

        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
            }
        });


        binding.backarroww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
            }
        });

        database.getReference().child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users users = snapshot.getValue(Users.class);
                        if (users != null) {
                            Picasso.get().load(users.getProfilepic())
                                    .placeholder(R.drawable.userr
                                    ).into(binding.add);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (data != null) {
                Uri imageURI = data.getData();
                binding.add.setImageURI(imageURI);

                if (storage != null) {
                    final StorageReference reference = storage.getReference().child("Profile picture")
                            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));


                    reference.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String profilePicUrl = uri.toString();
                                    Users users = new Users(); // Assuming you have a Users model
                                    users.setProfilepic(profilePicUrl);
                                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                            .child("Profile picture").setValue(profilePicUrl);
                                }
                            });
                        }
                    });
                }
            }
        }
    }
}