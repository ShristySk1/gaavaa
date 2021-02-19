package com.ayata.purvamart.ui.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Constants.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ViewPagerAdapterProduct extends RecyclerView.Adapter<ViewPagerAdapterProduct.myViewHolder> {
    private static final String TAG = "ViewPagerAdapterProduct";
    List<String> images;


    public ViewPagerAdapterProduct(List<String> images) {
        this.images = images;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_carousel_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Log.d("ViewPageraapter", "onBindViewHolder: " + position);
        Glide.with(holder.ivImage).load(images.get(position))
                .transition(DrawableTransitionOptions.withCrossFade()).
                fallback(Constants.FALLBACKIMAGE).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + images.size());
        return images.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }
}
