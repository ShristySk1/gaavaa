package com.ayata.purvamart.ui.Fragment.shop;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelCategory;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.data.network.response.ProductListResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements SearchAdapter.setOnSearchClickListener {
    public static final String TAG ="SearchFragment" ;
    private SearchView searchView;
    private RecyclerView recyclerView, recyclerViewSearchBefore;
    private SearchAdapter searchAdapter;
    private List<ProductDetail> listitem;

    //empty layout
    LinearLayout layout_recycler, layout_empty;
    //layout before search
    LinearLayout ll_before_search;
    List<ModelSearchBefore> modelSearchBeforeList;
    SearchAdapterBefore searchAdapterBefore;
    //query text
    Boolean isFirstTime = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_search, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
getActivity().onBackPressed();
            }
        });
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setContentInsetStartWithNavigation(0);
        layout_empty = view.findViewById(R.id.layout_empty);
        ll_before_search = view.findViewById(R.id.ll_before_search);
        layout_recycler = view.findViewById(R.id.layout_recycler);
        layout_recycler.setVisibility(View.GONE);
        layout_empty.setVisibility(View.GONE);

        //recycler search
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter();
        SearchAdapter.setListener(this);
        recyclerView.setAdapter(searchAdapter);
        listitem = new ArrayList<>();
        //recycler before search
        ll_before_search.setVisibility(View.VISIBLE);
        setRecyclerViewSearchBefore();
        recyclerViewSearchBefore = view.findViewById(R.id.rv_category_product_for_you);
        recyclerViewSearchBefore.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapterBefore = new SearchAdapterBefore(getContext(), modelSearchBeforeList);
//        searchAdapterBefore.setListener(this);
        recyclerViewSearchBefore.setAdapter(searchAdapterBefore);
        //suggestion
        allProduct();
        //for listening to filtered list
        searchAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                showEmptyStateIfAdapterIsEmpty();
            }
        });
        return view;
    }
    private void showEmptyStateIfAdapterIsEmpty() {
        if (searchAdapter.getFilteredSize() == 0) {
            layout_recycler.setVisibility(View.GONE);
            layout_empty.setVisibility(View.VISIBLE);
        } else {
            if (isFirstTime) {
                layout_recycler.setVisibility(View.GONE);
                layout_empty.setVisibility(View.GONE);
            } else {
                layout_recycler.setVisibility(View.VISIBLE);
                layout_empty.setVisibility(View.GONE);
            }
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);
        //remove underline
        View searchPlate = searchView.findViewById(androidx.appcompat.R.id.search_plate);
        if (searchPlate != null) {
            searchPlate.setBackgroundColor(Color.WHITE);
        }
        //remove icon
        ImageView magImage = (ImageView) searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        if (magImage != null) {
            magImage.setVisibility(View.GONE);
            magImage.setImageDrawable(null);
        }
        //hint color
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
//        searchEditText.setTextColor(Color.WHITE);
        searchEditText.setHintTextColor(Color.GRAY);
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                ll_before_search.setVisibility(View.GONE);
                layout_recycler.setVisibility(View.VISIBLE);
                isFirstTime=false;
                searchAdapter.getFilter().filter(query);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
//                finish();
                getFragmentManager().popBackStackImmediate();
                return false;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void allProduct() {
        ApiService productListapi = ApiClient.getClient().create(ApiService.class);
        productListapi.getProductsList().enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
//                progress_error.setVisibility(View.GONE);
                if (response.isSuccessful()) {
//                    Log.d(TAG, "onResponse: " + response.body().getDetails().size());
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
//                populateData(toolbar_title);

                searchAdapter.setProductDetailList(getContext(), listitem);
                searchAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
//                progress_error.setVisibility(View.GONE);
//                text_error.setText(t.getMessage());
                Log.d(TAG, "onResponse:failed " + t.getMessage());
            }
        });
    }

    @Override
    public void onSearchClick(ProductDetail productDetail) {

//        Intent i = new Intent(this, MainActivity.class);
//        i.putExtra("key", productDetail);
//        startActivity(i);

    }

    void setRecyclerViewSearchBefore() {
        modelSearchBeforeList = new ArrayList<>();
        List<ProductDetail> modelProducts = FragmentShop.getList_madeforyou();
        modelSearchBeforeList.add(new ModelSearchBefore("Products For You", "", ModelSearchBefore.MODELTYPE.TITLE));
        for (ProductDetail productDetail : modelProducts) {
            modelSearchBeforeList.add(new ModelSearchBefore(productDetail.getName(), productDetail.getImage(), ModelSearchBefore.MODELTYPE.PRODUCT));
        }
//        searchAdapterBefore.notifyDataSetChanged();
        List<ModelCategory> modelCategories = FragmentShop.getList_category();
        modelSearchBeforeList.add(new ModelSearchBefore("Categories", "", ModelSearchBefore.MODELTYPE.TITLE));
        for (ModelCategory category : modelCategories) {
            modelSearchBeforeList.add(new ModelSearchBefore(category.getName(), category.getImage(), ModelSearchBefore.MODELTYPE.PRODUCT));
        }
    }
}