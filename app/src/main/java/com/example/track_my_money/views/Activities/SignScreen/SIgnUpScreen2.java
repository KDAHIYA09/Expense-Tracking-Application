package com.example.track_my_money.views.Activities.SignScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.example.track_my_money.R;
import com.example.track_my_money.databinding.ActivitySignUpScreen2Binding;

public class SIgnUpScreen2 extends AppCompatActivity {
    ActivitySignUpScreen2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpScreen2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.signupNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SignUp3.class);


                //Add Shared Animation
                Pair[] pairs = new Pair[5];

                pairs[0] = new Pair<View, String>(binding.signupBackButton, "transition_back_arrow_btn");
                pairs[1] = new Pair<View, String>(binding.signupNextButton, "transition_next_btn");
                pairs[2] = new Pair<View, String>(binding.signupLoginButton, "transition_login_btn");
                pairs[3] = new Pair<View, String>(binding.signupTitleText, "transition_title_text");
                pairs[4] = new Pair<View, String>(binding.signupSlideText, "transition_slide_text");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SIgnUpScreen2.this, pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });
        binding.signupLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SIgnUpScreen2.this, LoginScreen.class));
                finish();
            }
        });
    }
}