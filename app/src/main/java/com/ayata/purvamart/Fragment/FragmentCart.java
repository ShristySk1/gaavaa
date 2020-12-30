package com.ayata.purvamart.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelItem;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Cart;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.response.MyOrderResponse;
import com.ayata.purvamart.data.network.response.OrderDetail;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCart extends Fragment {
    public static final String TAG = "FRAGMENT_CART";
    public static final String FRAGMENT_CART = "FRAGMENT_CART";
    List<ModelItem> modelItemList;

    private View view;

    CardView progress_bar;
    FrameLayout main_layout;

    Bundle bundle = new Bundle();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType3("Cart");
        //bottom nav
        ((MainActivity) getActivity()).showBottomNavBar(true);

//        progress_bar = view.findViewById(R.id.progress_cardview);
        main_layout = view.findViewById(R.id.fragment_cart);

//        progress_bar.setVisibility(View.VISIBLE);
//        main_layout.setVisibility(View.GONE);
//
//        final Handler handler = new Handler(Looper.getMainLooper());
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Do something after 100ms
//
//                progress_bar.setVisibility(View.GONE);
//                main_layout.setVisibility(View.VISIBLE);
//            }
//        }, 600);

        dataPrepare();

        return view;
    }

    private void dataPrepare() {
        modelItemList = new ArrayList<>();
//        modelItemList.add(new ModelItem("Fresh Spinach", "Rs. 100.00", "Rs. 120.35",
//                R.drawable.spinach, "1 kg", true, "15% Off", 1));
//        modelItemList.add(new ModelItem("Fresh Tomatoes", "Rs. 150.00", "Rs. 00",
//                R.drawable.tomato, "1 kg", false, "0%", 1));
//        modelItemList.add(new ModelItem("Fresh Spinach", "Rs. 100.00", "Rs. 120.35",
//                R.drawable.spinach, "1 kg", true, "10% Off", 2));

        ApiService myOrderApi = ApiClient.getClient().create(ApiService.class);
        myOrderApi.getMyOrder(PreferenceHandler.getToken(getContext())).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject.get("code").toString().equals("200")) {
                        Gson gson = new GsonBuilder().create();
                        String empty = jsonObject.get("message").getAsString();
                        Log.d(TAG, "onResponse: " + empty + "crt");
                        if (empty.equals("empty cart")) {
                            Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            MyOrderResponse myOrderResponse = gson.fromJson(gson.toJson(jsonObject), MyOrderResponse.class);
                            Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
                            for (OrderDetail orderDetail : myOrderResponse.getDetails()) {
                                if (orderDetail.getIsTaken()) {
                                    modelItemList.add(new ModelItem(orderDetail.getProductId(),orderDetail.getProductId().toString(), orderDetail.getPrice().toString(), String.valueOf(orderDetail.getPrice() * orderDetail.getProductQuantity()),
                                            R.drawable.spinach, orderDetail.getProductQuantity().toString(), true, "15% Off", 1));
                                }
                            }
                            Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
//                        Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "" + "Please login to continue".toString(), Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
                checkForData();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }

    private void checkForData() {

        if (modelItemList != null&&modelItemList.size()!=0) {
            bundle.putSerializable(FRAGMENT_CART, (Serializable) modelItemList);
            FragmentCartFilled fragmentCartFilled = new FragmentCartFilled();
            fragmentCartFilled.setArguments(bundle);
            changeFragment(fragmentCartFilled);

        } else {
            changeFragment(new FragmentCartEmpty());
        }
    }

    private void changeFragment(Fragment fragment) {

        getChildFragmentManager().beginTransaction().add(R.id.fragment_cart, fragment).addToBackStack("cart").commit();
    }


}