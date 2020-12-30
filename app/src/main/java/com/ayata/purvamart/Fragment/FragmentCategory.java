package com.ayata.purvamart.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayata.purvamart.Adapter.AdapterCategoryTop;
import com.ayata.purvamart.Adapter.AdapterItem;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelCategory;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.response.CategoryListResponse;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.data.network.response.ProductDetail2;
import com.ayata.purvamart.data.network.response.ProductDetail3;
import com.ayata.purvamart.data.network.response.ProductListResponse;
import com.ayata.purvamart.data.network.response.ProductListResponse2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentCategory extends Fragment implements AdapterItem.OnItemClickListener, AdapterCategoryTop.OnCategoryClickListener {

    private View view;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private AdapterItem adapterItem;
    private List<ProductDetail> listitem;


    private RecyclerView recyclerView_category;
    private LinearLayoutManager layoutManager_category;
    private List<ModelCategory> categoryTopList;
    private AdapterCategoryTop adapterCategoryTop;
    Bundle bundle;
    String toolbar_title = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);

        prepareCategory();
        adapterCategoryTop = new AdapterCategoryTop(getContext(), categoryTopList, this);

        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        bundle = this.getArguments();
        toolbar_title = "List";
        if (bundle != null) {
            ModelCategory modelCategory = (ModelCategory) bundle.getSerializable(FragmentShop.SELECTED_CATEGORY);
            toolbar_title = modelCategory.getName();
            setSelectedCategory(modelCategory);
            adapterCategoryTop.notifyDataSetChanged();
        }
        ((MainActivity) getActivity()).setToolbarType2(toolbar_title, false, true);
        //toolbar end

        //bottom nav bar
        ((MainActivity) getActivity()).showBottomNavBar(true);

        //top recycler
        recyclerView_category = view.findViewById(R.id.recycler_category);
        layoutManager_category = new LinearLayoutManager(getContext());
        layoutManager_category.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView_category.setLayoutManager(layoutManager_category);
        recyclerView_category.setAdapter(adapterCategoryTop);

        //bottom recycler
        listitem = new ArrayList<>();
        populateData(toolbar_title);
        recyclerView = view.findViewById(R.id.recycler);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        adapterItem = new AdapterItem(getContext(), listitem, this);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterItem);
        adapterItem.notifyDataSetChanged();

        return view;
    }

    private void populateData(String categoryname) {
//        listitem.add(new ModelItem("Fresh Spinach", "Rs. 100.00", "Rs. 120.35",
//                R.drawable.spinach, "1 kg", true, "15% Off"));
//        listitem.add(new ModelItem("Fresh Tomatoes", "Rs. 150.00", "Rs. 00",
//                R.drawable.tomato, "1 kg", false, "0% Off"));
//        listitem.add(new ModelItem("Fresh Spinach", "Rs. 100.00", "Rs. 120.35",
//                R.drawable.spinach, "1 kg", true, "15% Off"));
//        listitem.add(new ModelItem("Fresh Tomatoes", "Rs. 150.00", "Rs. 00",
//                R.drawable.tomato, "1 kg", false, "0%"));
//        listitem.add(new ModelItem("Fresh Spinach", "Rs. 100.00", "Rs. 120.35",
//                R.drawable.spinach, "1 kg", true, "15% Off"));
//        listitem.add(new ModelItem("Fresh Tomatoes", "Rs. 150.00", "Rs. 00",
//                R.drawable.tomato, "1 kg", false, "0%"));
//        listitem.add(new ModelItem("Fresh Spinach", "Rs. 100.00", "Rs. 120.35",
//                R.drawable.spinach, "1 kg", true, "15% Off"));
//        listitem.add(new ModelItem("Fresh Spinach", "Rs. 100.00", "Rs. 120.35",
//                R.drawable.spinach, "1 kg", true, "15% Off"));
//        listitem.add(new ModelItem("Fresh Tomatoes", "Rs. 150.00", "Rs. 00",
//                R.drawable.tomato, "1 kg", false, "0%"));
//        listitem.add(new ModelItem("Fresh Spinach", "Rs. 100.00", "Rs. 120.35",
//                R.drawable.spinach, "1 kg", true, "15% Off"));
//        ApiService productListapi = ApiClient.getClient().create(ApiService.class);
//        productListapi.getProductsList().enqueue(new Callback<ProductListResponse>() {
//            @Override
//            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
//                if (response.isSuccessful()) {
//                    ProductListResponse productListResresponse = response.body();
//                    for (ProductDetail productDetail : productListResresponse.getDetails()) {
//                                listitem.add(productDetail);
//                                adapterItem.notifyDataSetChanged();
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProductListResponse> call, Throwable t) {
//            }
//        });
        listitem.clear();
        ApiService productListapi = ApiClient.getClient().create(ApiService.class);
        if(categoryname!="All"){
        productListapi.getProductsListSpecific(categoryname).enqueue(new Callback<ProductListResponse2>() {
            @Override
            public void onResponse(Call<ProductListResponse2> call, Response<ProductListResponse2> response) {
                if (response.isSuccessful()) {
                    // TODO 2 types of product being created due to different response
                    ProductListResponse2 productListResresponse = response.body();
                    for (ProductDetail2 productDetail : productListResresponse.getDetails()) {
                        Integer cat = productDetail.getCategoryId();
                        List<String> image = Arrays.asList(productDetail.getProductImage());
                        for (ProductDetail3 detail : productDetail.getProductDetails()) {
                            listitem.add(new ProductDetail(detail.getName(), cat, image,detail.getUnit(), detail.getDescription(),
                                   detail.getDiscountAmount().toString(), detail.getOldPrice(), detail.getPrice()));
                        }
                    }
                    adapterItem.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse2> call, Throwable t) {
            }
        });}else{
                    productListapi.getProductsList().enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                if (response.isSuccessful()) {
                    ProductListResponse productListResresponse = response.body();
                    for (ProductDetail productDetail : productListResresponse.getDetails()) {
                                listitem.add(productDetail);
                                adapterItem.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
            }
        });
        }


    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), "Item---" + listitem.get(position).getName(), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentProduct.MODEL_ITEM, listitem.get(position));
        FragmentProduct fragmentProduct = new FragmentProduct();
        fragmentProduct.setArguments(bundle);
        ((MainActivity) getActivity()).changeFragment(fragmentProduct);
    }

    private void prepareCategory() {

        categoryTopList = new ArrayList<>();
//        categoryTopList.add(new ModelCategory("0","All",R.drawable.spices1,true));
//        categoryTopList.add(new ModelCategory("1","Spices",R.drawable.spices1, false));
//        categoryTopList.add(new ModelCategory("2","Herbs",R.drawable.spices1,false));
//        categoryTopList.add(new ModelCategory("3","Tea",R.drawable.spices1,false));
//        categoryTopList.add(new ModelCategory("4","Honey",R.drawable.spices1,false));
//        categoryTopList.add(new ModelCategory("5","Vegetable",R.drawable.spices1,false));
        ApiService categoryListapi = ApiClient.getClient().create(ApiService.class);
        categoryListapi.getCategoryList().enqueue(new Callback<CategoryListResponse>() {
            @Override
            public void onResponse(Call<CategoryListResponse> call, Response<CategoryListResponse> response) {
                if (response.isSuccessful() && response != null) {
                    List<ModelCategory> categoryList = response.body().getDetails();
                    categoryTopList.add(new ModelCategory(0, "All", "R.drawable.spices1", true));
                    for (ModelCategory category : categoryList) {
                        categoryTopList.add(category);
                        adapterCategoryTop.notifyDataSetChanged();
                        setFromBundle();
                    }
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryListResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCategoryClick(ModelCategory selectedItem) {
        setSelectedCategory(selectedItem);
        populateData(selectedItem.getName());
    }

    public void setSelectedCategory(ModelCategory selectedItem) {
        for (ModelCategory modelCategory : categoryTopList) {
            if (selectedItem.getName().equals(modelCategory.getName())) {
                modelCategory.setSelected(true);
                ((MainActivity) getActivity()).setToolbarType2(modelCategory.getName(), false, true);
            } else {
                modelCategory.setSelected(false);
            }
        }

        adapterCategoryTop.notifyDataSetChanged();
    }

    private void setFromBundle() {
        String toolbar_title = "List";
        if (bundle != null) {
            ModelCategory modelCategory = (ModelCategory) bundle.getSerializable(FragmentShop.SELECTED_CATEGORY);
            toolbar_title = modelCategory.getName();
            setSelectedCategory(modelCategory);
            adapterCategoryTop.notifyDataSetChanged();
        }
        ((MainActivity) getActivity()).setToolbarType2(toolbar_title, false, true);
    }


}