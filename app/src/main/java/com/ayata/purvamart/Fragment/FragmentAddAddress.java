package com.ayata.purvamart.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelItem;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;


public class FragmentAddAddress extends Fragment {


    public static final String FRAGMENT_ADDRESS_DELIVERY = "FRAGMENT_ADDRESS_DELIVERY";
    private String TAG = "FragmentAddAddress";
    //list of cart data
    List<ModelItem> modelItems;

    private View view;

    private TextInputLayout country_layout, tole_layout, detail_layout;
    private LinearLayout address_all;
    private TextView address_text;
    private Spinner spinner_city;

    private String tole, country, detail, total_address, city;

    private Button btn_save;

    private RelativeLayout progress_layout;
    Bundle bundle = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        view = inflater.inflate(R.layout.fragment_add_address, container, false);

        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2("Address Delivery", false, false);

        //bottom nav bar
        ((MainActivity) getActivity()).showBottomNavBar(false);

        progress_layout = view.findViewById(R.id.progress_view);
        progress_layout.setVisibility(View.GONE);

        country = "Nepal";
        country_layout = view.findViewById(R.id.country_layout);
        tole_layout = view.findViewById(R.id.tole_layout);
        detail_layout = view.findViewById(R.id.details_layout);
        address_all = view.findViewById(R.id.layout_address);
        address_text = view.findViewById(R.id.address_all);
        spinner_city = view.findViewById(R.id.spinner_city);
        btn_save = view.findViewById(R.id.btn_save);

        String[] cities = new String[]{"Kathmandu", "Bhaktapur", "Lalitpur", "Dharan", "Pokhara", "Chitwan", "Hetauda", "Jhapa", "Surkhet"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(adapter);

        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city = cities[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        return view;
    }

    private void saveData() {

        if (!validateTole() | !validateDetails() | city.isEmpty() | country.isEmpty()) {
            return;
        }


        total_address = detail + ", " + tole + ", " + city + ", " + country;
//        address_text.setText(total_address);
//        address_all.setVisibility(View.VISIBLE);

        //start
//        Handler handler =  new Handler();
//        Runnable myRunnable = new Runnable() {
//            public void run() {
//                // Things to be done
//                progress_layout.setVisibility(View.VISIBLE);
//            }
//        };
//
//        handler.postDelayed(myRunnable, 5000);
//
//        progress_layout.setVisibility(View.GONE);
//
//        nextFragment();
        //end

        getMyOrderList();


    }

    private void getMyOrderList() {

        progress_layout.setVisibility(View.VISIBLE);
        modelItems = new ArrayList<>();
        ApiService myOrderApi = ApiClient.getClient().create(ApiService.class);
//        myOrderApi.getMyOrder(PreferenceHandler.getToken(getContext())).enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response.isSuccessful()) {
//                    JsonObject jsonObject = response.body();
//                    progress_layout.setVisibility(View.GONE);
//                    if (jsonObject.get("code").toString().equals("200")) {
//                        if (jsonObject.get("message").getAsString().equals("empty cart")) {
//                            Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
//                        } else {
//                            Gson gson = new GsonBuilder().create();
//                            MyOrderResponse myOrderResponse = gson.fromJson(gson.toJson(jsonObject), MyOrderResponse.class);
//                            Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
//                            for (OrderDetail orderDetail : myOrderResponse.getDetails()) {
//                                if (orderDetail.getIsTaken()) {
//                                    modelItems.add(new ModelItem(orderDetail.getProductId(),orderDetail.getProductId().toString(), orderDetail.getPrice().toString(), String.valueOf(orderDetail.getPrice() * orderDetail.getProductQuantity()),
//                                            R.drawable.spinach, orderDetail.getProductQuantity().toString(), true, "15% Off", 1));
//                                }
//                            }
        //navigate to next fragment
        bundle = new Bundle();
        Log.d(TAG, "onResponse: " + modelItems.size());
//                            bundle.putSerializable(FRAGMENT_ADDRESS_DELIVERY, (Serializable) modelItems);
        bundle.putString(FRAGMENT_ADDRESS_DELIVERY, total_address);
        nextFragment();
//                        }

//                    } else {
////                        Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), "" + "Please login to continue", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }

//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                progress_layout.setVisibility(View.VISIBLE);
//                Toast.makeText(getContext(),"Please Check Your Internet Connection",Toast.LENGTH_LONG).show();
//                Log.d("testphase", "onFailure: "+t.getMessage());
//            }
//        });
    }

    private void nextFragment() {
        FragmentOrderSummary fragmentOrderSummary = new FragmentOrderSummary();
        fragmentOrderSummary.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.main_fragment, fragmentOrderSummary)
                .addToBackStack(null).commit();

    }

    private Boolean validateTole() {
        tole = tole_layout.getEditText().getText().toString();
        return validate(tole_layout, 100, "Tole");
    }

    private Boolean validateDetails() {
        detail = detail_layout.getEditText().getText().toString();
        return validate(detail_layout, 100, "Details");
    }


    private Boolean validate(TextInputLayout data_type, int max_no, String displayname) {
        String data_types = data_type.getEditText().getText().toString().trim();
        if (data_types.isEmpty()) {
            data_type.setError(displayname + " can't be empty");
            return false;
        } else if (data_types.length() > max_no) {
            data_type.setError("Field too long");
            return false;
        } else {
            data_type.setError(null);
            return true;
        }
    }

}