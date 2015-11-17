package com.tommy.gratiskartan;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private GPSTracker gps;

    private double curLatitude = 0;
    private double curLongitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Add the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Set a listener to the floating action bar
        findViewById(R.id.fab_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatingActionButton b = (FloatingActionButton) findViewById(R.id.fab_test);
                b.setIcon(R.drawable.ic_map);
                Toast.makeText(MapsActivity.this, "Clicked the fab", Toast.LENGTH_LONG).show();
            }
        });


        // Testing to use GPSTracker
        gps = new GPSTracker(MapsActivity.this);
        if (gps.canGetLocation()) {
            curLatitude = gps.getLatitude();
            curLongitude = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

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
    }
}
