package com.ayata.purvamart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayata.purvamart.Fragment.FragmentAccount;
import com.ayata.purvamart.Fragment.FragmentCart;
import com.ayata.purvamart.Fragment.FragmentCartEmpty;
import com.ayata.purvamart.Fragment.FragmentCartFilled;
import com.ayata.purvamart.Fragment.FragmentCategory;
import com.ayata.purvamart.Fragment.FragmentEditAddress;
import com.ayata.purvamart.Fragment.FragmentEditProfile;
import com.ayata.purvamart.Fragment.FragmentEmptyOrder;
import com.ayata.purvamart.Fragment.FragmentListOrder;
import com.ayata.purvamart.Fragment.FragmentMyOrder;
import com.ayata.purvamart.Fragment.FragmentPayment;
import com.ayata.purvamart.Fragment.FragmentPrivacyPolicy;
import com.ayata.purvamart.Fragment.FragmentProduct;
import com.ayata.purvamart.Fragment.FragmentShop;
import com.ayata.purvamart.Fragment.FragmentThankyou;
import com.ayata.purvamart.Fragment.FragmentTrackOrder;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    private String TAG = "MainActivity";
    BottomNavigationView bottomnav;
    View toolbar;
    RelativeLayout toolbarType1, toolbarType2, toolbarType3;
    ProgressBar progressBar;
    FragmentManager manager;
    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.appbar_main);
        toolbarType1 = toolbar.findViewById(R.id.appbar1);
        toolbarType2 = toolbar.findViewById(R.id.appbar2);
        toolbarType3 = toolbar.findViewById(R.id.appbar3);
        progressBar = findViewById(R.id.main_progressbar);
        showToolbar();

        setToolbarType1(true);
        Log.d("checkpreference", "onCreate: " + PreferenceHandler.getEmail(this));
        Log.d("checkpreference", "onCreate: " + PreferenceHandler.getPhone(this));
        Log.d("checkpreference", "onCreate: " + PreferenceHandler.getToken(this));

        manager = getSupportFragmentManager();
        manager.addOnBackStackChangedListener(this);
        bottomnav = findViewById(R.id.main_bottom_navigation);
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_main);
//        NavigationUI.setupWithNavController(bottomnav, navController);
        NavController navController ;
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_main);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomnav,navController);

        showBottomNavBar(true);
        }

    public Fragment getFragmentForBundle(int fragamentIndex) {
        return fragmentList.get(fragamentIndex);
    }


    public void hideToolbar() {
        toolbar.setVisibility(View.GONE);
    }

    public void showToolbar() {
        toolbar.setVisibility(View.VISIBLE);
    }

    public void setToolbarType1(Boolean notification_dot_visible) {

        toolbarType1.setVisibility(View.VISIBLE);
        toolbarType2.setVisibility(View.GONE);
        toolbarType3.setVisibility(View.GONE);

        RelativeLayout notification_layout;
        notification_layout = toolbar.findViewById(R.id.notification_layout);

        View notification_dot;
        notification_dot = toolbar.findViewById(R.id.notification_dot);

        if (notification_dot_visible)
            notification_dot.setVisibility(View.VISIBLE);
        else notification_dot.setVisibility(View.GONE);

    }

    public void setToolbarType2(String title, Boolean likeIcon, Boolean filterIcon) {

        toolbarType1.setVisibility(View.GONE);
        toolbarType2.setVisibility(View.VISIBLE);
        toolbarType3.setVisibility(View.GONE);

        TextView text;
        ImageButton back;
        ImageView like, filter;

        text = toolbar.findViewById(R.id.text_header);
        back = toolbar.findViewById(R.id.back);
        like = toolbar.findViewById(R.id.like);
        filter = toolbar.findViewById(R.id.filter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (!likeIcon) {
            like.setVisibility(View.GONE);
        } else {
            like.setVisibility(View.VISIBLE);
        }

        if (!filterIcon) {
            filter.setVisibility(View.GONE);
        } else {
            filter.setVisibility(View.VISIBLE);
        }

        text.setText(title);

        like.setTag(R.drawable.ic_like_outline);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (like.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.ic_like_filled).getConstantState()))
                    like.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_outline));
                else
                    like.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_filled));

                //        <<sdk 21+ android5>>
                if ((int) like.getTag() == R.drawable.ic_like_outline) {
                    like.setTag(R.drawable.ic_like_filled);
                    like.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_filled));
                } else {
                    like.setTag(R.drawable.ic_like_outline);
                    like.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_outline));
                }
            }
        });


    }

    public void setToolbarType3(String title) {
        toolbarType1.setVisibility(View.GONE);
        toolbarType2.setVisibility(View.GONE);
        toolbarType3.setVisibility(View.VISIBLE);

        TextView text = toolbar.findViewById(R.id.title);
        text.setText(title);

    }

    public void showBottomNavBar(Boolean visible) {
        if (visible) {
            bottomnav.setVisibility(View.VISIBLE);
        } else {
            bottomnav.setVisibility(View.GONE);
        }
    }

    public void changeFragment(int fragmentIndex, String tag, Bundle bundle) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment oldFragment = fragmentManager.findFragmentByTag(tag);
//        //if oldFragment already exits in fragmentManager use it
//        if (oldFragment != null) {
//            selectedFragment = oldFragment;
//            Log.d(TAG, "changeFragment: old fragment detcted");
//        }else {
//            Log.d(TAG, "changeFragment: new fragment not  detcted");
//        }
//


//        if (fragmentIndex == 0 || fragmentIndex == 1 || fragmentIndex == 2 || fragmentIndex == 11) {
//            if (bundle != null) {
//                getFragmentForBundle(fragmentIndex).setArguments(bundle);
//            }
//            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragmentList.get(fragmentIndex)).commit();
//        } else {
//            if (bundle != null) {
//                getFragmentForBundle(fragmentIndex).setArguments(bundle);
//            }
//            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragmentList.get(fragmentIndex)).addToBackStack(tag).commit();
//        }



//        manager.beginTransaction()
//                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
//                .replace(R.id.main_fragment, selectedFragment)
//                .addToBackStack(tag).commit();
    }

    public void selectMyOrderFragment() {
//        getSupportFragmentManager().popBackStack(FragmentPayment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        changeFragment(2, FragmentMyOrder.TAG, null);
//        bottomnav.setSelectedItemId(R.id.nav_order);

    }

    public void selectCartFragment() {
        Intent i = new Intent(this, MainActivity.class);
// set the new task and clear flags
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
//        bottomnav.setSelectedItemId(R.id.nav_shop);
//        getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
//                .replace(R.id.main_fragment, new FragmentCart())
//                .addToBackStack("cart").commit();
    }

    public void selectShopFragment() {
//        bottomnav.setSelectedItemId(R.id.nav_shop);
//        changeFragment(0, FragmentShop.TAG, null);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackStackChanged() {
        int count = manager.getBackStackEntryCount();
        for (int i = count - 1; i >= 0; i--) {
            Log.d(TAG, "onBackStackChanged: " + manager.getBackStackEntryAt(i).getName());
        }
        Log.d(TAG, "onBackStackChanged: " + count);

    }

//    @Override
//    public void onBackPressed() {
////        int fragments = getSupportFragmentManager().getBackStackEntryCount();
////        if (fragments == 1) {
//////            this.finish();
////        } else {
////            super.onBackPressed();
////        }
//                    super.onBackPressed();
//
//    }
}