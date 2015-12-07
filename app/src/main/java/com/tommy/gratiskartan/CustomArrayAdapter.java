package com.tommy.gratiskartan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tommy on 11/24/15.
 * CustomArrayAdapter
 */
public class CustomArrayAdapter extends ArrayAdapter<Item> {
    private final Context context;
    private final ArrayList<Item> items;

    public CustomArrayAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.item_row_list_layout, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_row_list_layout, parent, false);
        TextView firstLine = (TextView) rowView.findViewById(R.id.firstLine);
        TextView secondLine = (TextView) rowView.findViewById(R.id.secondLine);
        firstLine.setText(items.get(position).description);
        secondLine.setText("Posted by: " + items.get(position).author + items.get(position).toBeRemoved);
        // For now skip setting a custom image, leave default


        return rowView;
    }
}
