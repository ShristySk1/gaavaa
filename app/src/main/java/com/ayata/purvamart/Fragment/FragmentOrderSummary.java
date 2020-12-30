package com.ayata.purvamart.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ayata.purvamart.Adapter.AdapterOrderSummary;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelItem;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Cart;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FragmentOrderSummary extends Fragment implements AdapterOrderSummary.OnItemClickListener {
    private static final String TAG = "FragmentOrderSummary";
    private View view;
    private RecyclerView recyclerView;
    private AdapterOrderSummary adapterOrderSummary;
    private List<ModelItem> listitem;
    private LinearLayoutManager layoutManager;
    private Button btn_confirm;
    private TextView pay_total;
    private TextView pay_orderPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order_summary, container, false);

        initAppbar();
        pay_orderPrice = view.findViewById(R.id.pay_orderprice);
        pay_total = view.findViewById(R.id.pay_total);

        initRecycler(view);

        btn_confirm = view.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).changeFragment(new FragmentThankyou());
            }
        });

        return view;
    }

    private void initAppbar() {
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2(getString(R.string.os_title), false, false);

        //bottom nav bar
        ((MainActivity) getActivity()).showBottomNavBar(false);

    }


    private void initRecycler(View view) {
        listitem = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view);
        adapterOrderSummary = new AdapterOrderSummary(getContext(), listitem, this);
        recyclerView.setAdapter(adapterOrderSummary);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        dataPrepare();


    }

    //add to cart api
    private void dataPrepare() {
//        listitem.add(new ModelItem("Fresh Spinach", "100.00", "Rs. 120.35",
//                R.drawable.spinach, "1 kg", true, "15% Off", 1));
//        listitem.add(new ModelItem("Fresh Tomatoes", "150.00", "Rs. 00",
//                R.drawable.tomato, "1 kg", false, "0%", 1));
//        listitem.add(new ModelItem("Fresh Spinach", "100.00", "Rs. 120.35",
//                R.drawable.spinach, "1 kg", true, "10% Off", 2));

//        modelItemList.add(new ModelItem("Fresh Spinach", "Rs. 100.00", "Rs. 120.35",
//                R.drawable.spinach, "1 kg", true, "15% Off", 1));
//        modelItemList.add(new ModelItem("Fresh Tomatoes", "Rs. 150.00", "Rs. 00",
//                R.drawable.tomato, "1 kg", false, "0%", 1));
//        modelItemList.add(new ModelItem("Fresh Spinach", "Rs. 100.00", "Rs. 120.35",
//                R.drawable.spinach, "1 kg", true, "10% Off", 2));
        Bundle bundle = getArguments();
        if (bundle != null) {
//            listitem.addAll((ArrayList<ModelItem>) bundle.getSerializable(FragmentAddressDelivery.FRAGMENT_ADDRESS_DELIVERY));
            Log.d(TAG, "dataPrepare: " + listitem.size());
            //test
            listitem.addAll(Cart.getModelItems());
            adapterOrderSummary.notifyDataSetChanged();
        }
    }

    @Override
    public void onPriceTotalListener(Double total) {
        pay_orderPrice.setText(total.toString());
        pay_total.setText(total.toString());
    }

    @Override
    public void onAddClick(ModelItem modelItem, int position) {

    }

    @Override
    public void onMinusClick(ModelItem modelItem, int position) {

    }

    @Override
    public void onCartItemClick(int position) {

    }
}