package com.ayata.purvamart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayata.purvamart.Model.ModelAddress;
import com.ayata.purvamart.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterAddress extends RecyclerView.Adapter<AdapterAddress.MyViewHolder> {

    private Context context;
    private List<ModelAddress> listitem;
    private static OnItemClickListener onItemClickListener;

    public AdapterAddress(Context context, List<ModelAddress> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_billing_address, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

//        holder.text_delivery.setText("Estimated Delivery on"+" "+listitem.get(position).getDelivery_date());
//        holder.text_date.setText(listitem.get(position).getDate()+", "+listitem.get(position).getTime());
//        holder.text_order_id.setText("Order#:"+" "+listitem.get(position).getOrder_id());
//        Glide.with(context).load(listitem.get(position).getImage()).placeholder(Constants.PLACEHOLDER).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvEdit;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEdit = itemView.findViewById(R.id.tvEdit);
//
//            text_order_id= itemView.findViewById(R.id.text_order);
//            text_date=itemView.findViewById(R.id.text_date_time);
//            text_delivery= itemView.findViewById(R.id.text_delivery);
//            imageView= itemView.findViewById(R.id.image);
            tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onEditClick(getAdapterPosition(), listitem.get(getAdapterPosition()));

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onAddressClick(listitem.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onEditClick(int position, ModelAddress modelAddress);

        void onAddressClick(ModelAddress modelAddress);
    }

    public static void setListener(OnItemClickListener listeners) {
        onItemClickListener = listeners;
    }
}
