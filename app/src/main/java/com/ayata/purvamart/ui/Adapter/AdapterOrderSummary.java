package com.ayata.purvamart.ui.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Constants.Constants;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterOrderSummary extends RecyclerView.Adapter<AdapterOrderSummary.modelViewHolder> {
    private Context context;
    private List<ProductDetail> listitem;


    public AdapterOrderSummary(Context context, List<ProductDetail> listitem) {
        this.context = context;
        this.listitem = listitem;


    }

    @NonNull
    @Override
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_order_summary, parent, false);
        return new modelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull modelViewHolder holder, int position) {
        ProductDetail modelItem = listitem.get(position);
        String price = String.valueOf(modelItem.getProductPrice());
        String discount = modelItem.getProductDiscount();
        String count = modelItem.getProduct_quantity();
        String subTotal = modelItem.getTotal_price();
        Log.d("myproductcoynt", "onBindViewHolder: countinadapter" + count);
        String name = modelItem.getName();
        //bind data
        holder.textName.setText(name);
        holder.textPrice.setText(price + "/" + modelItem.getUnit());
        holder.textSubTotal.setText(subTotal);
        holder.textCount.setText(count);
        //handle discount
//        if (modelItem.getDiscount()) {
//            holder.text_summary_productDiscount.setVisibility(View.VISIBLE);
//            holder.text_summary_productDiscount.setText(discount);
//        } else {
//            holder.text_summary_productDiscount.setVisibility(View.GONE);
//        }
        Glide.with(context).load(listitem.get(position).getImage()).placeholder(Constants.PLACEHOLDER).fallback(Constants.FALLBACKIMAGE).into(holder.image);
    }

    @Override
    public int getItemCount() {
        Log.d("adaptertest", "getItemCount: " + listitem.size());
        return listitem.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView image;
        TextView textName, textPrice, textCount;
        ImageButton add, minus;
        TextView text_summary_productDiscount, textSubTotal;

        public modelViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_cart_productImage);
            textName = itemView.findViewById(R.id.text_cart_productName);
            textPrice = itemView.findViewById(R.id.text_cart_pricePerKg);
            textCount = itemView.findViewById(R.id.text_cart_productQuantity);
            textSubTotal = itemView.findViewById(R.id.text_subtotal);
            text_summary_productDiscount = itemView.findViewById(R.id.text_summary_productDiscount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            onItemClickListener.onCartItemClick(getAdapterPosition());
        }
    }
}
