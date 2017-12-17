package hu.bme.aut.skywatcher.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.List;

import hu.bme.aut.skywatcher.R;
import hu.bme.aut.skywatcher.model.Item;
import hu.bme.aut.skywatcher.model.SearchedPictures;

import hu.bme.aut.skywatcher.ui.adapter.PictureRecyclerAdapter;


public class PictureResultsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PictureRecyclerAdapter adapter;

    SearchedPictures searchedPictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_results);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        searchedPictures =  (SearchedPictures) bundle.getSerializable(this.getApplicationContext().getString(R.string.serialized_images));

        initRecyclerView();

        //listView.setOnItemClickListener(this);

    }



    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.PictureRecyclerView);
        adapter = new PictureRecyclerAdapter();
        loadItemsInBackground();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadItemsInBackground() {
        List<Item> linkItems = searchedPictures.getCollection().getItems();
        adapter.update(linkItems);
    }

}
