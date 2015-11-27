package com.tommy.gratiskartan;

//import android.app.FragmentTransaction;
import android.content.Intent;
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
import com.google.android.gms.maps.model.LatLng;
import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements  GMapFragment.OnFragmentInteractionListener {

    private GoogleMap mMap;

    private GPSTracker gps;

    private double curLatitude = 0;
    private double curLongitude = 0;

    // Used here for testing
    public LatLng centerPos = null;
    public static String CUR_LATITUDE_POS = "curLatitude";
    public static String CUR_LONGITUDE_POS = "cuLongitude";


    // Variable to toggle fab icon
    private boolean toggleFab = true;

    // Toggler on the Toolbar
    private MaterialMenuIconToolbar materialMenu;

    public static final Item[] DUMMY_ITEMS =
            {
                    new Item(58.39296355, 15.57187557, "Author1", "Category1", "Description1"),
                    new Item(58.3941106, 15.57432175, "Author2", "Category2", "Description2"),
                    new Item(58.3957299, 15.57960033, "Author3", "Category3", "Description3"),
                    new Item(58.39730415, 15.57470798, "Author4", "Category4", "Description4"),
                    new Item(58.39872091, 15.57397842, "Author5", "Category5", "Description5"),
                    new Item(58.40157675, 15.57427883, "Author6", "Category6", "Description6"),
                    new Item(58.40315074, 15.58011532, "Author7", "Category7", "Description7"),
                    new Item(58.40389274, 15.57625294, "Author8", "Category8", "Description8")
            };

    public static final ArrayList<Item> DUMMY_ARRAY_LIST = new ArrayList<Item>() {{
        add(new Item(58.39296355, 15.57187557, "Author1", "Category1", "Description1"));
        add(new Item(58.3941106, 15.57432175, "Author2", "Category2", "Description2"));
        add(new Item(58.3957299, 15.57960033, "Author3", "Category3", "Description3"));
        add(new Item(58.39730415, 15.57470798, "Author4", "Category4", "Description4"));
        add(new Item(58.39872091, 15.57397842, "Author5", "Category5", "Description5"));
        add(new Item(58.40157675, 15.57427883, "Author6", "Category6", "Description6"));
        add(new Item(58.40315074, 15.58011532, "Author7", "Category7", "Description7"));
        add(new Item(58.40389274, 15.57625294, "Author8", "Category8", "Description8"));
    }};

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

        // Enable and initialize parse
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "3pWEzeHgdbgiES4KRdNmp56eoDTHcxhpXsCmAyP9", "JsGTeFwPQEquUqERwKng05XhmyDGsMsNMmHRd8hP");

        // Testing the Parse SDK
        // Adding testObject
        /*ParseObject testObject = new ParseObject("TestMarkers");
        testObject.put("latitude", 58.3957299);
        testObject.put("longitude", 15.57960033);
        testObject.put("postedBy", "DummyUser3");
        testObject.put("category", "category3");
        testObject.put("description", "description3");
        testObject.saveInBackground();
        */

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
        //Toast.makeText(MapsActivity.this, "Clicked a menuOption", Toast.LENGTH_SHORT).show();
        int id = menuItem.getItemId();
        if (id == R.id.action_addItem) {
            Intent intent = new Intent(this, AddNewItem.class);
            intent.putExtra(CUR_LATITUDE_POS, centerPos.latitude);
            intent.putExtra(CUR_LONGITUDE_POS, centerPos.longitude);
            startActivity(intent);
            return true;
        }


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

    // Temporary method for getting center pos of map
    @Override
    public void setCenterPos(LatLng centerPos) {
        this.centerPos = centerPos;
        //Toast.makeText(MapsActivity.this, "Callback from GMapFragment", Toast.LENGTH_LONG).show();
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
