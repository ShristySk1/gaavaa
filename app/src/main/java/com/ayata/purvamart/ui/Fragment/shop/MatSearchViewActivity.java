package com.ayata.purvamart.ui.Fragment.shop;

import android.util.Log;
import android.widget.Toast;

import com.ayata.purvamart.R;
import com.claudiodegio.msv.BaseMaterialSearchView;
import com.claudiodegio.msv.OnSearchViewListener;


public class MatSearchViewActivity extends BaseMatSearchViewActivity implements OnSearchViewListener {


    @Override
    protected void initCustom() {
        mSearchView.setOnSearchViewListener(this);
    }

    public int getLayoutId() {
        return R.layout.test_msv_simple;
    }

    @Override
    public void onSearchViewShown() {

        Toast.makeText(this, "onSearchViewShown", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchViewClosed() {

        Toast.makeText(this, "onSearchViewClosed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "onQueryTextSubmit: " + query, Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onQueryTextChange(String newText) {

    }
}
