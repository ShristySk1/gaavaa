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
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.Adapter.AdapterAd;
import com.ayata.purvamart.Adapter.AdapterCategory;
import com.ayata.purvamart.Adapter.AdapterItem;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelAd;
import com.ayata.purvamart.Model.ModelCategory;
import com.ayata.purvamart.Model.ModelItem;
import com.ayata.purvamart.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class FragmentShop extends Fragment implements AdapterCategory.OnCategoryClickListener,AdapterItem.OnItemClickListener {

    public static final String SELECTED_CATEGORY="SelectCategory";
    private View view;
    private RecyclerView recyclerView_ad, recyclerView_category, recyclerView_madeforyou;
    private List<ModelAd> list_ad;
    private LinearLayoutManager linearLayoutManager_ad;
    private AdapterAd adapterAd;

    private List<ModelCategory> list_category;
    private LinearLayoutManager LayoutManager_category;
    private AdapterCategory adapterCategory;

    private List<ModelItem> list_madeforyou;
    private GridLayoutManager linearLayoutManager_madeforyou;
    private AdapterItem adapterItem_madeforyou;

    private RelativeLayout search_layout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_shop, container, false);

        //toolbar
        ((MainActivity)getActivity()).showToolbar();
        ((MainActivity)getActivity()).setToolbarType1(true);
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
        LayoutManager_category= new LinearLayoutManager(getContext());
        LayoutManager_category.setOrientation(RecyclerView.HORIZONTAL);
        adapterCategory= new AdapterCategory(getContext(),list_category,this);
        recyclerView_category.setLayoutManager(LayoutManager_category);
        recyclerView_category.setAdapter(adapterCategory);

        //recycler--made for you--list
        list_madeforyou= new ArrayList<>();
        populateMadeForYouList();
        linearLayoutManager_madeforyou= new GridLayoutManager(getContext(),2);
        adapterItem_madeforyou= new AdapterItem(getContext(),list_madeforyou,this);
        recyclerView_madeforyou.setAdapter(adapterItem_madeforyou);
        recyclerView_madeforyou.setLayoutManager(linearLayoutManager_madeforyou);

        //category seeall
        TextView category_see_all= view.findViewById(R.id.category_see_all);
        category_see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelCategory modelCategory= new ModelCategory("0","All",1,true);
                selectCategory(modelCategory);
            }
        });

    }

    private void populateAdList(){

        list_ad.add(new ModelAd("Fresh fruits","DISCOUNT 25% ALL FRUITS",R.drawable.fruit_image));
        list_ad.add(new ModelAd("First Order","DISCOUNT 25% ALL VEGETABLES",R.drawable.vegetable_image));
        list_ad.add(new ModelAd("Fresh fruits","DISCOUNT 25% ALL FRUITS",R.drawable.fruit_image));
    }

    private void populateCategoryList(){

        list_category.add(new ModelCategory("1","Spices", R.drawable.spices1,false));
        list_category.add(new ModelCategory("2","Herbs",R.drawable.spices2,false));
        list_category.add(new ModelCategory("3","Tea", R.drawable.spices3,false));
        list_category.add(new ModelCategory("4","Honey",R.drawable.spices1,false));

    }

    private void populateMadeForYouList(){

        list_madeforyou.add(new ModelItem("Fresh Spinach","Rs. 100.00", "Rs. 120.35",
                R.drawable.spinach,"1 kg",true,"15% Off"));
        list_madeforyou.add(new ModelItem("Fresh Tomatoes","Rs. 150.00", "Rs. 00",
                R.drawable.tomato,"1 kg",false,"0% Off"));
        list_madeforyou.add(new ModelItem("Fresh Spinach","Rs. 100.00", "Rs. 120.35",
                R.drawable.spinach,"1 kg",true,"15% Off"));
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
    public void onCategoryClick(ModelCategory selectedItem) {
        Toast.makeText(getContext(), "Item--"+selectedItem.getName(), Toast.LENGTH_SHORT).show();

      selectCategory(selectedItem);

    }

    public void selectCategory(ModelCategory modelCategory){
        Bundle bundle= new Bundle();
        bundle.putSerializable(SELECTED_CATEGORY,modelCategory);
        FragmentCategory fragmentCategory= new FragmentCategory();
        fragmentCategory.setArguments(bundle);
        ((MainActivity)getActivity()).changeFragment(fragmentCategory);
    }
}