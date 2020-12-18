package com.ayata.purvamart.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ayata.purvamart.Adapter.AdapterCart1;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelItem;
import com.ayata.purvamart.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentCart1 extends Fragment implements AdapterCart1.OnCartItemClickListener {
    public static final String FRAGMENT_CART = "FRAGMENT_CART";
    RecyclerView recyclerView;
    List<ModelItem> modelItemList;
    AdapterCart1 adapterCart;
    TextView textTotal;
    LinearLayout layout_proceed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        //toolbar
        ((MainActivity)getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType3("Cart");
        //bottom nav
        ((MainActivity)getActivity()).showBottomNavBar(true);

        initView(view);
        dataPrepare();
        setUpRecyclerView();
        layout_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent
                ((MainActivity)getActivity()).changeFragment(new FragmentPayment());
            }
        });
        return view;
    }

    private void dataPrepare() {
        modelItemList = new ArrayList<>();
        modelItemList.add(new ModelItem("Fresh Spinach", "Rs. 100.00", "Rs. 120.35",
                R.drawable.spinach, "1 kg", true, "15%", 1));
        modelItemList.add(new ModelItem("Fresh Tomatoes", "Rs. 150.00", "Rs. 00",
                R.drawable.tomato, "1 kg", false, "0%", 1));
        modelItemList.add(new ModelItem("Fresh Spinach", "Rs. 100.00", "Rs. 120.35",
                R.drawable.spinach, "1 kg", true, "10%", 2));
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adapterCart = new AdapterCart1(getContext(), modelItemList, this);
        recyclerView.setAdapter(adapterCart);
    }

    private void initView(View v) {
        recyclerView = v.findViewById(R.id.recycler_cart);
        textTotal = v.findViewById(R.id.text_subtotal);
        layout_proceed = v.findViewById(R.id.layout_cartProceed);
    }

    @Override
    public void onPriceTotalListener(Double total) {
        textTotal.setText(total.toString());
    }

    @Override
    public void onAddClick(ModelItem modelItem, int position) {
        Integer count = modelItem.getCount();
        count++;
        modelItem.setCount(count);
        modelItem.setTotalPrice(calculatePrice(getPriceOnly(modelItem.getPrice()) , modelItem.getCount()));
        adapterCart.notifyItemChanged(position);

    }

    @Override
    public void onMinusClick(ModelItem modelItem, int position) {
        Integer count = modelItem.getCount();
        if (count > 1) {
            count--;
            modelItem.setCount(count);
            modelItem.setTotalPrice(calculatePrice(getPriceOnly(modelItem.getPrice()), modelItem.getCount()));
            adapterCart.notifyItemChanged(position);
        } else {
            modelItemList.remove(position);
            adapterCart.notifyItemRemoved(position);
        }
    }

    @Override
    public void onCartItemClick(int position) {

    }

    Double getPriceOnly(String textPrice) {
        Pattern PRICE_PATTERN = Pattern.compile("(\\d*\\.)?\\d+");
        Matcher matcher = PRICE_PATTERN.matcher(textPrice);
        while (matcher.find()) {
            return Double.parseDouble(matcher.group());
        }
        return 1.00;
    }
    private double calculatePrice(Double price, int quantity) {
        return price * quantity;
    }

}