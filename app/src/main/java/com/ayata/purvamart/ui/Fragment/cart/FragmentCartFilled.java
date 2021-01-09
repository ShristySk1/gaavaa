package com.ayata.purvamart.ui.Fragment.cart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.ui.Adapter.AdapterCart;
import com.ayata.purvamart.ui.Fragment.account.FragmentDeliveryAddress;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.data.Model.ModelItem;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.helper.GenericNetworkResponse;
import com.ayata.purvamart.data.network.helper.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.UserCartDetail;
import com.ayata.purvamart.data.network.response.UserCartResponse;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
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
 */
public class FragmentCartFilled extends Fragment implements AdapterCart.OnCartItemClickListener, NetworkResponseListener<JsonObject> {
    public static String TAG = "FragmentCartFilled";
    RecyclerView recyclerView;
    List<ModelItem> modelItemList;
    Long grandTotal;
    AdapterCart adapterCart;
    TextView textTotal;
    LinearLayout layout_proceed;

    private View view;
    ApiService apiService;

    //int position
    static Integer id = null;
    static Integer pos = null;
    Boolean isItemDeleted = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        view = inflater.inflate(R.layout.fragment_cart_filled, container, false);
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType3("Cart");
        //bottom nav
        ((MainActivity) getActivity()).showBottomNavBar(true);

