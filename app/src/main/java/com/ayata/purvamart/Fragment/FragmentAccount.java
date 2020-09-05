package com.ayata.purvamart.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ayata.purvamart.Adapter.AdapterAccount;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelAccount;
import com.ayata.purvamart.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentAccount extends Fragment implements View.OnClickListener, AdapterAccount.OnLayoutClickListener {

    private View view;
    private RecyclerView recyclerView;
    private AdapterAccount adapterAccount;
    private List<ModelAccount> listitem;
    private LinearLayoutManager linearLayoutManager;

    private Button btn_edit, btn_logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_account, container, false);
        //toolbar
        ((MainActivity)getActivity()).showToolbar();
        ((MainActivity)getActivity()).setToolbarType3("Account");
        //bottom nav bar
        ((MainActivity)getActivity()).showBottomNavBar(true);

        btn_edit= view.findViewById(R.id.acc_btn_edit);
        btn_edit.setOnClickListener(this);
        btn_logout= view.findViewById(R.id.acc_btn_logout);
        btn_logout.setOnClickListener(this);

        initView();

        return view;
    }

    private void initView(){
        recyclerView= view.findViewById(R.id.acc_recycler);

        listitem= new ArrayList<>();
        prepareData();
        adapterAccount= new AdapterAccount(getContext(),listitem,this);
        linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(adapterAccount);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void prepareData(){

        listitem.add(new ModelAccount(getResources().getString(R.string.acc_rv_text11),
                getResources().getString(R.string.acc_rv_text12)));
        listitem.add(new ModelAccount(getResources().getString(R.string.acc_rv_text21),
                getResources().getString(R.string.acc_rv_text22)));
        listitem.add(new ModelAccount(getResources().getString(R.string.acc_rv_text31),
                getResources().getString(R.string.acc_rv_text32)));
        listitem.add(new ModelAccount(getResources().getString(R.string.acc_rv_text41),
                getResources().getString(R.string.acc_rv_text42)));
        listitem.add(new ModelAccount(getResources().getString(R.string.acc_rv_text51),
                getResources().getString(R.string.acc_rv_text52)));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.acc_btn_edit:
                Toast.makeText(getContext(), "Edit Profile Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.acc_btn_logout:
                Toast.makeText(getContext(), "Logout Button Clicked", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onLayoutClick(int position) {

        switch (position){
            case 0:
                //profile setting
                Toast.makeText(getContext(), listitem.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                break;

            case 1:
                //promos
                Toast.makeText(getContext(), listitem.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                break;

            case 2:
                //My Delivery Address
                Toast.makeText(getContext(), listitem.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                break;

            case 3:
                //Terms, Privacy & Policy
                Toast.makeText(getContext(), listitem.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                break;

            case 4:
                //Help & Support
                Toast.makeText(getContext(), listitem.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }

    }
}