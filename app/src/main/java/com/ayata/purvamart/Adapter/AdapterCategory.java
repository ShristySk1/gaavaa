package com.ayata.purvamart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayata.purvamart.Model.ModelCategory;
import com.ayata.purvamart.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.modelViewHolder> {

    private Context context;
    private List<ModelCategory> listitem;
    private OnCategoryClickListener onCategoryClickListener;

    public AdapterCategory(Context context, List<ModelCategory> listitem, OnCategoryClickListener onCategoryClickListener) {
        this.context = context;
        this.listitem = listitem;
        this.onCategoryClickListener = onCategoryClickListener;
    }

    @NonNull
    @Override
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.recycler_category,parent,false);
        return new modelViewHolder(view,onCategoryClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull modelViewHolder holder, int position) {
        holder.text.setText(listitem.get(position).getName());
        Glide.with(context).load(listitem.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        OnCategoryClickListener onCategoryClickListener;
        ImageView image;
        TextView text;

        public modelViewHolder(@NonNull View itemView, OnCategoryClickListener onCategoryClickListener) {
            super(itemView);
            this.onCategoryClickListener= onCategoryClickListener;
            text= itemView.findViewById(R.id.title);
            image= itemView.findViewById(R.id.image);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onCategoryClickListener.onCategoryClick(getAdapterPosition());
        }
    }

    public interface OnCategoryClickListener{

        void onCategoryClick(int position);
    }
}
