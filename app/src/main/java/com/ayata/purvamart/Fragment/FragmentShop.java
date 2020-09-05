package com.ayata.purvamart.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ayata.purvamart.Adapter.AdapterAd;
import com.ayata.purvamart.Adapter.AdapterCategory;
import com.ayata.purvamart.Adapter.AdapterItem;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelAd;
import com.ayata.purvamart.Model.ModelCategory;
import com.ayata.purvamart.Model.ModelItem;
import com.ayata.purvamart.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentShop extends Fragment implements AdapterCategory.OnCategoryClickListener,AdapterItem.OnItemClickListener {

    private View view;
    private RecyclerView recyclerView_ad, recyclerView_category, recyclerView_madeforyou;
    private List<ModelAd> list_ad;
    private LinearLayoutManager linearLayoutManager_ad;
    private AdapterAd adapterAd;

    private List<ModelCategory> list_category;
    private GridLayoutManager gridLayoutManager_category;
    private AdapterCategory adapterCategory;

    private List<ModelItem> list_madeforyou;
    private LinearLayoutManager linearLayoutManager_madeforyou;
    private AdapterItem adapterItem_madeforyou;

    private RelativeLayout search_layout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_shop, container, false);

        //toolbar
        ((MainActivity)getActivity()).showToolbar();
        ((MainActivity)getActivity()).setToolbarType1();
        //bottom nav bar
        ((MainActivity)getActivity()).showBottomNavBar(true);


        initView();

        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //search method
                Toast.makeText(getContext(), "Search clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void initView(){

        search_layout= view.findViewById(R.id.search_layout);
        recyclerView_ad= view.findViewById(R.id.recycler_ad);
        recyclerView_category= view.findViewById(R.id.recycler_category);
        recyclerView_madeforyou= view.findViewById(R.id.recycler_type1);

        //recycler--advertisement
        list_ad= new ArrayList<>();
        populateAdList();
        linearLayoutManager_ad= new LinearLayoutManager(getContext());
        linearLayoutManager_ad.setOrientation(RecyclerView.HORIZONTAL);

        adapterAd= new AdapterAd(getContext(),list_ad);
        recyclerView_ad.setAdapter(adapterAd);
        recyclerView_ad.setLayoutManager(linearLayoutManager_ad);

        //recycler--categories grid
        list_category= new ArrayList<>();
        populateCategoryList();
        gridLayoutManager_category= new GridLayoutManager(getContext(),4);
        adapterCategory= new AdapterCategory(getContext(),list_category,this);
        recyclerView_category.setLayoutManager(gridLayoutManager_category);
        recyclerView_category.setAdapter(adapterCategory);

        //recycler--made for you--list
        list_madeforyou= new ArrayList<>();
        populateMadeForYouList();
        linearLayoutManager_madeforyou= new LinearLayoutManager(getContext());
        linearLayoutManager_madeforyou.setOrientation(RecyclerView.HORIZONTAL);
        adapterItem_madeforyou= new AdapterItem(getContext(),list_madeforyou,this);
        recyclerView_madeforyou.setAdapter(adapterItem_madeforyou);
        recyclerView_madeforyou.setLayoutManager(linearLayoutManager_madeforyou);

    }

    private void populateAdList(){

        list_ad.add(new ModelAd("DISCOUNT 25% ALL FRUITS",R.drawable.fruit_image));
        list_ad.add(new ModelAd("DISCOUNT 25% ALL VEGETABLES",R.drawable.vegetable_image));
        list_ad.add(new ModelAd("DISCOUNT 25% ALL FRUITS",R.drawable.fruit_image));
    }

    private void populateCategoryList(){

        list_category.add(new ModelCategory("SeaFoods", R.drawable.ic_fish));
        list_category.add(new ModelCategory("Fruits",R.drawable.ic_fruits));
        list_category.add(new ModelCategory("Vegetables", R.drawable.ic_veggie));
        list_category.add(new ModelCategory("Bakery",R.drawable.ic_bread));
        list_category.add(new ModelCategory("Dairy", R.drawable.ic_dairy));
        list_category.add(new ModelCategory("Meat",R.drawable.ic_meat));
        list_category.add(new ModelCategory("Frozen", R.drawable.ic_frozen_yogurt));
        list_category.add(new ModelCategory("Herbs",R.drawable.ic_leaf));

    }

    private void populateMadeForYouList(){

        list_madeforyou.add(new ModelItem("Fresh Spinach","Rs. 100.00", "Rs. 120.35",
                R.drawable.spinach,"1 kg",true,"15%"));
        list_madeforyou.add(new ModelItem("Fresh Tomatoes","Rs. 150.00", "Rs. 00",
                R.drawable.tomato,"1 kg",false,"0%"));
        list_madeforyou.add(new ModelItem("Fresh Spinach","Rs. 100.00", "Rs. 120.35",
                R.drawable.spinach,"1 kg",true,"15%"));
    }

    @Override
    public void onItemClick(int position) {

        Toast.makeText(getContext(), "Item--"+list_madeforyou.get(position).getName(), Toast.LENGTH_SHORT).show();
        Bundle bundle= new Bundle();
        bundle.putSerializable(FragmentProduct.MODEL_ITEM,list_madeforyou.get(position));
        FragmentProduct fragmentProduct= new FragmentProduct();
        fragmentProduct.setArguments(bundle);
        ((MainActivity)getActivity()).changeFragment(fragmentProduct);

    }

    @Override
    public void onCategoryClick(int position) {
        Toast.makeText(getContext(), "Item--"+list_category.get(position).getName(), Toast.LENGTH_SHORT).show();

        Bundle bundle= new Bundle();
        bundle.putString("title",list_category.get(position).getName());
        FragmentCategory fragmentCategory= new FragmentCategory();
        fragmentCategory.setArguments(bundle);
        ((MainActivity)getActivity()).changeFragment(fragmentCategory);

    }
}