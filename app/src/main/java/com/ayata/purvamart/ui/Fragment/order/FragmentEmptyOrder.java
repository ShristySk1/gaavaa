package com.ayata.purvamart.ui.Fragment.order;

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


public class FragmentEmptyOrder extends Fragment {
    public String TAG = "FragmentEmptyOrder";
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        View view= inflater.inflate(R.layout.fragment_empty_order, container, false);

        //toolbar
        ((MainActivity)getActivity()).showToolbar();
        ((MainActivity)getActivity()).setToolbarType3("My Order");
        //bottom nav bar
        ((MainActivity)getActivity()).showBottomNavBar(true);

        TextView title= view.findViewById(R.id.text);
        Bundle bundle= getArguments();
        if(bundle!=null){
            title.setText(bundle.getString(FragmentMyOrder.empty_title));
        }

        button= view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Button Clicked", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).selectShopFragment();
            }
        });

        return view;
    }
}