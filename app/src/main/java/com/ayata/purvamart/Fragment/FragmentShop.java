package com.ayata.purvamart.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.Adapter.AdapterAd;
import com.ayata.purvamart.Adapter.AdapterCategory;
import com.ayata.purvamart.Adapter.AdapterItem;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelCategory;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.response.HomeResponse;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.data.network.response.Slider;
import com.ayata.purvamart.utils.AlertDialogHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentShop extends Fragment implements AdapterCategory.OnCategoryClickListener, AdapterItem.OnItemClickListener, AdapterAd.setOnAddListener {

    public static final String SELECTED_CATEGORY = "SelectCategory";
    private View view;
    private RecyclerView recyclerView_ad, recyclerView_category, recyclerView_madeforyou;
    private List<Slider> list_ad;
    private LinearLayoutManager linearLayoutManager_ad;
    private AdapterAd adapterAd;

    private List<ModelCategory> list_category;
    private LinearLayoutManager LayoutManager_category;
    private AdapterCategory adapterCategory;

    private List<ProductDetail> list_madeforyou;
    private GridLayoutManager linearLayoutManager_madeforyou;
    private AdapterItem adapterItem_madeforyou;

    private RelativeLayout search_layout;
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shop, container, false);

        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType1(true);
        //bottom nav bar
        ((MainActivity) getActivity()).showBottomNavBar(true);


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

    private void initView() {
        webView = view.findViewById(R.id.webView);
        search_layout = view.findViewById(R.id.search_layout);
        recyclerView_ad = view.findViewById(R.id.recycler_ad);
        recyclerView_category = view.findViewById(R.id.recycler_category);
        recyclerView_madeforyou = view.findViewById(R.id.recycler_type1);

        //recycler--advertisement
        list_ad = new ArrayList<>();
        linearLayoutManager_ad = new LinearLayoutManager(getContext());
        linearLayoutManager_ad.setOrientation(RecyclerView.HORIZONTAL);

        AdapterAd.setAddListener(this);
        adapterAd = new AdapterAd(getContext(), list_ad);
        recyclerView_ad.setAdapter(adapterAd);
        recyclerView_ad.setLayoutManager(linearLayoutManager_ad);

        //recycler--categories grid
        list_category = new ArrayList<>();
        LayoutManager_category = new LinearLayoutManager(getContext());
        LayoutManager_category.setOrientation(RecyclerView.HORIZONTAL);
        adapterCategory = new AdapterCategory(getContext(), list_category, this);
        recyclerView_category.setLayoutManager(LayoutManager_category);
        recyclerView_category.setAdapter(adapterCategory);

        //recycler--made for you--list
        list_madeforyou = new ArrayList<>();
        linearLayoutManager_madeforyou = new GridLayoutManager(getContext(), 2);
        adapterItem_madeforyou = new AdapterItem(getContext(), list_madeforyou, this);
        recyclerView_madeforyou.setAdapter(adapterItem_madeforyou);
        recyclerView_madeforyou.setLayoutManager(linearLayoutManager_madeforyou);

        //category seeall
        TextView category_see_all = view.findViewById(R.id.category_see_all);
        category_see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelCategory modelCategory = new ModelCategory(0, "All", "", true);
                selectCategory(modelCategory);
            }
        });
        getAllHomeList();

    }

    private void getAllHomeList() {
        AlertDialogHelper.show(getContext());
        ApiService categoryListapi = ApiClient.getClient().create(ApiService.class);
        categoryListapi.getAllHome().enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                AlertDialogHelper.dismiss(getContext());
                if (response.isSuccessful()&&response!=null) {
                    HomeResponse homeResponse = response.body();
                    List<ModelCategory> categories = homeResponse.getDetails().get(2).getCategory();
                    List<ProductDetail> productForYous = homeResponse.getDetails().get(0).getProductForYou();
                    List<Slider> ads = homeResponse.getDetails().get(1).getSliders();
                    populateCategoryList(categories);
                    populateAdList(ads);
                    populateMadeForYouList(productForYous);
                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                AlertDialogHelper.dismiss(getContext());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateAdList(List<Slider> ads) {

//        list_ad.add(new ModelAd("Fresh fruits", "DISCOUNT 25% ALL FRUITS", R.drawable.fruit_image));
//        list_ad.add(new ModelAd("First Order", "DISCOUNT 25% ALL VEGETABLES", R.drawable.vegetable_image));
//        list_ad.add(new ModelAd("Fresh fruits", "DISCOUNT 25% ALL FRUITS", R.drawable.fruit_image));
        list_ad.addAll(ads);
        adapterAd.notifyDataSetChanged();
    }

    private void populateCategoryList(List<ModelCategory> modelCategoryList) {
        list_category.addAll(modelCategoryList);
        adapterCategory.notifyDataSetChanged();
    }

    private void populateMadeForYouList(List<ProductDetail> productDetailList) {
        list_madeforyou.addAll(productDetailList);
        adapterItem_madeforyou.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), "Item--" + list_madeforyou.get(position).getName(), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentProduct.MODEL_ITEM, list_madeforyou.get(position));
        FragmentProduct fragmentProduct = new FragmentProduct();
        fragmentProduct.setArguments(bundle);
        ((MainActivity) getActivity()).changeFragment(fragmentProduct);

    }

    @Override
    public void onCategoryClick(ModelCategory selectedItem) {
        Toast.makeText(getContext(), "Item--" + selectedItem.getName(), Toast.LENGTH_SHORT).show();
        selectCategory(selectedItem);
    }

    public void selectCategory(ModelCategory modelCategory) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(SELECTED_CATEGORY, modelCategory);
        FragmentCategory fragmentCategory = new FragmentCategory();
        fragmentCategory.setArguments(bundle);
        ((MainActivity) getActivity()).changeFragment(fragmentCategory);
    }

    @Override
    public void onAddClick(int position,String url) {
        webView.loadUrl(url);
    }
}