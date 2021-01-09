package com.ayata.purvamart.ui.Fragment.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ayata.purvamart.ui.Fragment.unused.FragmentOrderSummary;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.data.Model.ModelItem;
import com.ayata.purvamart.R;

import java.io.Serializable;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


public class FragmentAddressDelivery extends Fragment implements View.OnClickListener {

    public static final String FRAGMENT_ADDRESS_DELIVERY = "FRAGMENT_ADDRESS_DELIVERY";
    private String TAG = "FragmentAddressDelivery";

    ImageView address2_check, address1_check;

    private View view;
    private CardView select_from_map;
    private CardView address1_layout, address2_layout;
    private Button btn_payment;
    //list of cart data
    List<ModelItem> modelItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        view = inflater.inflate(R.layout.fragment_address_delivery, container, false);

        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2("Address Delivery", false, false);

        //bottom nav bar
        ((MainActivity) getActivity()).showBottomNavBar(false);


        select_from_map = view.findViewById(R.id.select_from_map);
        select_from_map.setOnClickListener(this);

        address1_layout = view.findViewById(R.id.address1_layout);
        address2_layout = view.findViewById(R.id.address2_layout);
        address2_check = view.findViewById(R.id.address2_check);
        address1_check = view.findViewById(R.id.address1_check);

        address1_layout.setOnClickListener(this);
        address2_layout.setOnClickListener(this);

        btn_payment = view.findViewById(R.id.btn_pay);
        btn_payment.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.select_from_map:
                Toast.makeText(getContext(), "Select from map Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.address1_layout:
                selectAddress1();
                break;

            case R.id.address2_layout:
                selectAddress2();
                break;

            case R.id.btn_pay:
                //process summary of order and go to next fragment
                getMyOrderList();
                break;
        }
    }

    private void getMyOrderList() {
//        modelItems = new ArrayList<>();
//        ApiService myOrderApi = ApiClient.getClient().create(ApiService.class);
//        myOrderApi.getMyOrder(PreferenceHandler.getToken(getContext())).enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response.isSuccessful()) {
//                    JsonObject jsonObject = response.body();
//                    if (jsonObject.get("code").toString().equals("200")) {
//                        if (jsonObject.get("message").getAsString().equals("empty cart")) {
//                            Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
//                        } else {
//                            Gson gson = new GsonBuilder().create();
//                            UserCartResponse myOrderResponse = gson.fromJson(gson.toJson(jsonObject), UserCartResponse.class);
//                            Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
//                            for (UserCartDetail orderDetail : myOrderResponse.getDetails()) {
//                                if (orderDetail.getIsTaken()) {
//                                    modelItems.add(new ModelItem(orderDetail.getProductId(), orderDetail.getProductName(), orderDetail.getPrice().toString(), String.valueOf(orderDetail.getPrice() * orderDetail.getProductQuantity()),
//                                            orderDetail.getImage().get(0), orderDetail.getProductQuantity().toString(), true, orderDetail.getProductDiscount(), 1));
//                                }
//                            }
//                            //navigate to next fragment
                            Bundle bundle = new Bundle();
//                            Log.d(TAG, "onResponse: " + modelItems.size());
                            bundle.putSerializable(FRAGMENT_ADDRESS_DELIVERY, (Serializable) modelItems);
                            FragmentOrderSummary fragmentOrderSummary = new FragmentOrderSummary();
                            fragmentOrderSummary.setArguments(bundle);
                            getFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                                    .replace(R.id.main_fragment, fragmentOrderSummary)
                                    .addToBackStack(null).commit();
//                        }
//
//                    } else {
////                        Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), "" + "Please login to continue", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//
//            }
//        });
    }

    private void selectAddress1() {
        address1_check.setVisibility(View.VISIBLE);
        address2_check.setVisibility(View.GONE);
    }

    private void selectAddress2() {
        address1_check.setVisibility(View.GONE);
        address2_check.setVisibility(View.VISIBLE);

    }
}