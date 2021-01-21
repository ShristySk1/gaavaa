package com.ayata.purvamart.ui.Fragment.shop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.ayata.purvamart.R;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.data.network.response.ProductListResponse;
import com.claudiodegio.msv.OnSearchViewListener;
import com.claudiodegio.msv.SuggestionMaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SearchActivity";
//    SuggestionMaterialSearchView materialSearchView;
    //suggestions
    SuggestionMaterialSearchView cast;
Button simple,filter,suggestion,themed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        materialSearchView = findViewById(R.id.sv);
//        cast = (SuggestionMaterialSearchView) mSearchView;
//        requestSuggestionList();
simple=findViewById(R.id.bt_simple);
filter=findViewById(R.id.bt_filter);
themed=findViewById(R.id.bt_themed);
suggestion=findViewById(R.id.bt_suggestions);
simple.setOnClickListener(this);
filter.setOnClickListener(this);
themed.setOnClickListener(this);
suggestion.setOnClickListener(this);
//        toolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                materialSearchView.openSearch();
//            }
//        });
//        requestSuggestionList();
//        materialSearchView.closeSearch();
//        mSearchView.setOnSearchViewListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.bt_simple:
                intent = new Intent(this, MatSearchViewActivity.class);
                break;
            case R.id.bt_suggestions:
                intent = new Intent(this, MatSearchViewSuggestionActivity.class);
                break;
            case R.id.bt_filter:
                intent = new Intent(this, MatSearchViewFilterActivity.class);
                break;
            case R.id.bt_themed:
                intent = new Intent(this, ThemedMatSearchViewActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

//    private void requestSuggestionList() {
//        allProduct();
//    }
//
//    private void allProduct() {
//        ApiService productListapi = ApiClient.getClient().create(ApiService.class);
//        suggestions.clear();
//        productListapi.getProductsList().enqueue(new Callback<ProductListResponse>() {
//            @Override
//            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
//                if (response.isSuccessful()) {
//                    ProductListResponse productListResresponse = response.body();
//                    for (ProductDetail productDetail : productListResresponse.getDetails()) {
//                        suggestions.add(productDetail.getName());
////                        materialSearchView.addSuggestion(productDetail.getName());
//
//
//                    }
//                    cast.setSuggestion(suggestions);
//
//                } else {
//                    Log.d(TAG, "onResponse: " + response.body().getMessage());
//                }
//                //
//            }
//
//            @Override
//            public void onFailure(Call<ProductListResponse> call, Throwable t) {
//                Log.d(TAG, "onResponse:failed " + t.getMessage());
//            }
//        });
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
////        materialSearchView.openSearch();
////        materialSearchView.setMenuItem();
//        return true;
//    }
//
//    @Override
//    public void onSearchViewShown() {
//
//    }
//
//    @Override
//    public void onSearchViewClosed() {
//
//    }
//
//    @Override
//    public boolean onQueryTextSubmit(String s) {
//        return false;
//    }
//
//    @Override
//    public void onQueryTextChange(String s) {
//
//    }
}

//    @Override
//    public void onBackPressed() {
//        if (materialSearchView.isOpen()) {
//            // Close the search on the back button press.
////            materialSearchView.closeSearch();
//            finish();
//        } else {
////            super.onBackPressed();
//            finish();
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
//            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//            if (matches != null && matches.size() > 0) {
//                String searchWrd = matches.get(0);
//                if (!TextUtils.isEmpty(searchWrd)) {
//                    materialSearchView.setQuery(searchWrd, false);
//                    Toast.makeText(SearchingActivity.this, searchWrd, Toast.LENGTH_SHORT).show();
//                }
//            }
//            return;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        materialSearchView.clearSuggestions();
//        materialSearchView.clearPinned();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        materialSearchView.onViewStopped();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
////        materialSearchView.activityResumed();
////        String[] arr = getResources().getStringArray(R.array.suggestions);
//        requestSuggestionList();
//        materialSearchView.addSuggestions(suggestions);
////        materialSearchView.addPin(suggestions[0]);
//    }
//
//    private void clearHistory() {
//        materialSearchView.clearHistory();
//    }
//
//    private void clearSuggestions() {
//        materialSearchView.clearSuggestions();
//    }
//
//    private void clearAll() {
//        materialSearchView.clearAll();
//    }
//}