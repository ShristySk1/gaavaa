package com.ayata.purvamart.ui.Fragment.order;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Constants.Constants;
import com.ayata.purvamart.data.Model.ModelOrderList;
import com.ayata.purvamart.data.Model.ModelOrderTrack;
import com.ayata.purvamart.data.network.generic.NetworkResponseListener;
import com.ayata.purvamart.ui.Adapter.AdapterOrderTracker;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FragmentTrackOrder extends Fragment implements NetworkResponseListener<JsonObject> {
    public static String TAG = "FragmentTrackOrder";
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private AdapterOrderTracker adapterItem;
    private List<ModelOrderTrack> list_orderTrack;
    ImageView cancel_order;
    //error
    TextView text_error;
    ProgressBar progress_error;
    //conditional status
    String conditionalStatus = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_order, container, false);
        inflateLayout(view);
        list_orderTrack = new ArrayList<>();
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2("Track Orders", false, false);
        ((MainActivity) getActivity()).showBottomNavBar(false);

        initOrderView(view);

        populateData();
        recyclerView = view.findViewById(R.id.recycler_order_tracker);
        linearLayoutManager = new LinearLayoutManager(getContext());
        adapterItem = new AdapterOrderTracker(getContext(), list_orderTrack);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterItem);
        return view;
    }

    private void initOrderView(View view) {
        TextView text_orderid, text_datetime, text_delivery;
        ImageView image_item;

        text_datetime = view.findViewById(R.id.text_datetime);
        text_delivery = view.findViewById(R.id.text_delivery);
        text_orderid = view.findViewById(R.id.text_order_id);
        image_item = view.findViewById(R.id.image);
        cancel_order = view.findViewById(R.id.cancel_order);
        Bundle bundle = getArguments();
        if (bundle != null) {
            ModelOrderList listitem = (ModelOrderList) bundle.getSerializable(FragmentListOrder.order_item);
            text_delivery.setText("Estimated Delivery on" + " " + listitem.getDelivery_date());
            text_datetime.setText(listitem.getDate() + ", " + listitem.getTime());
            text_orderid.setText("Order#:" + " " + listitem.getOrder_id());
            conditionalStatus = listitem.getActualCondition();
            Glide.with(getContext()).load(listitem.getImage()).placeholder(Constants.PLACEHOLDER).into(image_item);
            cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    new Repository(FragmentTrackOrder.this, ApiClient.getApiService()).requestCancelOrder(listitem.getOrder_id());
                    showAlertDialog();
                }
            });
        }

    }

    void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (getActivity()).getLayoutInflater();
        builder.setCancelable(false);
        View v = inflater.inflate(R.layout.dialog_alert, null);
        builder.setView(v);
        Button yes = v.findViewById(R.id.btn_yes);
        Button no = v.findViewById(R.id.btn_no);
        AlertDialog dialog = builder.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.background_dialog_margin);
        dialog.show();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
    }

    private void populateData() {
        String[] titles = {
                ModelOrderTrack.ORDER_TYPE_PLACED,
                ModelOrderTrack.ORDER_TYPE_CONFIRMED,
                ModelOrderTrack.ORDER_TYPE_PROCESS,
                ModelOrderTrack.ORDER_TYPE_SHIP,
                ModelOrderTrack.ORDER_TYPE_DELIVERY,
                ModelOrderTrack.ORDER_TYPE_DELIVERED};
        String[] descriptions = {
                "We have received your order on 20 Dec,2020",
                "Order has been confirmed on 20 Dec,2020",
                "We are preparing your order",
                "Your Product is ready for shipping",
                "Your Product is out for delivery",
                "The Product is out for delivery"
        };
        int colorInComplete = R.color.colorGrayLight;
        int colorCompleted = R.color.colorPriceTag;
        int colorCurrent = R.color.colorPrimary;
        Boolean setNone = false;
        for (int i = 0; i < titles.length; i++) {
            Log.d(TAG, "populateData:conditional status " + conditionalStatus);
            if (!(titles[i].toLowerCase().trim().equals(conditionalStatus.toLowerCase().trim()))) {
                if (setNone) {
                    //set rest to grayed out
                    list_orderTrack.add(new ModelOrderTrack(titles[i], descriptions[i], ModelOrderTrack.ORDER_TYPE_NONE, colorInComplete, false));
                } else {
                    list_orderTrack.add(new ModelOrderTrack(titles[i], descriptions[i], titles[i], colorCompleted, false));
                }
            } else {
                list_orderTrack.add(new ModelOrderTrack(titles[i], descriptions[i], titles[i], colorCurrent, true));
                setNone = true;//set it to true after we found the exact title
            }
        }
    }

    @Override
    public void onResponseReceived(JsonObject response) {
        progress_error.setVisibility(View.GONE);
        Toast.makeText(getContext(), response.get("message").getAsString(), Toast.LENGTH_SHORT).show();
        getFragmentManager().popBackStackImmediate();
    }

    @Override
    public void onLoading() {
        progress_error.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message) {
        progress_error.setVisibility(View.GONE);
        text_error.setText(message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    //inflate layout for error and progressbar
    void inflateLayout(View view) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        //Avoid pass null in the root it ignores spaces in the child pullRefreshLayout
        View inflatedLayout = inflater.inflate(R.layout.error_layout, (ViewGroup) view, false);
        ViewGroup viewGroup = view.findViewById(R.id.root_main);
        viewGroup.addView(inflatedLayout);
        text_error = view.findViewById(R.id.text_error);
        progress_error = view.findViewById(R.id.progress_error);
    }
}