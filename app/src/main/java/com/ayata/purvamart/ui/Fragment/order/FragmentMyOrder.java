package com.ayata.purvamart.ui.Fragment.order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelOrderList;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.helper.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.RecentOrderDetails;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.data.repository.Repository;
import com.ayata.purvamart.ui.Adapter.AdapterOrder;
import com.ayata.purvamart.ui.Adapter.AdapterOrderCompleted;
import com.ayata.purvamart.ui.Adapter.ViewPagerMyOrderAdapter;
import com.ayata.purvamart.ui.Fragment.unused.FragmentOrderSummary;
import com.ayata.purvamart.ui.login.SignupActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import retrofit2.Call;

public class FragmentMyOrder extends Fragment implements NetworkResponseListener<JsonObject>, AdapterOrder.OnItemClickListener, ViewPagerMyOrderAdapter.setOnShopButtonClick, AdapterOrderCompleted.OnCompletedItemClickListener {
    public static final String order_item = "ORDER_ITEM";
    public static final String completed_order_item = "COMPLETED_ORDER_ITEM";
    public static final String FRAGMENT_MY_ORDER = "FRAGMENT_MY_ORDER";
    public static String TAG = "FragmentMyOrder";
    private LinearLayout option1, option2, option3;
    private TextView textView1, textView2, textView3;
    private View line1, line2, line3;
    public static final String empty_title = "EMPTY_TITLE";
    List<ModelOrderList> listitem;
    //error
    TextView text_error;
    ProgressBar progress_error;
    //switch case
    Fragment fragment = null;
    Bundle bundle = new Bundle();
    //call
    Call<JsonObject> mCall;


