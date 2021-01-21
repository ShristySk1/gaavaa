package com.ayata.purvamart.ui.Fragment.account;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.akplacepicker.models.AddressData;
import com.app.akplacepicker.utilities.Constants;
import com.app.akplacepicker.utilities.PlacePicker;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelAddress;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.helper.NetworkResponseListener;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.data.repository.Repository;
import com.ayata.purvamart.ui.login.SignupActivity;
import com.ayata.purvamart.utils.GpsTracker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import androidx.annotation.Nullable;
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
    //map
    LinearLayout ll_select_from_map;

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
                ModelAddress modelAddress2 = new ModelAddress(modelAddress.getId(), phone, postal, city, name, streetAddress, country);
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
        ll_select_from_map = view.findViewById(R.id.ll_select_from_map);
        ll_select_from_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMapIntent();
            }
        });
    }

    @Override
    public void onResponseReceived(JsonObject response) {
        if(isAdded())
        Toast.makeText(getContext(), response.get("message").getAsString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void setMapIntent() {
        // check if GPS enabled
//        GpsTracker gpsTracker = new GpsTracker(getContext());
//        if (gpsTracker.getIsGPSTrackingEnabled()) {
//            Double lat = gpsTracker.getLatitude();
//            Double lon = gpsTracker.getLongitude();
            Intent intent = new PlacePicker.IntentBuilder()
                    .setGoogleMapApiKey(com.ayata.purvamart.data.Constants.Constants.API)
//                    .setLatLong(18.520430, 73.856743)
                    .setLatLong(com.ayata.purvamart.data.Constants.Constants.latitude, com.ayata.purvamart.data.Constants.Constants.longitude)
                    .setMapZoom(17.0f)
                    .setAddressRequired(true)
                    .setFabColor(R.color.colorPrimary)
                    .setPrimaryTextColor(R.color.black)
                    .build(getActivity());
            startActivityForResult(intent, Constants.PLACE_PICKER_REQUEST);
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                AddressData addressData = data.getParcelableExtra(Constants.ADDRESS_INTENT);
                addressData.getAddressList();
                Log.d(TAG, "onActivityResult: " + addressData.getAddressList().get(0));
                String streetName = addressData.getAddressList().get(0).getAddressLine(0);
                String postalCode = addressData.getAddressList().get(0).getPostalCode();
                String city = addressData.getAddressList().get(0).getLocality();
                tilCity.getEditText().setText(city);
                tilPostalCode.getEditText().setText(postalCode);
                tilStreetAddress.getEditText().setText(streetName);
                Location mLocation = new Location("");
                mLocation.setLatitude(addressData.getLatitude());
                mLocation.setLongitude(addressData.getLongitude());
                getCurrentAddress(mLocation);
            } else {
            }
        }
    }

    private void getCurrentAddress(Location mLocation) {

    }
}