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
    private OnItemClickListener onItemClickListener;

    public AdapterOrderSummary(Context context, List<ModelItem> listitem, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.listitem = listitem;
        this.onItemClickListener = onItemClickListener;
            setTotalPriceInModel();
            setTotalInFragment();

    }

    @NonNull
    @Override
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_order_summary, parent, false);
        return new modelViewHolder(view, onItemClickListener);
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
//        holder.textTotalPrice.setText("Rs. " + totalprice.toString());
        //handle discount
//        if (modelItem.getDiscount()) {
//            holder.textDiscount.setVisibility(View.VISIBLE);
//            holder.textDiscount.setText(discount);
//        } else {
//            holder.textDiscount.setVisibility(View.GONE);
//        }
        Glide.with(context).load(listitem.get(position).getImage()).placeholder(Constants.PLACEHOLDER).into(holder.image);

    }

    @Override
    public int getItemCount() {
        Log.d("adaptertest", "getItemCount: "+listitem.size());
        return listitem.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnItemClickListener onItemClickListener;
        ImageView image;
        TextView textName, textPrice, textCount;
        ImageButton add, minus;
        public modelViewHolder(@NonNull View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            image = itemView.findViewById(R.id.image_cart_productImage);
            textName = itemView.findViewById(R.id.text_cart_productName);
//            textTotalPrice = itemView.findViewById(R.id.text_cart_productPrice);
            textPrice = itemView.findViewById(R.id.text_cart_pricePerKg);
            textCount = itemView.findViewById(R.id.text_cart_productQuantity);
//            textDiscount = itemView.findViewById(R.id.text_cart_productDiscount);
            add = itemView.findViewById(R.id.imageButton_add);
            minus = itemView.findViewById(R.id.imageButton_minus);
            setTotalInFragment();
            itemView.setOnClickListener(this);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onAddClick(listitem.get(getAdapterPosition()),getAdapterPosition());
                    Double c = Double.valueOf(textCount.getText().toString());
//                    textPrice.setText(listitem.get(getAdapterPosition()).getBasePrice() * c + "");
                    setTotalInFragment();
                }
            });
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   onItemClickListener.onMinusClick(listitem.get(getAdapterPosition()),getAdapterPosition());
                    Double c = Double.valueOf(textCount.getText().toString());
//                    textPrice.setText(listitem.get(getAdapterPosition()).getBasePrice() * c + "");
                   setTotalInFragment();
                }
            });

        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onCartItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void onPriceTotalListener(Double total);

        void onAddClick(ModelItem modelItem, int position);

        void onMinusClick(ModelItem modelItem, int position);

        void onCartItemClick(int position);
    }

    //Rs 100.00 to 100.00
    Double getPriceOnly(String textPrice) {
        Pattern PRICE_PATTERN = Pattern.compile("(\\d*\\.)?\\d+");
        Matcher matcher = PRICE_PATTERN.matcher(textPrice);
        while (matcher.find()) {
            return Double.parseDouble(matcher.group());
        }
        return 1.00;
    }

    public void setTotalInFragment() {
        double total = 0;
        for (int i = 0; i < listitem.size(); i++) {
//            total = total + listitem.get(i).getTotalPrice();
        }
        onItemClickListener.onPriceTotalListener(total);
    }

    void setTotalPriceInModel() {
        for (int i = 0; i < listitem.size(); i++) {
            Double totalprice = getPriceOnly(listitem.get(i).getPrice());
//            listitem.get(i).setTotalPrice(totalprice);
        }
    }
    public List<ModelItem> getOrderList(){
        return listitem;
    }
}
