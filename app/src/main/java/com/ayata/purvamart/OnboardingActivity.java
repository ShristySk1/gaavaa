package com.ayata.purvamart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rd.PageIndicatorView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;


public class OnboardingActivity extends AppCompatActivity {
    // creating object of ViewPager
    ViewPager2 mViewPager;
    PageIndicatorView pageIndicatorView;
    // images array
    int[] images = {R.drawable.signup, R.drawable.signup, R.drawable.signup};

    // Creating Object of ViewPagerAdapter
    ViewPagerAdapter mViewPagerAdapter;
    TextView tvGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        // Initializing the ViewPager Object
        mViewPager = findViewById(R.id.viewPager);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        tvGetStarted=findViewById(R.id.tvGetStarted);
        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerAdapter(images);
        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
                if(position==images.length-1){
                    tvGetStarted.setVisibility(View.VISIBLE);
                }else{
                    tvGetStarted.setVisibility(View.GONE);
                }
            }
        });
        tvGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnboardingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}