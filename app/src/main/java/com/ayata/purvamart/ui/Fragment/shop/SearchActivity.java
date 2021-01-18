package com.ayata.purvamart.ui.Fragment.shop;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.ayata.purvamart.R;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import br.com.mauker.materialsearchview.MaterialSearchView;
import br.com.mauker.materialsearchview.db.model.History;

public class SearchActivity extends AppCompatActivity {

    MaterialSearchView materialSearchView;
    //suggestions
    String[] suggestions = {"hello", "hello2", "test3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        materialSearchView = findViewById(R.id.search_view);
//        toolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                materialSearchView.openSearch();
//            }
//        });
        requestSuggestionList();
        materialSearchView.closeSearch();
        materialSearchView = findViewById(R.id.search_view);
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(@NotNull String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(@NotNull String s) {
                return false;
            }
        });
        materialSearchView.setSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewOpened() {
                // Do something once the view is open.
            }

            @Override
            public void onSearchViewClosed() {
                // Do something once the view is closed.
            }
        });
        materialSearchView.setOnItemClickListener(new MaterialSearchView.OnHistoryItemClickListener() {
            @Override
            public void onClick(@NotNull History history) {
                //put submit as true if you want to search by clicking on suggestion
                materialSearchView.setQuery(history.getQuery(), true);
                Toast.makeText(SearchActivity.this, history.getQuery(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(@NotNull History history) {
                Toast.makeText(SearchActivity.this, "Long clicked! Item: $history", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestSuggestionList() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        materialSearchView.openSearch();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (materialSearchView.isOpen()) {
            // Close the search on the back button press.
//            materialSearchView.closeSearch();
            finish();
        } else {
//            super.onBackPressed();
            finish();
        }
    }

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

    @Override
    protected void onPause() {
        super.onPause();
        materialSearchView.clearSuggestions();
        materialSearchView.clearPinned();
    }

    @Override
    protected void onStop() {
        super.onStop();
        materialSearchView.onViewStopped();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        materialSearchView.activityResumed();
//        String[] arr = getResources().getStringArray(R.array.suggestions);
        materialSearchView.addSuggestions(suggestions);
//        materialSearchView.addPin(suggestions[0]);
    }

    private void clearHistory() {
        materialSearchView.clearHistory();
    }

    private void clearSuggestions() {
        materialSearchView.clearSuggestions();
    }

    private void clearAll() {
        materialSearchView.clearAll();
    }
}