package hu.bme.aut.skywatcher.ui.adapter;

import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.skywatcher.R;
import hu.bme.aut.skywatcher.model.Item;
import hu.bme.aut.skywatcher.ui.FullscreenActivity;

public class PictureRecyclerAdapter extends RecyclerView.Adapter<PictureRecyclerAdapter.PictureViewHolder> {

    private final List<Item> items;

    public PictureRecyclerAdapter() {
        items = new ArrayList<>();
    }
    
    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_normal, parent, false);
        PictureViewHolder viewHolder = new PictureViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PictureViewHolder holder, int position) {
        Item item = items.get(position);
        holder.item = item;
        Glide.with(holder.itemView).asBitmap().load(item.getLinks().get(0).getHref())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        holder.picture.setImageBitmap(resource);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Item item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void update(List<Item> shoppingItems) {
        items.clear();
        items.addAll(shoppingItems);
        notifyDataSetChanged();
    }


    public class PictureViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;
        Item item;
        View itemView;

        public PictureViewHolder(final View itemView) {
            super(itemView);
            picture = (ImageView) itemView.findViewById(R.id.element_image);
            picture.setOnTouchListener(new View.OnTouchListener() {
                private GestureDetector gestureDetector = new GestureDetector(itemView.getContext(), new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        showInFullscreen();
                        return super.onDoubleTap(e);
                    }
                });

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.d("TEST", "Raw event: " + event.getAction() + ", (" + event.getRawX() + ", " + event.getRawY() + ")");
                    gestureDetector.onTouchEvent(event);
                    return true;
                }
            });
            this.itemView = itemView;

        }

        public void showInFullscreen() {
            if (item != null){
                Bundle bundle = new Bundle();
                bundle.putSerializable(itemView.getContext().getString(R.string.serialize_item), item);
                Intent intent = new Intent(itemView.getContext(), FullscreenActivity.class);
                intent.putExtras(bundle);
                itemView.getContext().startActivity(intent);
            }

        }

    }
}