        initView(view);
        dataPrepare();
        setUpRecyclerView();
        apiService = ApiClient.getClient().create(ApiService.class);
        layout_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent
                //get data accepted for checkout
//                Cart.modelItems = adapterCart.getAllDataFromCart();
                requestCheckOut(new NetworkResponseListener<JsonObject>() {
                    @Override
                    public void onResponseReceived(JsonObject response) {
                        if (response.get("code").getAsString().equals("200")) {
                            String orderId = response.getAsJsonObject("details").get("order_id").getAsString();
                            Log.d(TAG, "myOrderIdis: " + orderId);
                            PreferenceHandler.setOrderId(getContext(), orderId);
                            PreferenceHandler.setGrandTotal(getContext(), grandTotal.toString());
                            ((MainActivity) getActivity()).changeFragment(18, FragmentDeliveryAddress.TAG, null);
                        } else {
                            Toast.makeText(getContext(), response.get("message").getAsString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                    }
                }, apiService, modelItemList);
            }
        });
        return view;
    }

    private void dataPrepare() {
        modelItemList = new ArrayList<>();
        Bundle bundle = getArguments();
        if (bundle != null) {
            modelItemList = (ArrayList<ModelItem>) bundle.getSerializable(FragmentCart.FRAGMENT_CART);
            grandTotal = bundle.getLong(FragmentCart.FRAGMENT_CART_TOTAL);
            Log.d(TAG, "dataPrepare: grandtotal" + grandTotal);
            textTotal.setText(grandTotal.toString());
        }
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adapterCart = new AdapterCart(getContext(), modelItemList, this);
        recyclerView.setAdapter(adapterCart);
    }

    private void initView(View v) {
        recyclerView = v.findViewById(R.id.recycler_cart);
        textTotal = v.findViewById(R.id.text_subtotal);
        layout_proceed = v.findViewById(R.id.layout_cartProceed);
    }

    @Override
    public void onAddClick(ModelItem modelItem, int position) {
        id = modelItem.getId();
        pos = position;
        requestAddProductCount(this, apiService, modelItem.getId());
    }

    public void requestAddProductCount(NetworkResponseListener<JsonObject> listener, ApiService api, Integer product_id) {
        api.addProductCount(product_id).enqueue(new GenericNetworkResponse<>(listener));
    }

    public void requestMinusProductCount(NetworkResponseListener<JsonObject> listener, ApiService api, Integer product_id) {
        api.minusProductCount( product_id).enqueue(new GenericNetworkResponse<>(listener));
    }

    public void requestCheckOut(NetworkResponseListener<JsonObject> listener, ApiService api, List<ModelItem> modelItemList) {
        api.addToOrder(new Gson().toJson(modelItemList)).enqueue(new GenericNetworkResponse<>(listener));
//        api.addToOrder(PreferenceHandler.getToken(getContext()),modelItemList).enqueue(new NetworkResponse<>(listener));

    }

    @Override
    public void onMinusClick(ModelItem modelItem, int position) {
        id = modelItem.getId();
        pos = position;
        if (modelItem.getCount() == 1) {
            isItemDeleted = true;
        } else {
            isItemDeleted = false;

        }
        requestMinusProductCount(this, apiService, modelItem.getId());
    }

    @Override
    public void onCartItemClick(int position) {
    }

    @Override
    public void onResponseReceived(JsonObject jsonObject) {
        ModelItem newItem = null;
        if (jsonObject.get("code").toString().equals("200")) {
            Log.d(TAG, "onResponseReceived: " + jsonObject.get("details").toString() + jsonObject.get("message"));
            if (isItemDeleted) {
                modelItemList.remove(modelItemList.get(pos));
                adapterCart.notifyItemRemoved(pos);
                Log.d(TAG, "onResponseReceiveddeleted: " + modelItemList.size());
                Gson gson = new GsonBuilder().create();
                UserCartResponse myOrderResponse = gson.fromJson(gson.toJson(jsonObject), UserCartResponse.class);
                String grandTotal = myOrderResponse.getGrandTotal().toString();
                textTotal.setText(grandTotal);
                isItemDeleted = false;
//                if (grandTotal.equals("0")) {
//                    Log.d(TAG, "equals zero: " + grandTotal);
//                    if (isAdded())
//                        changeFragment(new FragmentCartEmpty());
//                }
            }
            Log.d(TAG, "onResponseReceivednotdelete: " + modelItemList.size());
            Gson gson = new GsonBuilder().create();
            String empty = jsonObject.get("message").getAsString();
            Log.d(TAG, "onResponse: " + empty + "crt");
            if (empty.equals("empty cart")) {
                if (isAdded())
                    changeFragment(new FragmentCartEmpty());
            } else {
                UserCartResponse myOrderResponse = gson.fromJson(gson.toJson(jsonObject), UserCartResponse.class);
                if (isAdded())
                    Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
                for (UserCartDetail orderDetail : myOrderResponse.getDetails()) {
                    if (orderDetail.getId() == id) {
                        String nullCheckImage = "";
                        if (orderDetail.getProductImage().size() > 0) {
                            nullCheckImage = orderDetail.getProductImage().get(0);
                        }
                        newItem = new ModelItem(orderDetail.getId(),
                                orderDetail.getName(), String.valueOf(orderDetail.getTotalPrice()),
                                String.valueOf(orderDetail.getProductPrice()),
                                nullCheckImage, orderDetail.getProductQuantity().toString(),
                                true, orderDetail.getProductDiscount(), orderDetail.getProductQuantity(),orderDetail.getUnit());
                        modelItemList.set(pos, newItem);
                        Log.d(TAG, "onResponseReceived: " + modelItemList.get(pos).getQuantity());
                        adapterCart.notifyItemChanged(pos);
                    } else {
                    }
                }
                String grandTotal = myOrderResponse.getGrandTotal().toString();
                Log.d(TAG, "onResponseReceived: grandtotal" + grandTotal);
                Log.d(TAG, "dataPrepare: grandtotal" + grandTotal);
                textTotal.setText(grandTotal);
                if (grandTotal.equals("0")) {
                    changeFragment(new FragmentCartEmpty());
                }
            }
        }
    }

    private void changeFragment(Fragment fragment) {
//        getFragmentManager().beginTransaction().replace(R.id.fragment_cart, fragment).commit();
        ((MainActivity) getActivity()).changeFragment(5, FragmentCart.TAG, null);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}