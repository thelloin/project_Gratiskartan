package com.tommy.gratiskartan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM 'kl' kk:mm");
        secondLine.setText("Tillgänglig till: " + dateFormat.format(items.get(position).toBeRemoved));

        // Add the image
        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_image);
        if (items.get(position).category.equals("Frukt")) {
            imageView.setImageResource(R.drawable.ic_fruit_test);
        } else if (items.get(position).category.equals("Bär")) {
            imageView.setImageResource(R.drawable.ic_berries_test);
        } else if (items.get(position).category.equals("Elektronik")) {
            imageView.setImageResource(R.drawable.ic_electronics_test);
        } else if (items.get(position).category.equals("För Hemmet")) {
            imageView.setImageResource(R.drawable.ic_home_test);
        } else if (items.get(position).category.equals("Hobby")) {
            imageView.setImageResource(R.drawable.ic_hobby_test);
        } else if (items.get(position).category.equals("Övrigt")) {
            imageView.setImageResource(R.drawable.ic_other_test);
        }



        return rowView;
    }
}
