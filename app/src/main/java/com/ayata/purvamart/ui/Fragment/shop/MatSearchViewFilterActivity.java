package com.ayata.purvamart.ui.Fragment.shop;

import android.util.Log;
import android.widget.Toast;


import com.ayata.purvamart.R;
import com.claudiodegio.msv.BaseMaterialSearchView;
import com.claudiodegio.msv.FilterMaterialSearchView;
import com.claudiodegio.msv.OnFilterViewListener;
import com.claudiodegio.msv.OnSearchViewListener;
import com.claudiodegio.msv.model.Filter;
import com.claudiodegio.msv.model.Section;

import java.util.List;

public class MatSearchViewFilterActivity extends BaseMatSearchViewActivity implements OnSearchViewListener, OnFilterViewListener {

    @Override
    protected void initCustom() {
        super.initCustom();
        FilterMaterialSearchView cast = (FilterMaterialSearchView)mSearchView;
        cast.setOnSearchViewListener(this);
        Section section = new Section("Animals");

        cast.addSection(section);

        Filter filter = new Filter(1, "Duck", 0, R.drawable.ic_user, getResources().getColor(R.color.colorAccent));
        cast.addFilter(filter);

        filter = new Filter(1, "Elephant", 0, R.drawable.ic_balance_icon,getResources().getColor(R.color.colorOrange));
        cast.addFilter(filter);

        filter = new Filter(1, "Frog", 0, R.drawable.ic_cart,getResources().getColor(R.color.colorGray));
        cast.addFilter(filter);

        section = new Section("Outdoor");

        cast.addSection(section);

        filter = new Filter(2, "Forest", 1, R.drawable.ic_user, getResources().getColor(R.color.colorGray));
        cast.addFilter(filter);

        filter = new Filter(2, "Mountain", 1, R.drawable.ic_account,getResources().getColor(R.color.colorOrange));
        cast.addFilter(filter);

        filter = new Filter(2, "Tent", 1, R.drawable.ic_connect_ips, getResources().getColor(R.color.colorGreen));
        cast.addFilter(filter);

        cast.setOnFilterViewListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.test_msv_filter;
    }

    @Override
    public void onSearchViewShown(){
    }

    @Override
    public void onSearchViewClosed() {
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "onQueryTextSubmit:" + query, Toast.LENGTH_SHORT).show();

        return true;
    }

    @Override
    public void onQueryTextChange(String newText) {

    }

    @Override
    public void onFilterAdded(Filter filter) {

        Log.d("TAG", "onFilterAdded:" + filter.getName());

    }

    @Override
    public void onFilterRemoved(Filter filter) {
        Log.d("TAG", "onFilterRemoved:" + filter.getName());

    }

    @Override
    public void onFilterChanged(List<Filter> list) {
        Log.d("TAG", "onFilterChanged:" + list.size());

    }

    /*
    public class SimpleRVAdapter extends RecyclerView.Adapter<BaseMatSearchViewActivity.SimpleRVAdapter.SimpleViewHolder> {
        private List<String> dataSource;
        public SimpleRVAdapter(List<String> dataArgs){
            dataSource = dataArgs;
        }

        @Override
        public BaseMatSearchViewActivity.SimpleRVAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(BaseMatSearchViewActivity.this).inflate(android.R.layout.simple_list_item_1, parent, false);
            BaseMatSearchViewActivity.SimpleRVAdapter.SimpleViewHolder viewHolder = new BaseMatSearchViewActivity.SimpleRVAdapter.SimpleViewHolder(view);
            return viewHolder;
        }

        public  class SimpleViewHolder extends RecyclerView.ViewHolder{
            public TextView textView;
            public SimpleViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView;
            }
        }

        @Override
        public void onBindViewHolder(BaseMatSearchViewActivity.SimpleRVAdapter.SimpleViewHolder holder, int position) {
            holder.textView.setText(dataSource.get(position));
        }

        @Override
        public int getItemCount() {
            return dataSource.size();
        }
    }*/
}
