package com.ayata.purvamart.ui.Fragment.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayata.purvamart.R;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapterBefore extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static onProCatClickListner listner;
    Context context;
    static List<ModelSearchBefore> listitem;
    private static final int TYPE_A = 1;//title
    private static final int TYPE_B = 2;//product
    private static final int TYPE_C = 3;//category


    public SearchAdapterBefore(Context context, List<ModelSearchBefore> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case TYPE_A:
                View viewA = inflater.inflate(R.layout.recycler_search_category_title, parent, false);
                viewHolder = new modelViewHolderTypeA(viewA);
                break;
            case TYPE_B:
                View viewB = inflater.inflate(R.layout.recycler_search_product_before, parent, false);
                viewHolder = new modelViewHolderTypeB(viewB);
                break;
            case TYPE_C:
                View viewC = inflater.inflate(R.layout.recycler_search_product_before, parent, false);
                viewHolder = new modelViewHolderTypeB(viewC);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case TYPE_A:
                ((modelViewHolderTypeA) holder).title.setText(listitem.get(position).getTitle());
                break;
            case TYPE_B:
                ((modelViewHolderTypeB) holder).title.setText(listitem.get(position).getTitle());
                Glide.with(context).load(listitem.get(position).getImage()).into(((modelViewHolderTypeB) holder).image);
                break;
            case TYPE_C:
                ((modelViewHolderTypeB) holder).title.setText(listitem.get(position).getTitle());
                Glide.with(context).load(listitem.get(position).getImage()).into(((modelViewHolderTypeB) holder).image);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (listitem.get(position).getView_type() == 1) {
//            return TYPE_A;
//        } else if (listitem.get(position).getView_type() == 2) {
//            return TYPE_B;
//        } else if (listitem.get(position).getView_type() == 3) {
//            return TYPE_C;
//        }
//
        switch (listitem.get(position).getType()){
            case TITLE:
                return TYPE_A;
            case PRODUCT:
                return TYPE_B;
            case CATEGORY:
                return  TYPE_C;
        }
        throw new RuntimeException("error");
        //return super.getItemViewType(position);
    }

    public static class modelViewHolderTypeA extends RecyclerView.ViewHolder {

        TextView title;


        public modelViewHolderTypeA(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text);
        }
    }

    public static class modelViewHolderTypeB extends RecyclerView.ViewHolder {

        TextView title;
        CircularImageView image;

        public modelViewHolderTypeB(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onProCatClick(listitem.get(getAdapterPosition()));
                }
            });
        }
    }
  public   static void setListener(onProCatClickListner listners){
        listner=listners;
    }
   public interface onProCatClickListner{
        void  onProCatClick(ModelSearchBefore modelSearchBefore);
    }

}
