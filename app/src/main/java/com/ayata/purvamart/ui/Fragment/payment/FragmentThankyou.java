package com.ayata.purvamart.ui.Fragment.payment;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;

import androidx.fragment.app.Fragment;


public class FragmentThankyou extends Fragment {
    public static String TAG = "FragmentThankyou";
//    ImageView tick;
//
//    AnimatedVectorDrawableCompat animatedVectorDrawableCompat;
//    AnimatedVectorDrawable animatedVectorDrawable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        View view = inflater.inflate(R.layout.fragment_thankyou, container, false);

        ((MainActivity) getActivity()).hideToolbar();
        ((MainActivity) getActivity()).showBottomNavBar(false);
        Button btn_menu = view.findViewById(R.id.button_menu);
        Button btn_orderlist = view.findViewById(R.id.button_orderlist);

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).selectCartFragment();
            }
        });

        btn_orderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).selectMyOrderFragment();
            }
        });
        return view;
    }

}