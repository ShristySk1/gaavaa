package com.ayata.purvamart.ui.Fragment.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelItem;
import com.ayata.purvamart.data.Model.ModelOrderList;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.ui.Adapter.AdapterOrderSummary;
import com.ayata.purvamart.ui.Fragment.order.FragmentMyOrder;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * fragmentList.add(new FragmentShop());//0
 * fragmentList.add(new FragmentCart());//1
 * fragmentList.add(new FragmentMyOrder());//2
 * fragmentList.add(new FragmentListOrder());//3
 * fragmentList.add(new FragmentEmptyOrder());//4
 * fragmentList.add(new FragmentCart());//5
 * fragmentList.add(new FragmentCartEmpty());//6
 * fragmentList.add(new FragmentCartFilled());//7
 * fragmentList.add(new FragmentProduct());//8
 * fragmentList.add(new FragmentCategory());//9
 * fragmentList.add(new FragmentTrackOrder());//10
 * fragmentList.add(new FragmentAccount());//11
 * fragmentList.add(new FragmentEditAddress());//12
 * fragmentList.add(new FragmentEditProfile());//13
 * fragmentList.add(new FragmentPrivacyPolicy());//14
 * fragmentList.add(new FragmentPayment());//15
 * fragmentList.add(new FragmentThankyou());//16
 */

public class FragmentOrderSummary extends Fragment {
    public static String TAG = "FragmentOrderSummary";

    private View view;
    private RecyclerView recyclerView;
    private AdapterOrderSummary adapterOrderSummary;
    private List<ModelItem> listitem;
    private LinearLayoutManager layoutManager;
    private Button btn_confirm;
    private TextView pay_total;
    private TextView pay_orderPrice, text_address;
    private TextView image_pay_type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        view = inflater.inflate(R.layout.fragment_order_summary, container, false);

        initAppbar();
        pay_orderPrice = view.findViewById(R.id.pay_orderprice);
        pay_total = view.findViewById(R.id.pay_total);
        image_pay_type = view.findViewById(R.id.image_pay_type);
//        text_address = view.findViewById(R.id.text_address);

        initRecycler(view);
//        btn_confirm = view.findViewById(R.id.btn_confirm);
//        btn_confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //save to server
////                saveOrderListToServer();
//
//            }
//        });

        return view;
    }

//    private void saveOrderListToServer() {

////        List<MyCart> carts = new ArrayList<>();
////        ApiService apiService = ApiClient.getClient().create(ApiService.class);
////        MyCart myCart = new MyCart();
////        Log.d(TAG, "nextFragment: " + modelItem.getId());
////        myCart.setProductId(modelItem.getId());
////        carts.add(myCart);
//        if (!PreferenceHandler.isUserAlreadyLoggedIn(getContext())) {
//            Toast.makeText(getContext(), "Please Login to continue", Toast.LENGTH_LONG).show();
//            startActivity(new Intent(getContext(), SignupActivity.class));
//            return;
//        }
////        List<MyCart> myCarts = new ArrayList<>();
////        for (ModelItem modelItem : adapterOrderSummary.getOrderList()) {
////            myCarts.add(new MyCart(modelItem.getId(), Integer.valueOf(modelItem.getQuantity())));
////        }
//
////        ApiService apiService = ApiClient.getClient().create(ApiService.class);
////        apiService.addToOrder(PreferenceHandler.getToken(getContext()), new Gson().toJson(adapterOrderSummary.getOrderList())).enqueue(new Callback<JsonObject>() {
////            @Override
////            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
////                if (response.isSuccessful()&&response!=null) {
////                    Log.d(TAG, "onResponse: " + response.body().get("message"));
////                    if (response.body().get("code").getAsString().equals("200")) {
////                        Toast.makeText(getContext(), "Order Successful", Toast.LENGTH_LONG).show();
////                        ((MainActivity) getActivity()).changeFragment(16,FragmentThankyou.TAG,null);
////                    } else {
////                        Toast.makeText(getContext(), "Please login to continue", Toast.LENGTH_LONG).show();
////                        startActivity(new Intent(getContext(), SignupActivity.class));
////                    }
////                }else {
////                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
////                }
////            }
////
////            @Override
////            public void onFailure(Call<JsonObject> call, Throwable t) {
////                Log.d(TAG, "onResponse: " + t.getMessage());
////            }
////        });
//    }

    private void initAppbar() {
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2(getString(R.string.os_title), false, false);

        //bottom nav bar
        ((MainActivity) getActivity()).showBottomNavBar(false);

    }


    private void initRecycler(View view) {
        listitem = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view);
        adapterOrderSummary = new AdapterOrderSummary(getContext(), listitem);
        recyclerView.setAdapter(adapterOrderSummary);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        dataPrepare();


    }

    //add to cart api
    private void dataPrepare() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            ModelOrderList modelOrderList = (ModelOrderList) bundle.getSerializable(FragmentMyOrder.completed_order_item);
//            Glide.with(getContext()).load(modelOrderList.getPayment_type()).placeholder(R.drawable.placeholder).into(image_pay_type);
            image_pay_type.setText(modelOrderList.getPayment_type());
            pay_orderPrice.setText(modelOrderList.getGrand_total());
            pay_total.setText(modelOrderList.getGrand_total());
            for (ProductDetail productDetail : modelOrderList.getProductDetails()) {
                listitem.add(new ModelItem(
                        productDetail.getId(),
                        productDetail.getName(),
                        String.valueOf(productDetail.getTotal_price()),
                        String.valueOf(productDetail.getProductPrice()),
                        productDetail.getImage(),
                        productDetail.getProduct_quantity(),
                        true,
                        productDetail.getProductDiscount(),
                        1,
                        productDetail.getUnit()));
            }
            adapterOrderSummary.notifyDataSetChanged();
        }
    }


}