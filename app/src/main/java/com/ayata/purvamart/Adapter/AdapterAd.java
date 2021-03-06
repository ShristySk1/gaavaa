package com.ayata.purvamart.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ayata.purvamart.Model.ModelAd;
import com.ayata.purvamart.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterAd extends RecyclerView.Adapter<AdapterAd.modelViewHolder> {

    private Context context;
    private List<ModelAd> listitem;

    private static int count=0;

    public AdapterAd(Context context, List<ModelAd> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @NonNull
    @Override
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_ad,parent,false);
        return new modelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull modelViewHolder holder, int position) {

        holder.title.setText(listitem.get(position).getTitle());
        Glide.with(context).load(listitem.get(position).getImage()).into(holder.image);

        switch (count){
            case 0:
                holder.background.setBackgroundColor(ContextCompat.getColor(context,R.color.colorLime));
                holder.button.setBackground(ContextCompat.getDrawable(context,R.drawable.button_orange));
                count=1;
                break;

            case 1:
                holder.background.setBackgroundColor(ContextCompat.getColor(context,R.color.colorOrange));
                holder.button.setBackground(ContextCompat.getDrawable(context,R.drawable.button_purple));
                count=2;
                break;

            case 2:
                holder.background.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPurple));
                holder.button.setBackground(ContextCompat.getDrawable(context,R.drawable.button_green));
                count=0;
                break;

            default:
                holder.background.setBackgroundColor(ContextCompat.getColor(context,R.color.colorGreen));
                holder.button.setBackground(ContextCompat.getDrawable(context,R.drawable.button_orange));
                count=1;
                break;
        }

    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView image;
        TextView title;
        Button button;
        RelativeLayout background;

        public modelViewHolder(@NonNull View itemView) {
            super(itemView);

            image= itemView.findViewById(R.id.image);
            title= itemView.findViewById(R.id.text_discount);
            button= itemView.findViewById(R.id.button);
            background= itemView.findViewById(R.id.background);

            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, listitem.get(getAdapterPosition()).getTitle(), Toast.LENGTH_SHORT).show();
        }
    }
}
