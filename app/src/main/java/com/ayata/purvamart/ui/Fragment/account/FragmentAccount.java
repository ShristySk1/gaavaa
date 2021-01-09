package com.ayata.purvamart.ui.Fragment.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.ui.Adapter.AdapterAccount;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.data.Model.ModelAccount;
import com.ayata.purvamart.R;
import com.ayata.purvamart.ui.login.SignupActivity;
import com.ayata.purvamart.data.preference.PreferenceHandler;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 *         fragmentList.add(new FragmentShop());//0
 *         fragmentList.add(new FragmentCart());//1
 *         fragmentList.add(new FragmentMyOrder());//2
 *         fragmentList.add(new FragmentListOrder());//3
 *         fragmentList.add(new FragmentEmptyOrder());//4
 *         fragmentList.add(new FragmentCart());//5
 *         fragmentList.add(new FragmentCartEmpty());//6
 *         fragmentList.add(new FragmentCartFilled());//7
 *         fragmentList.add(new FragmentProduct());//8
 *         fragmentList.add(new FragmentCategory());//9
 *         fragmentList.add(new FragmentTrackOrder());//10
 *         fragmentList.add(new FragmentAccount());//11
 *         fragmentList.add(new FragmentEditAddress());//12
 *         fragmentList.add(new FragmentEditProfile());//13
 *         fragmentList.add(new FragmentPrivacyPolicy());//14
 */
public class FragmentAccount extends Fragment implements View.OnClickListener, AdapterAccount.OnLayoutClickListener {
    public static String TAG="FragmentAccount";
    private View view;
    private RecyclerView recyclerView;
    private AdapterAccount adapterAccount;
    private List<ModelAccount> listitem;
    private LinearLayoutManager linearLayoutManager;

    private Button btn_logout;
    private ImageView imageBtn_edit;
    private TextView acc_email,acc_name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType3("Account");
        //bottom nav bar
        ((MainActivity) getActivity()).showBottomNavBar(true);
        acc_email = view.findViewById(R.id.acc_email);
        acc_name=view.findViewById(R.id.acc_name);
        imageBtn_edit = view.findViewById(R.id.acc_btn_edit);
        imageBtn_edit.setOnClickListener(this);
        btn_logout = view.findViewById(R.id.acc_btn_logout);
        btn_logout.setOnClickListener(this);

        initView();

        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.acc_recycler);
        acc_email.setText(PreferenceHandler.getEmail(getContext()));
        acc_name.setText(PreferenceHandler.getUsername(getContext()));

        listitem = new ArrayList<>();
        prepareData();
        adapterAccount = new AdapterAccount(getContext(), listitem, this);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(adapterAccount);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void prepareData() {

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

        switch (view.getId()) {
            case R.id.acc_btn_edit:
                //Intent
                if (!PreferenceHandler.isUserAlreadyLoggedIn(getContext())) {
                    Toast.makeText(getContext(), "Please Login to continue", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getContext(), SignupActivity.class));
                    return;
                }
                ((MainActivity) getActivity()).changeFragment(13, FragmentEditProfile.TAG,null);
                break;

            case R.id.acc_btn_logout:
                Toast.makeText(getContext(), "User logged out", Toast.LENGTH_SHORT).show();
                PreferenceHandler.logout(getContext());
                break;
        }

    }

    @Override
    public void onLayoutClick(int position) {

        switch (position) {
            case 0:
                //profile setting
                if (!PreferenceHandler.isUserAlreadyLoggedIn(getContext())) {
                    Toast.makeText(getContext(), "Please Login to continue", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getContext(), SignupActivity.class));
                    return;
                }
                ((MainActivity) getActivity()).changeFragment(13,FragmentEditProfile.TAG,null);
                break;

            case 1:
                //promos
                Toast.makeText(getContext(), listitem.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                break;

            case 2:
                //My Delivery Address
                //check token
                if (!PreferenceHandler.isUserAlreadyLoggedIn(getContext())) {
                    Toast.makeText(getContext(), "Please Login to continue", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getContext(), SignupActivity.class));
                    return;
                }
                ((MainActivity) getActivity()).changeFragment(18, FragmentDeliveryAddress.TAG,null);
                break;

            case 3:
                //Terms, Privacy & Policy
                ((MainActivity) getActivity()).changeFragment(14, FragmentPrivacyPolicy.TAG,null);
                break;

            case 4:
                //Help & Support
                Toast.makeText(getContext(), listitem.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }

    }
}