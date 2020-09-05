package com.ayata.purvamart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayata.purvamart.Model.ModelAccount;
import com.ayata.purvamart.R;

import java.util.List;

public class AdapterAccount extends RecyclerView.Adapter<AdapterAccount.modelViewHolder> {

    private Context context;
    private List<ModelAccount> listitem;
    private OnLayoutClickListener onLayoutClickListener;

    public AdapterAccount(Context context, List<ModelAccount> listitem, OnLayoutClickListener onLayoutClickListener) {
        this.context = context;
        this.listitem = listitem;
        this.onLayoutClickListener = onLayoutClickListener;
    }

    @NonNull
    @Override
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_account,parent,false);
        return new modelViewHolder(view,onLayoutClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull modelViewHolder holder, int position) {

        holder.title.setText(listitem.get(position).getTitle());
        holder.body.setText(listitem.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnLayoutClickListener onLayoutClickListener;

        TextView title,body;

        public modelViewHolder(@NonNull View itemView, OnLayoutClickListener onLayoutClickListener) {
            super(itemView);
            this.onLayoutClickListener=onLayoutClickListener;
            itemView.setOnClickListener(this);
            title=itemView.findViewById(R.id.text1);
            body= itemView.findViewById(R.id.text2);
        }

        @Override
        public void onClick(View view) {
            onLayoutClickListener.onLayoutClick(getAdapterPosition());
        }
    }

    public interface OnLayoutClickListener{
        void onLayoutClick(int position);
    }
}
