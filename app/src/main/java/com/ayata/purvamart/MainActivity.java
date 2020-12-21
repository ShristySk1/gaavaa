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
import com.ayata.purvamart.Fragment.FragmentPayment;
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

        setToolbarType1(true);

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
                    String stack_text=null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_shop:
                            //samepagex
                            setToolbarType1(true);
                            stack_text="shop";
                            selectedFragment = new FragmentShop();
                            break;

                        case R.id.nav_cart:
                            setToolbarType3("Cart");
                            stack_text="cart";
                            selectedFragment = new FragmentCart();
                            break;

                        case R.id.nav_order:
                            setToolbarType3("My Order");
                            stack_text="myOrder";
                            selectedFragment = new FragmentMyOrder();
                            break;

                        case R.id.nav_account:
                            setToolbarType3("Account");
                            stack_text="account";
                            selectedFragment = new FragmentAccount();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                            .replace(R.id.main_fragment, selectedFragment)
                            .addToBackStack(stack_text).commit();

                    return true;

                }

            };

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
        notification_dot= toolbar.findViewById(R.id.notification_dot);

        if(notification_dot_visible)
        notification_dot.setVisibility(View.VISIBLE);
        else notification_dot.setVisibility(View.GONE);

    }

    public void setToolbarType2(String title, Boolean likeIcon,Boolean filterIcon) {

        toolbarType1.setVisibility(View.GONE);
        toolbarType2.setVisibility(View.VISIBLE);
        toolbarType3.setVisibility(View.GONE);

        TextView text;
        ImageButton back;
        ImageView like,filter;

        text = toolbar.findViewById(R.id.text_header);
        back = toolbar.findViewById(R.id.back);
        like = toolbar.findViewById(R.id.like);
        filter= toolbar.findViewById(R.id.filter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (!likeIcon) {
            like.setVisibility(View.GONE);
        }else{like.setVisibility(View.VISIBLE);}

        if (!filterIcon) {
            filter.setVisibility(View.GONE);
        }else{filter.setVisibility(View.VISIBLE);}

        text.setText(title);

        like.setTag(R.drawable.ic_like_outline);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(like.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.ic_like_filled).getConstantState()))
                like.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_outline));
                else
                    like.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_filled));

                //        <<sdk 21+ android5>>
                if((int)like.getTag()== R.drawable.ic_like_outline){
                    like.setTag(R.drawable.ic_like_filled);
                    like.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_filled));
                }else{
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

    public void selectShopFragment() {

        bottomnav.setSelectedItemId(R.id.nav_shop);
        changeFragment(new FragmentShop());
    }

}