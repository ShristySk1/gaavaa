package com.ayata.purvamart.ui.Fragment.shop.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayata.purvamart.R;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.MyViewHolder> {

    private Context context;
    private List<ModelNotification> listitem;
    private OnNotificationClickListener onNotificationClickListener;

    public AdapterNotification(Context context, List<ModelNotification> listitem, OnNotificationClickListener onNotificationClickListener) {
        this.context = context;
        this.listitem = listitem;
        this.onNotificationClickListener = onNotificationClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_notification,parent,false);
        return new MyViewHolder(view,onNotificationClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(listitem.get(position).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String newstring = new SimpleDateFormat("dd MMMM yyyy").format(date);

        holder.title.setText(listitem.get(position).getTitle());
        holder.date.setText(newstring);
        holder.desp.setText(listitem.get(position).getDesp());
//        Glide.with(context).load(listitem.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView title,desp,date;
        ImageView image;
        OnNotificationClickListener onNotificationClickListener;

        public MyViewHolder(@NonNull View itemView, OnNotificationClickListener onNotificationClickListener) {
            super(itemView);

            title= itemView.findViewById(R.id.title);
            desp= itemView.findViewById(R.id.desp);
            date= itemView.findViewById(R.id.date);
            image= itemView.findViewById(R.id.image);

            this.onNotificationClickListener=onNotificationClickListener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNotificationClickListener.onNotificationClick(getAdapterPosition(),listitem.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnNotificationClickListener{

        void onNotificationClick(int position,ModelNotification modelNotification);
    }
}
