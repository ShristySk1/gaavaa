package com.ayata.purvamart.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Constants.Constants;
import com.ayata.purvamart.data.Model.ModelOrderList;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterOrderCompleted extends RecyclerView.Adapter<AdapterOrderCompleted.MyViewHolder> {

    private Context context;
    private List<ModelOrderList> listitem;
    private static OnCompletedItemClickListener onItemClickListener;

    public AdapterOrderCompleted(Context context, List<ModelOrderList> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_order_listitem,parent,false);
        return new MyViewHolder(view);
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
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            text_order_id= itemView.findViewById(R.id.text_order);
            text_date=itemView.findViewById(R.id.text_date_time);
            text_delivery= itemView.findViewById(R.id.text_delivery);
            imageView= itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onCompletedItemClick(getAdapterPosition(),listitem.get(getAdapterPosition()));
                }
            });

        }
    }

    public interface OnCompletedItemClickListener{
        void onCompletedItemClick(int position, ModelOrderList modelOrderList);
    }
   public static void setListener(OnCompletedItemClickListener listeners){
       onItemClickListener=listeners;
   }
}
