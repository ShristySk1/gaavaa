package com.ayata.purvamart.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.ayata.purvamart.data.network.response.ProductListResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * fragmentList.add(new FragmentShop());//0
 * fragmentList.add(new FragmentCart());//1
 * fragmentList.add(new FragmentMyOrder());//2
 * fragmentList.add(new FragmentListOrder());//3
 * fragmentList.add(new FragmentEmptyOrder());//4
 * fragmentList.add(new FragmentCart());//5
 * fragmentList.add(new FragmentCartEmpty());//6
 * fragmentList.add(new FragmentCartFilled());//7
 * fragmentList.add(new FragmentProduct());//8
 * fragmentList.add(new FragmentCategory());//9
 * fragmentList.add(new FragmentTrackOrder());//10
 * fragmentList.add(new FragmentAccount());//11
 * fragmentList.add(new FragmentEditAddress());//12
 * fragmentList.add(new FragmentEditProfile());//13
 * fragmentList.add(new FragmentPrivacyPolicy());//14
 * fragmentList.add(new FragmentPayment());//15
 */

public class FragmentCategory extends Fragment implements AdapterItem.OnItemClickListener, AdapterCategoryTop.OnCategoryClickListener {
    public static String TAG = "FragmentCategory";
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
    List<ProductDetail> filterlist;
    RelativeLayout empty_layout;

    //error
    TextView text_error;
    ProgressBar progress_error;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);
        empty_layout = view.findViewById(R.id.layout_empty);
        //for error
        inflateLayout();
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
        filterlist = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        adapterItem = new AdapterItem(getContext(), filterlist, this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterItem);
        //allproduct
        progress_error.setVisibility(View.VISIBLE);
        allProduct();
        return view;
    }

    private void allProduct() {
        Log.d(TAG, "allProduct: called");
        ApiService productListapi = ApiClient.getClient().create(ApiService.class);
        productListapi.getProductsList().enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                progress_error.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body().getDetails().size());
                    ProductListResponse productListResresponse = response.body();
                    for (ProductDetail productDetail : productListResresponse.getDetails()) {
                        listitem.add(productDetail);
                        Log.d(TAG, "onResponse: " + productDetail.getProductImage());
                        Log.d(TAG, "onResponse: " + productDetail.getTitle());
                    }

                } else {
                    Log.d(TAG, "onResponse: " + response.body().getMessage());
                }
                //
                populateData(toolbar_title);
                adapterItem.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                progress_error.setVisibility(View.GONE);
                text_error.setText(t.getMessage());
                Log.d(TAG, "onResponse:failed " + t.getMessage());
            }
        });
    }

    private void populateData(String categoryname) {
        Log.d(TAG, "populateData: ");
        Log.d(TAG, "populateData: listitemsize " + listitem.size());
        filterlist.clear();
        if (categoryname != "All") {
            for (ProductDetail productDetail : listitem) {
                if (productDetail.getTitle().equals(categoryname)) {
                    Log.d(TAG, "populateData: filtering on process");
                    filterlist.add(productDetail);
                }
            }

        } else {
            filterlist.addAll(listitem);
        }
        if (filterlist.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            empty_layout.setVisibility(View.VISIBLE);
        } else {
            empty_layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        adapterItem.notifyDataSetChanged();


        Log.d(TAG, "populateData: filtering size" + filterlist.size());


    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), "Item---" + filterlist.get(position).getName(), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentProduct.MODEL_ITEM, filterlist.get(position));
        Log.d(TAG, "onItemClick: " + filterlist.get(position).getProductImage());

//        Fragment fragmentProduct =((MainActivity)getActivity()).getFragmentForBundle(8);
//        fragmentProduct.setArguments(bundle);
        ((MainActivity) getActivity()).changeFragment(8, FragmentEditProfile.TAG, bundle);
    }

    private void prepareCategory() {
        categoryTopList = new ArrayList<>();
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

    //inflate pullRefreshLayout for error and progressbar
    void inflateLayout() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        //Avoid pass null in the root it ignores spaces in the child pullRefreshLayout
        View inflatedLayout = inflater.inflate(R.layout.error_layout, (ViewGroup) view, false);
        ViewGroup viewGroup = view.findViewById(R.id.root_main);
        viewGroup.addView(inflatedLayout);
        text_error = view.findViewById(R.id.text_error);
        progress_error = view.findViewById(R.id.progress_error);
    }
}