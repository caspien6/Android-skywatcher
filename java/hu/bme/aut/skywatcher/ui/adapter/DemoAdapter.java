package hu.bme.aut.skywatcher.ui.adapter;

import android.widget.ListAdapter;

import java.util.List;

import hu.bme.aut.skywatcher.model.item_search.DemoItem;

public interface DemoAdapter extends ListAdapter {

  void appendItems(List<DemoItem> newItems);

  void setItems(List<DemoItem> moreItems);
}