package com.ayata.purvamart.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.R;
import com.ayata.purvamart.data.network.response.Slider;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterAd extends RecyclerView.Adapter<AdapterAd.modelViewHolder> {
    private static setOnAddListener listener;
    private String TAG ="AdapterAd";
    private Context context;
    private List<Slider> listitem;

    private static int count = 0;

    public AdapterAd(Context context, List<Slider> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @NonNull
    @Override
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_ad, parent, false);
        return new modelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull modelViewHolder holder, int position) {

        holder.title.setText(listitem.get(position).getTitle());
        Glide.with(context).load("http://142.93.221.85/media/"+listitem.get(position).getPhotos()).into(holder.image);
        Log.d(TAG, "onBindViewHolder: "+"http://"+listitem.get(position).getPhotos());
        Log.d(TAG, "onBindViewHolder: ");

        switch (count) {
            case 0:
                holder.background.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAd1));
                holder.button.setBackground(ContextCompat.getDrawable(context, R.drawable.button_yellow));
                count = 1;
                break;

            case 1:
                holder.background.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAd2));
                holder.button.setBackground(ContextCompat.getDrawable(context, R.drawable.button_yellow));
                count = 0;
                break;

//            case 2:
//                holder.background.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPurple));
//                holder.button.setBackground(ContextCompat.getDrawable(context,R.drawable.button_green));
//                count=0;
//                break;

            default:
                holder.background.setBackgroundColor(ContextCompat.getColor(context, R.color.colorLightGreen));
                holder.button.setBackground(ContextCompat.getDrawable(context, R.drawable.button_yellow));
                count = 1;
                break;
        }

    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView title, top_text;
        Button button;
        RelativeLayout background;

        public modelViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.text_discount);
            button = itemView.findViewById(R.id.button);
            background = itemView.findViewById(R.id.background);
            top_text = itemView.findViewById(R.id.text1);

            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, listitem.get(getAdapterPosition()).getTitle(), Toast.LENGTH_SHORT).show();
            listener.onAddClick(getAdapterPosition(), listitem.get(getAdapterPosition()).getUrl());
        }
    }

    public static void setAddListener(setOnAddListener listeners) {
        listener = listeners;
    }

    public interface setOnAddListener {
        void onAddClick(int position, String url);
    }
}
