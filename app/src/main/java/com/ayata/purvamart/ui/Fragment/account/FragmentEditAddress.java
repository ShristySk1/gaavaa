package com.ayata.purvamart.ui.Fragment.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelAddress;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.helper.NetworkResponseListener;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.data.repository.Repository;
import com.ayata.purvamart.ui.login.SignupActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import androidx.fragment.app.Fragment;

public class FragmentEditAddress extends Fragment implements NetworkResponseListener<JsonObject> {
    public static final String TAG2 = "FragmentEditAddressForAdd";
    public static String TAG = "FragmentEditAddressForEdit";


    Button save;
    ModelAddress modelAddress;
    String toolbarTitle;
    //inputs
    TextInputLayout tilCountry, tilCity, tilPersonName, tilPersonPhoneNo, tilPostalCode, tilStreetAddress;
    //Repository
    Repository repository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_address, container, false);
        initView(view);
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        getBundleArguments();
        repository = new Repository(this, ApiClient.getApiService());
//        //bottom nav bar
//        ((MainActivity)getActivity()).showBottomNavBar(false);
        save.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                //get data from view and update server
                getDataFromView();
            }
        });
        return view;
    }

    private void getDataFromView() {
        String name = tilPersonName.getEditText().getText().toString();
        String phone = tilPersonPhoneNo.getEditText().getText().toString();
        String streetAddress = tilStreetAddress.getEditText().getText().toString();
        String postal = tilPostalCode.getEditText().getText().toString();
        String city = tilCity.getEditText().getText().toString();
        String country = tilCountry.getEditText().getText().toString();
        if (TextUtils.isEmpty(name) | TextUtils.isEmpty(phone) | TextUtils.isEmpty(streetAddress) |
                TextUtils.isEmpty(postal) | TextUtils.isEmpty(city) | TextUtils.isEmpty(country)) {
            Toast.makeText(getContext(), "Empty field", Toast.LENGTH_LONG).show();
        } else {
            //add address in server
            if (modelAddress != null) {
                //if edit address
                if (!PreferenceHandler.isUserAlreadyLoggedIn(getContext())) {
                    Toast.makeText(getContext(), "Please Login to continue", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getContext(), SignupActivity.class));
                    return;
                }
                ModelAddress modelAddress2 = new ModelAddress(modelAddress.getId(),phone, postal, city, name, streetAddress, country);
                repository.requestUpdateMyAddress(modelAddress2);
            } else {
                //if add address
                modelAddress = new ModelAddress(phone, postal, city, name, streetAddress, country);
                repository.requestAddAdress(modelAddress);
            }
            getFragmentManager().popBackStackImmediate();
        }
    }


    private void getBundleArguments() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            modelAddress = (ModelAddress) bundle.getSerializable(FragmentDeliveryAddress.FragmentDeliveryAddress);
            toolbarTitle = bundle.getString(FragmentDeliveryAddress.FragmentDeliveryAddressTitle);
            ((MainActivity) getActivity()).setToolbarType2(toolbarTitle, false, false);
            //set saved address to view
            setDataToView();
        } else {
            ((MainActivity) getActivity()).setToolbarType2("Add Address", false, false);
        }
    }

    private void setDataToView() {
        tilCountry.getEditText().setText(modelAddress.getCountry());
        tilCity.getEditText().setText(modelAddress.getCity());
        tilPostalCode.getEditText().setText(modelAddress.getPostalCode());
        tilStreetAddress.getEditText().setText(modelAddress.getStreetName());
        tilPersonName.getEditText().setText(modelAddress.getName());
        tilPersonPhoneNo.getEditText().setText(modelAddress.getContactNumber());
    }

    private void initView(View view) {
        tilCity = view.findViewById(R.id.tilCity);
        tilCountry = view.findViewById(R.id.tilCountry);
        tilPostalCode = view.findViewById(R.id.tilPostalCode);
        tilStreetAddress = view.findViewById(R.id.tilStreetName);
        tilPersonName = view.findViewById(R.id.tilPersonName);
        tilPersonPhoneNo = view.findViewById(R.id.tilPhoneNo);
        save = view.findViewById(R.id.btn_save);
    }

    @Override
    public void onResponseReceived(JsonObject response) {
        Toast.makeText(getContext(), response.get("message").getAsString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}