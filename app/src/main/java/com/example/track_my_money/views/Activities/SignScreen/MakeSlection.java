package com.example.track_my_money.views.Activities.SignScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.track_my_money.R;
import com.example.track_my_money.databinding.ActivityMakeSlectionBinding;

public class MakeSlection extends AppCompatActivity {
    ActivityMakeSlectionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMakeSlectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.phoneSelection.setOnClickListener(c->{
            Intent intent= new Intent(MakeSlection.this,VerifcationOTP.class);
            startActivity(intent);
        });

        binding.mailSelection.setOnClickListener(c->{
            Intent intent= new Intent(MakeSlection.this,ForgetPassword.class);
            startActivity(intent);
        });
    }
}