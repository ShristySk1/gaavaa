package com.ayata.purvamart.Fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelItem;
import com.ayata.purvamart.R;

import androidx.fragment.app.Fragment;

public class FragmentProduct extends Fragment implements View.OnClickListener {
    public static final String FRAGMENT_PRODUCT = "FRAGMENT_PRODUCT";
    public static final String MODEL_ITEM = "param1";
    private ModelItem modelItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            modelItem = (ModelItem) getArguments().getSerializable(MODEL_ITEM);
        }
    }

    Button btnAddToCart;
    private static int quantity = 0;
    TextView textQuantity, textProductTitle, textProductNewPrice, textProductOldPrice, textWeight, textDiscount;
    ImageButton btn_add, btn_minus;
    ImageView image_product;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        //toolbar
        ((MainActivity) getActivity()).setToolbarType2("", false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btnAddToCart = view.findViewById(R.id.btn_add_to_cart);
        textQuantity = view.findViewById(R.id.text_product_quantity);
        btn_add = view.findViewById(R.id.imageButton_add);
        btn_minus = view.findViewById(R.id.imageButton_minus);
        image_product = view.findViewById(R.id.image_product);
        textProductNewPrice = view.findViewById(R.id.text_product_newPrice);
        textProductOldPrice = view.findViewById(R.id.text_product_previousPrice);
        textProductTitle = view.findViewById(R.id.text_product_name);
        textWeight = view.findViewById(R.id.text_product_weight);
        textDiscount = view.findViewById(R.id.text_product_discount);
        btn_add.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btnAddToCart.setOnClickListener(this);
        //setData
        textQuantity.setText("0");
        image_product.setImageResource(modelItem.getImage());
        textProductTitle.setText(modelItem.getName());
        textProductNewPrice.setText(modelItem.getPrice());
        handleDiscount();
    }

    private void handleDiscount() {
        if(modelItem.getDiscount()){
            showDiscount();
        }else {
            hideDiscount();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton_add:
                add();
                break;
            case R.id.imageButton_minus:
                minus();
                break;
            case R.id.btn_add_to_cart:
                nextFragment();
                break;
        }
    }

    private void nextFragment() {
        FragmentCart fragmentCart = new FragmentCart();
        ((MainActivity) getActivity()).changeFragment(fragmentCart);
    }

    private void minus() {
        if (quantity == 0) {
            textQuantity.setText("" + quantity);
        } else {
            quantity--;
            textQuantity.setText("" + quantity);
        }
    }

    private void add() {
        quantity++;
        textQuantity.setText("" + quantity);
    }
    private void showDiscount(){
        textProductOldPrice.setVisibility(View.VISIBLE);
        textDiscount.setVisibility(View.VISIBLE);
        textDiscount.setText(modelItem.getDiscount_percent());
        textProductOldPrice.setText(modelItem.getPrev_price());
        textProductOldPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }
    private void hideDiscount(){
        textProductOldPrice.setVisibility(View.GONE);
        textDiscount.setVisibility(View.GONE);
    }
}