package com.ayata.purvamart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.esewa.android.sdk.payment.ESewaConfiguration;
import com.esewa.android.sdk.payment.ESewaPayment;
import com.esewa.android.sdk.payment.ESewaPaymentActivity;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.checkout.helper.PaymentPreference;
import com.khalti.utils.Constant;
import com.khalti.widget.KhaltiButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragmentTest extends Fragment {

    public static final int REQUEST_CODE_PAYMENT=3;
    public static final String TAG="errors";

    ESewaConfiguration eSewaConfiguration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_test, container, false);

        KhaltiButton khaltiButton = (KhaltiButton) view.findViewById(R.id.khalti_button);

        Map<String, Object> map = new HashMap<>();
        map.put("merchant_extra", "This is extra data");

        Config.Builder builder = new Config.Builder(Constant.pub, "Product ID", "Main", 1100L, new OnCheckOutListener() {
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
        khaltiButton.setCheckOutConfig(config);



        //esewa

        String client_id="JB0BBQ4aD0UqIThFJwAKBgAXEUkEGQUBBAwdOgABHD4DChwUAB0R";
        String secret_key="BhwIWQQADhIYSxILExMcAgFXFhcOBwAKBgAXEQ==";
        eSewaConfiguration = new ESewaConfiguration()
                .clientId(client_id)
                .secretKey(secret_key)
                .environment(ESewaConfiguration.ENVIRONMENT_TEST);

        Button esewa_btn= view.findViewById(R.id.btn_esewa);
        esewa_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEsewa();
            }
        });
        return view;
    }

    private void clickEsewa(){

        String  product_price="120";
        String productId="01";
        String product_name="abc";
        String call_back_url="https://somecallbackurl.com/payment/confirmation";
        ESewaPayment eSewaPayment = new ESewaPayment(product_price,
        product_name, productId,call_back_url);

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