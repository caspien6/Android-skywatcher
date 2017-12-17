package hu.bme.aut.skywatcher.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hu.bme.aut.skywatcher.R;
import hu.bme.aut.skywatcher.model.SearchedPictures;
import hu.bme.aut.skywatcher.ui.adapter.LoggerRecyclerAdapter;


public class LoggerFragment extends Fragment {

    private RecyclerView recyclerView;
    public LoggerRecyclerAdapter adapter;



    public LoggerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_logger, container, false);

        setHasOptionsMenu(true);

        initRecyclerView(v);

        //listView.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.delete_all, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.deleteAllBtn) {
            adapter.update(new ArrayList<SearchedPictures>());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.saveLoggerItems(this.getView());
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.reloadLoggerItems(this.getView());
    }

    private void initRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.LoggerRecyclerView);
        adapter = new LoggerRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);
    }



}
