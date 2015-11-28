package com.tommy.gratiskartan;

//import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by tommy on 11/23/15.
 */
public class ListItemsFragment extends ListFragment {

    String[] list_test = new String[] {"one", "two", "three", "four", "five"};

    // Creates a new instance of ListItemsFragment
    public static ListItemsFragment newInstance() {
        ListItemsFragment lf = new ListItemsFragment();

        return lf;
    }

    // onActivityCreated() is called when the activity's onCreate() method
    // has returned.
    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "ListItemsFragment:onActivityCreated", Toast.LENGTH_SHORT).show();

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "ListItemsFragment:onCreateView", Toast.LENGTH_SHORT).show();
        /*LinearLayout linearLayout = new LinearLayout(getActivity());
        TextView testText = new TextView(getActivity());
        linearLayout.addView(testText);
        testText.setText("This should be seen on the screen");

        return linearLayout;*/
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1, list_test);
        setListAdapter(adapter);*/
        Bundle extras = getArguments();
        ArrayList<Item> markersTest = extras.getParcelableArrayList("markers");


        //CustomArrayAdapter adapter = new CustomArrayAdapter(getContext(), MapsActivity.DUMMY_ARRAY_LIST);
        CustomArrayAdapter adapter = new CustomArrayAdapter(getContext(), markersTest);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);

    }
}
