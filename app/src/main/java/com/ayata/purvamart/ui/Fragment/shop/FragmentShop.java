package com.ayata.purvamart.ui.Fragment.shop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelCategory;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.helper.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.HomeResponse;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.data.network.response.Slider;
import com.ayata.purvamart.data.repository.Repository;
import com.ayata.purvamart.ui.Adapter.AdapterAd;
import com.ayata.purvamart.ui.Adapter.AdapterCategory;
import com.ayata.purvamart.ui.Adapter.AdapterItem;
import com.baoyz.widget.PullRefreshLayout;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
 * fragmentList.add(new FragmentThankyou());//16
 */
public class FragmentShop extends Fragment implements AdapterCategory.OnCategoryClickListener, AdapterItem.OnItemClickListener, AdapterAd.setOnAddListener, NetworkResponseListener<HomeResponse> {
    public static String TAG = "FragmentShop";
    public static final String SELECTED_CATEGORY = "SelectCategory";
    private View view;
    private RecyclerView recyclerView_ad, recyclerView_category, recyclerView_madeforyou;
    private List<Slider> list_ad;
    private LinearLayoutManager linearLayoutManager_ad;
    private AdapterAd adapterAd;

    private static List<ModelCategory> list_category;
    private LinearLayoutManager LayoutManager_category;
    private AdapterCategory adapterCategory;

    private static List<ProductDetail> list_madeforyou;

    public static List<ModelCategory> getList_category() {
        return list_category;
    }

    public static List<ProductDetail> getList_madeforyou() {
        return list_madeforyou;
    }

    private GridLayoutManager linearLayoutManager_madeforyou;
    private AdapterItem adapterItem_madeforyou;

    private RelativeLayout search_layout;
    WebView webView;
    //error
    TextView text_error;
    ProgressBar progress_error;
    //pull refresh
    PullRefreshLayout pullRefreshLayout;
    //shimmer
    ShimmerFrameLayout shimmerFrameLayout;
    RelativeLayout relativeLayout_main_view;
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        view = inflater.inflate(R.layout.fragment_shop, container, false);
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType1(true);

        //bottom nav bar
        ((MainActivity) getActivity()).showBottomNavBar(true);
        initView();
        //refresh
        pullRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        // listen refresh event
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                getAllHomeList();
            }
        });
        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //search method
                Toast.makeText(getContext(), "Search clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), SearchActivity.class));
//                //toolbar
//                ((MainActivity) getActivity()).hideToolbar();
//                //bottom nav
//                ((MainActivity) getActivity()).showBottomNavBar(false);
//                ((MainActivity)getActivity()).changeFragment(21,SearchFragment.TAG, null);

            }
        });
        toolbar = view.findViewById(R.id.toolbar);
        return view;
    }

    private void initView() {
        webView = view.findViewById(R.id.webView);
        search_layout = view.findViewById(R.id.search_layout);
        recyclerView_ad = view.findViewById(R.id.recycler_ad);
        recyclerView_category = view.findViewById(R.id.recycler_category);
        recyclerView_madeforyou = view.findViewById(R.id.recycler_type1);
        //main view other than shimmer
        relativeLayout_main_view = view.findViewById(R.id.rl_main_view);
        //shimmer init
        shimmerFrameLayout = view.findViewById(R.id.shimmerlayout);
        relativeLayout_main_view.setVisibility(View.GONE);
        shimmerFrameLayout.setVisibility(View.VISIBLE);

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
        //api
        getAllHomeList();

    }

    /*
    Api call
     */
    private void getAllHomeList() {
        Repository repository = new Repository(this, ApiClient.getApiService());
        repository.requestAllHome();
    }


    private void populateAdList(List<Slider> ads) {
        list_ad.clear();
        list_ad.addAll(ads);
        adapterAd.notifyDataSetChanged();
    }

    private void populateCategoryList(List<ModelCategory> modelCategoryList) {
        list_category.clear();
        list_category.addAll(modelCategoryList);
        adapterCategory.notifyDataSetChanged();
    }

    private void populateMadeForYouList(List<ProductDetail> productDetailList) {
        list_madeforyou.clear();
        list_madeforyou.addAll(productDetailList);
        adapterItem_madeforyou.notifyDataSetChanged();
    }

    /**
     * on single product click
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), "Item--" + list_madeforyou.get(position).getName(), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentProduct.MODEL_ITEM, list_madeforyou.get(position));
        ((MainActivity) getActivity()).changeFragment(8, FragmentProduct.TAG, bundle);

    }

    /**
     * For each category click listener
     *
     * @param selectedItem
     */
    @Override
    public void onCategoryClick(ModelCategory selectedItem) {
        Toast.makeText(getContext(), "Item--" + selectedItem.getName(), Toast.LENGTH_SHORT).show();
        selectCategory(selectedItem);
    }

    /**
     * Category to be passed in fragmentcategory class from shop page
     *
     * @param modelCategory
     */
    public void selectCategory(ModelCategory modelCategory) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(SELECTED_CATEGORY, modelCategory);
        ((MainActivity) getActivity()).changeFragment(9, FragmentCategory.TAG, bundle);
    }

    /**
     * Advertisement click listener
     *
     * @param position
     * @param url
     */
    @Override
    public void onAddClick(int position, String url) {
        webView.loadUrl(url);
    }

    @Override
    public void onResponseReceived(HomeResponse homeResponse) {
        shimmerFrameLayout.stopShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.GONE);
        relativeLayout_main_view.setVisibility(View.VISIBLE);
        List<ModelCategory> categories = homeResponse.getDetails().get(0).getCategory();
        List<ProductDetail> productForYous = homeResponse.getDetails().get(0).getProductForYou();
        List<Slider> ads = homeResponse.getDetails().get(0).getSliders();
        populateCategoryList(categories);
        populateAdList(ads);
        populateMadeForYouList(productForYous);
        pullRefreshLayout.setRefreshing(false);
        Log.d(TAG, "onResponseReceived: " + homeResponse.getDetails().size());
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onError(String message) {
        shimmerFrameLayout.setVisibility(View.GONE);
        if (isAdded()) {
            inflateLayout();
            progress_error.setVisibility(View.GONE);
            text_error.setText(message);
        }
    }

    //inflate pullRefreshLayout for error and progressbar
    void inflateLayout() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        //Avoid pass null in the root it ignores spaces in the child pullRefreshLayout
        View inflatedLayout = inflater.inflate(R.layout.error_layout, (ViewGroup) view, false);
//         int id=view.findViewById(android.R.id.content).getRootView().getId();
        ViewGroup viewGroup = view.findViewById(R.id.root_main);
//        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) view.findViewById(android.R.id.content)).getChildAt(0);
        viewGroup.addView(inflatedLayout);
        text_error = view.findViewById(R.id.text_error);
        progress_error = view.findViewById(R.id.progress_error);
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmerAnimation();
        super.onPause();

    }


}
