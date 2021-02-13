package com.ayata.purvamart.ui.Fragment.account.promos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayata.purvamart.R;
import com.vipulasri.ticketview.TicketView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterPromos extends RecyclerView.Adapter<AdapterPromos.MyViewHolder> {

    private Context context;
    private List<ModelPromos> listitem;
    private OnPromosClickListener onPromosClickListener;

    public AdapterPromos(Context context, List<ModelPromos> listitem, OnPromosClickListener onPromosClickListener) {
        this.context = context;
        this.listitem = listitem;
        this.onPromosClickListener = onPromosClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_promos, parent, false);
        return new MyViewHolder(view, onPromosClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ModelPromos modelPromos = listitem.get(position);
        holder.ticketView.setBackgroundColor(modelPromos.getImage());
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, desp, date;
        ImageView image;
        TicketView ticketView;
        OnPromosClickListener onPromosClickListener;

        public MyViewHolder(@NonNull View itemView, OnPromosClickListener onPromosClickListener) {
            super(itemView);

            this.onPromosClickListener = onPromosClickListener;
            ticketView = itemView.findViewById(R.id.ticketView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPromosClickListener.onPromoClick(getAdapterPosition(), listitem.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnPromosClickListener {
        void onPromoClick(int position, ModelPromos modelNotification);
    }
}
