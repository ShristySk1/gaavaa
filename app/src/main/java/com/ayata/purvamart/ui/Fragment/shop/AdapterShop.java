package com.ayata.purvamart.ui.Fragment.shop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelCategory;
import com.ayata.purvamart.data.Model.Stories;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.data.network.response.Slider;
import com.ayata.purvamart.ui.Adapter.AdapterAd;
import com.ayata.purvamart.ui.Adapter.AdapterCategory;
import com.ayata.purvamart.ui.Adapter.AdapterItem;
import com.ayata.purvamart.ui.Adapter.AdapterStories;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterShop extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_TITLE = 0;
    private final int VIEW_TYPE_AD = 1;
    private final int VIEW_TYPE_CATEGORY = 2;
    private final int VIEW_TYPE_PRODUCT = 3;
    private final int VIEW_TYPE_STORY = 4;


    private Context context;
    private List<ModelShop> listitem;
    private OnSeeAllClickListener onSeeAllClickListener;

    public AdapterShop(Context context, List<ModelShop> listitem, OnSeeAllClickListener onSeeAllClickListener) {
        this.context = context;
        this.listitem = listitem;
        this.onSeeAllClickListener = onSeeAllClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_TITLE) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_shop_title, parent, false);
            return new TitleViewHolder(view, onSeeAllClickListener);
        } else if (viewType == VIEW_TYPE_AD) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_shop_recycler, parent, false);
            return new AdViewHolder(view);
        } else if (viewType == VIEW_TYPE_CATEGORY) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_shop_recycler, parent, false);
            return new CategoryViewHolder(view);
        } else if (viewType == VIEW_TYPE_PRODUCT) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_shop_recycler, parent, false);
            return new ProductViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_shop_recycler, parent, false);
            return new StoryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleViewHolder) {
            ((TitleViewHolder) holder).bindTitle();
        } else if (holder instanceof CategoryViewHolder) {
            ((CategoryViewHolder) holder).bindCategory();
        } else if (holder instanceof AdViewHolder) {
            ((AdViewHolder) holder).bindAd();
        } else if (holder instanceof ProductViewHolder) {
            ((ProductViewHolder) holder).bindProduct();
        } else {
            ((StoryViewHolder) holder).bindStory();

        }
    }

    @Override
    public int getItemCount() {
        if (listitem != null)
            Log.d("shopsize", "getItemCount: " + listitem.size());
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
        switch (listitem.get(position).getType()) {
            case 0:
                return ModelShop.TTTLE_TYPE;
            case 1:
                Log.d("myviewtype", "getItemViewType: _adtype");
                return ModelShop.AD_TYPE;
            case 2:
                Log.d("myviewtype", "getItemViewType: categorytypew");
                return ModelShop.CATEGORY_TYPE;
            case 3:
                return ModelShop.PRODUCT_TYPE;
            case 4:
                return ModelShop.STORY_TYPE;
            default:
                return -1;
        }
    }


    public class TitleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnSeeAllClickListener onSeeAllClickListener;
        TextView tvTitle, tvSeeAll;

        public TitleViewHolder(@NonNull View itemView, OnSeeAllClickListener onSeeAllClickListener) {
            super(itemView);
            this.onSeeAllClickListener = onSeeAllClickListener;
            tvSeeAll = itemView.findViewById(R.id.tvSeeAll);
            tvTitle = itemView.findViewById(R.id.tvTitle);

        }

        public void bindTitle() {
            tvTitle.setText((String) listitem.get(getAdapterPosition()).getData());
            if (((String) listitem.get(getAdapterPosition()).getData()).contains("Categories")) {
                tvSeeAll.setOnClickListener(this);
            } else {
                tvSeeAll.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View view) {
            onSeeAllClickListener.onSeeAllClick();
        }
    }

    private class AdViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvAd;

        public AdViewHolder(@NonNull View itemView) {
            super(itemView);
            rvAd = itemView.findViewById(R.id.recycler_shop);
        }

        public void bindAd() {
            RecyclerView.LayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            AdapterAd.setAddListener((AdapterAd.setOnAddListener) listitem.get(getAdapterPosition()).getListener());
            AdapterAd adapterAd = new AdapterAd(context, (List<Slider>) listitem.get(getAdapterPosition()).getData());
            rvAd.setAdapter(adapterAd);
            rvAd.setLayoutManager(manager);
            adapterAd.notifyDataSetChanged();
        }
    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            rvCategory = itemView.findViewById(R.id.recycler_shop);
        }

        public void bindCategory() {
            AdapterCategory.OnCategoryClickListener listener = (AdapterCategory.OnCategoryClickListener) listitem.get(getAdapterPosition()).getListener();
            List<ModelCategory> modelCategory = (List<ModelCategory>) listitem.get(getAdapterPosition()).getData();
            RecyclerView.LayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            AdapterCategory adapterCategory = new AdapterCategory(context, modelCategory, listener);
            rvCategory.setLayoutManager(manager);
            rvCategory.setAdapter(adapterCategory);
            adapterCategory.notifyDataSetChanged();
        }
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            rvProduct = itemView.findViewById(R.id.recycler_shop);
        }

        public void bindProduct() {
            AdapterItem.OnItemClickListener listener = (AdapterItem.OnItemClickListener) listitem.get(getAdapterPosition()).getListener();
            List<ProductDetail> list_madeforyou = (List<ProductDetail>) listitem.get(getAdapterPosition()).getData();
            RecyclerView.LayoutManager manager = new GridLayoutManager(context, 2);
            AdapterItem adapterItem_madeforyou = new AdapterItem(context, list_madeforyou, listener);
            rvProduct.setLayoutManager(manager);
            rvProduct.setAdapter(adapterItem_madeforyou);
            adapterItem_madeforyou.notifyDataSetChanged();

        }
    }

    private class StoryViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvStory;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            rvStory = itemView.findViewById(R.id.recycler_shop);
        }

        public void bindStory() {
            RecyclerView.LayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            AdapterStories.setAddListener((AdapterStories.setOnStoryListener) listitem.get(getAdapterPosition()).getListener());
            AdapterStories adapterStories = new AdapterStories(context, (List<Stories>) listitem.get(getAdapterPosition()).getData());
            rvStory.setAdapter(adapterStories);
            rvStory.setLayoutManager(manager);
            adapterStories.notifyDataSetChanged();
        }
    }

    public interface OnSeeAllClickListener {
        void onSeeAllClick();
    }

}
