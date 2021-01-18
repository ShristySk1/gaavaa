package com.ayata.purvamart.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayata.purvamart.data.Constants.Constants;
import com.ayata.purvamart.data.Model.ModelPayment;
import com.ayata.purvamart.R;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterPayment extends RecyclerView.Adapter<AdapterPayment.MyViewHolder> {

    private Context context;
    private List<ModelPayment> listitem;
    private static OnPayMethodClickListener onPayMethodClickListener;
    Integer row_index = 0;

    public AdapterPayment(Context context, List<ModelPayment> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_payment2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelPayment modelPayment = listitem.get(position);
        String paymentName = modelPayment.getPayment_name();
        int paymentImage = modelPayment.getPayment_image();
        holder.tvPaymentMethod.setText(paymentName);
        Glide.with(context).load(paymentImage).placeholder(Constants.PLACEHOLDER).fallback(Constants.FALLBACKIMAGE).into(holder.ivPaymentImage);
        if (row_index == position) {
            holder.recycler_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.background_green_border2));
        } else {
            holder.recycler_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.background_green_border3));
        }
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvPaymentMethod;
        ImageView ivPaymentImage;
        ConstraintLayout recycler_main;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recycler_main = itemView.findViewById(R.id.recycler_main);
            tvPaymentMethod = itemView.findViewById(R.id.tvPaymentMethod);
            ivPaymentImage = itemView.findViewById(R.id.ivPaymentImage);
            recycler_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onPayMethodClickListener.onPayMethodClick(position, listitem.get(position));
                        row_index = position;
                        notifyDataSetChanged();
                    }
                }
            });

        }
    }

    public interface OnPayMethodClickListener {
        void onPayMethodClick(int position, ModelPayment modelPayment);
    }

    public static void setListener(OnPayMethodClickListener listeners) {
        onPayMethodClickListener = listeners;
    }
}
