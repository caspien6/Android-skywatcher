package hu.bme.aut.skywatcher.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;

import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.skywatcher.R;
import hu.bme.aut.skywatcher.model.SearchedPictures;
import hu.bme.aut.skywatcher.model.item_search.DemoItem;
import hu.bme.aut.skywatcher.model.item_search.DemoUtils;
import hu.bme.aut.skywatcher.ui.adapter.DefaultListAdapter;
import hu.bme.aut.skywatcher.ui.adapter.DemoAdapter;


public class PictureResultsActivity extends AppCompatActivity {

    private AsymmetricGridView listView;
    private DemoAdapter adapter;
    private final DemoUtils demoUtils = new DemoUtils();
    SearchedPictures searchedPictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_results);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        searchedPictures =  (SearchedPictures) bundle.getSerializable("serialized_images");
        listView = (AsymmetricGridView) findViewById(R.id.listView);

        listView.setRequestedColumnCount(3);
        listView.setRequestedHorizontalSpacing(Utils.dpToPx(this, 3));

        listView.setDebugging(true);

        if (savedInstanceState == null) {
            List<DemoItem> mylist = demoUtils.moarItems(50);
            for (int i = 0; i < 50; i ++){
                mylist.get(i).myItem = searchedPictures.getCollection().getItems().get(i);
            }
            adapter = new DefaultListAdapter(this, mylist);
        } else {
            adapter = new DefaultListAdapter(this);
        }
        listView.setAdapter(getNewAdapter());


        //listView.setOnItemClickListener(this);

    }

    private AsymmetricGridViewAdapter getNewAdapter() {
        return new AsymmetricGridViewAdapter(this, listView, adapter);
    }
}
