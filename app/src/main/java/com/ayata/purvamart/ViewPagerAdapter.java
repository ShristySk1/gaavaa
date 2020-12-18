package com.ayata.purvamart;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.myViewHolder> {
    int[] images;


    public ViewPagerAdapter(int[] images) {
        this.images = images;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_carousel_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
            Log.d("ViewPageraapter", "onBindViewHolder: "+position);
            Glide.with(holder.ivImage).load(images[position]).into(holder.ivImage);



    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }
}
