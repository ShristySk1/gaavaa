package com.ayata.purvamart.ui.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayata.purvamart.data.Constants.Constants;
import com.ayata.purvamart.data.Model.ModelItem;
import com.ayata.purvamart.R;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdapterOrderSummary extends RecyclerView.Adapter<AdapterOrderSummary.modelViewHolder> {
    private Context context;
    private List<ModelItem> listitem;


    public AdapterOrderSummary(Context context, List<ModelItem> listitem) {
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
        ModelItem modelItem = listitem.get(position);
        String price = modelItem.getPrice();
        String discount = modelItem.getDiscount_percent();

        Integer count=modelItem.getCount();
        String name = modelItem.getName();
        //bind data
        holder.textName.setText(name);
        holder.textPrice.setText(price + "/kg");
        holder.textCount.setText(count.toString());

        Glide.with(context).load(listitem.get(position).getImage()).placeholder(Constants.PLACEHOLDER).into(holder.image);

    }

    @Override
    public int getItemCount() {
        Log.d("adaptertest", "getItemCount: "+listitem.size());
        return listitem.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView image;
        TextView textName, textPrice, textCount;
        ImageButton add, minus;
        public modelViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_cart_productImage);
            textName = itemView.findViewById(R.id.text_cart_productName);
//            textTotalPrice = itemView.findViewById(R.id.text_cart_productPrice);
            textPrice = itemView.findViewById(R.id.text_cart_pricePerKg);
            textCount = itemView.findViewById(R.id.text_cart_productQuantity);
//            textDiscount = itemView.findViewById(R.id.text_cart_productDiscount);
            add = itemView.findViewById(R.id.imageButton_add);
            minus = itemView.findViewById(R.id.imageButton_minus);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            onItemClickListener.onCartItemClick(getAdapterPosition());
        }
    }


    public List<ModelItem> getOrderList(){
        return listitem;
    }
}
