package com.example.sampark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sampark.Adapter.Fragmentsadapter;
import com.example.sampark.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
   ActivityMainBinding binding;
   FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        auth=FirebaseAuth.getInstance();
        setContentView(binding.getRoot());
        binding.viewpg.setAdapter(new Fragmentsadapter(getSupportFragmentManager()));
        binding.tablayout.setupWithViewPager(binding.viewpg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                auth.signOut();
                Intent intent = new Intent(MainActivity.this,SigninActivity.class);
                startActivity(intent);

                break;
            case R.id.settings:
                Toast.makeText(this, "settings clicked", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(MainActivity.this,Settings.class);
                startActivity(intent1);
                break;
        }
        return true;
    }
}