package com.ayata.purvamart.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayata.purvamart.Adapter.AdapterItem;
import com.ayata.purvamart.Adapter.AdapterOrderTracker;
import com.ayata.purvamart.Constants.Constants;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelOrderList;
import com.ayata.purvamart.Model.ModelOrderTrack;
import com.ayata.purvamart.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FragmentTrackOrder extends Fragment implements AdapterItem.OnItemClickListener, AdapterOrderTracker.OnCategoryClickListener {
    public static String TAG = "FragmentTrackOrder";
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private AdapterOrderTracker adapterItem;
    private List<ModelOrderTrack> list_orderTrack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track_order, container, false);
        list_orderTrack = new ArrayList<>();
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2("Track Orders", false,false);
        ((MainActivity)getActivity()).showBottomNavBar(false);

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
        TextView text_orderid,text_datetime,text_delivery;
        ImageView image_item;

        text_datetime= view.findViewById(R.id.text_datetime);
        text_delivery= view.findViewById(R.id.text_delivery);
        text_orderid= view.findViewById(R.id.text_order_id);
        image_item= view.findViewById(R.id.image);

        Bundle bundle=getArguments();

        if(bundle!=null){
            ModelOrderList listitem= (ModelOrderList) bundle.getSerializable(FragmentListOrder.order_item);
            text_delivery.setText("Estimated Delivery on"+" "+listitem.getDelivery_date());
            text_datetime.setText(listitem.getDate()+", "+listitem.getTime());
            text_orderid.setText("Order#:"+" "+listitem.getOrder_id());
            Glide.with(getContext()).load(listitem.getImage()).placeholder(Constants.PLACEHOLDER).into(image_item);
        }

    }

    private void populateData(){

        list_orderTrack.add(new ModelOrderTrack("Placed","The Product is placed",false,false,true));
        list_orderTrack.add(new ModelOrderTrack("Confirmed","The Product is confirmed",false,true,false));
        list_orderTrack.add(new ModelOrderTrack("Processed","The Product is being processded",true,false,false));
        list_orderTrack.add(new ModelOrderTrack("Shipped","The Product is shipped",true,false,false));
        list_orderTrack.add(new ModelOrderTrack("Out For Delivery","The Product is out for delivery",true,false,false));

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onCategoryClick(int position) {

    }
}