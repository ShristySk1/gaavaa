package com.ayata.purvamart.ui.Fragment.shop.stories;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayata.purvamart.R;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


/*
used with viewpager2 recyclerviews
*/
public class ViewPagerAdapter3 extends RecyclerView.Adapter<ViewPagerAdapter3.myViewHolder> {
    //array of colors to change the background color of screen
    private int[] colors ={R.color.colorBlueLight,R.color.colorAccent,R.color.colorPrimary,R.color.colorPrimaryDark};
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_story, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        if(position == 0){
//            holder.tvTitle.setText("ViewPager2");
//            holder.tvAbout.setText("In this application we will learn about ViewPager2");
//            holder.ivImage.setImageResource(R.drawable.ic_launcher_background);
//            holder.container.setBackgroundResource(colors[position]);
        }
        if(position == 1) {
//            holder.tvTitle.setText("ViewPager2");
//            holder.tvAbout.setText("In this application we will learn about ViewPager2");
//            holder.ivImage.setImageResource(R.drawable.ic_launcher_background);
//            holder.container.setBackgroundResource(colors[position]);
        }
        if(position == 2) {
//            holder.tvTitle.setText("ViewPager2");
//            holder.tvAbout.setText("In this application we will learn about ViewPager2");
//            holder.ivImage.setImageResource(R.drawable.ic_launcher_background);
//            holder.container.setBackgroundResource(colors[position]);
        }
        if(position == 3) {
//            holder.tvTitle.setText("ViewPager2");
//            holder.tvAbout.setText("In this application we will learn about ViewPager2");
//            holder.ivImage.setImageResource(R.drawable.ic_launcher_background);
//            holder.container.setBackgroundResource(colors[position]);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout container;
        TextView tvTitle,tvAbout;
        ImageView ivImage;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
//            container=itemView.findViewById(R.id.container);
//            tvAbout=itemView.findViewById(R.id.tvAbout);
//            tvTitle=itemView.findViewById(R.id.tvTitle);
//            ivImage=itemView.findViewById(R.id.ivImage);
        }
    }
}
