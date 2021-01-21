package com.ayata.purvamart.ui.Fragment.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ayata.purvamart.R;
import com.claudiodegio.msv.BaseMaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BaseMatSearchViewActivity extends AppCompatActivity {
    Toolbar mToolbar;

    RecyclerView mRvItem;

    BaseMaterialSearchView mSearchView;


    CoordinatorLayout mCoordinator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRvItem = findViewById(R.id.rv_item);
        mCoordinator = findViewById(R.id.cl);
        mSearchView = findViewById(R.id.sv);
        // Action bar di supporti
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getTitle());


        init();

        initCustom();
    }

    public int getLayoutId() {
        return R.layout.test_msv_simple;
    }

    protected void init() {
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        mRvItem.setLayoutManager(layoutManager);
        mRvItem.setHasFixedSize(true);

        List<String> list = new ArrayList<>();

        list.add("Have");
        list.add("Sodium");
        list.add("Routine");
        list.add("Systematic");
        list.add("Departure");
        list.add("Transparent");
        list.add("Amputate");
        list.add("Dialogue");
        list.add("Uncle");
        list.add("Credit card");
        list.add("Greet");
        list.add("Dollar");

        mRvItem.setAdapter(new MatSearchViewActivity.SimpleRVAdapter(list));
    }

    protected void initCustom() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.test_adv_serach_view, menu);
        mSearchView.setMenuItem(menu.findItem(R.id.action_search));
        return true;
    }

    public class SimpleRVAdapter extends RecyclerView.Adapter<SimpleRVAdapter.SimpleViewHolder> {
        private List<String> dataSource;

        public SimpleRVAdapter(List<String> dataArgs) {
            dataSource = dataArgs;
        }

        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(BaseMatSearchViewActivity.this).inflate(android.R.layout.simple_list_item_1, parent, false);
            SimpleViewHolder viewHolder = new SimpleViewHolder(view);
            return viewHolder;
        }

        public class SimpleViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public SimpleViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView;
            }
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position) {
            holder.textView.setText(dataSource.get(position));
        }

        @Override
        public int getItemCount() {
            return dataSource.size();
        }
    }
}