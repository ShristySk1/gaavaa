package com.ayata.purvamart.ui.Fragment.order;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Constants.Constants;
import com.ayata.purvamart.data.Model.ModelOrderList;
import com.ayata.purvamart.data.Model.ModelOrderTrack;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.generic.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.data.repository.Repository;
import com.ayata.purvamart.ui.Adapter.AdapterItem;
import com.ayata.purvamart.ui.Adapter.AdapterOrderTracker;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FragmentTrackOrder extends Fragment implements AdapterItem.OnItemClickListener, AdapterOrderTracker.OnCategoryClickListener, NetworkResponseListener<JsonObject> {
    public static String TAG = "FragmentTrackOrder";
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private AdapterOrderTracker adapterItem;
    private List<ModelOrderTrack> list_orderTrack;
    Button cancel_order;
    //error
    TextView text_error;
    ProgressBar progress_error;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        View view = inflater.inflate(R.layout.fragment_track_order, container, false);
        inflateLayout(view);
        list_orderTrack = new ArrayList<>();
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2("Track Orders", false, false);
        ((MainActivity) getActivity()).showBottomNavBar(false);

        initOrderView(view);

        populateData();
        recyclerView = view.findViewById(R.id.recycler_order_tracker);
        linearLayoutManager = new LinearLayoutManager(getContext());
        adapterItem = new AdapterOrderTracker(getContext(), list_orderTrack, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterItem);
        return view;
    }

    private void initOrderView(View view) {
        TextView text_orderid, text_datetime, text_delivery;
        ImageView image_item;

        text_datetime = view.findViewById(R.id.text_datetime);
        text_delivery = view.findViewById(R.id.text_delivery);
        text_orderid = view.findViewById(R.id.text_order_id);
        image_item = view.findViewById(R.id.image);
        cancel_order = view.findViewById(R.id.cancel_order);


        Bundle bundle = getArguments();

        if (bundle != null) {
            ModelOrderList listitem = (ModelOrderList) bundle.getSerializable(FragmentListOrder.order_item);
            text_delivery.setText("Estimated Delivery on" + " " + listitem.getDelivery_date());
            text_datetime.setText(listitem.getDate() + ", " + listitem.getTime());
            text_orderid.setText("Order#:" + " " + listitem.getOrder_id());
            Glide.with(getContext()).load(listitem.getImage()).placeholder(Constants.PLACEHOLDER).into(image_item);
            cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Repository(FragmentTrackOrder.this, ApiClient.getApiService()).requestCancelOrder(listitem.getOrder_id());
                }
            });
        }

    }

    private void populateData() {
        String testString = "Order placed";
        String[] titles = {
                "Order Placed",
                "Order Confirmed",
                "Order Processed",
                "Ready to Ship",
                "Out For Delivery",
                "Delivered"};
        String[] descriptions = {
                "We have rerceived your order on 20 Dec,2020",
                "Order has been confirmed on 20 Dec,2020",
                "We are preparing your order",
                "Your Product is ready for shipping",
                "Your Product is out for delivery",
                "The Product is out for delivery"
        };
        Boolean setNone = false;
        for (int i = 0; i < titles.length; i++) {
            if (!(titles[i].toLowerCase().trim().equals(testString.toLowerCase().trim()))) {
                if (setNone) {
                    Log.d(TAG, "populateData: setrest as none" + titles[i]);
                    list_orderTrack.add(new ModelOrderTrack(titles[i], descriptions[i], ModelOrderTrack.ORDER_TYPE_NONE));
                } else {
                    Log.d(TAG, "populateData: add orderlist" + titles[i]);
                    list_orderTrack.add(new ModelOrderTrack(titles[i], descriptions[i], i));
                }
            } else {
                Log.d(TAG, "populateData: orderlistfound" + titles[i]);
                list_orderTrack.add(new ModelOrderTrack(titles[i], descriptions[i], i));
                setNone = true;
            }
        }
    }

    @Override
    public void onItemClick(ProductDetail productDetail) {

    }

    @Override
    public void onCategoryClick(int position) {

    }


    @Override
    public void onResponseReceived(JsonObject response) {
        progress_error.setVisibility(View.GONE);
        Toast.makeText(getContext(), response.get("message").getAsString(), Toast.LENGTH_SHORT).show();
        getFragmentManager().popBackStackImmediate();
//        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(FragmentMyOrder.TAG);
//
//        if (fragment != null && fragment instanceof FragmentMyOrder)
//            ((FragmentMyOrder) fragment).setCurrentPositionViewPager(2);
//        else
//            Log.e(TAG, "cannot change tab its null...");

    }

    @Override
    public void onLoading() {
        progress_error.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message) {
        progress_error.setVisibility(View.GONE);
        text_error.setText(message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    //inflate pullRefreshLayout for error and progressbar
    void inflateLayout(View view) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        //Avoid pass null in the root it ignores spaces in the child pullRefreshLayout
        View inflatedLayout = inflater.inflate(R.layout.error_layout, (ViewGroup) view, false);
        ViewGroup viewGroup = view.findViewById(R.id.root_main);
        viewGroup.addView(inflatedLayout);
        text_error = view.findViewById(R.id.text_error);
        progress_error = view.findViewById(R.id.progress_error);
    }
}