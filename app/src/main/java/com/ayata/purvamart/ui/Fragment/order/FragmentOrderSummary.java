package com.ayata.purvamart.ui.Fragment.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelOrderList;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.ui.Adapter.AdapterOrderSummary;

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
    private List<ProductDetail> listitem;
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
        initView(view);
        initRecycler(view);
        return view;
    }

    private void initView(View view) {
        pay_orderPrice = view.findViewById(R.id.pay_orderprice);
        pay_total = view.findViewById(R.id.pay_total);
        image_pay_type = view.findViewById(R.id.image_pay_type);
    }

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


    //set bundled data to list
    private void dataPrepare() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            ModelOrderList modelOrderList = (ModelOrderList) bundle.getSerializable(FragmentMyOrder.ORDER_ITEM_FOR_SUMMARY);
//            Glide.with(getContext()).load(modelOrderList.getPayment_type()).placeholder(R.drawable.placeholder).into(image_pay_type);
            image_pay_type.setText(modelOrderList.getPayment_type());
            pay_orderPrice.setText(modelOrderList.getGrand_total());
            pay_total.setText(modelOrderList.getGrand_total());
//            for (ProductDetail productDetail : modelOrderList.getProductDetails()) {
//                listitem.add(new ModelItem(
//                        productDetail.getId(),
//                        productDetail.getName(),
//                        String.valueOf(productDetail.getTotal_price()),
//                        String.valueOf(productDetail.getProductPrice()),
//                        productDetail.getImage(),
//                        productDetail.getProduct_quantity(),
//                        true,
//                        productDetail.getProductDiscount(),
//                        1,
//                        productDetail.getUnit()));
//            }
            listitem.addAll(modelOrderList.getProductDetails());
            adapterOrderSummary.notifyDataSetChanged();
        }
    }


}