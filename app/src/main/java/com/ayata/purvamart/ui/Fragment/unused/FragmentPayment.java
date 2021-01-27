package com.ayata.purvamart.ui.Fragment.unused;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ayata.purvamart.ui.Fragment.account.address.FragmentDeliveryAddress;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.esewa.android.sdk.payment.ESewaConfiguration;
import com.esewa.android.sdk.payment.ESewaPayment;
import com.esewa.android.sdk.payment.ESewaPaymentActivity;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.utils.Constant;

import java.util.HashMap;
import java.util.Map;


public class FragmentPayment extends Fragment implements View.OnClickListener{
    public static String TAG = "FragmentPayment";
    public static final int REQUEST_CODE_PAYMENT=3;

    ImageView khalti_check,esewa_check;

    private View view;
    private CardView add_payment;
    private CardView pay_esewa, pay_khalti;
    private Button btn_payment;

    ESewaConfiguration eSewaConfiguration;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        view= inflater.inflate(R.layout.fragment_payment, container, false);

        //toolbar
        ((MainActivity)getActivity()).showToolbar();
        ((MainActivity)getActivity()).setToolbarType2("Payment",false,false);

        //bottom nav bar
        ((MainActivity)getActivity()).showBottomNavBar(false);

        //esewa
        String client_id="JB0BBQ4aD0UqIThFJwAKBgAXEUkEGQUBBAwdOgABHD4DChwUAB0R";
        String secret_key="BhwIWQQADhIYSxILExMcAgFXFhcOBwAKBgAXEQ==";
        eSewaConfiguration = new ESewaConfiguration()
                .clientId(client_id)
                .secretKey(secret_key)
                .environment(ESewaConfiguration.ENVIRONMENT_TEST);

        initView();

        return view;
    }

    private void initView(){
        add_payment=view.findViewById(R.id.add_payment_method);
        add_payment.setOnClickListener(this);

        pay_esewa= view.findViewById(R.id.esewa);
        pay_khalti= view.findViewById(R.id.khalti);
        khalti_check=view.findViewById(R.id.khalti_check);
        esewa_check= view.findViewById(R.id.esewa_check);

        pay_khalti.setOnClickListener(this);
        pay_esewa.setOnClickListener(this);

        btn_payment=view.findViewById(R.id.btn_pay);
        btn_payment.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.add_payment_method:
                Toast.makeText(getContext(), "Add Payment Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.esewa:
                selectEsewa();
                break;

            case R.id.khalti:
                selectKhalti();
                break;

            case R.id.btn_pay:
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                        .replace(R.id.main_fragment, new FragmentDeliveryAddress())
                        .addToBackStack(null).commit();
                break;
        }
    }

    private void selectEsewa(){
        esewa_check.setVisibility(View.VISIBLE);
        khalti_check.setVisibility(View.GONE);
        setForEsewa();
    }

    private void selectKhalti(){
        esewa_check.setVisibility(View.GONE);
        khalti_check.setVisibility(View.VISIBLE);
        setForKhalti();

    }

    private void setForKhalti(){

        String productId="Product ID";
        String product_name="Main";
        long amount=100L;
        String khalti_merchant_id="This is extra data";
        String khalti_merchant_extra="merchant_extra";

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

    private void setForEsewa(){
//        https://somecallbackurl.com/payment/confirmation
            String  product_price="";
            String productId="";
            String product_name="";
            String call_back_url="";
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