    //for click listener
    AdapterOrder adapterOrder;
    AdapterOrderCompleted adapterOrderCompleted;
    //buslist tablayout
    TabLayout tabLayoutBusList;
    ViewPager2 viewPagerBusList;
    List<ModelOrderList> listitemIsOrdered, listitemIsTaken, listitemIsCancelled;
    ViewPagerMyOrderAdapter viewPagerBusListAdapter;
    //Buttton shop
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);
        inflateLayout(view);
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType3("My Order");
        //bottom nav bar
        ((MainActivity) getActivity()).showBottomNavBar(true);
        listitemIsTaken = new ArrayList<>();
        listitemIsCancelled = new ArrayList<>();
        listitemIsOrdered = new ArrayList<>();
        //buslist tablayout and viewpager
        tabLayoutBusList = view.findViewById(R.id.tabLayout2);
        viewPagerBusList = view.findViewById(R.id.viewPager2);
        //bus-list recyclerview with tab pullRefreshLayout
        viewPagerBusListAdapter = new ViewPagerMyOrderAdapter();
        viewPagerBusListAdapter.setShopListener(this);
        getAllOrder();
        viewPagerBusList.setAdapter(viewPagerBusListAdapter);
        String[] titles = {"On Progress", "Completed", "Cancelled"};
        new TabLayoutMediator(tabLayoutBusList, viewPagerBusList,
                (tab, position) -> tab.setText(titles[position])
        ).attach();
        adapterOrder.setListener(this);
        adapterOrderCompleted.setListener(this);
        return view;
    }

    void getAllOrder() {
        ApiService myOrderApi = ApiClient.getClient().create(ApiService.class);
        if (!PreferenceHandler.isUserAlreadyLoggedIn(getContext())) {
            Toast.makeText(getContext(), "Please Login to continue", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getContext(), SignupActivity.class));
            return;
        }
        new Repository(this, myOrderApi).requestMyRecentOrder();
    }


    @Override
    public void onDestroyView() {
        if (mCall != null && mCall.isExecuted()) {
            mCall.cancel();
        }
        super.onDestroyView();
    }

    @Override
    public void onResponseReceived(JsonObject jsonObject) {
        progress_error.setVisibility(View.GONE);
        if (jsonObject.get("code").toString().equals("200")) {
            if (jsonObject.get("message").getAsString().equals("empty cart")) {
                viewPagerBusListAdapter.setEmptyCompletedOrder(true);
                viewPagerBusListAdapter.setEmptyOnProgressOrder(true);
                viewPagerBusListAdapter.setEmptyCancelledOrder(true);
                viewPagerBusListAdapter.notifyDataSetChanged();
            } else {
                Gson gson = new GsonBuilder().create();
                TypeToken<List<RecentOrderDetails>> responseTypeToken = new TypeToken<List<RecentOrderDetails>>() {
                };
                List<RecentOrderDetails> detail = gson.fromJson(gson.toJson(jsonObject.getAsJsonArray("details")), responseTypeToken.getType());
                for (RecentOrderDetails orderDetails : detail) {
                    String orderId = orderDetails.getOrderId();
                    String date = orderDetails.getCreatedDate();
                    String estimatedTime = orderDetails.getEstimatedDate();
//                    for (ProductDetail productDetail : orderDetails.getItems()) {
//                        String image = "";
//                        if (productDetail.getProductImage().size() > 0) {
//                            image = productDetail.getProductImage().get(0);
//                        }
//                        if (productDetail.getOrdered()) {
//                            listitemIsOrdered.add(new ModelOrderList(image, orderId, productDetail.getCreatedDate(), "", "1st Jan"));
//                        } else if (productDetail.getCancelled()) {
//                            listitemIsCancelled.add(new ModelOrderList(image, "22574", productDetail.getCreatedDate(), "", "1st Jan"));
//                        } else if (productDetail.getTaken()) {
//                            listitemIsTaken.add(new ModelOrderList(image, "22574", productDetail.getCreatedDate(), "", "1st Jan"));
//                        }
//                    }
                    switch (orderDetails.getConditional_status()) {
                        case "Delivered":
                            listitemIsOrdered.add(new ModelOrderList("", orderId, date, "", estimatedTime, orderDetails.getItems()));
                            break;
                        case "Ordered":
                            listitemIsTaken.add(new ModelOrderList("", orderId, date, "", estimatedTime, orderDetails.getItems()));
                            break;
                        case "Cancelled":
                            listitemIsCancelled.add(new ModelOrderList("", "22574", date, "", estimatedTime, orderDetails.getItems()));
                            break;
                    }

                }
                Log.d(TAG, "onResponseReceived: calcelled size" + listitemIsCancelled.size() + "onprogree" + listitemIsTaken.size());
                viewPagerBusListAdapter.setToday(listitemIsOrdered);
                viewPagerBusListAdapter.setTomorrow(listitemIsTaken);
                viewPagerBusListAdapter.setDayAfter(listitemIsCancelled);
                if (listitemIsOrdered.size() == 0) {
                    viewPagerBusListAdapter.setEmptyCompletedOrder(true);
                    Log.d(TAG, "onResponseReceived: complete");
                }
                if (listitemIsTaken.size() == 0) {
                    viewPagerBusListAdapter.setEmptyOnProgressOrder(true);
                }
                if (listitemIsCancelled.size() == 0) {
                    viewPagerBusListAdapter.setEmptyCancelledOrder(true);
                    Log.d(TAG, "onResponseReceived: calnceeled");
                }
                viewPagerBusListAdapter.notifyDataSetChanged();

            }
        }

    }

    @Override
    public void onLoading() {
        progress_error.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message) {
        progress_error.setVisibility(View.GONE);
        text_error.setText(message);
        viewPagerBusListAdapter.setEmptyCompletedOrder(false);
        viewPagerBusListAdapter.setEmptyOnProgressOrder(false);
        viewPagerBusListAdapter.setEmptyCancelledOrder(false);
        viewPagerBusListAdapter.notifyDataSetChanged();
    }

    //inflate pullRefreshLayout for error and progressbar
    void inflateLayout(View view) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        //Avoid pass null in the root it ignores spaces in the child pullRefreshLayout
        View inflatedLayout = inflater.inflate(R.layout.error_layout, (ViewGroup) view, false);
        ViewGroup viewGroup = view.findViewById(R.id.root_main);
        viewGroup.addView(inflatedLayout);
        text_error = view.findViewById(R.id.text_error);
        progress_error = view.findViewById(R.id.progress_error);
    }

    @Override
    public void onItemClick(int position, ModelOrderList modelOrderList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(order_item, modelOrderList);
        ((MainActivity) getActivity()).changeFragment(10, FragmentTrackOrder.TAG, bundle);
    }

    @Override
    public void onShopButtonClick() {
        ((MainActivity) getActivity()).selectShopFragment();
    }

    @Override
    public void onCompletedItemClick(int position, ModelOrderList modelOrderList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(completed_order_item, modelOrderList);
        ((MainActivity) getActivity()).changeFragment(20, FragmentOrderSummary.TAG, bundle);
    }
}