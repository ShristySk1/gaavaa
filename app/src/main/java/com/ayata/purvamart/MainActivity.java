package com.ayata.purvamart;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayata.purvamart.data.Model.ModelCategory;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.helper.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.data.network.response.UserCartResponse;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.data.repository.Repository;
import com.ayata.purvamart.ui.Fragment.account.FragmentAccount;
import com.ayata.purvamart.ui.Fragment.account.FragmentDeliveryAddress;
import com.ayata.purvamart.ui.Fragment.account.FragmentEditAddress;
import com.ayata.purvamart.ui.Fragment.account.FragmentEditProfile;
import com.ayata.purvamart.ui.Fragment.account.FragmentPrivacyPolicy;
import com.ayata.purvamart.ui.Fragment.cart.FragmentCart;
import com.ayata.purvamart.ui.Fragment.cart.FragmentCartEmpty;
import com.ayata.purvamart.ui.Fragment.cart.FragmentCartFilled;
import com.ayata.purvamart.ui.Fragment.order.FragmentEmptyOrder;
import com.ayata.purvamart.ui.Fragment.order.FragmentListOrder;
import com.ayata.purvamart.ui.Fragment.order.FragmentMyOrder;
import com.ayata.purvamart.ui.Fragment.order.FragmentOrderSummary;
import com.ayata.purvamart.ui.Fragment.order.FragmentTrackOrder;
import com.ayata.purvamart.ui.Fragment.payment.FragmentPayment2;
import com.ayata.purvamart.ui.Fragment.payment.FragmentThankyou;
import com.ayata.purvamart.ui.Fragment.shop.FragmentCategory;
import com.ayata.purvamart.ui.Fragment.shop.FragmentProduct;
import com.ayata.purvamart.ui.Fragment.shop.FragmentShop;
import com.ayata.purvamart.ui.Fragment.shop.SearchFragment;
import com.ayata.purvamart.ui.Fragment.unused.FragmentPayment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.ayata.purvamart.ui.Fragment.shop.FragmentShop.SELECTED_CATEGORY;
import static com.ayata.purvamart.utils.BadgeDrawable.setBadgeCount;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener, NetworkResponseListener<JsonObject> {
    private String TAG = "MainActivity";
    BottomNavigationView bottomnav;
    View toolbar;
    RelativeLayout toolbarType1, toolbarType2, toolbarType3;
    ProgressBar progressBar;
    FragmentManager manager;
    List<Fragment> fragmentList = new ArrayList<>();
    static int badgeCount = 1;
    //cart and badge
    ImageView itemCart;
    //back button when came from search view
    static Boolean isFromSearchView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //for internet checking
        new ApiClient(new WeakReference<>(getApplicationContext()));
        CartCount.addMyBooleanListener(new MainActivity.cartCountChangeListener() {
            @Override
            public void onCartCountChange(Integer count) {
                if (count != null) {
                    Log.d(TAG, "onCartCountChange: " + count);
//                    setBadge(String.valueOf(count));
                    Log.d(TAG, "setBadge: badgecalled");
                    LayerDrawable icon = (LayerDrawable) itemCart.getDrawable();
                    setBadgeCount(MainActivity.this, icon, count + "");
                    setBadgeCount(MainActivity.this, icon, count + "");
                    Log.d(TAG, "setBadge: badgedone");
                }
            }
        });
        toolbar = findViewById(R.id.appbar_main);
        toolbarType1 = toolbar.findViewById(R.id.appbar1);
        //image and badge
        itemCart = toolbar.findViewById(R.id.notification);

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
        bottomnav.setOnNavigationItemSelectedListener(navListener);
        addAllFragments();


        //product from searchactivity
        ProductDetail productDetail = (ProductDetail) getIntent().getSerializableExtra("key");
        if (productDetail != null) {
            isFromSearchView = true;
            Bundle bundle = new Bundle();
            bundle.putSerializable(FragmentProduct.MODEL_ITEM, productDetail);
            changeFragment(8, FragmentProduct.TAG, bundle);
            return;
        }
        //category from searchactivity
        ModelCategory modelCategory = (ModelCategory) getIntent().getSerializableExtra("key2");
        if (modelCategory != null) {
            isFromSearchView = true;
            Bundle bundle = new Bundle();
            bundle.putSerializable(SELECTED_CATEGORY, modelCategory);
            changeFragment(9, FragmentCategory.TAG, bundle);
            return;
        }

        showBottomNavBar(true);
        if (findViewById(R.id.main_fragment) != null) {
            if (savedInstanceState != null) {
                return;
            }
            changeFragment(0, FragmentShop.TAG, null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void addAllFragments() {
        fragmentList.add(new FragmentShop());//0
        fragmentList.add(new FragmentCart());//1
        fragmentList.add(new FragmentMyOrder());//2
        fragmentList.add(new FragmentListOrder());//3
        fragmentList.add(new FragmentEmptyOrder());//4
        fragmentList.add(new FragmentCart());//5
        fragmentList.add(new FragmentCartEmpty());//6
        fragmentList.add(new FragmentCartFilled());//7
        fragmentList.add(new FragmentProduct());//8
        fragmentList.add(new FragmentCategory());//9
        fragmentList.add(new FragmentTrackOrder());//10
        fragmentList.add(new FragmentAccount());//11
        fragmentList.add(new FragmentEditAddress());//12
        fragmentList.add(new FragmentEditProfile());//13
        fragmentList.add(new FragmentPrivacyPolicy());//14
        fragmentList.add(new FragmentPayment());//15
        fragmentList.add(new FragmentThankyou());//16
        fragmentList.add(new FragmentPayment2());//17
        fragmentList.add(new FragmentDeliveryAddress());//18
        fragmentList.add(new FragmentEditAddress());//19//for add
        fragmentList.add(new FragmentOrderSummary());//20
        fragmentList.add(new SearchFragment());//21//search


    }

    public Fragment getFragmentForBundle(int fragamentIndex) {
        return fragmentList.get(fragamentIndex);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    int selectedFragment = 0;
                    String stack_text = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_shop:
                            //samepagex
                            setToolbarType1(true);
                            stack_text = FragmentShop.TAG;
                            selectedFragment = 0;
                            break;

                        case R.id.nav_cart:
                            setToolbarType3("Cart");
                            stack_text = FragmentCart.TAG;
                            selectedFragment = 1;
                            break;

                        case R.id.nav_order:
                            setToolbarType3("My Order");
                            stack_text = FragmentMyOrder.TAG;
                            selectedFragment = 2;
                            break;

                        case R.id.nav_account:
                            setToolbarType3("Account");
                            stack_text = FragmentAccount.TAG;
                            selectedFragment = 11;
                            break;

                    }
//setItemCart();
                    changeFragment(selectedFragment, stack_text, null);
//
//                    getSupportFragmentManager().beginTransaction()
//                            .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
//                            .replace(R.id.main_fragment, selectedFragment).commit();

                    return true;

                }

            };

    public void hideToolbar() {
        toolbar.setVisibility(View.INVISIBLE);
    }

    public void showToolbar() {
        toolbar.setVisibility(View.VISIBLE);
    }

    public void setToolbarType1(Boolean notification_dot_visible) {

        toolbarType1.setVisibility(View.VISIBLE);
        toolbarType2.setVisibility(View.GONE);
        toolbarType3.setVisibility(View.GONE);
        itemCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomnav.setSelectedItemId(R.id.nav_cart);
            }
        });

