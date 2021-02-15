package com.ayata.purvamart.ui.Fragment.order;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Constants.Constants;
import com.ayata.purvamart.data.Model.ModelOrderList;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.generic.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.BaseResponse;
import com.ayata.purvamart.data.network.response.ConfirmCheckoutResponse;
import com.ayata.purvamart.data.network.response.OrderSummaryDetail;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.data.repository.Repository;
import com.ayata.purvamart.ui.Adapter.AdapterOrderSummary;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentOrderSummary extends Fragment {
    public static String TAG = "FragmentOrderSummary";

    private View view;
    private RecyclerView recyclerView;
    private AdapterOrderSummary adapterOrderSummary;
    private List<ProductDetail> listitem;
    private LinearLayoutManager layoutManager;
    private Button btn_placeOrder;
    private TextView pay_total;
    private TextView pay_orderPrice, text_address, text_payamentMethod;
    private ImageView image_pay_type;
    //get required data
    String orderId;
    String addressId;
    String paymentGateway;
    //error
    TextView text_error;
    ProgressBar progress_error;
    //view to make gone until api gets data
    NestedScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        view = inflater.inflate(R.layout.fragment_order_summary, container, false);
        initAppbar();
        scrollView = view.findViewById(R.id.scrollview);
        scrollView.setVisibility(View.GONE);
        inflateLayout(view);
        initView(view);
        initRecycler(view);
        return view;
    }

    private void initView(View view) {
        pay_orderPrice = view.findViewById(R.id.pay_orderprice);
        pay_total = view.findViewById(R.id.pay_total);
        text_address = view.findViewById(R.id.tvDeliveryAddressType);
        text_payamentMethod = view.findViewById(R.id.tvPaymentMethod);
        btn_placeOrder = view.findViewById(R.id.btn_placeOrder);
        image_pay_type = view.findViewById(R.id.ivPaymentImage);
        btn_placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Repository(new NetworkResponseListener<JsonObject>() {
                    @Override
                    public void onResponseReceived(JsonObject response) {
                        if (response.get("code").getAsString().equals("200")) {
                            Toast.makeText(getContext(), response.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            Gson gson = new GsonBuilder().create();
                            TypeToken<List<ConfirmCheckoutResponse>> responseTypeToken = new TypeToken<List<ConfirmCheckoutResponse>>() {
                            };
                            if (isAdded()) {
                                ((MainActivity) getActivity()).changeFragmentThankyou(16);
                            }
                        } else {
                            Toast.makeText(getContext(), response.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }, ApiClient.getApiService()).requestConfirmOrder(orderId, paymentGateway, addressId);

            }
        });
    }

    private void initAppbar() {
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2(getString(R.string.os_title), false, false);

        //bottom nav bar
        ((MainActivity) getActivity()).showBottomNavBar(false);

    }


    private void initRecycler(View view) {
        listitem = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view);
        adapterOrderSummary = new AdapterOrderSummary(getContext(), listitem);
        recyclerView.setAdapter(adapterOrderSummary);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        dataPrepare();
    }


    //set bundled data to list
    private void dataPrepare() {
        orderId = PreferenceHandler.getOrderId(getContext());
        addressId = PreferenceHandler.getAddressId(getContext());
        paymentGateway = PreferenceHandler.getPaymentGateway(getContext());
        Log.d(TAG, "dataPrepare: orderid:" + orderId);
        Log.d(TAG, "dataPrepare: addresssid:" + addressId);
        Log.d(TAG, "dataPrepare: paymentgateway:" + paymentGateway);

        Bundle bundle = getArguments();
        if (bundle != null) {
            //requirements
            scrollView.setVisibility(View.VISIBLE);
            btn_placeOrder.setVisibility(View.GONE);

            ModelOrderList modelOrderList = (ModelOrderList) bundle.getSerializable(FragmentMyOrder.ORDER_ITEM_FOR_SUMMARY);
            pay_orderPrice.setText(modelOrderList.getGrand_total());
            pay_total.setText(modelOrderList.getGrand_total());
//            text_address.setText(modelOrderList.ge());
            listitem.addAll(modelOrderList.getProductDetails());
            setPaymentMethod("CASHONDELIVERY");
            Log.d(TAG, "dataPrepare: " + modelOrderList.getPayment_type());
            adapterOrderSummary.notifyDataSetChanged();
        } else {
            new Repository(new NetworkResponseListener<BaseResponse<List<OrderSummaryDetail>>>() {

                @Override
                public void onResponseReceived(BaseResponse<List<OrderSummaryDetail>> response) {
                    progress_error.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    if (response.getCode().toString().equals("200")) {
                        OrderSummaryDetail orderSummaryDetail = (OrderSummaryDetail) response.getDetails().get(0);
                        pay_orderPrice.setText(orderSummaryDetail.getGrandTotal());
                        pay_total.setText(orderSummaryDetail.getGrandTotal());
                        text_address.setText(orderSummaryDetail.getAddress().getFullAddress());
                        listitem.addAll(orderSummaryDetail.getProduct());
                        setPaymentMethod(orderSummaryDetail.getPaymentMethod());
                    }
                    adapterOrderSummary.notifyDataSetChanged();
                }

                @Override
                public void onLoading() {
                    progress_error.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError(String message) {
                    progress_error.setVisibility(View.GONE);
                    text_error.setText(message);
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                }
            }, ApiClient.getApiService()).requestMyOrderSummary(orderId, addressId, paymentGateway);
        }

    }

    private void setPaymentMethod(String paymentMethod) {
        switch (paymentMethod) {
            case "CASHONDELIVERY":
                text_payamentMethod.setText("Cash On Delivery");
                Glide.with(getContext()).load(R.drawable.cash_on_delivery).placeholder(R.drawable.placeholder).fallback(Constants.FALLBACKIMAGE).into(image_pay_type);
                break;
            case "ESEWA":
                text_payamentMethod.setText("Esewa");
                Glide.with(getContext()).load(R.drawable.esewa).placeholder(R.drawable.placeholder).fallback(Constants.FALLBACKIMAGE).into(image_pay_type);
                break;
            case "KHALTI":
                text_payamentMethod.setText("Khalti");
                Glide.with(getContext()).load(R.drawable.khalti).placeholder(R.drawable.placeholder).fallback(Constants.FALLBACKIMAGE).into(image_pay_type);
                break;
        }
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
}