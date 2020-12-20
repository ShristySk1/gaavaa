package com.ayata.purvamart.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.utils.Constant;

import java.util.HashMap;
import java.util.Map;


public class FragmentPayment extends Fragment implements View.OnClickListener{

    ImageView khalti_check,esewa_check;

    private View view;
    private CardView add_payment;
    private CardView pay_esewa, pay_khalti;
    private Button btn_payment;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_payment, container, false);

        //toolbar
        ((MainActivity)getActivity()).showToolbar();
        ((MainActivity)getActivity()).setToolbarType2("Payment",false,false);

        //bottom nav bar
        ((MainActivity)getActivity()).showBottomNavBar(false);



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



        return view;
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
                        .replace(R.id.main_fragment, new FragmentAddressDelivery())
                        .addToBackStack(null).commit();
                break;
        }
    }

    private void selectEsewa(){
        esewa_check.setVisibility(View.VISIBLE);
        khalti_check.setVisibility(View.GONE);
    }

    private void selectKhalti(){
        esewa_check.setVisibility(View.GONE);
        khalti_check.setVisibility(View.VISIBLE);
        setForKhalti();

    }

    private void setForKhalti(){
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
        KhaltiCheckOut khaltiCheckOut = new KhaltiCheckOut(getContext(), config);
        khaltiCheckOut.show();
    }
}