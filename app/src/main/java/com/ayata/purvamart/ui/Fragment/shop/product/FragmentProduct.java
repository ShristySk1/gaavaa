package com.ayata.purvamart.ui.Fragment.shop.product;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.ui.Adapter.ViewPagerAdapterProduct;
import com.ayata.purvamart.ui.login.SignupActivity;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.transition.MaterialContainerTransform;
import com.google.gson.JsonObject;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.viewpager2.widget.ViewPager2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This is single product page
 */
public class FragmentProduct extends Fragment implements View.OnClickListener {
    public static String TAG = "FragmentProduct";
    public static final String MODEL_ITEM = "param1";
    public static final String MODEL_TRANSITION_NAME = "transition_view";
    private ProductDetail modelItem;
    //viewpager
    ViewPager2 mViewPager;
    PageIndicatorView pageIndicatorView;
    //360
    ImageView iv360;
    List<String> images;
    // Creating Object of ViewPagerAdapter
    ViewPagerAdapterProduct mViewPagerAdapter;
    String transition_image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            modelItem = (ProductDetail) getArguments().getSerializable(MODEL_ITEM);
            transition_image = getArguments().getString(MODEL_TRANSITION_NAME);

        }
        //        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(buildContainerTransform());
            setSharedElementReturnTransition(buildContainerTransform());
        }
    }

    Button btnAddToCart;
    private static int quantity = 0;
    TextView tvStock, textProductTitle, textProductNewPrice, textProductOldPrice, textWeight, textDiscount, textProductDescription, text_product_from;
    //like
    ImageView thumb_image;
    TextView thumb_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2("", true, false);

        //bottom nav
        ((MainActivity) getActivity()).showBottomNavBar(false);

        initView(view);
        return view;
    }

    private MaterialContainerTransform buildContainerTransform() {
        MaterialContainerTransform materialContainerTransform = new MaterialContainerTransform();
        materialContainerTransform.setInterpolator(new FastOutSlowInInterpolator());
//        materialContainerTransform.setContainerColor(getResources().getColor(R.color.colorPriceTag));
        materialContainerTransform.setDuration(300);
        materialContainerTransform.setFadeMode(MaterialContainerTransform.FADE_MODE_OUT);
        return materialContainerTransform;

    }

    private void initView(View view) {
        ConstraintLayout constraintLayout;
        constraintLayout = view.findViewById(R.id.constraintLayout);
        btnAddToCart = view.findViewById(R.id.btn_add_to_cart);
        text_product_from = view.findViewById(R.id.text_product_from);
        iv360 = view.findViewById(R.id.iv360);
        tvStock = view.findViewById(R.id.tvStock);
        thumb_image = view.findViewById(R.id.thumb_image);
        thumb_text = view.findViewById(R.id.thumb_text);
        textProductNewPrice = view.findViewById(R.id.text_product_newPrice);
        textProductDescription = view.findViewById(R.id.text_product_description);
        textProductTitle = view.findViewById(R.id.text_product_name);
        textWeight = view.findViewById(R.id.text_product_weight);
        btnAddToCart.setOnClickListener(this);
        //setData-----
        //transition_image
        constraintLayout.setTransitionName(transition_image);

        //viewpager image
        images = new ArrayList<>();
        mViewPager = view.findViewById(R.id.viewPager2);
        pageIndicatorView = view.findViewById(R.id.pageIndicatorView);
        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerAdapterProduct(images);
        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
            }
        });
        images.addAll(modelItem.getProductImage());
        pageIndicatorView.setCount(images.size());
        mViewPagerAdapter.notifyDataSetChanged();
        Log.d(TAG, "initView: " + images.size());
        //rest of data
        textProductTitle.setText(modelItem.getName());
        tvStock.setText(modelItem.getStock());
        textProductNewPrice.setText(modelItem.getProductPrice().toString());
        textWeight.setText(modelItem.getUnit());
        textProductDescription.setText(Html.fromHtml(modelItem.getDescription()));
        thumb_text.setText(modelItem.getProductLikes().toString());
        text_product_from.setText(modelItem.getFrom());
        Log.d(TAG, "initView: " + modelItem.getFrom());
        thumb_image.setOnClickListener(this);

        iv360.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
////                    Intent intent = new Intent(getContext(), ThreeDActivity.class);
////                    startActivity(intent);
//                    ((MainActivity) getActivity()).changeFragment3d();
//                } else {
//                    Toast.makeText(getContext(), "Sorry! Your device doesn't support 3D view", Toast.LENGTH_SHORT).show();
//                }
            }
        });


    }

    private void handleDiscount() {
        if (modelItem.getDiscounted()) {
            showDiscount();
        } else {
            hideDiscount();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_to_cart:
                nextFragment();
                break;
            case R.id.thumb_image:
                break;
        }
    }

    /**
     * go to cart fragment
     */
    private void nextFragment() {
        //check if user is logged in then only we hit api otherwise return
        if (!PreferenceHandler.isUserAlreadyLoggedIn(getContext())) {
            Toast.makeText(getContext(), "Please Login to continue", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getContext(), SignupActivity.class));
            return;
        }
        //api
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.addToCart(modelItem.getId()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String message = response.body().get("message").getAsString();
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body().get("message"));
                    if (response.body().get("code").getAsString().equals("200")) {
                        showToast(message, true);
//                        Toast.makeText(getContext(), response.body().get("message").getAsString(), Toast.LENGTH_LONG).show();
                    } else {
                        showToast(message, false);
//                        Toast.makeText(getContext(), response.body().get("message").getAsString(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    showToast(message, false);
//                    Toast.makeText(getContext(), response.body().get("message").getAsString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
                showToast(t.getMessage(), false);
            }
        });
    }

    private void showToast(String message, Boolean isSuccess) {
        Toast toast = new Toast(getContext());
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.custom_toast, null);
        TextView tvMessage = view.findViewById(R.id.tvMessage);
        ImageView ivImage = view.findViewById(R.id.ivImage);
        CardView cardView = view.findViewById(R.id.cardBackground);
        tvMessage.setText(message);
        if (isSuccess) {
            cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            ivImage.setImageResource(R.drawable.ic_success);
        } else {
            cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorPriceTag));
            ivImage.setImageResource(R.drawable.ic_info);
        }
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, 150);
        toast.show();
    }

    private void showDiscount() {
        textProductOldPrice.setVisibility(View.VISIBLE);
        textDiscount.setVisibility(View.VISIBLE);
        textDiscount.setText(modelItem.getProductDiscount());
        textProductOldPrice.setText(modelItem.getOldPrice().toString());
        textProductOldPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void hideDiscount() {
        textProductOldPrice.setVisibility(View.GONE);
        textDiscount.setVisibility(View.GONE);
    }


}