package com.ayata.purvamart.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.rd.PageIndicatorView;

import java.lang.ref.WeakReference;

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
        new ApiClient(new WeakReference<>(getApplicationContext()));

        // Initializing the ViewPager Object
        mViewPager = findViewById(R.id.viewPager);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        tvGetStarted = findViewById(R.id.tvGetStarted);
        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerAdapter(images);
        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
                if (position == images.length - 1) {
                    tvGetStarted.setVisibility(View.VISIBLE);
                } else {
                    tvGetStarted.setVisibility(View.GONE);
                }
            }
        });
        tvGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnboardingActivity.this, PortalActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (PreferenceHandler.isUserAlreadyLoggedIn(this)) {
            Intent intent = (new Intent(OnboardingActivity.this, MainActivity.class));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}