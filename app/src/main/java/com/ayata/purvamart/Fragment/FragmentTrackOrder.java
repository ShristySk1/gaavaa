package com.ayata.purvamart.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayata.purvamart.Adapter.AdapterItem;
import com.ayata.purvamart.Adapter.AdapterOrderTracker;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelOrderTrack;
import com.ayata.purvamart.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTrackOrder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTrackOrder extends Fragment implements AdapterItem.OnItemClickListener, AdapterOrderTracker.OnCategoryClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentTrackOrder() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTrackOrder.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTrackOrder newInstance(String param1, String param2) {
        FragmentTrackOrder fragment = new FragmentTrackOrder();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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
        ((MainActivity) getActivity()).setToolbarType2("Track Orders", false);
        populateData();
        recyclerView = view.findViewById(R.id.recycler_order_tracker);
        linearLayoutManager = new LinearLayoutManager(getContext());
        adapterItem = new AdapterOrderTracker(getContext(), list_orderTrack, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterItem);
        return view;
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