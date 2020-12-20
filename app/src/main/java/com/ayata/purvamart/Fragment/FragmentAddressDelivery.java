package com.ayata.purvamart.Fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;


public class FragmentAddressDelivery extends Fragment implements View.OnClickListener{

        ImageView address2_check,address1_check;

        private View view;
        private CardView select_from_map;
        private CardView address1_layout, address2_layout;
        private Button btn_payment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_address_delivery, container, false);

        //toolbar
        ((MainActivity)getActivity()).showToolbar();
        ((MainActivity)getActivity()).setToolbarType2("Address Delivery",false,false);

        //bottom nav bar
        ((MainActivity)getActivity()).showBottomNavBar(false);



        select_from_map=view.findViewById(R.id.select_from_map);
        select_from_map.setOnClickListener(this);

        address1_layout= view.findViewById(R.id.address1_layout);
        address2_layout= view.findViewById(R.id.address2_layout);
        address2_check=view.findViewById(R.id.address2_check);
        address1_check= view.findViewById(R.id.address1_check);

        address1_layout.setOnClickListener(this);
        address2_layout.setOnClickListener(this);

        btn_payment=view.findViewById(R.id.btn_pay);
        btn_payment.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
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
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                        .replace(R.id.main_fragment, new FragmentOrderSummary())
                        .addToBackStack(null).commit();
                break;
        }
    }

    private void selectAddress1(){
        address1_check.setVisibility(View.VISIBLE);
        address2_check.setVisibility(View.GONE);
    }

    private void selectAddress2(){
        address1_check.setVisibility(View.GONE);
        address2_check.setVisibility(View.VISIBLE);

    }
}