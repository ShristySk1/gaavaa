package com.ayata.purvamart.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ayata.purvamart.Adapter.AdapterPayment;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelPayment;
import com.ayata.purvamart.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPayment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPayment2 extends Fragment implements AdapterPayment.OnPayMethodClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentPayment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPayment2.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPayment2 newInstance(String param1, String param2) {
        FragmentPayment2 fragment = new FragmentPayment2();
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

    RecyclerView recyclerView;
    List<ModelPayment> listitem;
    AdapterPayment adapterPayment;
    LinearLayout ll_add_address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment2, container, false);
        //toolbar
        ((MainActivity)getActivity()).showToolbar();
        ((MainActivity)getActivity()).setToolbarType2("Payment",false,false);

        //bottom nav bar
        ((MainActivity)getActivity()).showBottomNavBar(false);
        initRecycler(view);
        return view;
    }

    private void initRecycler(View view) {
        listitem = new ArrayList<>();
        prepareData();
        recyclerView = view.findViewById(R.id.recycler_payment);
        adapterPayment = new AdapterPayment(getContext(), listitem);
        AdapterPayment.setListener(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapterPayment);
        recyclerView.setLayoutManager(manager);
    }

    private void prepareData() {
        listitem.add(new ModelPayment("Cash On Delivery", R.drawable.cash_on_delivery));
        listitem.add(new ModelPayment("Esewa Mobile Wallet", R.drawable.esewa));
        listitem.add(new ModelPayment("Khalti Wallet", R.drawable.khalti));
    }

    @Override
    public void onPayMethodClick(int position, ModelPayment modelPayment) {

    }
}