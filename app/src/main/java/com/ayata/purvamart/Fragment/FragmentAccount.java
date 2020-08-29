package com.ayata.purvamart.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;


public class FragmentAccount extends Fragment {

    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_account, container, false);
        //toolbar
        ((MainActivity)getActivity()).setToolbarType3("Account");

        return view;
    }
}