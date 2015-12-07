package com.tommy.gratiskartan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseObject;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by tommy on 11/24/15.
 * GMapFragment
 * A subclass of Fragment
 *
 */
public class GMapFragment extends SupportMapFragment implements
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener, OnMapReadyCallback,
        GoogleMap.OnCameraChangeListener {

    private GoogleMap mMap;


    private ArrayList<Item> markers = null;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment FirstFragment.
     */
    public static GMapFragment newInstance() {

        return new GMapFragment();
    }

    public GMapFragment() {
        // Required empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // Activity is created for the first time
        } else {

        }
        //Toast.makeText(getActivity(), "GMapFragment:onCreate", Toast.LENGTH_SHORT).show();

        getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle extras = getArguments();
        this.markers = extras.getParcelableArrayList("markers");

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Set up mMap to handle different events
        setUpmMap();


        // Add markers to map
        LatLng coord;
        for (Item item : markers) {
            coord = new LatLng(item.latitude, item.longitude);
            mMap.addMarker(new MarkerOptions()
                    .position(coord)
                    .title(item.category)
                    .snippet(item.author + " " + item.description));
        }


        // TODO Get user position with the class GPSTracker
        // Move the camera
        LatLng cameraPos = new LatLng(58.39858598, 15.57723999);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPos));

    }


    private void setUpmMap() {
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                if (mListener != null) {
                    // Send over the center position of the map
                    mListener.setCenterPos(cameraPosition.target);
                }
            }
        });
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                //Toast.makeText(getActivity(), "Clicked on info marker, id: " + marker.getId() , Toast.LENGTH_LONG).show();

                // Find out what info window is clicked and open a new InfoItem activity
                String title = marker.getTitle();
                String snippet = marker.getSnippet();

                String[] postedByAndDesc = snippet.split(" ", 2);

                //Toast.makeText(getActivity(), "Title: " + title + " author: " + postedByAndDesc[0]  + " desc: " + postedByAndDesc[1], Toast.LENGTH_LONG).show();
                for (Item item : markers) {
                    if (item.category.equals(title) && item.author.equals(postedByAndDesc[0])
                            && item.description.equals(postedByAndDesc[1]) ) {

                        // Correct item is found, start InfoActivity and
                        // pass along the item
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), ItemInfo.class);
                        intent.putExtra("item", item);
                        startActivity(intent);
                    }
                }
            }
        });
    }


    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDrag(Marker marker) {
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //mMarkerPosition = marker.getPosition();
    }

    // For some reason this does not work, have to add this listener dirctly to mMap
    @Override
    public void onCameraChange(CameraPosition position) {
        Toast.makeText(getActivity(), "Callback for camera change" , Toast.LENGTH_LONG).show();
        //getActivity().setCenterPos(position.target);
        if (mListener != null) {
            mListener.setCenterPos(position.target);

        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
            + "must implement OnFragmentInteractionListener");
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment tot allow an interaction in this fragment to be communicated
     * to the activity.
     */
    public interface OnFragmentInteractionListener {
        void setCenterPos(LatLng centerPos);
    }

}
