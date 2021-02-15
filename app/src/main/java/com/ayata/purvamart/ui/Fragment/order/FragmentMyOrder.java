package com.ayata.purvamart.ui.Fragment.order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelOrderList;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.generic.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.RecentOrderDetails;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.data.repository.Repository;
import com.ayata.purvamart.ui.Adapter.AdapterOrder;
import com.ayata.purvamart.ui.Adapter.AdapterOrderDetails;
import com.ayata.purvamart.ui.Adapter.ViewPagerMyOrderAdapter;
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

public class FragmentMyOrder extends Fragment implements NetworkResponseListener<JsonObject>, AdapterOrder.OnItemClickListener, ViewPagerMyOrderAdapter.setOnShopButtonClick, AdapterOrderDetails.OnCompletedAndCancelledItemClickListener {
    public static final String ORDER_ITEM_FOR_TRACK = "ORDER_ITEM";
    public static final String ORDER_ITEM_FOR_SUMMARY = "ORDER_ITEM_FOR_SUMMARY";
    public static final String FRAGMENT_MY_ORDER = "FRAGMENT_MY_ORDER";
    public static String TAG = "FragmentMyOrder";
    public static final String empty_title = "EMPTY_TITLE";
    //error
    TextView text_error;
    ProgressBar progress_error;
    //call
    Call<JsonObject> mCall;
    //for click listener
    AdapterOrder adapterOrder;
    AdapterOrderDetails adapterOrderCompleted;
    //myOrder tablayout
    TabLayout tabLayoutMyOrder;
    ViewPager2 viewPagerMyOrder;
    List<ModelOrderList> listitemIsOrdered, listitemIsTaken, listitemIsCancelled;
    ViewPagerMyOrderAdapter viewPagerMyOrderAdapter;

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
        //myorder tablayout and viewpager
        tabLayoutMyOrder = view.findViewById(R.id.tabLayout2);
        viewPagerMyOrder = view.findViewById(R.id.viewPager2);
        //adapter
        viewPagerMyOrderAdapter = new ViewPagerMyOrderAdapter();
        //listener for shop button inside empty myorder
        viewPagerMyOrderAdapter.setShopListener(this);
        getAllOrder();
        viewPagerMyOrder.setAdapter(viewPagerMyOrderAdapter);
        //tab titles
        String[] titles = {"On Progress", "Completed", "Cancelled"};
        new TabLayoutMediator(tabLayoutMyOrder, viewPagerMyOrder,
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
                //set empty view for all
                viewPagerMyOrderAdapter.setEmptyCompletedOrder(true);
                viewPagerMyOrderAdapter.setEmptyOnProgressOrder(true);
                viewPagerMyOrderAdapter.setEmptyCancelledOrder(true);
                viewPagerMyOrderAdapter.notifyDataSetChanged();
            } else {
                Gson gson = new GsonBuilder().create();
                TypeToken<List<RecentOrderDetails>> responseTypeToken = new TypeToken<List<RecentOrderDetails>>() {
                };
                List<RecentOrderDetails> detail = gson.fromJson(gson.toJson(jsonObject.getAsJsonArray("details")), responseTypeToken.getType());
                for (RecentOrderDetails orderDetails : detail) {
                    String orderId = orderDetails.getOrderId();
                    String date = orderDetails.getCreatedDate();
                    String estimatedTime = orderDetails.getEstimatedDate();
                    if (orderDetails.getConditional_status() != null) {
                        switch (orderDetails.getConditional_status()) {
                            case "Order Delivered":
                                listitemIsOrdered.add(new ModelOrderList("", orderId, date, "", estimatedTime, orderDetails.getItems(), orderDetails.getTotal(), orderDetails.getPayment_type(), orderDetails.getActual_condition()));
                                break;
                            case "Order Cancelled":
//                                listitemIsCancelled.add(new ModelOrderList("", orderId, date, "", estimatedTime, orderDetails.getItems(), orderDetails.getTotal(), orderDetails.getPayment_type()));
                                break;
                            default:
                                listitemIsTaken.add(new ModelOrderList("", orderId, date, "", estimatedTime, orderDetails.getItems(), orderDetails.getTotal(), orderDetails.getPayment_type(), orderDetails.getConditional_status()));
                                break;
                        }
                    }
                }
                Log.d(TAG, "onResponseReceived: calcelled size" + listitemIsCancelled.size() + "onprogree" + listitemIsTaken.size());
                viewPagerMyOrderAdapter.setOnCompleted(listitemIsOrdered);
                viewPagerMyOrderAdapter.setOnProgress(listitemIsTaken);
                viewPagerMyOrderAdapter.setOnCancelled(listitemIsCancelled);
                //remove empty view layout when list size>0
                if (listitemIsOrdered.size() == 0) {
                    viewPagerMyOrderAdapter.setEmptyCompletedOrder(true);
                    Log.d(TAG, "onResponseReceived: complete");
                }
                if (listitemIsTaken.size() == 0) {
                    viewPagerMyOrderAdapter.setEmptyOnProgressOrder(true);
                }
                if (listitemIsCancelled.size() == 0) {
                    viewPagerMyOrderAdapter.setEmptyCancelledOrder(true);
                    Log.d(TAG, "onResponseReceived: calnceeled");
                }
                viewPagerMyOrderAdapter.notifyDataSetChanged();

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
        viewPagerMyOrderAdapter.setEmptyCompletedOrder(false);
        viewPagerMyOrderAdapter.setEmptyOnProgressOrder(false);
        viewPagerMyOrderAdapter.setEmptyCancelledOrder(false);
        viewPagerMyOrderAdapter.notifyDataSetChanged();
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

    /**
     * Go to order tracking Fragment when clicked on OnProgress order Tab
     *
     * @param position
     * @param modelOrderList
     * @see FragmentTrackOrder
     */
    @Override
    public void onItemClick(int position, ModelOrderList modelOrderList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ORDER_ITEM_FOR_TRACK, modelOrderList);
        ((MainActivity) getActivity()).changeFragment(10, FragmentTrackOrder.TAG, bundle, new FragmentTrackOrder());
    }

    /**
     * Shop button when empty layout is visible
     */
    @Override
    public void onShopButtonClick() {
        ((MainActivity) getActivity()).selectShopFragment();
    }

    /**
     * Go to order summary Fragment when clicked on OnCancelled and OnCompleted Tab
     *
     * @param position
     * @param modelOrderList
     * @see FragmentOrderSummary
     */
    @Override
    public void onCompletedAndCancelledItemClick(int position, ModelOrderList modelOrderList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ORDER_ITEM_FOR_SUMMARY, modelOrderList);
        ((MainActivity) getActivity()).changeFragment(20, FragmentOrderSummary.TAG, bundle, new FragmentOrderSummary());
    }
}