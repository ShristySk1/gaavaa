package com.ayata.purvamart.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.ayata.purvamart.Model.ModelOrderList;
import com.ayata.purvamart.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewPagerMyOrderAdapter extends RecyclerView.Adapter<ViewPagerMyOrderAdapter.myViewHolder> {
    List<ModelOrderList> today = new ArrayList<>();
    List<ModelOrderList> tomorrow = new ArrayList<>();
    List<ModelOrderList> dayAfter = new ArrayList<>();
    Boolean isEmptyCompletedOrder = false;
    Boolean isEmptyOnProgressOrder = false;
    Boolean isEmptyCancelledOrder = false;
    public  setOnShopButtonClick setOnShopButtonClick;

    public Boolean getEmptyCompletedOrder() {
        return isEmptyCompletedOrder;
    }

    public void setEmptyCompletedOrder(Boolean emptyCompletedOrder) {
        isEmptyCompletedOrder = emptyCompletedOrder;
    }

    public Boolean getEmptyOnProgressOrder() {
        return isEmptyOnProgressOrder;
    }

    public void setEmptyOnProgressOrder(Boolean emptyOnProgressOrder) {
        isEmptyOnProgressOrder = emptyOnProgressOrder;
    }

    public Boolean getEmptyCancelledOrder() {
        return isEmptyCancelledOrder;
    }

    public void setEmptyCancelledOrder(Boolean emptyCancelledOrder) {
        isEmptyCancelledOrder = emptyCancelledOrder;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        holder.recyclerView.setLayoutManager(manager);
        if (position == 0) {
            Log.d("checkorder", "onBindViewHolder: "+isEmptyCompletedOrder);
            if (getEmptyCompletedOrder()) {
                holder.emptyLayout.setVisibility(View.VISIBLE);
            } else {
                holder.emptyLayout.setVisibility(View.GONE);
                AdapterOrder ModelOrderListAdapter = new AdapterOrder(context, today);
                holder.recyclerView.setAdapter(ModelOrderListAdapter);
            }
        }
        if (position == 1) {
            Log.d("checkorder", "onBindViewHolder: "+getEmptyOnProgressOrder());
            if (getEmptyOnProgressOrder()) {
                holder.emptyLayout.setVisibility(View.VISIBLE);

            } else {
                holder.emptyLayout.setVisibility(View.GONE);
                AdapterOrder ModelOrderListAdapter = new AdapterOrder(context, tomorrow);
                holder.recyclerView.setAdapter(ModelOrderListAdapter);
            }
        }
        if (position == 2) {
            Log.d("checkorder", "onBindViewHolder: "+isEmptyCancelledOrder);
            if (getEmptyCancelledOrder()) {
                holder.emptyLayout.setVisibility(View.VISIBLE);
            } else {

                holder.emptyLayout.setVisibility(View.GONE);
                AdapterOrder ModelOrderListAdapter = new AdapterOrder(context, dayAfter);
                holder.recyclerView.setAdapter(ModelOrderListAdapter);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        RelativeLayout emptyLayout;
        Button button;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_bus_passengers_item);
            emptyLayout = itemView.findViewById(R.id.empty_order);
            button = itemView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setOnShopButtonClick.onShopButtonClick();
                }
            });
        }
    }


    public List<ModelOrderList> getToday() {
        return today;
    }

    public void setToday(List<ModelOrderList> today) {
        this.today = today;
    }

    public List<ModelOrderList> getTomorrow() {
        return tomorrow;
    }

    public void setTomorrow(List<ModelOrderList> tomorrow) {
        this.tomorrow = tomorrow;
    }

    public List<ModelOrderList> getDayAfter() {
        return dayAfter;
    }

    public void setDayAfter(List<ModelOrderList> dayAfter) {
        this.dayAfter = dayAfter;
    }

    public void setShopListener(setOnShopButtonClick listeners) {
        setOnShopButtonClick = listeners;
    }

    public interface setOnShopButtonClick {
        void onShopButtonClick();
    }
}
