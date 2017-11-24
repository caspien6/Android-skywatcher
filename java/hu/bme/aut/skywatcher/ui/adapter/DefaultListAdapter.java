package hu.bme.aut.skywatcher.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.annotation.GlideType;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import hu.bme.aut.skywatcher.R;
import hu.bme.aut.skywatcher.model.item_search.DemoItem;

public class DefaultListAdapter extends ArrayAdapter<DemoItem> implements DemoAdapter {
//https://dzone.com/articles/optimizing-your-listview
    private final LayoutInflater layoutInflater;
    private ViewHolder holder;

  public DefaultListAdapter(Context context, List<DemoItem> items) {
    super(context, 0, items);
    layoutInflater = LayoutInflater.from(context);
  }

  public DefaultListAdapter(Context context) {
    super(context, 0);
    layoutInflater = LayoutInflater.from(context);
  }

  @Override
  public View getView(int position, View convertView, @NonNull ViewGroup parent) {
      View v = convertView;
      boolean isRegular = getItemViewType(position) == 0;
      if (v == null) {
          v = layoutInflater.inflate(isRegular ? R.layout.image_normal : R.layout.image_odd,parent, false);
          holder = new ViewHolder(v,isRegular);
      }
      //holder.bindView(getItem(position));
      final ImageView imageView;



    return v;
  }

  static class ViewHolder{
      private ImageView imageView;
      public ViewHolder(View itemView, boolean isRegular) {
          if (isRegular) {
              imageView = (ImageView) itemView.findViewById(R.id.element_image);
          } else {
              imageView = (ImageView) itemView.findViewById(R.id.element_image_odd);
          }

          Glide.with(itemView).asBitmap().load(item.myItem.getLinks().get(0).getHref())
                  .into(new SimpleTarget<Bitmap>() {
                      @Override
                      public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                          imageView.setImageBitmap(resource);
                      }
                  });

      }

  }

  @Override public int getViewTypeCount() {
    return 2;
  }

  @Override public int getItemViewType(int position) {
    return position % 2 == 0 ? 1 : 0;
  }

  public void appendItems(List<DemoItem> newItems) {
    addAll(newItems);
    notifyDataSetChanged();
  }

  public void setItems(List<DemoItem> moreItems) {
    clear();
    appendItems(moreItems);
  }
}