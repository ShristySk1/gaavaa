package com.ayata.purvamart.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelItem;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.helper.NetworkResponse;
import com.ayata.purvamart.data.network.helper.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.UserCartDetail;
import com.ayata.purvamart.data.network.response.UserCartResponse;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import retrofit2.Call;

public class FragmentCart extends Fragment implements NetworkResponseListener<JsonObject> {
    public static String TAG = "FragmentCart";
    public static final String FRAGMENT_CART = "FRAGMENT_CART";
    public static final String FRAGMENT_CART_TOTAL = "FRAGMENT_CART_TOTAL";
    //list and int to bundle
    List<ModelItem> modelItemList;
    long totalPrice = 0;


    private View view;
    Bundle bundle = new Bundle();
    //call
    Call<JsonObject> mCall;
    //error
    TextView text_error;
    ProgressBar progress_error;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        inflateLayout();
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType3("Cart");
        //bottom nav
        ((MainActivity) getActivity()).showBottomNavBar(true);
        dataPrepare();
        return view;
    }

    private void dataPrepare() {
        modelItemList = new ArrayList<>();
        ApiService cartApi = ApiClient.getClient().create(ApiService.class);
        requestCart(this, cartApi);
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                if (call.isCanceled()) {
//                    //do nothing
//                }else {
//                    //show some thing to user ui
//                    AlertDialogHelper.dismiss(getContext());
//                    Toast.makeText(getContext(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
    }

    public void requestCart(NetworkResponseListener<JsonObject> listener, ApiService api) {
        api.getMyOrder(PreferenceHandler.getToken(getContext())).enqueue(new NetworkResponse<>(listener));
    }

    private void checkForData() {
        if (modelItemList != null && modelItemList.size() != 0) {
            //filled layout
            bundle.putSerializable(FRAGMENT_CART, (Serializable) modelItemList);
            bundle.putLong(FRAGMENT_CART_TOTAL, totalPrice);
            FragmentCartFilled fragmentCartFilled = new FragmentCartFilled();
            fragmentCartFilled.setArguments(bundle);
            changeFragment(fragmentCartFilled, FragmentCartFilled.TAG);
            Log.d(TAG, "checkForData: " + modelItemList.size());

        } else {
            //empty layout
            changeFragment(new FragmentCartEmpty(), FragmentCartEmpty.TAG);
        }
    }

    private void changeFragment(Fragment fragment, String tag) {
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_cart, fragment).commit();
    }

    @Override
    public void onDestroyView() {
        if (mCall != null && mCall.isExecuted()) {
            mCall.cancel();
        }
        super.onDestroyView();
    }

    //inflate pullRefreshLayout for error and progressbar
    void inflateLayout() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        //Avoid pass null in the root it ignores spaces in the child pullRefreshLayout
        View inflatedLayout = inflater.inflate(R.layout.error_layout, (ViewGroup) view, false);
        ViewGroup viewGroup = view.findViewById(R.id.root_main);
        viewGroup.addView(inflatedLayout);
        text_error = view.findViewById(R.id.text_error);
        progress_error = view.findViewById(R.id.progress_error);
    }


    @Override
    public void onResponseReceived(JsonObject jsonObject) {
        progress_error.setVisibility(View.GONE);
        if (jsonObject.get("code").toString().equals("200")) {
            Gson gson = new GsonBuilder().create();
            String empty = jsonObject.get("message").getAsString();
            Log.d(TAG, "onResponse: " + empty + "crt");
            if (empty.equals("empty cart")) {
                if (isAdded())
                    Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
            } else {
                UserCartResponse myOrderResponse = gson.fromJson(gson.toJson(jsonObject), UserCartResponse.class);
                if (isAdded())
                    Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponseReceived1: " + myOrderResponse.getDetails().size());
                totalPrice = myOrderResponse.getGrandTotal();
                for (UserCartDetail orderDetail : myOrderResponse.getDetails()) {
                    String nullCheckImage = "";
                    if (orderDetail.getProductImage().size() > 0) {
                        nullCheckImage = orderDetail.getProductImage().get(0);
                    }
                    modelItemList.add(new ModelItem(orderDetail.getId(),
                            orderDetail.getName(), orderDetail.getProductPrice().toString(),
                            String.valueOf(orderDetail.getProductPrice() * orderDetail.getProductQuantity()),
                            nullCheckImage, orderDetail.getProductQuantity().toString(),
                            true, orderDetail.getProductDiscount(), orderDetail.getProductQuantity()));

                }
                if (isAdded())
                    Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
            }
        }
        if (isAdded())
            checkForData();
    }

    @Override
    public void onLoading() {
        progress_error.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message) {
        progress_error.setVisibility(View.GONE);
        text_error.setText(message);
    }
}