package com.ayata.purvamart.ui.Fragment.shop.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Constants.Constants;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable {
    static setOnSearchClickListener listener;
    private List<ProductDetail> productList;
    private List<ProductDetail> productListFiltered;
    private Context context;

    public void setProductDetailList(Context context, final List<ProductDetail> productList) {
        this.context = context;
        if (this.productList == null) {
            this.productList = productList;
            this.productListFiltered = productList;
            notifyItemChanged(0, productListFiltered.size());
        } else {
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return SearchAdapter.this.productList.size();
                }

                @Override
                public int getNewListSize() {
                    return productList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return SearchAdapter.this.productList.get(oldItemPosition).getName() == productList.get(newItemPosition).getName();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    ProductDetail newProductDetail = SearchAdapter.this.productList.get(oldItemPosition);

                    ProductDetail oldProductDetail = productList.get(newItemPosition);

                    return newProductDetail.getName() == oldProductDetail.getName();
                }
            });
            this.productList = productList;
            this.productListFiltered = productList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_search_product, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(productListFiltered.get(position).getName());
        Glide.with(context).load(productListFiltered.get(position).getImage()).placeholder(Constants.PLACEHOLDER).fallback(Constants.FALLBACKIMAGE).into(holder.image);
    }

    @Override
    public int getItemCount() {

        if (productListFiltered != null) {
            return productListFiltered.size();
        } else {
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    productListFiltered = productList;
                } else {
                    List<ProductDetail> filteredList = new ArrayList<>();
                    for (ProductDetail movie : productList) {
                        if (movie.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(movie);
                        }
                    }
                    productListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productListFiltered = (ArrayList<ProductDetail>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        CircularImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            image = (CircularImageView) view.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSearchClick(productListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public int getFilteredSize() {
        return productListFiltered.size();
    }

    public static void setListener(setOnSearchClickListener listeners) {
        listener = listeners;
    }

    interface setOnSearchClickListener {
        void onSearchClick(ProductDetail productDetail);
    }
}