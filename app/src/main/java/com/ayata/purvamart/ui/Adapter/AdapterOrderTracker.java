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

    public AdapterOrderTracker(Context context, List<ModelOrderTrack> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @NonNull
    @Override
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_order_tracker, parent, false);
        return new modelViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull modelViewHolder holder, int position) {
        ModelOrderTrack modelOrderTrack = listitem.get(position);
        holder.title.setText(modelOrderTrack.getOrderTrackTitle());
        holder.desc.setText(modelOrderTrack.getOrderTrackDescription());
        int color = listitem.get(position).getColor();
        if (modelOrderTrack.getOrdertype().equals(ModelOrderTrack.ORDER_TYPE_NONE)) {
            holder.mTimelineView.setMarkerColor(context.getResources().getColor(color));
            holder.imageView.setColorFilter(context.getResources().getColor(color));
        } else {
            holder.mTimelineView.setMarkerColor(context.getResources().getColor(color));
            holder.title.setTextColor(context.getResources().getColor(color));
            holder.desc.setTextColor(context.getResources().getColor(color));
            if (position == 0) {
                if(!modelOrderTrack.getActual()){
                    holder.mTimelineView.setEndLineColor(context.getResources().getColor(color), 1);
                }
                //do nothing
            } else {
                if (!(listitem.get(position).getActual())) {
                    //already completed
                    holder.mTimelineView.setStartLineColor(context.getResources().getColor(color), 0);
                    holder.mTimelineView.setEndLineColor(context.getResources().getColor(color), 0);
                } else {
                    //current
                    holder.mTimelineView.setStartLineColor(context.getResources().getColor(listitem.get(position - 1).getColor()), 0);
                }
            }
            Log.d("sizenadposition", "onBindViewHolder: position: " + position + "size" + listitem.size());
            holder.imageView.setColorFilter(context.getResources().getColor(color));
        }
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder {

        public TimelineView mTimelineView;
        TextView title, desc;
        ImageView imageView;

        public modelViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.timeline);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            imageView = itemView.findViewById(R.id.image_tint);
            mTimelineView.initLine(viewType);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }
}
