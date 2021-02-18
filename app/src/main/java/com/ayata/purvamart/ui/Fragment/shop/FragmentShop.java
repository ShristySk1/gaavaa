package com.ayata.purvamart.ui.Fragment.shop;

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
import com.ayata.purvamart.data.Model.Stories;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.generic.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.BaseResponse;
import com.ayata.purvamart.data.network.response.HomeDetail;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.data.network.response.Slider;
import com.ayata.purvamart.data.repository.Repository;
import com.ayata.purvamart.ui.Adapter.AdapterAd;
import com.ayata.purvamart.ui.Adapter.AdapterCategory;
import com.ayata.purvamart.ui.Adapter.AdapterItem;
import com.ayata.purvamart.ui.Adapter.AdapterStories;
import com.ayata.purvamart.ui.Fragment.shop.category.FragmentCategory;
import com.ayata.purvamart.ui.Fragment.shop.product.FragmentProduct;
import com.ayata.purvamart.ui.Fragment.shop.search.SearchActivity;
import com.ayata.purvamart.ui.Fragment.shop.stories.StoryActivity;
import com.baoyz.widget.PullRefreshLayout;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentShop extends Fragment implements AdapterCategory.OnCategoryClickListener, AdapterItem.OnItemClickListener, AdapterAd.setOnAddListener, AdapterStories.setOnStoryListener, NetworkResponseListener<BaseResponse<List<HomeDetail>>>, AdapterShop.OnSeeAllClickListener {
    public static String TAG = "FragmentShop";
    public static final String SELECTED_CATEGORY = "SelectCategory";
    private View view;
    private RecyclerView recyclerView_all;
    private static List<ModelCategory> list_category;
    private static List<ProductDetail> list_madeforyou;
    List<Stories> storiesList = new ArrayList<>();


    public static List<ModelCategory> getList_category() {
        return list_category;
    }

    public static List<ProductDetail> getList_madeforyou() {
        return list_madeforyou;
    }

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
    //adaptersgop
    AdapterShop adapterShop;
    List<ModelShop> list_shop;

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
                list_shop.clear();
                getAllHomeList();
            }
        });
        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //search method
                Toast.makeText(getContext(), "Search clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
        toolbar = view.findViewById(R.id.toolbar);
        return view;
    }

    private void initView() {
        webView = view.findViewById(R.id.webView);
        search_layout = view.findViewById(R.id.search_layout);
        recyclerView_all = view.findViewById(R.id.recycler_all);
//        //main view other than shimmer
        relativeLayout_main_view = view.findViewById(R.id.rl_main_view);
//        //shimmer init
        shimmerFrameLayout = view.findViewById(R.id.shimmerlayout);
        relativeLayout_main_view.setVisibility(View.GONE);
        shimmerFrameLayout.setVisibility(View.VISIBLE);

        //setRecyclerview
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView_all.setLayoutManager(manager);
        list_shop = new ArrayList<>();
        adapterShop = new AdapterShop(getContext(), list_shop, this);
        recyclerView_all.setAdapter(adapterShop);
        //api
        ((MainActivity)getActivity()).setItemCart();
        getAllHomeList();
    }

    /*
    Api call
     */
    private void getAllHomeList() {
        Repository repository = new Repository(this, ApiClient.getApiService());
        repository.requestAllHome();
    }

    /**
     * on single product click
     *
     * @param productDetail
     */
    @Override
    public void onItemClick(ProductDetail productDetail) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentProduct.MODEL_ITEM, productDetail);
        ((MainActivity) getActivity()).changeFragment(8, FragmentProduct.TAG, bundle, new FragmentProduct());

    }

    /**
     * For each category click listener
     *
     * @param selectedItem
     */
    @Override
    public void onCategoryClick(ModelCategory selectedItem) {
//        Toast.makeText(getContext(), "Item--" + selectedItem.getName(), Toast.LENGTH_SHORT).show();
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
        ((MainActivity) getActivity()).changeFragment(9, FragmentCategory.TAG, bundle, new FragmentCategory());
    }

    /**
     * Advertisement click listener
     *
     * @param position
     * @param url
     */
    @Override
    public void onAdClick(int position, String url) {
        webView.loadUrl(url);
    }

    /**
     * inflate layout for error and progressbar
     */
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

    @Override
    public void onResponseReceived(BaseResponse<List<HomeDetail>> homeDetailList) {
        shimmerFrameLayout.stopShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.GONE);
        relativeLayout_main_view.setVisibility(View.VISIBLE);
        List<ModelCategory> categories = homeDetailList.getDetails().get(0).getCategory();
        List<ProductDetail> productForYous = homeDetailList.getDetails().get(0).getProductForYou();
        List<Slider> ads = homeDetailList.getDetails().get(0).getSliders();
        populateWholeList(ads, categories, productForYous);
        pullRefreshLayout.setRefreshing(false);
        Log.d(TAG, "onResponseReceived: " + homeDetailList.getDetails().size());
    }

    private void populateWholeList(List<Slider> ads, List<ModelCategory> categories, List<ProductDetail> productForYous) {
        list_category = categories;
        list_madeforyou = productForYous;
        storiesList.clear();
        storiesList.add(new Stories("https://cdn.mos.cms.futurecdn.net/atyrpYQoxdoTzmEgu8HMWE.jpg","Gaava"));
        storiesList.add(new Stories("https://skinshare.sg/wp-content/uploads/2016/05/hooney.jpg","Gaava"));
        storiesList.add(new Stories("https://www.therahnuma.com/wp-content/uploads/2019/07/coffee.jpg","Gaava"));
        storiesList.add(new Stories("https://cdn.mos.cms.futurecdn.net/atyrpYQoxdoTzmEgu8HMWE.jpg","Gaava"));
        storiesList.add(new Stories("https://skinshare.sg/wp-content/uploads/2016/05/hooney.jpg","Gaava"));
        storiesList.add(new Stories("https://www.therahnuma.com/wp-content/uploads/2019/07/coffee.jpg","Gaava"));
        list_shop.add(new ModelShop(ModelShop.STORY_TYPE, storiesList, this));
        list_shop.add(new ModelShop(ModelShop.AD_TYPE, ads, this));
        list_shop.add(new ModelShop(ModelShop.TTTLE_TYPE, "Categories", this));
        list_shop.add(new ModelShop(ModelShop.CATEGORY_TYPE, categories, this));
        list_shop.add(new ModelShop(ModelShop.TTTLE_TYPE, "Made For You", this));
        list_shop.add(new ModelShop(ModelShop.PRODUCT_TYPE, productForYous, this));

        Log.d(TAG, "populateWholeList: " + list_shop.size());
        adapterShop.notifyDataSetChanged();
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

    @Override
    public void onSeeAllClick() {
        ModelCategory modelCategory = new ModelCategory(0, "All", "", true);
        selectCategory(modelCategory);
    }

    @Override
    public void onStoryClick(int position, String url) {
        startActivity(new Intent(getActivity(), StoryActivity.class));
    }
}
