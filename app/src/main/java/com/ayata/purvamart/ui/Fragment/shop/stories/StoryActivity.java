package com.ayata.purvamart.ui.Fragment.shop.stories;

import android.os.Bundle;

import com.ayata.purvamart.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class StoryActivity extends AppCompatActivity {
    //stories
    ViewPagerAdapter2 viewPagerAdapter2;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_story);
        viewPager2 = findViewById(R.id.viewPager2);
        viewPagerAdapter2=new ViewPagerAdapter2(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(viewPagerAdapter2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
//                viewPagerAdapter2.notifyDataSetChanged();
//                viewPager2.setCurrentItem( position);
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }
}