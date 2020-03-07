package com.example.enterprisetaskscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ItemListAdapter<T extends ListItem> extends ArrayAdapter<T> {
    private ArrayList<T> items;
    private LayoutInflater inflater;
    private int viewResourceId;

    public ItemListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<T> items) {
        super(context, resource, items);
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewResourceId = resource;
    }

    public void update(ArrayList<T> filteredItems) {
        items = new ArrayList<>();
        items.addAll(filteredItems);
        notifyDataSetChanged(); // Notify android to update the listView
    }

    // Must be overridden for updated size of filtered employee list
    @Override
    public int getCount() {
        return items.size();
    }

    public ArrayList<T> getItems() {
        return items;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public int getViewResourceId() {
        return viewResourceId;
    }
}
