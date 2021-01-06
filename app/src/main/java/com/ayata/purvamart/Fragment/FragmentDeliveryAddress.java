package com.ayata.purvamart.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ayata.purvamart.Adapter.AdapterAddress;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelAddress;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.helper.NetworkResponse;
import com.ayata.purvamart.data.network.helper.NetworkResponseListener;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentDeliveryAddress extends Fragment implements AdapterAddress.OnItemClickListener, NetworkResponseListener<JsonObject> {
    public static final String TAG = "FragmentDeliveryAddress";
    public static final String FragmentDeliveryAddress = "FragmentDeliveryAddress";
    public static final String FragmentDeliveryAddressTitle = "FragmentDeliveryAddressTitle";


    RecyclerView recyclerView;
    List<ModelAddress> listitem;
    AdapterAddress adapterAddress;
    LinearLayout ll_add_address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery_address, container, false);
        ll_add_address = view.findViewById(R.id.ll_add_address);
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2("Shipping Address", false, false);
        //bottom nav bar
        ((MainActivity) getActivity()).showBottomNavBar(false);
        initRecycler(view);
        ll_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FragmentDeliveryAddressTitle, "Add Address");
                ((MainActivity) getActivity()).changeFragment(12, FragmentEditAddress.TAG, bundle);
            }
        });
        return view;
    }

    private void initRecycler(View view) {
        listitem = new ArrayList<>();
        prepareData();
        recyclerView = view.findViewById(R.id.recycler_address);
        adapterAddress = new AdapterAddress(getContext(), listitem);
        AdapterAddress.setListener(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapterAddress);
        recyclerView.setLayoutManager(manager);
    }

    private void prepareData() {
//        listitem.add(new ModelAddress());
//        listitem.add(new ModelAddress());
        getAddress();

    }

    void getAddress() {
        requestMyAddress(this, ApiClient.getApiService());
    }

    public void requestMyAddress(NetworkResponseListener<JsonObject> listener, ApiService api) {
        api.getAddress(PreferenceHandler.getToken(getContext())).enqueue(new NetworkResponse<>(listener));
    }

    @Override
    public void onEditClick(int position, ModelAddress modelAddress) {
        //make bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentDeliveryAddress, modelAddress);
        ((MainActivity) getActivity()).changeFragment(12, FragmentEditAddress.TAG, bundle);

    }

    @Override
    public void onAddressClick(ModelAddress modelAddress) {
        ((MainActivity) getActivity()).changeFragment(17, FragmentPayment.TAG, null);

    }

    @Override
    public void onResponseReceived(JsonObject response) {
        Gson gson = new GsonBuilder().create();
        TypeToken<List<ModelAddress>> responseTypeToken = new TypeToken<List<ModelAddress>>() {
        };
        List<ModelAddress> details = gson.fromJson(gson.toJson(response.getAsJsonArray("details")), responseTypeToken.getType());
        listitem.addAll(details);
        adapterAddress.notifyDataSetChanged();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}