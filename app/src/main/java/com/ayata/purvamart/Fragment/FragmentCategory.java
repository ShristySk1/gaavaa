package com.ayata.purvamart.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayata.purvamart.Adapter.AdapterItem;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelItem;
import com.ayata.purvamart.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentCategory extends Fragment implements AdapterItem.OnItemClickListener {

    private View view;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private AdapterItem adapterItem;
    private List<ModelItem> listitem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_category, container, false);

        //toolbar
        Bundle bundle = this.getArguments();
        String toolbar_title="List";
        if(bundle!=null) {
            toolbar_title = bundle.getString("title");
        }
        ((MainActivity)getActivity()).setToolbarType2(toolbar_title,false);
        //toolbar end

        listitem= new ArrayList<>();
        populateData();
        recyclerView= view.findViewById(R.id.recycler);
        gridLayoutManager= new GridLayoutManager(getContext(),2);
        adapterItem=new AdapterItem(getContext(),listitem,this);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterItem);

        return view;
    }

    private void populateData(){
        listitem.add(new ModelItem("Fresh Spinach","Rs. 100.00", "Rs. 120.35",
                R.drawable.spinach,"1 kg",true,"15%"));
        listitem.add(new ModelItem("Fresh Tomatoes","Rs. 150.00", "Rs. 00",
                R.drawable.tomato,"1 kg",false,"0%"));
        listitem.add(new ModelItem("Fresh Spinach","Rs. 100.00", "Rs. 120.35",
                R.drawable.spinach,"1 kg",true,"15%"));
        listitem.add(new ModelItem("Fresh Tomatoes","Rs. 150.00", "Rs. 00",
                R.drawable.tomato,"1 kg",false,"0%"));
        listitem.add(new ModelItem("Fresh Spinach","Rs. 100.00", "Rs. 120.35",
                R.drawable.spinach,"1 kg",true,"15%"));
        listitem.add(new ModelItem("Fresh Tomatoes","Rs. 150.00", "Rs. 00",
                R.drawable.tomato,"1 kg",false,"0%"));
        listitem.add(new ModelItem("Fresh Spinach","Rs. 100.00", "Rs. 120.35",
                R.drawable.spinach,"1 kg",true,"15%"));
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), "Item---"+listitem.get(position).getName(), Toast.LENGTH_SHORT).show();
    }
}