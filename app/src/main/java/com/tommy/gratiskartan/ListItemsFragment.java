package com.tommy.gratiskartan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by tommy on 11/23/15.
 * ListItemFragment
 *
 */
public class ListItemsFragment extends ListFragment {


    private ArrayList<Item> markers_test = new ArrayList<Item>();

    // Creates a new instance of ListItemsFragment
    public static ListItemsFragment newInstance() {
        ListItemsFragment lf = new ListItemsFragment();

        return lf;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Toast.makeText(getActivity(), "ListItemsFragment:onCreateView", Toast.LENGTH_SHORT).show();

        Bundle extras = getArguments();
        ArrayList<Item> markersTest = extras.getParcelableArrayList("markers");
        this.markers_test = markersTest;

        CustomArrayAdapter adapter = new CustomArrayAdapter(getContext(), markersTest);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Item selectedItem = markers_test.get(position);

        // Start InfoActivity and add selectedItem
        Intent intent = new Intent();
        intent.setClass(getActivity(), ItemInfo.class);
        intent.putExtra("item", selectedItem);
        startActivity(intent);
    }
}
