package com.ayata.purvamart.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayata.purvamart.Adapter.AdapterOrder;
import com.ayata.purvamart.Model.ModelOrderList;
import com.ayata.purvamart.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentListOrder extends Fragment implements AdapterOrder.OnItemClickListener {


    private View view;
    private RecyclerView recyclerView;
    private AdapterOrder adapterOrder;
    private LinearLayoutManager layoutManager;
    private List<ModelOrderList> listitem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_list_order, container, false);

        initRecycler();
        return view;
    }

    private void initRecycler(){

        listitem= new ArrayList<>();
        recyclerView= view.findViewById(R.id.recycler_view);
        adapterOrder= new AdapterOrder(getContext(),listitem,this);
        recyclerView.setAdapter(adapterOrder);
        layoutManager= new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        dataPrepare();

    }

    private void dataPrepare(){

        listitem.add(new ModelOrderList(R.drawable.spinach,"22574","20-Dec-2019", "3:00 PM","22 Dec"));
        listitem.add(new ModelOrderList(R.drawable.spinach,"22574","20-Dec-2019", "3:00 PM","22 Dec"));
        listitem.add(new ModelOrderList(R.drawable.spinach,"22574","20-Dec-2019", "3:00 PM","22 Dec"));
    }

    @Override
    public void onItemClick(int position, ModelOrderList modelOrderList) {
        Toast.makeText(getContext(), "List Clicked Item--"+position, Toast.LENGTH_SHORT).show();
    }
}