package com.ayata.purvamart.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayata.purvamart.Adapter.AdapterOrder;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.Model.ModelOrderList;
import com.ayata.purvamart.R;

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
 */

public class FragmentListOrder extends Fragment implements AdapterOrder.OnItemClickListener {

    public static String TAG = "FragmentListOrder";
    private View view;
    private RecyclerView recyclerView;
    private AdapterOrder adapterOrder;
    private LinearLayoutManager layoutManager;
    private List<ModelOrderList> listitem;

    public static final String order_item = "ORDER_ITEM";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        view = inflater.inflate(R.layout.fragment_list_order, container, false);

        getBundleArguments();
        initRecycler();
        return view;
    }

    private void getBundleArguments() {
        Bundle bundle = getArguments();
        listitem = new ArrayList<>();
        if (bundle != null) {
            listitem = (ArrayList<ModelOrderList>) bundle.getSerializable(FragmentMyOrder.FRAGMENT_MY_ORDER);
        }
    }

    private void initRecycler() {
        recyclerView = view.findViewById(R.id.recycler_view);
        adapterOrder = new AdapterOrder(getContext(), listitem);
        recyclerView.setAdapter(adapterOrder);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        dataPrepare();
    }

    private void dataPrepare() {

//        listitem.add(new ModelOrderList(R.drawable.spinach,"22574","20-Dec-2019", "3:00 PM","22 Dec"));
//        listitem.add(new ModelOrderList(R.drawable.spinach,"22574","20-Dec-2019", "3:00 PM","22 Dec"));
//        listitem.add(new ModelOrderList(R.drawable.spinach,"22574","20-Dec-2019", "3:00 PM","22 Dec"));
        adapterOrder.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position, ModelOrderList modelOrderList) {
//        Toast.makeText(getContext(), "List Clicked Item--"+position, Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putSerializable(order_item, modelOrderList);
//        FragmentTrackOrder fragmentTrackOrder = ;
//        fragmentTrackOrder.setArguments(bundle);
        ((MainActivity) getActivity()).changeFragment(10, FragmentTrackOrder.TAG, bundle);

    }
}