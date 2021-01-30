package com.ayata.purvamart.ui.Fragment.payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelPayment;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.generic.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.ConfirmCheckoutResponse;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.data.repository.Repository;
import com.ayata.purvamart.ui.Adapter.AdapterPayment;
import com.esewa.android.sdk.payment.ESewaConfiguration;
import com.esewa.android.sdk.payment.ESewaPayment;
import com.esewa.android.sdk.payment.ESewaPaymentActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.utils.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This fragment displays all payments required.
 */
public class FragmentPayment2 extends Fragment implements AdapterPayment.OnPayMethodClickListener, NetworkResponseListener<JsonObject> {
    RecyclerView recyclerView;
    List<ModelPayment> listitem;
    AdapterPayment adapterPayment;
    Button btn_pay;
    String paymentName = "CASHONDELIVERY";
    public static final int REQUEST_CODE_PAYMENT = 3;
    public static String TAG = "FragmentPayment2";

    //total
    TextView pay_total, pay_orderprice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment2, container, false);
        btn_pay = view.findViewById(R.id.btn_pay);
        pay_total = view.findViewById(R.id.pay_total);
        pay_orderprice = view.findViewById(R.id.pay_orderprice);
        pay_total.setText("Rs. " + PreferenceHandler.getGrandTotal(getContext()));
        pay_orderprice.setText("Rs. " + PreferenceHandler.getGrandTotal(getContext()));
//        pay_total.setText("Rs. " + FragmentCartFilled.GRANDTOTAL);
//        pay_orderprice.setText("Rs. " +  FragmentCartFilled.GRANDTOTAL);
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2("Payment", false, false);

        //bottom nav bar
        ((MainActivity) getActivity()).showBottomNavBar(false);
        initRecycler(view);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paymentName.equals("ESEWA") || paymentName.equals("KHALTI")) {
                    Toast.makeText(getContext(), "Not yet implemented", Toast.LENGTH_SHORT).show();
                    return;
                }
                //thankyou fragment
                String orderId = PreferenceHandler.getOrderId(getContext());
                String addressId = PreferenceHandler.getAddressId(getContext());
                new Repository(FragmentPayment2.this, ApiClient.getApiService()).requestConfirmOrder(orderId, paymentName, addressId);
            }
        });
        return view;
    }

    private void initRecycler(View view) {
        listitem = new ArrayList<>();
        prepareData();
        recyclerView = view.findViewById(R.id.recycler_payment);
        adapterPayment = new AdapterPayment(getContext(), listitem);
        AdapterPayment.setListener(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapterPayment);
        recyclerView.setLayoutManager(manager);
    }

    private void prepareData() {
        listitem.add(new ModelPayment("Cash On Delivery", R.drawable.cash_on_delivery));
        listitem.add(new ModelPayment("Esewa Mobile Wallet", R.drawable.esewa));
        listitem.add(new ModelPayment("Khalti Wallet", R.drawable.khalti));
    }

    @Override
    public void onPayMethodClick(int position, ModelPayment modelPayment) {
        switch (position) {
            case 0:
                paymentName = "CASHONDELIVERY";
                Toast.makeText(getContext(), "Available", Toast.LENGTH_SHORT).show();

                break;
            case 1:
                paymentName = "ESEWA";
                Toast.makeText(getContext(), "Not yet implemented", Toast.LENGTH_SHORT).show();
//                setForEsewa();
                break;
            case 2:
                paymentName = "KHALTI";
                Toast.makeText(getContext(), "Not yet implemented", Toast.LENGTH_SHORT).show();
//                setForKhalti();
                break;
            default:
                paymentName = "CASHONDELIVERY";
        }
//        Toast.makeText(getContext(), paymentName, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResponseReceived(JsonObject response) {
        if (response.get("code").getAsString().equals("200")) {
            Toast.makeText(getContext(), response.get("message").getAsString(), Toast.LENGTH_SHORT).show();
            Gson gson = new GsonBuilder().create();
            TypeToken<List<ConfirmCheckoutResponse>> responseTypeToken = new TypeToken<List<ConfirmCheckoutResponse>>() {
            };
//            List<ConfirmCheckoutResponse> details = gson.fromJson(gson.toJson(response.getAsJsonArray("details")), responseTypeToken.getType());
//            String orderId = details.get(0).getOrderId();
//            String merchantId = details.get(0).getMerchantId();
//            String merchantSecrete = details.get(0).getMerchantSecret();
//            String scd = details.get(0).getScd();
            //thankyou fragment
            if (isAdded())
                ((MainActivity) getActivity()).changeFragmentThankyou(16);
//                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
//                        .replace(R.id.main_fragment, new FragmentThankyou()).commit();
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


    private void setForKhalti() {
        String productId = "Product ID";
        String product_name = "Main";
        long amount = 100L;
        String khalti_merchant_id = "This is extra data";
        String khalti_merchant_extra = "merchant_extra";

        Map<String, Object> map = new HashMap<>();
        map.put(khalti_merchant_extra, khalti_merchant_id);

        Config.Builder builder = new Config.Builder(Constant.pub, productId, product_name, amount, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i(action, errorMap.toString());
            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());
            }
        });
//                .paymentPreferences(new ArrayList<PaymentPreference>() {{
//                    add(PaymentPreference.KHALTI);
//                    add(PaymentPreference.EBANKING);
//                    add(PaymentPreference.MOBILE_BANKING);
//                    add(PaymentPreference.CONNECT_IPS);
//                    add(PaymentPreference.SCT);
//                }})
//                .additionalData(map)
//                .productUrl("")
//                .mobile("9800000000");

        Config config = builder.build();
        KhaltiCheckOut khaltiCheckOut = new KhaltiCheckOut(getContext(), config);
        khaltiCheckOut.show();
    }

    private void setForEsewa() {
//        https://somecallbackurl.com/payment/confirmation
        String client_id = "JB0BBQ4aD0UqIThFJwAKBgAXEUkEGQUBBAwdOgABHD4DChwUAB0R";
        String secret_key = "BhwIWQQADhIYSxILExMcAgFXFhcOBwAKBgAXEQ==";
        ESewaConfiguration eSewaConfiguration = new ESewaConfiguration()
                .clientId(client_id)
                .secretKey(secret_key)
                .environment(ESewaConfiguration.ENVIRONMENT_TEST);
        String product_price = "";
        String productId = "";
        String product_name = "";
        String call_back_url = "";
        ESewaPayment eSewaPayment = new ESewaPayment(product_price,
                product_name, productId, call_back_url);

        Intent intent = new Intent(getContext(), ESewaPaymentActivity.class);
        intent.putExtra(ESewaConfiguration.ESEWA_CONFIGURATION, eSewaConfiguration);

        intent.putExtra(ESewaPayment.ESEWA_PAYMENT, eSewaPayment);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) return;
                String message = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                Log.i(TAG, "Proof of Payment " + message);
                Toast.makeText(getContext(), "SUCCESSFUL PAYMENT", Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getContext(), "Canceled By User", Toast.LENGTH_SHORT).show();
            } else if (resultCode == ESewaPayment.RESULT_EXTRAS_INVALID) {
                if (data == null) return;
                String message = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                Log.i(TAG, "Proof of Payment " + message);
            }
        }
    }

}