package com.wearweather;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class MainSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}
//public class MainSearchActivity extends Fragment {
//
//    private SearchView searchView = null;
//    private SearchView.OnQueryTextListener queryTextListener;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.search_layout, container, false);
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.search_menu, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//
//        if (searchItem != null) {
//            searchView = (SearchView) searchItem.getActionView();
//        }
//        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//
//            queryTextListener = new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    Log.i("onQueryTextChange", newText);
//
//                    return true;
//                }
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    Log.i("onQueryTextSubmit", query);
//
//                    return true;
//                }
//            };
//            searchView.setOnQueryTextListener(queryTextListener);
//        }
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_search:
//                // Not implemented here
//                return false;
//            default:
//                break;
//        }
//        searchView.setOnQueryTextListener(queryTextListener);
//        return super.onOptionsItemSelected(item);
//    }
//}