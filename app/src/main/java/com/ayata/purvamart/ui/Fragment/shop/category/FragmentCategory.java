package com.ayata.purvamart.ui.Fragment.shop.category;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelCategory;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.generic.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.BaseResponse;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.data.repository.Repository;
import com.ayata.purvamart.ui.Adapter.AdapterCategoryTop;
import com.ayata.purvamart.ui.Adapter.AdapterItem;
import com.ayata.purvamart.ui.Fragment.account.profile.FragmentEditProfile;
import com.ayata.purvamart.ui.Fragment.shop.FragmentShop;
import com.ayata.purvamart.ui.Fragment.shop.product.FragmentProduct;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentCategory extends Fragment implements AdapterItem.OnItemClickListener, AdapterCategoryTop.OnCategoryClickListener, NetworkResponseListener<BaseResponse<List<ModelCategory>>> {
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
        //set bundle arguments to list
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

    //api
    private void allProduct() {
        Log.d(TAG, "allProduct: called");
        new Repository(new NetworkResponseListener<BaseResponse<List<ProductDetail>>>() {
            @Override
            public void onResponseReceived(BaseResponse<List<ProductDetail>> response) {
                Log.d(TAG, "onResponse: " + response.getDetails().size());
                for (ProductDetail productDetail : (List<ProductDetail>) response.getDetails()) {
                    listitem.add(productDetail);
                    Log.d(TAG, "onResponse: " + productDetail.getProductImage());
                    Log.d(TAG, "onResponse: " + productDetail.getTitle());
                }
                populateData(toolbar_title);
                adapterItem.notifyDataSetChanged();

            }

            @Override
            public void onLoading() {

            }

            @Override
            public void onError(String message) {
                progress_error.setVisibility(View.GONE);
                text_error.setText(message);
                Log.d(TAG, "onResponse:failed " + message);

            }
        }, ApiClient.getApiService()).requestAllProducts();
    }

    //filter data when clicked on different category
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

    //when clicked on products of that category
    @Override
    public void onItemClick(ProductDetail productDetail) {
//        Toast.makeText(getContext(), "Item---" + filterlist.get(position).getName(), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentProduct.MODEL_ITEM, productDetail);
        Log.d(TAG, "onItemClick: " + productDetail.getProductImage());
        ((MainActivity) getActivity()).changeFragment(8, FragmentProduct.TAG, bundle,new FragmentProduct());
    }


    private void prepareCategory() {
        categoryTopList = new ArrayList<>();
        //api
        new Repository(this, ApiClient.getApiService()).requestAllCategory();
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

    /**
     * Category "All" is added to list manually for getting all products.
     */
    @Override
    public void onResponseReceived(BaseResponse<List<ModelCategory>> response) {
        progress_error.setVisibility(View.GONE);
        text_error.setText("");
        List<ModelCategory> categoryList = (List<ModelCategory>) response.getDetails();
        categoryTopList.add(new ModelCategory(0, "All", "R.drawable.spices1", true));
        for (ModelCategory category : categoryList) {
            categoryTopList.add(category);
            adapterCategoryTop.notifyDataSetChanged();
            setFromBundle();
        }
    }

    @Override
    public void onLoading() {
        progress_error.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        progress_error.setVisibility(View.GONE);
        text_error.setText(message);
    }
}