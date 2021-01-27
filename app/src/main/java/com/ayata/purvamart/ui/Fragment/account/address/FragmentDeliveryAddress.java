package com.ayata.purvamart.ui.Fragment.account.address;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelAddress;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.generic.NetworkResponseListener;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.data.repository.Repository;
import com.ayata.purvamart.ui.Adapter.AdapterAddress;
import com.ayata.purvamart.ui.Fragment.payment.FragmentPayment2;
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
    public static final String FragmentDeliveryAddressId = "FragmentDeliveryAddressId";

    //view for displaying saved address list if any
    RecyclerView recyclerView;
    List<ModelAddress> listitem;
    AdapterAddress adapterAddress;
    //add address layout
    LinearLayout ll_add_address;
    //error
    TextView text_error;
    ProgressBar progress_error;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery_address, container, false);
        ll_add_address = view.findViewById(R.id.ll_add_address);
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2("Shipping Address", false, false);
        inflateLayout(view);
        //bottom nav bar
        ((MainActivity) getActivity()).showBottomNavBar(false);
        initRecycler(view);
        ll_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listitem.size() >= 3) {
                    Toast.makeText(getContext(), "Address add limit exceeded", Toast.LENGTH_SHORT).show();
                    return;
                }
                ((MainActivity) getActivity()).changeFragment(19, FragmentEditAddress.TAG2, null);
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
        getAddress();

    }

    //inflate pullRefreshLayout for error and progressbar
    void inflateLayout(View view) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        //Avoid pass null in the root it ignores spaces in the child pullRefreshLayout
        View inflatedLayout = inflater.inflate(R.layout.error_layout, (ViewGroup) view, false);
        ViewGroup viewGroup = view.findViewById(R.id.root_main);
        viewGroup.addView(inflatedLayout);
        text_error = view.findViewById(R.id.text_error);
        progress_error = view.findViewById(R.id.progress_error);
    }

    void getAddress() {
        new Repository(this, ApiClient.getApiService()).requestMyAddress();
    }


    @Override
    public void onEditClick(int position, ModelAddress modelAddress) {
        //make bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentDeliveryAddress, modelAddress);
        bundle.putString(FragmentDeliveryAddressTitle, "Edit Address");
        ((MainActivity) getActivity()).changeFragment(12, FragmentEditAddress.TAG, bundle);

    }

    @Override
    public void onAddressClick(ModelAddress modelAddress) {
        //setAddressId of clicked address
        PreferenceHandler.setAddressId(getContext(), modelAddress.getId().toString());
        ((MainActivity) getActivity()).changeFragment(17, FragmentPayment2.TAG, null);

    }

    @Override
    public void onResponseReceived(JsonObject response) {
        progress_error.setVisibility(View.GONE);
        Gson gson = new GsonBuilder().create();
        TypeToken<List<ModelAddress>> responseTypeToken = new TypeToken<List<ModelAddress>>() {
        };
        List<ModelAddress> details = gson.fromJson(gson.toJson(response.getAsJsonArray("details")), responseTypeToken.getType());
        listitem.addAll(details);
        adapterAddress.notifyDataSetChanged();
    }

    @Override
    public void onLoading() {
        progress_error.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message) {
        progress_error.setVisibility(View.GONE);
        text_error.setText(message);
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }


}