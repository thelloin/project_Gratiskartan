package com.tommy.gratiskartan;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewGroupCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by tommy on 11/24/15.
 * A subclass of Fragment
 *
 */
public class GMapFragment extends SupportMapFragment implements
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener, OnMapReadyCallback,
        GoogleMap.OnCameraChangeListener {

    private GoogleMap mMap;

    protected List<ParseObject> markersTEST = null;

    private OnFragmentInteractionListener mListener;
    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment FirstFragment.
     */
    public static GMapFragment newInstance() {
        GMapFragment fragment = new GMapFragment();
        return fragment;
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

        getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
    }*/

    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add some dummy markers
        mMap = googleMap;

        // Test to set up mMap
        setUpmMap();

        /*
        LatLng coords;
        for (int i = 0; i < MapsActivity.DUMMY_ITEMS.length; i++) {
            coords = new LatLng(MapsActivity.DUMMY_ITEMS[i].latitude,
                    MapsActivity.DUMMY_ITEMS[i].longitude);
            mMap.addMarker(new MarkerOptions()
                    .position(coords)
                    .title(MapsActivity.DUMMY_ITEMS[i].description));
        }
        */
        // Fetch markers from parse and add to map
        ParseQuery<ParseObject> markers = ParseQuery.getQuery("TestMarkers");
        markers.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    //Toast.makeText(getActivity(), "Probably works " , Toast.LENGTH_LONG).show();
                    markersTEST = objects;
                    LatLng coord;
                    for(ParseObject marker : markersTEST) {
                        coord = new LatLng(marker.getDouble("latitude"), marker.getDouble("longitude"));
                        mMap.addMarker(new MarkerOptions()
                            .position(coord)
                            .title(marker.getString("postedBy")));
                    }
                    // Send over the markers to the parent activity
                    if (mListener != null) {
                        mListener.setMarkers(markersTEST);
                    }
                } else {
                    Toast.makeText(getActivity(), "Something went terribly wrong " , Toast.LENGTH_LONG).show();
                }
            }
        });
        /*try {

            int numbersOfMarkers = markers.count();
            Toast.makeText(getActivity(), "Numbers of markers found: " + numbersOfMarkers, Toast.LENGTH_LONG).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        // Move the camera
        LatLng cameraPos = new LatLng(58.39858598, 15.57723999);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPos));
        /*
        // Add a marker for the current location
        LatLng currentLocation = new LatLng(curLatitude, curLongitude);
        mMap.addMarker(new MarkerOptions()
                .position(currentLocation)
                .title("Current Location!!"));

        // Add a marker in Soderkoping and move the camera
        LatLng soderkoping = new LatLng(58.472815, 16.307447);
        mMap.addMarker(new MarkerOptions().position(soderkoping).title("Marker in Söderköping"));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(soderkoping));
    */
    }

    private void setUpmMap() {
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                //Toast.makeText(getActivity(), "Will this work???? " , Toast.LENGTH_LONG).show();
                if (mListener != null) {
                    // Send over the center position of the map
                    mListener.setCenterPos(cameraPosition.target);
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

    /*@Override
    public void onMapLongClick(LatLng point) {
        Toast.makeText(getActivity(), "Callback for long click on map" , Toast.LENGTH_LONG).show();
    }*/

    // Get the position where the map is right now, used for testing when
    // a new marker is added to this position
    /*
    protected LatLng getMapCenterPosition() {
        LatLng centerPos = null;
        centerPos = mMap.getCameraPosition().target;
        return centerPos;
    }
    */

    //private void setCenterPos(LatLng centerPos) {

    //}


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
        public void setCenterPos(LatLng centerPos);
        public void setMarkers(List<ParseObject> markers);
    }

}
