package com.ayata.purvamart.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ayata.purvamart.data.Model.ModelCategory;
import com.ayata.purvamart.R;

import java.util.List;

public class AdapterCategoryTop extends RecyclerView.Adapter<AdapterCategoryTop.MyViewHolder> {

    private Context context;
    private List<ModelCategory> listitem;
    private OnCategoryClickListener onCategoryClickListener;

    public AdapterCategoryTop(Context context, List<ModelCategory> listitem, OnCategoryClickListener onCategoryClickListener) {
        this.context = context;
        this.listitem = listitem;
        this.onCategoryClickListener = onCategoryClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_category_top,parent,false);
        return new MyViewHolder(view,onCategoryClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.text.setText(listitem.get(position).getName());

        if(listitem.get(position).getSelected()){
            holder.text.setTextColor(ContextCompat.getColor(context,R.color.colorBlack));
            holder.line.setBackgroundColor(ContextCompat.getColor(context,R.color.colorBlack));
        }else{
            holder.text.setTextColor(ContextCompat.getColor(context,R.color.colorGray));
            holder.line.setBackgroundColor(ContextCompat.getColor(context,R.color.colorGrayLight));
        }
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView text;
        View line;
        OnCategoryClickListener onCategoryClickListener;

        public MyViewHolder(@NonNull View itemView,OnCategoryClickListener onCategoryClickListener) {
            super(itemView);

            this.onCategoryClickListener=onCategoryClickListener;

            text= itemView.findViewById(R.id.text);
            line= itemView.findViewById(R.id.line);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCategoryClickListener.onCategoryClick(listitem.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnCategoryClickListener{
        void onCategoryClick(ModelCategory selectedItem);
    }
}
