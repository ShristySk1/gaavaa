package com.ayata.purvamart.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelHelp;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterHelp extends RecyclerView.Adapter<AdapterHelp.myHelpViewHolder> {
    List<ModelHelp> list;
    Context context;
    static HelpClickInterface listener;
    RotateAnimation rotateAnimation;


    public AdapterHelp(List<ModelHelp> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public myHelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_help, parent, false);
        return new myHelpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myHelpViewHolder holder, int position) {

        if (list.get(position).getHelpType() == ModelHelp.HelpType.HELP_OPEN) {
            holder.help_question.setText(list.get(position).getQuestion());
            holder.help_answer.setText(list.get(position).getAnswer());
            holder.help_answer.setVisibility(View.VISIBLE);
            rotateAnimation = new RotateAnimation(0.0f, 90.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setInterpolator(new DecelerateInterpolator());
            holder.ivArrow.startAnimation(rotateAnimation);
        } else if (list.get(position).getHelpType() == ModelHelp.HelpType.HELP_CLOSE) {
            holder.help_question.setText(list.get(position).getQuestion());
            holder.help_answer.setVisibility(View.GONE);
            rotateAnimation = new RotateAnimation(90.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setInterpolator(new DecelerateInterpolator());

            holder.ivArrow.startAnimation(rotateAnimation);
        } else {

        }
        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myHelpViewHolder extends RecyclerView.ViewHolder {
        TextView help_question, help_answer;
        ConstraintLayout root_layout;
        ImageView ivArrow;

        public myHelpViewHolder(@NonNull View itemView) {
            super(itemView);
            help_question = itemView.findViewById(R.id.help_question);
            help_answer = itemView.findViewById(R.id.help_answer);
            root_layout = itemView.findViewById(R.id.helplayout_root);
            ivArrow = itemView.findViewById(R.id.ivArrow);
            root_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.helpListener(getAdapterPosition());
                }
            });

        }
    }

    public static void setListener(HelpClickInterface listeners) {
        listener = listeners;
    }

    public interface HelpClickInterface {
        void helpListener(int position);
    }
}
