package com.ayata.purvamart.ui.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ayata.purvamart.data.Constants.Constants;
import com.ayata.purvamart.data.Model.ModelCategory;
import com.ayata.purvamart.R;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.modelViewHolder> {

    private static int count = 0;
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

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_category, parent, false);
        return new modelViewHolder(view, onCategoryClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull modelViewHolder holder, int position) {
        holder.text.setText(listitem.get(position).getName());
        Glide.with(context).load(listitem.get(position).getImage()).placeholder(Constants.PLACEHOLDER).into(holder.image);
        Log.d("adaptercategory", "onBindViewHolder: " + listitem.get(position).getImage());
        switch (count) {
            case 0:
                holder.text.setTextColor(ContextCompat.getColor(context, R.color.colorCat_text1));
                holder.background.setBackground(ContextCompat.getDrawable(context, R.drawable.background_cat_red));
                count = 1;
                break;

            case 1:
                holder.text.setTextColor(ContextCompat.getColor(context, R.color.colorCat_text2));
                holder.background.setBackground(ContextCompat.getDrawable(context, R.drawable.background_cat_green));
                count = 2;
                break;

            case 2:
                holder.text.setTextColor(ContextCompat.getColor(context, R.color.colorCat_text3));
                holder.background.setBackground(ContextCompat.getDrawable(context, R.drawable.background_cat_grey));
                count = 0;
                break;

            default:
                holder.text.setTextColor(ContextCompat.getColor(context, R.color.colorCat_text3));
                holder.background.setBackground(ContextCompat.getDrawable(context, R.drawable.background_cat_grey));
                count = 1;
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnCategoryClickListener onCategoryClickListener;
        ImageView image;
        TextView text;
        LinearLayout background;

        public modelViewHolder(@NonNull View itemView, OnCategoryClickListener onCategoryClickListener) {
            super(itemView);
            this.onCategoryClickListener = onCategoryClickListener;
            text = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
            background = itemView.findViewById(R.id.background);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onCategoryClickListener.onCategoryClick(listitem.get(getAdapterPosition()));
        }
    }

    public interface OnCategoryClickListener {

        void onCategoryClick(ModelCategory selectedItem);
    }
}
