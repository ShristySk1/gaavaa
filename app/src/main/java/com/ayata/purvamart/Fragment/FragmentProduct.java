package com.ayata.purvamart.Fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.MyCart;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProduct extends Fragment implements View.OnClickListener {
    public static final String TAG = "FRAGMENT_PRODUCT";
    public static final String MODEL_ITEM = "param1";
    private ProductDetail modelItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            modelItem = (ProductDetail) getArguments().getSerializable(MODEL_ITEM);
        }
    }

    Button btnAddToCart;
    private static int quantity = 0;
    TextView textQuantity, textProductTitle, textProductNewPrice, textProductOldPrice, textWeight, textDiscount, textProductDescription;
    ImageButton btn_add, btn_minus;
    ImageView image_product;

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

    private void initView(View view) {
        btnAddToCart = view.findViewById(R.id.btn_add_to_cart);
//        textQuantity = view.findViewById(R.id.text_product_quantity);
//        btn_add = view.findViewById(R.id.imageButton_add);
//        btn_minus = view.findViewById(R.id.imageButton_minus);
        image_product = view.findViewById(R.id.image_product);
        thumb_image = view.findViewById(R.id.thumb_image);
        thumb_text = view.findViewById(R.id.thumb_text);
        textProductNewPrice = view.findViewById(R.id.text_product_newPrice);
        textProductDescription = view.findViewById(R.id.text_product_description);
        textProductTitle = view.findViewById(R.id.text_product_name);
        textWeight = view.findViewById(R.id.text_product_weight);
//        textDiscount = view.findViewById(R.id.text_product_discount);
//        btn_add.setOnClickListener(this);
//        btn_minus.setOnClickListener(this);
        btnAddToCart.setOnClickListener(this);
        //setData
//        textQuantity.setText("0");
//        image_product.setImageResource(modelItem.getImage());
        if (modelItem.getProductImage().size() > 0) {
            Glide.with(getContext()).load("http://" + modelItem.getProductImage().get(0)).into(image_product);
            Log.d(TAG, "initView: " + "http://" + modelItem.getProductImage().get(0));
        }
        textProductTitle.setText(modelItem.getName());
        textProductNewPrice.setText(modelItem.getProductPrice().toString());
        textWeight.setText(modelItem.getUnit());
        textProductDescription.setText(modelItem.getDescription());
//        thumb_text.setText(modelItem.getProductLikes().toString());
//        handleDiscount();
        thumb_image.setOnClickListener(this);
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
//            case R.id.imageButton_add:
////                add();
//                break;
//            case R.id.imageButton_minus:
//                minus();
//                break;
            case R.id.btn_add_to_cart:
                nextFragment();
                break;
            case R.id.thumb_image:
                break;
        }
    }

    private void nextFragment() {
        FragmentCart fragmentCart = new FragmentCart();
        //TODO JSON DATA POST
        List<MyCart> carts = new ArrayList<>();
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        MyCart myCart = new MyCart();
        Log.d(TAG, "nextFragment: " + modelItem.getId());
        myCart.setProductId(modelItem.getId());
        myCart.setProductQuantity(1);
        carts.add(myCart);
        apiService.addToCart(PreferenceHandler.getToken(getContext()), "application/json", carts).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body().get("message"));
                    if (response.body().get("code").getAsString().equals("200")) {
                        Toast.makeText(getContext(), "Successfully Added To Cart", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), response.body().get("details").toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });
//        ((MainActivity) getActivity()).changeFragment(fragmentCart);
    }

    //
//    private void minus() {
//        if (quantity == 0) {
//            textQuantity.setText("" + quantity);
//        } else {
//            quantity--;
//            textQuantity.setText("" + quantity);
//        }
//    }
//
//    private void add() {
//        quantity++;
//        textQuantity.setText("" + quantity);
//    }
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