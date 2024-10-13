package com.example.sampark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Toast;

import com.example.sampark.Models.Users;
import com.example.sampark.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
     ActivitySignupBinding binding;
     private FirebaseAuth auth;
     private FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        auth =FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        progressDialog= new ProgressDialog(SignupActivity.this);
        progressDialog.setTitle("Creating your account ");
        progressDialog.setMessage("we are creating your account");

        binding.btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                auth.createUserWithEmailAndPassword(
                        binding.tvemail.getText().toString(),binding.tvPassword.getText().toString()
                ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                  if(task.isSuccessful()){
                      Users user = new Users(binding.tvuser.getText().toString(),binding.tvemail.getText().toString(),
                              binding.tvPassword.getText().toString());
                      String id = task.getResult().getUser().getUid();
                      database.getReference().child("Users").child(id).setValue(user);
                      Toast.makeText(SignupActivity.this, "Registration completed successfully", Toast.LENGTH_SHORT).show();
                      Intent intent=new Intent(SignupActivity.this,MainActivity.class);
                      startActivity(intent);
                  }
                  else{
                      Toast.makeText(SignupActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                  }
                    }
                });
            }
        });
        binding.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SignupActivity.this,SigninActivity.class);
                startActivity(intent);
            }
        });
    }
}