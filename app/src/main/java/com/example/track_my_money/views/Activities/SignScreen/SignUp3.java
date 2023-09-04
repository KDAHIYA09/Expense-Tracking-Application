package com.example.track_my_money.views.Activities.SignScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.example.track_my_money.R;
import com.example.track_my_money.databinding.ActivitySignUp3Binding;

public class SignUp3 extends AppCompatActivity {
    ActivitySignUp3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivitySignUp3Binding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        setContentView(binding.getRoot());

        binding.signupNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VerifcationOTP.class);
//Add Transition
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(binding.signup3rdScreenScrollView, "transition_OTP_screen");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3.this, pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }

            }
        });



    }
}