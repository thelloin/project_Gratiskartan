package com.tommy.gratiskartan;

//import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by tommy on 11/23/15.
 */
public class ListFragment extends Fragment {

    // Creates a new instance of ListFragment
    public static ListFragment newInstance() {
        ListFragment lf = new ListFragment();

        return lf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "Creating a ListFragment", Toast.LENGTH_SHORT).show();
        LinearLayout linearLayout = new LinearLayout(getActivity());
        TextView testText = new TextView(getActivity());
        linearLayout.addView(testText);
        testText.setText("This should be seen on the screen");

        return linearLayout;

    }
}
