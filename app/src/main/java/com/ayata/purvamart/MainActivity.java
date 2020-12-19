package com.ayata.purvamart;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayata.purvamart.Fragment.FragmentAccount;
import com.ayata.purvamart.Fragment.FragmentCart;
import com.ayata.purvamart.Fragment.FragmentMyOrder;
import com.ayata.purvamart.Fragment.FragmentShop;
import com.ayata.purvamart.Fragment.FragmentTrackOrder;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomnav;
    View toolbar;
    RelativeLayout toolbarType1, toolbarType2, toolbarType3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.appbar_main);
        toolbarType1 = toolbar.findViewById(R.id.appbar1);
        toolbarType2 = toolbar.findViewById(R.id.appbar2);
        toolbarType3 = toolbar.findViewById(R.id.appbar3);

        showToolbar();

        setToolbarType1();

        if (findViewById(R.id.main_fragment) != null) {

            if (savedInstanceState != null) {
                return;
            }

            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                    .replace(R.id.main_fragment, new FragmentShop())
                    .commit();

        }

        bottomnav = findViewById(R.id.main_bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navListener);

        showBottomNavBar(true);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_shop:
                            //samepagex
                            setToolbarType1();
                            selectedFragment = new FragmentShop();
                            break;

                        case R.id.nav_cart:
                            setToolbarType3("Cart");
                            selectedFragment = new FragmentCart();
                            break;

                        case R.id.nav_order:
                            setToolbarType3("My Order");
                            selectedFragment = new FragmentMyOrder();
                            break;

                        case R.id.nav_account:
                            setToolbarType3("Account");
                            selectedFragment = new FragmentAccount();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                            .replace(R.id.main_fragment, selectedFragment)
                            .addToBackStack(null).commit();

                    return true;

                }

            };

    public void hideToolbar() {
        toolbar.setVisibility(View.GONE);
    }

    public void showToolbar() {
        toolbar.setVisibility(View.VISIBLE);
    }

    public void setToolbarType1() {

        toolbarType1.setVisibility(View.VISIBLE);
        toolbarType2.setVisibility(View.GONE);
        toolbarType3.setVisibility(View.GONE);

        ImageView notification, tag;
        notification = toolbar.findViewById(R.id.notification);
        tag = toolbar.findViewById(R.id.tag);

    }

    public void setToolbarType2(String title, Boolean shareIcon) {

        toolbarType1.setVisibility(View.GONE);
        toolbarType2.setVisibility(View.VISIBLE);
        toolbarType3.setVisibility(View.GONE);

        TextView text;
        ImageButton back;
        ImageView share;

        text = toolbar.findViewById(R.id.text_header);
        back = toolbar.findViewById(R.id.back);
        share = toolbar.findViewById(R.id.share);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (!shareIcon) {
            share.setVisibility(View.GONE);
        }

        text.setText(title);

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

    public void changeFragment(Fragment selectedFragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.main_fragment, selectedFragment)
                .addToBackStack(null).commit();
    }

    public void selectMyOrderFragment(){

        bottomnav.setSelectedItemId(R.id.nav_order);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.main_fragment, new FragmentMyOrder())
                .addToBackStack("myOrder").commit();
    }

    public void selectCartFragment(){

        bottomnav.setSelectedItemId(R.id.nav_cart);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.main_fragment, new FragmentCart())
                .addToBackStack("cart").commit();
    }
}