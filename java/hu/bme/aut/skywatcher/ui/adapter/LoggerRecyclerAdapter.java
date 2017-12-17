package hu.bme.aut.skywatcher.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hu.bme.aut.skywatcher.R;
import hu.bme.aut.skywatcher.model.SearchedPictures;
import hu.bme.aut.skywatcher.model.lib_button.FButton;
import hu.bme.aut.skywatcher.ui.PictureResultsActivity;


public class LoggerRecyclerAdapter extends RecyclerView.Adapter<LoggerRecyclerAdapter.LoggerHolder>{

    private final List<SearchedPictures> items;

    public LoggerRecyclerAdapter() {
        items = new ArrayList<>();
    }

    public void saveLoggerItems(View fragmentView){
        SearchedPictures[] sp = new SearchedPictures[items.size()];
        for (int i = 0; i < items.size(); i++){
            sp[i] = items.get(i);
        }
        try {
            FileOutputStream fos = fragmentView.getContext().openFileOutput(fragmentView.getContext().getString(R.string.logged_items), Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(sp);
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadLoggerItems(View fragmentView){
        FileInputStream fis = null;
        try {
            fis = fragmentView.getContext().openFileInput(fragmentView.getContext().getString(R.string.logged_items));
            ObjectInputStream is = new ObjectInputStream(fis);
            SearchedPictures[] sp = (SearchedPictures[]) is.readObject();
            is.close();
            fis.close();
            List<SearchedPictures> my_searchedPictures = new ArrayList<>();
            for (int i = 0; i < sp.length; i++){
                my_searchedPictures.add(sp[i]);
            }
            update(my_searchedPictures);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public LoggerRecyclerAdapter.LoggerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.logger_row, parent, false);
        LoggerHolder viewHolder = new LoggerHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final LoggerHolder holder, final int position) {
        final SearchedPictures item = items.get(position);
        holder.reloadbtn.setText(item.getSearchedName());
        holder.reloadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(view.getContext().getString(R.string.serialized_images), items.get(position));
                Intent intent = new Intent(view.getContext(), PictureResultsActivity.class);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(position);
            }
        });
        int[] androidColors = holder.itemView.getResources().getIntArray(R.array.androidcolors);
        holder.reloadbtn.setButtonColor(androidColors[item.getRandomColorIndex()]);

        int num = item.getRandomColorIndex()+4%androidColors.length;
        if (num < 0) num*=-1;
        else if(num > androidColors.length-1) num = 0;

        //holder.deletebtn.setButtonColor(androidColors[num]);

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void deleteItem(int pos){
        items.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, items.size());
    }

    public void addItem(SearchedPictures item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void update(List<SearchedPictures> shoppingItems) {
        items.clear();
        items.addAll(shoppingItems);
        notifyDataSetChanged();
    }

    public class LoggerHolder extends RecyclerView.ViewHolder {

        FButton reloadbtn, deletebtn;
        View itemView;

        public LoggerHolder(final View itemView) {
            super(itemView);
            reloadbtn = (FButton) itemView.findViewById(R.id.reload_downloaded);
            deletebtn = (FButton) itemView.findViewById(R.id.deleteTagBtn);
            this.itemView = itemView;

        }
    }



}
