package com.ayata.purvamart.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayata.purvamart.Model.ModelItem;
import com.ayata.purvamart.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
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
        String quantity = "" + 1;
        Double totalprice = modelItem.getTotalPrice();
        String name = modelItem.getName();
        holder.textName.setText(name);
        holder.textPrice.setText(price + "/kg");
        holder.textQuantity.setText("" + 1);
        holder.textTotalPrice.setText("Rs. " + totalprice.toString());
        if (modelItem.getDiscount()) {
            holder.textDiscount.setText(discount);
        } else {
            holder.textDiscount.setVisibility(View.GONE);
        }
        Glide.with(context).load(listitem.get(position).getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnCartItemClickListener onCategoryClickListener;
        ImageView image;
        TextView textName, textTotalPrice, textPrice, textQuantity, textDiscount;
        ImageButton add, minus;
        int quantity;
        Double price;
        Double totalPrice;

        public modelViewHolder(@NonNull View itemView, OnCartItemClickListener onCategoryClickListener) {
            super(itemView);
            this.onCategoryClickListener = onCategoryClickListener;
            image = itemView.findViewById(R.id.image_cart_productImage);
            textName = itemView.findViewById(R.id.text_cart_productName);
            textTotalPrice = itemView.findViewById(R.id.text_cart_productPrice);
            textPrice = itemView.findViewById(R.id.text_cart_pricePerKg);
            textQuantity = itemView.findViewById(R.id.text_cart_productQuantity);
            textDiscount = itemView.findViewById(R.id.text_cart_productDiscount);
            add = itemView.findViewById(R.id.imageButton_add);
            minus = itemView.findViewById(R.id.imageButton_minus);
            itemView.setOnClickListener(this);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quantity = Integer.parseInt(textQuantity.getText().toString());
                    quantity++;
                    textQuantity.setText("" + quantity);
                    try {
                        price = getPriceOnly(textPrice.getText().toString());
                        totalPrice = calculatePrice(price, quantity);
                        textTotalPrice.setText("Rs. " + totalPrice);
                        listitem.get(getAdapterPosition()).setTotalPrice(totalPrice);
                        setTotalInFragment();
                    } catch (Exception e) {
                    }
                }
            });
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quantity = Integer.parseInt(textQuantity.getText().toString());
                    if (quantity > 1) {
                        quantity--;
                        textQuantity.setText("" + quantity);
                        try {
                            price = getPriceOnly(textPrice.getText().toString());
                            totalPrice = calculatePrice(price, quantity);
                            textTotalPrice.setText("Rs. " + totalPrice);
                            listitem.get(getAdapterPosition()).setTotalPrice(totalPrice);
                            setTotalInFragment();
                        } catch (Exception e) {
                        }
                    } else {
                        listitem.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
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

    private double calculatePrice(Double price, int quantity) {
        return price * quantity;
    }

    public interface OnCartItemClickListener {
        void onPriceTotalListener(Double total);

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
    void setTotalPriceInModel(){
        for (int i = 0; i < listitem.size(); i++) {
            Double totalprice = getPriceOnly(listitem.get(i).getPrice());
            listitem.get(i).setTotalPrice(totalprice);
        }
    }
}