//        RelativeLayout notification_layout;
//        notification_layout = toolbar.findViewById(R.id.notification_layout);
//
//        ImageView itemCart;
//        itemCart = toolbar.findViewById(R.id.notification);
//        setBadge();

//
//        if (notification_dot_visible)
//            notification_dot.setVisibility(View.VISIBLE);
//        else notification_dot.setVisibility(View.GONE);

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
//                if (isFromSearchView) {
//                    onBackPressed();
//                    onBackPressed();
//                    isFromSearchView = false;
//                } else {
//                    onBackPressed();
//                }
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

    @Override
    public void onBackPressed() {
        if (isFromSearchView) {
            super.onBackPressed();
            super.onBackPressed();
            isFromSearchView = false;
        } else {
            super.onBackPressed();
        }
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

    public synchronized void changeFragment(int fragmentIndex, String tag, Bundle bundle) {
        if (fragmentIndex == 0 || fragmentIndex == 1 || fragmentIndex == 2 || fragmentIndex == 11) {
            if (bundle != null) {
                getFragmentForBundle(fragmentIndex).setArguments(bundle);
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment, fragmentList.get(fragmentIndex)).commit();
        } else {
            if (bundle != null) {
                getFragmentForBundle(fragmentIndex).setArguments(bundle);
            }
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                    .replace(R.id.main_fragment, fragmentList.get(fragmentIndex)).addToBackStack(tag).commit();
        }

    }

    public void changeFragmentThankyou(int fragmentIndex) {
        removeAllBackstacK();
        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.main_fragment, fragmentList.get(fragmentIndex)).addToBackStack(FragmentThankyou.TAG).commit();
    }

    private void removeAllBackstacK() {
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry entry = manager.getBackStackEntryAt(0);
            manager.popBackStack(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void selectMyOrderFragment() {
        getSupportFragmentManager().popBackStack(FragmentThankyou.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        bottomnav.setSelectedItemId(R.id.nav_order);
//        changeFragment(2, FragmentMyOrder.TAG, null);
    }

    public void selectCartFragment() {
        Intent i = new Intent(this, MainActivity.class);
// set the new task and clear flags
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }

    public void selectShopFragment() {
        bottomnav.setSelectedItemId(R.id.nav_shop);
        changeFragment(0, FragmentShop.TAG, null);
    }


    @Override
    public void onBackStackChanged() {
        int count = manager.getBackStackEntryCount();
        for (int i = count - 1; i >= 0; i--) {
            Log.d(TAG, "onBackStackChanged: " + manager.getBackStackEntryAt(i).getName());
        }
        Log.d(TAG, "onBackStackChanged: " + count);

    }

    public void setBadge(String badgeCount) {

    }

    public void setItemCart() {
        new Repository(MainActivity.this, ApiClient.getApiService()).requestCart();
    }

    @Override
    public void onResponseReceived(JsonObject jsonObject) {
        Log.d(TAG, "onResponseReceived: run");
        if (jsonObject.get("code").toString().equals("200")) {
            Gson gson = new GsonBuilder().create();
            String empty = jsonObject.get("message").getAsString();
            Log.d(TAG, "onResponse: " + empty + "crt");
            if (empty.equals("empty cart")) {
                CartCount.setMyBoolean(0);
            } else {
                UserCartResponse myOrderResponse = gson.fromJson(gson.toJson(jsonObject), UserCartResponse.class);
//                        badgeCount =
                Integer counter = myOrderResponse.getDetails().size();
                Log.d(TAG, "onResponseReceived:counter " + counter);
                CartCount.setMyBoolean(counter);
            }
        } else {
            Log.d(TAG, "onResponseReceived:error ");
        }
    }

    @Override
    public void onLoading() {
        Log.d(TAG, "onResponseReceived:loading ");
    }

    @Override
    public void onError(String message) {
        Log.d(TAG, "onResponseReceived:failed ");

    }


    public interface cartCountChangeListener {
        void onCartCountChange(Integer count);
    }
    //might need in future
//    @Override
//    public void onBackPressed() {
//        int fragments = getSupportFragmentManager().getBackStackEntryCount();
//        if (fragments == 1) {
//            this.finish();
//        } else {
//            super.onBackPressed();
//        }
//                    super.onBackPressed();
//
//    }


}