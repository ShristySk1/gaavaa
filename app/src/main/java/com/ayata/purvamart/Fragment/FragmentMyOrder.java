package com.ayata.purvamart.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ayata.purvamart.Adapter.AdapterOrder;
import com.ayata.purvamart.Adapter.ViewPagerMyOrderAdapter;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelOrderList;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.helper.NetworkResponse;
import com.ayata.purvamart.data.network.helper.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.UserCartDetail;
import com.ayata.purvamart.data.network.response.UserCartResponse;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import retrofit2.Call;

public class FragmentMyOrder extends Fragment implements NetworkResponseListener<JsonObject>, AdapterOrder.OnItemClickListener, ViewPagerMyOrderAdapter.setOnShopButtonClick {
    public static final String order_item = "ORDER_ITEM";
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


    //for click listener on bus
    AdapterOrder adapterOrder;
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
        String[] titles = {"Completed", "On Progress", "Cancelled"};
        new TabLayoutMediator(tabLayoutBusList, viewPagerBusList,
                (tab, position) -> tab.setText(titles[position])
        ).attach();
        adapterOrder.setListener(this);
        return view;
    }

    void getAllOrder() {
        ApiService myOrderApi = ApiClient.getClient().create(ApiService.class);
        requestMyOrder(this, myOrderApi);
    }

    public void requestMyOrder(NetworkResponseListener<JsonObject> listener, ApiService api) {
        api.getMyOrder(PreferenceHandler.getToken(getContext())).enqueue(new NetworkResponse<>(listener));
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
                UserCartResponse myOrderResponse = gson.fromJson(gson.toJson(jsonObject), UserCartResponse.class);
                for (UserCartDetail orderDetail : myOrderResponse.getDetails()) {
                    if (orderDetail.getIsTaken()==null) {
                    }else {
                        String image = "";
                        if (orderDetail.getProductImage().size() > 0) {
                            image = orderDetail.getProductImage().get(0);
                        }
                        if (orderDetail.getIsOrdered()) {
                            listitemIsOrdered.add(new ModelOrderList(image, "22574", orderDetail.getCreatedDate(), "", "1st Jan"));
                        } else if (orderDetail.getIsCancelled()) {
                            listitemIsCancelled.add(new ModelOrderList(image, "22574", orderDetail.getCreatedDate(), "", "1st Jan"));
                        } else if (orderDetail.getIsTaken()) {
                            listitemIsTaken.add(new ModelOrderList(image, "22574", orderDetail.getCreatedDate(), "", "1st Jan"));
                        }
                    }
                }
                Log.d(TAG, "onResponseReceived: calcelled size" + listitemIsCancelled.size()+"onprogree"+listitemIsTaken.size());
                if (listitemIsOrdered.size() == 0) {
                    viewPagerBusListAdapter.setEmptyCompletedOrder(true);
                } else if (listitemIsTaken.size() == 0) {
                    viewPagerBusListAdapter.setEmptyOnProgressOrder(true);
                } else if (listitemIsCancelled.size() == 0) {
                    viewPagerBusListAdapter.setEmptyCancelledOrder(true);
                }
                viewPagerBusListAdapter.setToday(listitemIsOrdered);
                viewPagerBusListAdapter.setTomorrow(listitemIsTaken);
                viewPagerBusListAdapter.setDayAfter(listitemIsCancelled);
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
}