package com.ayata.purvamart.ui.Fragment.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayata.purvamart.CartCount;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.generic.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.data.network.response.UserCartResponse;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.data.repository.Repository;
import com.ayata.purvamart.ui.login.SignupActivity;
import com.ayata.purvamart.utils.MyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import retrofit2.Call;

public class FragmentCart extends Fragment implements NetworkResponseListener<JsonObject> {
    public static String TAG = "FragmentCart";
    public static final String FRAGMENT_CART = "FRAGMENT_CART";
    public static final String FRAGMENT_CART_TOTAL = "FRAGMENT_CART_TOTAL";
    //list and int to bundle
    List<ProductDetail> modelItemList;
    long totalPrice = 0;


    private View view;
    Bundle bundle = new Bundle();
    //call
    Call<JsonObject> mCall;
    //error
    MyError myError;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);
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
        if (!PreferenceHandler.isUserAlreadyLoggedIn(getContext())) {
            Toast.makeText(getContext(), "Please Login to continue", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getContext(), SignupActivity.class));
            return;
        }
        new Repository(this, cartApi).requestCart();
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
        getFragmentManager().beginTransaction().replace(R.id.fragment_cart, fragment).commit();
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
        myError.hideProgress();
        if (jsonObject.get("code").toString().equals("200")) {
            Gson gson = new GsonBuilder().create();
            String empty = jsonObject.get("message").getAsString();
            Log.d(TAG, "onResponse: " + empty + "crt");
            if (jsonObject.get("details").getAsJsonArray().size() == 0) {
            } else {
                UserCartResponse myOrderResponse = gson.fromJson(gson.toJson(jsonObject), UserCartResponse.class);
                Log.d(TAG, "onResponseReceived1: " + myOrderResponse.getDetails().size());
                CartCount.setMyBoolean(myOrderResponse.getDetails().size());
                totalPrice = myOrderResponse.getGrandTotal();
                modelItemList.addAll(myOrderResponse.getDetails());
            }
        }
        if (isAdded())
            checkForData();
    }

    @Override
    public void onLoading() {
        myError = new MyError();
        myError.inflateLayout(view, new WeakReference<Context>(getContext()));
        myError.showProgress();
    }

    @Override
    public void onError(String message) {
        if (isAdded())
            myError.showError(message);
    }
}