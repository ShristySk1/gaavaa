package com.ayata.purvamart.ui.Fragment.account.promos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentPromos extends Fragment implements AdapterPromos.OnPromosClickListener {
    public static final String TAG = "FragmentPromos";
    RecyclerView rvPromos;
    List<ModelPromos> listitem;
    AdapterPromos adapterPromos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_promos, container, false);
        rvPromos = view.findViewById(R.id.rvPromos);
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2("Promos", false, false);
        //bottom nav bar
        ((MainActivity) getActivity()).showBottomNavBar(false);
        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView() {
        listitem = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapterPromos = new AdapterPromos(getContext(), listitem, this);
        rvPromos.setLayoutManager(layoutManager);
        rvPromos.setAdapter(adapterPromos);
        dataPrepare();
    }

    private void dataPrepare() {
        listitem.add(new ModelPromos(R.color.error_red, "E-ticketing",
                "This is the description of each notification.This is the description of each notification.",
                "2020-08-29"));
        listitem.add(new ModelPromos(R.color.colorEditBorderProfile, "E-ticketing",
                "This is the description of each notification.This is the description of each notification.",
                "2020-08-29"));
        listitem.add(new ModelPromos(R.color.colorAccent, "E-ticketing",
                "This is the description of each notification.This is the description of each notification.",
                "2020-08-29"));
        adapterPromos.notifyDataSetChanged();
    }

    @Override
    public void onPromoClick(int position, ModelPromos modelNotification) {
        Toast.makeText(getContext(), "Promo clicked", Toast.LENGTH_SHORT).show();
    }
}