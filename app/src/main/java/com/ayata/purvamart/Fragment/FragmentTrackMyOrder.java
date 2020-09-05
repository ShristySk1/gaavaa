package com.ayata.purvamart.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;


public class FragmentTrackMyOrder extends Fragment {

    private View view;

    private TextView title;
    private Button btn_track;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_track_my_order, container, false);

        //toolbar hide
        ((MainActivity)getActivity()).hideToolbar();

        //bottomnav bar hide
        ((MainActivity)getActivity()).showBottomNavBar(false);

        title= view.findViewById(R.id.tmo_title);

        title.setText("Username"+", your order has been successful ");

        btn_track= view.findViewById(R.id.tmo_btn);
        btn_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Track My Order Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}