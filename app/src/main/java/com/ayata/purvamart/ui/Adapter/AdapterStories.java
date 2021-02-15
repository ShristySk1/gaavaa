package com.ayata.purvamart.ui.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Constants.Constants;
import com.ayata.purvamart.data.Model.Stories;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterStories extends RecyclerView.Adapter<AdapterStories.modelViewHolder> {
    private static setOnStoryListener listener;
    private String TAG = "Stories";
    private Context context;
    private List<Stories> listitem;

    private static int count = 0;

    public AdapterStories(Context context, List<Stories> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @NonNull
    @Override
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_story, parent, false);
        return new modelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull modelViewHolder holder, int position) {

//        holder.title.setText(listitem.get(position).getTitle());
        Glide.with(context).load(listitem.get(position).getImageUrl()).placeholder(Constants.PLACEHOLDER).fallback(Constants.FALLBACKIMAGE).into(holder.image);
        Log.d(TAG, "onBindViewHolder: " + listitem.get(position).getImageUrl());

    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;

        public modelViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ivStoryImage);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onStoryClick(getAdapterPosition(), listitem.get(getAdapterPosition()).getImageUrl());
        }
    }

    public static void setAddListener(setOnStoryListener listeners) {
        listener = listeners;
    }

    public interface setOnStoryListener {
        void onStoryClick(int position, String url);
    }
}
