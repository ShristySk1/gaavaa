package com.ayata.purvamart.ui.Fragment.shop.stories;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/*
used with viewpager2 with fragments
mostly used with tab layout
*/
public class ViewPagerAdapter2 extends FragmentStateAdapter {
    public ViewPagerAdapter2(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new FragmentStory();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
