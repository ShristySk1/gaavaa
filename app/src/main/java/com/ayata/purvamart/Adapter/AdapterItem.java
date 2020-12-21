package com.ayata.purvamart.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayata.purvamart.Model.ModelItem;
import com.ayata.purvamart.R;
import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;

public class AdapterItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private Context context;
    private List<ModelItem> listitem;
    private OnItemClickListener onItemClickListener;

    public AdapterItem(Context context, List<ModelItem> listitem, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.listitem = listitem;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
            return new modelViewHolder(view,onItemClickListener);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_load_progressbar, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof modelViewHolder) {
            populateItemRows((modelViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return listitem == null ? 0 : listitem.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return listitem.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    public class modelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnItemClickListener onItemClickListener;
        ImageView image;
        TextView name,price,prev_price,discount,quantity;


        public modelViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener= onItemClickListener;

            name= itemView.findViewById(R.id.text_name);
            price= itemView.findViewById(R.id.text_price);
            prev_price= itemView.findViewById(R.id.text_price_previous);
            discount= itemView.findViewById(R.id.button_discount);
            quantity= itemView.findViewById(R.id.text_quantity);
            image= itemView.findViewById(R.id.image);


//            RotateAnimation rotate= (RotateAnimation) AnimationUtils.loadAnimation(context,R.anim.rotate_textview);
//            discount.setAnimation(rotate);
//            discount.animate().translationY(-215).setDuration(0);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    private void populateItemRows(modelViewHolder holder, int position) {

                holder.name.setText(listitem.get(position).getName());
        holder.quantity.setText(listitem.get(position).getQuantity());
        Glide.with(context).load(listitem.get(position).getImage()).into(holder.image);
        holder.price.setText(listitem.get(position).getPrice());

        holder.prev_price.setText(listitem.get(position).getPrice());

        //strike through
        holder.prev_price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        if(listitem.get(position).getDiscount()){
            holder.discount.setVisibility(View.VISIBLE);
            holder.discount.setText(listitem.get(position).getDiscount_percent());

            holder.prev_price.setVisibility(View.VISIBLE);
        }else {
            holder.discount.setVisibility(View.GONE);
            //holder.discount.setText(listitem.get(position).getDiscount_percent());
            holder.prev_price.setVisibility(View.GONE);
        }


    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}
