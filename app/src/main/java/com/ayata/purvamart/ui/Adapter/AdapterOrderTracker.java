package com.ayata.purvamart.ui.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelOrderTrack;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterOrderTracker extends RecyclerView.Adapter<AdapterOrderTracker.modelViewHolder> {

    private Context context;
    private List<ModelOrderTrack> listitem;
    private OnCategoryClickListener onCategoryClickListener;

    public AdapterOrderTracker(Context context, List<ModelOrderTrack> listitem, OnCategoryClickListener onCategoryClickListener) {
        this.context = context;
        this.listitem = listitem;
        this.onCategoryClickListener = onCategoryClickListener;
    }

    @NonNull
    @Override
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_order_tracker, parent, false);
        return new modelViewHolder(view, viewType, onCategoryClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull modelViewHolder holder, int position) {
        ModelOrderTrack modelOrderTrack = listitem.get(position);
        holder.title.setText(modelOrderTrack.getOrderTrackTitle());
        holder.desc.setText(modelOrderTrack.getOrderTrackDescription());
        if (modelOrderTrack.getOrdertype() == ModelOrderTrack.ORDER_TYPE_NONE) {
            holder.mTimelineView.setMarkerColor(context.getResources().getColor(R.color.colorGrayLight));
            holder.imageView.setColorFilter(context.getResources().getColor(R.color.colorGrayLight));
        } else {
            int color = listitem.get(position).getColor();
            holder.mTimelineView.setMarkerColor(context.getResources().getColor(color));
            holder.title.setTextColor(context.getResources().getColor(color));
            holder.desc.setTextColor(context.getResources().getColor(color));
            holder.mTimelineView.setEndLineColor(context.getResources().getColor(color), 1);
            if (position != 0) {
                holder.mTimelineView.setStartLineColor(context.getResources().getColor(color), 0);
            }
            Log.d("sizenadposition", "onBindViewHolder: position: "+position+"size"+listitem.size());
            holder.imageView.setColorFilter(context.getResources().getColor(color));
        }
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnCategoryClickListener onCategoryClickListener;
        public TimelineView mTimelineView;
        TextView title, desc;
        ImageView imageView;

        public modelViewHolder(@NonNull View itemView, int viewType, OnCategoryClickListener onCategoryClickListener) {
            super(itemView);
            this.onCategoryClickListener = onCategoryClickListener;
            mTimelineView = (TimelineView) itemView.findViewById(R.id.timeline);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            imageView = itemView.findViewById(R.id.image_tint);
            mTimelineView.initLine(viewType);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onCategoryClickListener.onCategoryClick(getAdapterPosition());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    public interface OnCategoryClickListener {

        void onCategoryClick(int position);
    }
}
