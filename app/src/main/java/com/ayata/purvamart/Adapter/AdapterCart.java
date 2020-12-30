package com.ayata.purvamart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayata.purvamart.Model.ModelItem;
import com.ayata.purvamart.R;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        setTotalPriceInModel();
        setTotalInFragment();
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
        String price = modelItem.getPrice();
        String discount = modelItem.getDiscount_percent();
        Double pri = Double.valueOf(price);
        Integer count = modelItem.getCount();
        String name = modelItem.getName();
        Double totalprice = pri * Double.valueOf(count);
        //bind data
        holder.textName.setText(name);
        holder.textPrice.setText(price + "/kg");
        holder.textCount.setText(count.toString());
        holder.textTotalPrice.setText(totalprice.toString());
        //handle discount
        if (modelItem.getDiscount()) {
            holder.textDiscount.setVisibility(View.VISIBLE);
            holder.textDiscount.setText(discount);
        } else {
            holder.textDiscount.setVisibility(View.GONE);
        }
        Glide.with(context).load("http://"+listitem.get(position).getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnCartItemClickListener onCategoryClickListener;
        ImageView image;
        TextView textName, textPrice, textCount, textTotalPrice,textDiscount;
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
                        Double c = Double.valueOf(textCount.getText().toString());
//                        textTotalPrice.setText(listitem.get(getAdapterPosition()).getBasePrice() * c + "");
                        setTotalInFragment();
                    }
                }
            });
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getAdapterPosition()!=-1) {
                        onCartClickListener.onMinusClick(listitem.get(getAdapterPosition()), getAdapterPosition());
                        Double c = Double.valueOf(textCount.getText().toString());
//                        textTotalPrice.setText(listitem.get(getAdapterPosition()).getBasePrice() * c + "");
                        setTotalInFragment();
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
            total = total + listitem.get(i).getTotalPrice();
        }
        onCartClickListener.onPriceTotalListener(total);
    }

    void setTotalPriceInModel() {
        for (int i = 0; i < listitem.size(); i++) {
            Double totalprice = getPriceOnly(listitem.get(i).getPrice());
            listitem.get(i).setTotalPrice(totalprice);
        }
    }

    public List<ModelItem> getAllDataFromCart() {
        return listitem;
    }
}
