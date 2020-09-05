package com.ayata.purvamart.Fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;


public class FragmentPayment extends Fragment implements View.OnClickListener{


    private View view;
    private CardView add_payment;
    private LinearLayout pay_esewa, pay_khalti;
    private Button btn_payment;

    private TextView price_text, total_text, delivery_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_payment, container, false);

        //toolbar
        ((MainActivity)getActivity()).showToolbar();
        ((MainActivity)getActivity()).setToolbarType2("Payment",false);

        //bottom nav bar
        ((MainActivity)getActivity()).showBottomNavBar(false);

        setPrice("200","Free","200");

        add_payment=view.findViewById(R.id.pay_add_payment);
        add_payment.setOnClickListener(this);

        pay_esewa= view.findViewById(R.id.pay_esewa_background);
        pay_khalti= view.findViewById(R.id.pay_khalti_background);

        pay_khalti.setOnClickListener(this);
        pay_esewa.setOnClickListener(this);

        btn_payment=view.findViewById(R.id.pay_btn);
        btn_payment.setOnClickListener(this);



        return view;
    }

    private void setPrice(String price, String delivery, String total){
        price_text= view.findViewById(R.id.pay_orderprice);
        total_text= view.findViewById(R.id.pay_total);
        delivery_text= view.findViewById(R.id.pay_delivery);

        price_text.setText("Rs. "+price);
        total_text.setText("Rs. "+total);
        delivery_text.setText(delivery);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.pay_add_payment:
                Toast.makeText(getContext(), "Add Payment Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.pay_esewa_background:
                selectEsewa();
                break;

            case R.id.pay_khalti_background:
                selectKhalti();
                break;

            case R.id.pay_btn:
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                        .replace(R.id.main_fragment, new FragmentTrackMyOrder())
                        .addToBackStack(null).commit();
                break;
        }
    }

    private void selectEsewa(){
        pay_esewa.setBackground(getResources().getDrawable(R.drawable.outline_green));
        pay_khalti.setBackground(getResources().getDrawable(R.drawable.outline_gray));
    }

    private void selectKhalti(){
        pay_esewa.setBackground(getResources().getDrawable(R.drawable.outline_gray));
        pay_khalti.setBackground(getResources().getDrawable(R.drawable.outline_green));
    }
}