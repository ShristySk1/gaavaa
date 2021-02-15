package com.ayata.purvamart.ui.Fragment.cart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.CartCount;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.generic.GenericNetworkResponse;
import com.ayata.purvamart.data.network.generic.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.data.network.response.UserCartResponse;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.ui.Adapter.AdapterCart;
import com.ayata.purvamart.ui.Fragment.account.address.FragmentDeliveryAddress;
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
    List<ProductDetail> modelItemList;
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
                requestCheckOut(new NetworkResponseListener<JsonObject>() {
                    @Override
                    public void onResponseReceived(JsonObject response) {
                        if (response.get("code").getAsString().equals("200")) {
                            String orderId = response.getAsJsonObject("details").get("order_id").getAsString();
                            Log.d(TAG, "myOrderIdis: " + orderId);
                            PreferenceHandler.setOrderId(getContext(), orderId);
                            ((MainActivity) getActivity()).changeFragment(18, FragmentDeliveryAddress.TAG, null,new FragmentDeliveryAddress());
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
            modelItemList = (ArrayList<ProductDetail>) bundle.getSerializable(FragmentCart.FRAGMENT_CART);
            grandTotal = bundle.getLong(FragmentCart.FRAGMENT_CART_TOTAL);
            Log.d(TAG, "dataPrepare: grandtotal" + grandTotal);
            PreferenceHandler.setGrandTotal(getContext(), grandTotal.toString());
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
    public void onAddClick(ProductDetail modelItem, int position) {
        id = modelItem.getId();
        pos = position;
        requestAddProductCount(this, apiService, modelItem.getId());
    }

    public void requestAddProductCount(NetworkResponseListener<JsonObject> listener, ApiService api, Integer product_id) {
        api.addProductCount(product_id).enqueue(new GenericNetworkResponse<>(listener));
    }

    public void requestMinusProductCount(NetworkResponseListener<JsonObject> listener, ApiService api, Integer product_id) {
        api.minusProductCount(product_id).enqueue(new GenericNetworkResponse<>(listener));
    }

    public void requestCheckOut(NetworkResponseListener<JsonObject> listener, ApiService api, List<ProductDetail> modelItemList) {
        api.addToOrder(new Gson().toJson(modelItemList)).enqueue(new GenericNetworkResponse<>(listener));

    }

    @Override
    public void onMinusClick(ProductDetail modelItem, int position) {
        id = modelItem.getId();
        pos = position;
        if (Integer.valueOf(modelItem.getProduct_quantity()) == 1) {
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
        if (jsonObject.get("code").toString().equals("200")) {
            Log.d(TAG, "onResponseReceived: " + jsonObject.get("details").toString() + jsonObject.get("message"));
            if (isItemDeleted) {
                modelItemList.remove(modelItemList.get(pos));
                adapterCart.notifyItemRemoved(pos);
                Log.d(TAG, "onResponseReceiveddeleted: " + modelItemList.size());
                Gson gson = new GsonBuilder().create();
                UserCartResponse myOrderResponse = gson.fromJson(gson.toJson(jsonObject), UserCartResponse.class);
                String grandTotal = myOrderResponse.getGrandTotal().toString();
                PreferenceHandler.setGrandTotal(getContext(), grandTotal);
                textTotal.setText(grandTotal);
                CartCount.setMyBoolean(myOrderResponse.getDetails().size());
                isItemDeleted = false;
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
//                if (isAdded())
//                    Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
                for (ProductDetail orderDetail : myOrderResponse.getDetails()) {
                    if (orderDetail.getId() == id) {
//                        String nullCheckImage = "";
//                        if (orderDetail.getProductImage().size() > 0) {
//                            nullCheckImage = orderDetail.getProductImage().get(0);
//                        }
//                        newItem = new ModelItem(orderDetail.getId(),
//                                orderDetail.getName(), String.valueOf(orderDetail.getTotal_price()),
//                                String.valueOf(orderDetail.getProductPrice()),
//                                nullCheckImage, orderDetail.getProduct_quantity().toString(),
//                                true, orderDetail.getProductDiscount(), Integer.valueOf(orderDetail.getProduct_quantity()),orderDetail.getUnit());
//
                        modelItemList.set(pos, orderDetail);
                        Log.d(TAG, "onResponseReceived: " + modelItemList.get(pos).getQuantity());
                        adapterCart.notifyItemChanged(pos);
                    } else {
                    }
                }
                String grandTotal = myOrderResponse.getGrandTotal().toString();
                Log.d(TAG, "onResponseReceived: grandtotal" + grandTotal);
                Log.d(TAG, "dataPrepare: grandtotal" + grandTotal);
                PreferenceHandler.setGrandTotal(getContext(), grandTotal);
                textTotal.setText(grandTotal);
                if (grandTotal.equals("0")) {
                    changeFragment(new FragmentCartEmpty());
                }
            }
        }
    }

    private void changeFragment(Fragment fragment) {
        ((MainActivity) getActivity()).changeFragment(5, FragmentCart.TAG, null,new FragmentCart());
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}