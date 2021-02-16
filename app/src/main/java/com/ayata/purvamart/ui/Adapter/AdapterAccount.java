package com.ayata.purvamart.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelAccount;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterAccount extends RecyclerView.Adapter<AdapterAccount.modelViewHolder> {

    private Context context;
    private List<ModelAccount> listitem;
    private OnLayoutClickListener onLayoutClickListener;
    Integer row_index = -1;

    public AdapterAccount(Context context, List<ModelAccount> listitem, OnLayoutClickListener onLayoutClickListener) {
        this.context = context;
        this.listitem = listitem;
        this.onLayoutClickListener = onLayoutClickListener;
    }

    @NonNull
    @Override
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_account, parent, false);
        return new modelViewHolder(view, onLayoutClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull modelViewHolder holder, int position) {

        holder.title.setText(listitem.get(position).getTitle());
        holder.body.setText(listitem.get(position).getBody());
        if (row_index == position) {
            holder.title.setTextColor(context.getResources().getColor(R.color.colorGrayDrawable));
        } else {
            holder.title.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnLayoutClickListener onLayoutClickListener;

        TextView title, body;

        public modelViewHolder(@NonNull View itemView, OnLayoutClickListener onLayoutClickListener) {
            super(itemView);
            this.onLayoutClickListener = onLayoutClickListener;
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.text1);
            body = itemView.findViewById(R.id.text2);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                //to show text grayed out first then click
                title.setSelected(true);
                onLayoutClickListener.onLayoutClick(getAdapterPosition());
                row_index = position;
                notifyDataSetChanged();
            }
        }
    }

    public interface OnLayoutClickListener {
        void onLayoutClick(int position);
    }
}
