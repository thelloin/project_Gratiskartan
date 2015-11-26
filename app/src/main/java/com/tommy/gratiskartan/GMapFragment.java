package com.tommy.gratiskartan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewGroupCompat;
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
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener, OnMapReadyCallback {

    private GoogleMap mMap;

    protected List<ParseObject> markersTEST = null;
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
                    Toast.makeText(getActivity(), "Probably works " , Toast.LENGTH_LONG).show();
                    markersTEST = objects;
                    LatLng coord;
                    for(ParseObject marker : markersTEST) {
                        coord = new LatLng(marker.getDouble("latitude"), marker.getDouble("longitude"));
                        mMap.addMarker(new MarkerOptions()
                            .position(coord)
                            .title(marker.getString("postedBy")));
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

    //@Override
    public void onCameraChange(CameraPosition position) {
        // TODO How do you find setCenterPos??
        //getActivity().setCenterPos(position.target);
    }

    // Get the position where the map is right now, used for testing when
    // a new marker is added to this position
    /*
    protected LatLng getMapCenterPosition() {
        LatLng centerPos = null;
        centerPos = mMap.getCameraPosition().target;
        return centerPos;
    }
    */
}
