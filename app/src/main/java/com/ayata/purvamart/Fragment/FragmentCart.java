package com.ayata.purvamart.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ayata.purvamart.Adapter.AdapterCart;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelItem;
import com.ayata.purvamart.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentCart extends Fragment {
    public static final String FRAGMENT_CART = "FRAGMENT_CART";

    List<ModelItem> modelItemList;

    private View view;

    CardView progress_bar;
    FrameLayout main_layout;

    Bundle bundle= new Bundle();

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
         view = inflater.inflate(R.layout.fragment_cart, container, false);
        //toolbar
        ((MainActivity)getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType3("Cart");
        //bottom nav
        ((MainActivity)getActivity()).showBottomNavBar(true);

        progress_bar= view.findViewById(R.id.progress_cardview);
        main_layout= view.findViewById(R.id.fragment_cart);

        progress_bar.setVisibility(View.VISIBLE);
        main_layout.setVisibility(View.GONE);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms

                progress_bar.setVisibility(View.GONE);
                main_layout.setVisibility(View.VISIBLE);
            }
        }, 600);

        dataPrepare();

        return view;
    }

    private void dataPrepare() {
        modelItemList = new ArrayList<>();
        modelItemList.add(new ModelItem("Fresh Spinach", "Rs. 100.00", "Rs. 120.35",
                R.drawable.spinach, "1 kg", true, "15% Off", 1));
        modelItemList.add(new ModelItem("Fresh Tomatoes", "Rs. 150.00", "Rs. 00",
                R.drawable.tomato, "1 kg", false, "0%", 1));
        modelItemList.add(new ModelItem("Fresh Spinach", "Rs. 100.00", "Rs. 120.35",
                R.drawable.spinach, "1 kg", true, "10% Off", 2));

        checkForData();
    }

    private void checkForData(){

        if(modelItemList!=null){

            bundle.putSerializable(FRAGMENT_CART, (Serializable) modelItemList);
            FragmentCartFilled fragmentCartFilled= new FragmentCartFilled();
            fragmentCartFilled.setArguments(bundle);
            changeFragment(fragmentCartFilled);

        }else{
            changeFragment(new FragmentCartEmpty());
        }
    }

    private void changeFragment(Fragment fragment){

        getChildFragmentManager().beginTransaction().add(R.id.fragment_cart,fragment).addToBackStack("cart").commit();
    }



}