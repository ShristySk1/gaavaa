package com.ayata.purvamart.ui.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelOrderList;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewPagerMyOrderAdapter extends RecyclerView.Adapter<ViewPagerMyOrderAdapter.myViewHolder> {
    List<ModelOrderList> onCompleted = new ArrayList<>();
    List<ModelOrderList> onProgress = new ArrayList<>();
    List<ModelOrderList> onCancelled = new ArrayList<>();
    Boolean isEmptyCompletedOrder = false;
    Boolean isEmptyOnProgressOrder = false;
    Boolean isEmptyCancelledOrder = false;
    public setOnShopButtonClick setOnShopButtonClick;

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
            Log.d("checkorderprogress", "onBindViewHolder: " + getEmptyOnProgressOrder());
            if (getEmptyOnProgressOrder()) {
                holder.emptyLayout.setVisibility(View.VISIBLE);

            } else {
                holder.emptyLayout.setVisibility(View.GONE);
                AdapterOrder ModelOrderListAdapter = new AdapterOrder(context, onProgress);
                holder.recyclerView.setAdapter(ModelOrderListAdapter);
            }
        }
        if (position == 1) {
            Log.d("checkordercompleted", "onBindViewHolder: " + isEmptyCompletedOrder);
            if (getEmptyCompletedOrder()) {
                holder.emptyLayout.setVisibility(View.VISIBLE);
            } else {
                holder.emptyLayout.setVisibility(View.GONE);
                AdapterOrderDetails ModelOrderListAdapter = new AdapterOrderDetails(context, onCompleted);
                holder.recyclerView.setAdapter(ModelOrderListAdapter);
            }
        }
        if (position == 2) {
            Log.d("checkordercancelled", "onBindViewHolder: " + isEmptyCancelledOrder);
            if (getEmptyCancelledOrder()) {
                holder.emptyLayout.setVisibility(View.VISIBLE);
            } else {
                holder.emptyLayout.setVisibility(View.GONE);
                AdapterOrderDetails ModelOrderListAdapter = new AdapterOrderDetails(context, onCancelled);
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


    public List<ModelOrderList> getOnCompleted() {
        return onCompleted;
    }

    public void setOnCompleted(List<ModelOrderList> onCompleted) {
        this.onCompleted = onCompleted;
    }

    public List<ModelOrderList> getOnProgress() {
        return onProgress;
    }

    public void setOnProgress(List<ModelOrderList> onProgress) {
        this.onProgress = onProgress;
    }

    public List<ModelOrderList> getOnCancelled() {
        return onCancelled;
    }

    public void setOnCancelled(List<ModelOrderList> onCancelled) {
        this.onCancelled = onCancelled;
    }

    public void setShopListener(setOnShopButtonClick listeners) {
        setOnShopButtonClick = listeners;
    }

    public interface setOnShopButtonClick {
        void onShopButtonClick();
    }
}
