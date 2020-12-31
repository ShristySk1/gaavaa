package com.ayata.purvamart.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelOrderList;
import com.ayata.purvamart.R;
import com.ayata.purvamart.SignupActivity;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.response.UserCartDetail;
import com.ayata.purvamart.data.network.response.UserCartResponse;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMyOrder extends Fragment implements View.OnClickListener {

    public static final String FRAGMENT_MY_ORDER = "FRAGMENT_MY_ORDER";
    public String TAG = "FRAGMENT_MY_ORDER";
    private LinearLayout option1, option2, option3;
    private TextView textView1, textView2, textView3;
    private View line1, line2, line3;
    public static final String empty_title = "EMPTY_TITLE";
    List<ModelOrderList> listitem;

    //switch case
    Fragment fragment = null;
    Bundle bundle = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);

        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType3("My Order");
        //bottom nav bar
        ((MainActivity) getActivity()).showBottomNavBar(true);

        initView(view);

        if (view.findViewById(R.id.fragment_order) != null) {

            if (savedInstanceState != null) {
                return null;
            }

//            getChildFragmentManager().beginTransaction()
////                    .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
////                    .replace(R.id.fragment_order, new FragmentListOrder())
////                    .commit();
            option1.performClick();

        }

        return view;
    }

    private void initView(View view) {
        option1 = view.findViewById(R.id.layout_option1);
        option2 = view.findViewById(R.id.layout_option2);
        option3 = view.findViewById(R.id.layout_option3);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);

        textView1 = view.findViewById(R.id.text1);
        textView2 = view.findViewById(R.id.text2);
        textView3 = view.findViewById(R.id.text3);
        line1 = view.findViewById(R.id.line1);
        line2 = view.findViewById(R.id.line2);
        line3 = view.findViewById(R.id.line3);

        selectOption1();


    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.layout_option1:
                selectOption1();
                //fetch api
                getCompletedOrder();
                break;

            case R.id.layout_option2:
                selectOption2();
                //api fetch
                getOnProgressOrder();
                break;

            case R.id.layout_option3:
                selectOption3();
                //api fetch
                getCancelledOrder();
                break;
        }


    }

    private void getCancelledOrder() {
        listitem = new ArrayList<>();
        ApiService myOrderApi = ApiClient.getClient().create(ApiService.class);
        myOrderApi.getMyOrder(PreferenceHandler.getToken(getContext())).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject.get("code").toString().equals("200")) {
                        if (jsonObject.get("message").getAsString().equals("empty cart")) {
                            Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new GsonBuilder().create();
                            UserCartResponse myOrderResponse = gson.fromJson(gson.toJson(jsonObject), UserCartResponse.class);
                            Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
                            for (UserCartDetail orderDetail : myOrderResponse.getDetails()) {
                                if (orderDetail.getIsOrdered()) {
//                                    listitem.add(new ModelOrderList(R.drawable.spinach, "22574", "20-Dec-2019", "3:00 PM", "22 Dec"));
                                }
                            }

                        }
                        //navigate to next fragment
                        nextFragment(new FragmentListOrder(), getString(R.string.eo_text3));
                    } else {
//                        Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "" + "Please login to continue", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }


    private void getCompletedOrder() {
        listitem = new ArrayList<>();
        ApiService myOrderApi = ApiClient.getClient().create(ApiService.class);
        myOrderApi.getMyOrder(PreferenceHandler.getToken(getContext())).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject.get("code").toString().equals("200")) {
                        if (jsonObject.get("message").getAsString().equals("empty cart")) {
                            Toast.makeText(getContext(), jsonObject.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new GsonBuilder().create();
                            UserCartResponse myOrderResponse = gson.fromJson(gson.toJson(jsonObject), UserCartResponse.class);
                            Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
                            for (UserCartDetail orderDetail : myOrderResponse.getDetails()) {
                                if (orderDetail.getIsOrdered()) {
                                    String image = "";
                                    if (orderDetail.getImage().size() > 0) {
                                        image = orderDetail.getImage().get(0);
                                    }
                                    listitem.add(new ModelOrderList(image, "22574", orderDetail.getCreatedDate(), "", "1st Jan"));
                                }
                            }

                        }
                        //navigate to next fragment
                        nextFragment(new FragmentListOrder(), getString(R.string.eo_text1));
                    } else {
//                        Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "" + "Please login to continue", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext(), SignupActivity.class));

                    }
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void getOnProgressOrder() {
        listitem = new ArrayList<>();
        ApiService myOrderApi = ApiClient.getClient().create(ApiService.class);
        myOrderApi.getMyOrder(PreferenceHandler.getToken(getContext())).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject.get("code").toString().equals("200")) {
                        if (jsonObject.get("message").getAsString().equals("empty cart")) {
                            Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new GsonBuilder().create();
                            UserCartResponse myOrderResponse = gson.fromJson(gson.toJson(jsonObject), UserCartResponse.class);
                            Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
                            for (UserCartDetail orderDetail : myOrderResponse.getDetails()) {
                                if (orderDetail.getIsTaken()) {
                                    String image = "";
                                    if (orderDetail.getImage().size() > 0) {
                                        image = orderDetail.getImage().get(0);
                                    }
                                    listitem.add(new ModelOrderList(image, "22574", orderDetail.getCreatedDate(), "", "1st Jan"));
                                }
                            }

                        }
                        //navigate to next fragment
                        nextFragment(new FragmentListOrder(), getString(R.string.eo_text1));

                    } else {
//                        Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "" + "Please login to continue", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }


    private void nextFragment(Fragment fragment, String title) {
        if (listitem != null && listitem.size() != 0) {
            bundle.putSerializable(FRAGMENT_MY_ORDER, (Serializable) listitem);
            FragmentCartFilled fragmentCartFilled = new FragmentCartFilled();
            fragmentCartFilled.setArguments(bundle);
            Log.d("checkcart", "nextFragment: " + "not emptycart" + listitem.size());
        } else {
            Log.d("checkcart", "nextFragment: " + "emptycart");
            fragment = new FragmentEmptyOrder();
            bundle.putString(empty_title, title);
        }
        fragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.fragment_order, fragment).addToBackStack(null).commit();
    }

    private void selectOption1() {

        textView1.setTextColor(getResources().getColor(R.color.colorGreen));
        textView2.setTextColor(getResources().getColor(R.color.colorGray));
        textView3.setTextColor(getResources().getColor(R.color.colorGray));

        line1.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        line2.setBackgroundColor(getResources().getColor(R.color.colorGray));
        line3.setBackgroundColor(getResources().getColor(R.color.colorGray));
    }

    private void selectOption2() {

        textView1.setTextColor(getResources().getColor(R.color.colorGray));
        textView2.setTextColor(getResources().getColor(R.color.colorGreen));
        textView3.setTextColor(getResources().getColor(R.color.colorGray));

        line1.setBackgroundColor(getResources().getColor(R.color.colorGray));
        line2.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        line3.setBackgroundColor(getResources().getColor(R.color.colorGray));
    }

    private void selectOption3() {

        textView1.setTextColor(getResources().getColor(R.color.colorGray));
        textView2.setTextColor(getResources().getColor(R.color.colorGray));
        textView3.setTextColor(getResources().getColor(R.color.colorGreen));

        line1.setBackgroundColor(getResources().getColor(R.color.colorGray));
        line2.setBackgroundColor(getResources().getColor(R.color.colorGray));
        line3.setBackgroundColor(getResources().getColor(R.color.colorGreen));
    }
}