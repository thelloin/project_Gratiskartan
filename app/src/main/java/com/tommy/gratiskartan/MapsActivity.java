package com.tommy.gratiskartan;

//import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.GoogleMap;

public class MapsActivity extends AppCompatActivity { //implements OnMapReadyCallback {

    private GoogleMap mMap;

    private GPSTracker gps;

    private double curLatitude = 0;
    private double curLongitude = 0;

    // Variable to toggle fab icon
    private boolean toggleFab = true;

    // Toggler on the Toolbar
    private MaterialMenuIconToolbar materialMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Testing to add GMapFragment at runtime
        if (findViewById(R.id.fragment_container) != null) {
            GMapFragment gMapFragment = GMapFragment.newInstance();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            gMapFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, gMapFragment).commit();
        }

        // Add the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        // Add the toggler  to the Toolbar **********************
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle your drawable state here
                materialMenu.animateState(MaterialMenuDrawable.IconState.ARROW);
            }
        });
        materialMenu = new MaterialMenuIconToolbar(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN) {
            @Override
            public int getToolbarViewId() {
                return R.id.my_toolbar;
            }
        };
        // ******************************************************

        // Set a listener to the floating action bar
        findViewById(R.id.fab_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatingActionButton b = (FloatingActionButton) findViewById(R.id.fab_test);
                if (toggleFab) {
                    b.setIcon(R.drawable.ic_map);
                    toggleFragment(!toggleFab);
                } else {
                    b.setIcon(R.drawable.ic_list);
                    toggleFragment(!toggleFab);
                }
                toggleFab = !toggleFab;
                Toast.makeText(MapsActivity.this, "Clicked the fab", Toast.LENGTH_LONG).show();
            }
        });


        // Testing to use GPSTracker
        /*
        gps = new GPSTracker(MapsActivity.this);
        if (gps.canGetLocation()) {
            curLatitude = gps.getLatitude();
            curLongitude = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }*/

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
    }

    /**
     * private method to toggle list and map fragment
     * @param showMap
     * @return void
     */
    private void toggleFragment(boolean showMap) {
        if (showMap) {
            GMapFragment gmf = GMapFragment.newInstance();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, gmf);
            //ft.addToBackStack(null);
            //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
        } else {
            ListItemsFragment lf = ListItemsFragment.newInstance();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, lf);
            //ft.addToBackStack(null);
            //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Toast.makeText(MapsActivity.this, "Clicked a menuOption", Toast.LENGTH_SHORT).show();



        //FragmentTransaction ft = getFragmentManager().beginTransaction();
        //ft.replace(R.id.main_linear_layout,lf);
        //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //ft.commit();
        /*ListItemsFragment lf = ListItemsFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_linear_layout,lf);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        ft.commit();*/


        return super.onOptionsItemSelected(menuItem);
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
    /*@Override
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
    }*/
}
