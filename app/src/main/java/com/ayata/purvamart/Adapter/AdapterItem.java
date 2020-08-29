package com.ayata.purvamart.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayata.purvamart.Model.ModelItem;
import com.ayata.purvamart.R;
import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.modelViewHolder> {

    private Context context;
    private List<ModelItem> listitem;
    private OnItemClickListener onItemClickListener;

    public AdapterItem(Context context, List<ModelItem> listitem, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.listitem = listitem;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new modelViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull modelViewHolder holder, int position) {

        holder.name.setText(listitem.get(position).getName());
        holder.quantity.setText(listitem.get(position).getQuantity());
        Glide.with(context).load(listitem.get(position).getImage()).into(holder.image);
        holder.price.setText(listitem.get(position).getPrice());

        holder.prev_price.setText(listitem.get(position).getPrice());

        //strike through
        holder.prev_price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        if(listitem.get(position).getDiscount()){
            holder.discount.setVisibility(View.VISIBLE);
            holder.discount.setText(listitem.get(position).getDiscount_percent());

            holder.prev_price.setVisibility(View.VISIBLE);
        }else {
            holder.discount.setVisibility(View.GONE);
            //holder.discount.setText(listitem.get(position).getDiscount_percent());
            holder.prev_price.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnItemClickListener onItemClickListener;
        ImageView image;
        TextView name,price,prev_price,discount,quantity;

        public modelViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener= onItemClickListener;

            name= itemView.findViewById(R.id.text_name);
            price= itemView.findViewById(R.id.text_price);
            prev_price= itemView.findViewById(R.id.text_price_previous);
            discount= itemView.findViewById(R.id.button_discount);
            quantity= itemView.findViewById(R.id.text_quantity);
            image= itemView.findViewById(R.id.image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}
