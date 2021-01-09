package com.ayata.purvamart.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayata.purvamart.data.Constants.Constants;
import com.ayata.purvamart.data.Model.ModelItem;
import com.ayata.purvamart.R;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.modelViewHolder> {
    private Context context;
    private List<ModelItem> listitem;
    private OnCartItemClickListener onCartClickListener;

    public AdapterCart(Context context, List<ModelItem> listitem, OnCartItemClickListener onCategoryClickListener) {
        this.context = context;
        this.listitem = listitem;
        this.onCartClickListener = onCategoryClickListener;
    }

    @NonNull
    @Override
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_cart, parent, false);
        return new modelViewHolder(view, onCartClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull modelViewHolder holder, int position) {
        ModelItem modelItem = listitem.get(position);
        String price = modelItem.getPrev_price();
        String discount = modelItem.getDiscount_percent();
        Integer count = modelItem.getCount();
        String name = modelItem.getName();
        String totalprice = String.valueOf(modelItem.getPrice());
        //bind data
        holder.textName.setText(name);
        holder.textPrice.setText(price + "/"+modelItem.getUnit());
        holder.textCount.setText(count.toString());
        holder.textTotalPrice.setText(totalprice);
        //handle discount
        if (modelItem.getDiscount()) {
            holder.textDiscount.setVisibility(View.VISIBLE);
            holder.textDiscount.setText(discount);
        } else {
            holder.textDiscount.setVisibility(View.GONE);
        }
        Glide.with(context).load(listitem.get(position).getImage()).placeholder(Constants.PLACEHOLDER).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnCartItemClickListener onCategoryClickListener;
        ImageView image;
        TextView textName, textPrice, textCount, textTotalPrice, textDiscount;
        ImageButton add, minus;

        public modelViewHolder(@NonNull View itemView, OnCartItemClickListener onCategoryClickListener) {
            super(itemView);
            this.onCategoryClickListener = onCategoryClickListener;
            image = itemView.findViewById(R.id.image_cart_productImage);
            textName = itemView.findViewById(R.id.text_cart_productName);
            textTotalPrice = itemView.findViewById(R.id.text_cart_productPrice);
            textPrice = itemView.findViewById(R.id.text_cart_pricePerKg);
            textCount = itemView.findViewById(R.id.text_cart_productQuantity);
            textDiscount = itemView.findViewById(R.id.text_cart_productDiscount);
            add = itemView.findViewById(R.id.imageButton_add);
            minus = itemView.findViewById(R.id.imageButton_minus);
            itemView.setOnClickListener(this);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getAdapterPosition() != -1) {
                        onCartClickListener.onAddClick(listitem.get(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getAdapterPosition() != -1) {
                        onCartClickListener.onMinusClick(listitem.get(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });

        }

        @Override
        public void onClick(View view) {
            onCategoryClickListener.onCartItemClick(getAdapterPosition());
        }
    }

    public interface OnCartItemClickListener {
        void onAddClick(ModelItem modelItem, int position);

        void onMinusClick(ModelItem modelItem, int position);

        void onCartItemClick(int position);
    }

    public List<ModelItem> getAllDataFromCart() {
        return listitem;
    }
}
