package com.example.track_my_money.views.Activities.SignScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.track_my_money.R;
import com.example.track_my_money.databinding.ActivityLoginScreenBinding;

public class LoginScreen extends AppCompatActivity {
    ActivityLoginScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityLoginScreenBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.createButton.setOnClickListener(c->{
            Intent intent= new Intent(LoginScreen.this,SignUpScreen.class);
            startActivity(intent);
        });
        binding.forgetPassword.setOnClickListener(c->{
            Intent intent= new Intent(LoginScreen.this,MakeSlection.class);
            startActivity(intent);
        });
    }
}