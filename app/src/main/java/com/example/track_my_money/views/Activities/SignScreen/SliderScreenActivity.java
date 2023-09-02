package com.example.track_my_money.views.Activities.SignScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.track_my_money.Adapters.ViewPagerAdapter;
import com.example.track_my_money.R;
import com.example.track_my_money.views.Activities.MainActivity;
import com.example.track_my_money.views.Activities.SplashScreenActivity;

public class SliderScreenActivity extends AppCompatActivity {
    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;
    Button backbtn, nextbtn, skipbtn;

    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_screen);


        backbtn = findViewById(R.id.backbtn);
        nextbtn = findViewById(R.id.nextbtn);
        skipbtn = findViewById(R.id.skipButton);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getitem(0) > 0){

                    mSLideViewPager.setCurrentItem(getitem(-1),true);

                }

            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getitem(0) < 3)
                    mSLideViewPager.setCurrentItem(getitem(1),true);
                else {

                    Intent i = new Intent(getApplicationContext(),LoginScreen.class);


                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View, String>(findViewById(R.id.nextbtn), "transition_login");


                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SliderScreenActivity.this, pairs);
                        startActivity(i, options.toBundle());
                    } else {
                        startActivity(i);
                    }

                    finish();

                }

            }
        });

        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getApplicationContext(), MainActivity.class);


                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(findViewById(R.id.skipButton), "transition_login");


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SliderScreenActivity.this, pairs);
                    startActivity(i, options.toBundle());
                } else {
                    startActivity(i);
                }
                finish();

            }
        });

        mSLideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.indicator_layout);

        viewPagerAdapter = new ViewPagerAdapter(this);

        mSLideViewPager.setAdapter(viewPagerAdapter);

        setUpindicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);

    }

    public void setUpindicator(int position){

        dots = new TextView[4];
        mDotLayout.removeAllViews();

        for (int i = 0 ; i < dots.length ; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dots[i].setTextColor(getResources().getColor(R.color.inactive,getApplicationContext().getTheme()));
            }
            mDotLayout.addView(dots[i]);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dots[position].setTextColor(getResources().getColor(R.color.active,getApplicationContext().getTheme()));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setUpindicator(position);

            if (position > 0){

                backbtn.setVisibility(View.VISIBLE);

            }else {

                backbtn.setVisibility(View.INVISIBLE);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getitem(int i){

        return mSLideViewPager.getCurrentItem() + i;
    }

    }
