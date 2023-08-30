package com.example.track_my_money.views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.track_my_money.R;
import com.example.track_my_money.databinding.ActivitySplashScreenBinding;
import com.example.track_my_money.views.Activities.SignScreen.SliderScreenActivity;

public class SplashScreenActivity extends AppCompatActivity {
    // animation variable

    private static  int SPLASH_SCREEN=4000;
    Animation topAnim , bottomAnim;
    ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Animation initalize
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this ,R.anim.bottom_animation);

      // add the imagination
        binding.splashLogo.setAnimation(topAnim);
        binding.splashIcon.setAnimation(bottomAnim);
        binding.subtitle.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this , SliderScreenActivity.class );
              startActivity(intent);
              finish();
            }
        },SPLASH_SCREEN);


    }
}