package com.ayata.purvamart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayata.purvamart.Constants.Constants;
import com.ayata.purvamart.Model.ModelOrderList;
import com.ayata.purvamart.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.MyViewHolder> {

    private Context context;
    private List<ModelOrderList> listitem;
    private OnItemClickListener onItemClickListener;

    public AdapterOrder(Context context, List<ModelOrderList> listitem, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.listitem = listitem;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_order_listitem,parent,false);
        return new MyViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.text_delivery.setText("Estimated Delivery on"+" "+listitem.get(position).getDelivery_date());
        holder.text_date.setText(listitem.get(position).getDate()+", "+listitem.get(position).getTime());
        holder.text_order_id.setText("Order#:"+" "+listitem.get(position).getOrder_id());
        Glide.with(context).load(listitem.get(position).getImage()).placeholder(Constants.PLACEHOLDER).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_order_id, text_date,text_delivery;
        ImageView imageView;

        OnItemClickListener onItemClickListener;
        public MyViewHolder(@NonNull View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);

            this.onItemClickListener=onItemClickListener;
            text_order_id= itemView.findViewById(R.id.text_order);
            text_date=itemView.findViewById(R.id.text_date_time);
            text_delivery= itemView.findViewById(R.id.text_delivery);
            imageView= itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(getAdapterPosition(),listitem.get(getAdapterPosition()));
                }
            });

        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position, ModelOrderList modelOrderList);
    }
}
