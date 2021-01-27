package com.ayata.purvamart.ui.Fragment.account.privacypolicy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;

import androidx.fragment.app.Fragment;

public class FragmentPrivacyPolicy extends Fragment {
    public static final String TAG = "FragmentPrivacyPolicy";
    Button btn_continue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        View view = inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2("Privacy Policy", false,false);
        initView(view);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStackImmediate();
            }
        });
        return view;
    }
    private void initView(View view) {
        btn_continue = view.findViewById(R.id.btn_continue);
    }
}