package com.tommy.gratiskartan;

//import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements  GMapFragment.OnFragmentInteractionListener {

    static final int ADD_NEW_ITEM_REQUEST = 1;
    static final int SEARCH_REQUEST = 2;

    // TODO Use theese for actual user position instead of variable centerPos, possibly use both?
    private double curLatitude = 0;
    private double curLongitude = 0;

    // Used here for testing
    public LatLng centerPos = null;
    public static String CUR_LATITUDE_POS = "curLatitude";
    public static String CUR_LONGITUDE_POS = "cuLongitude";

    private ArrayList<Item> markers = null;


    // Variable to toggle fab icon
    private boolean toggleFab = true;

    // Toggler on the Toolbar
    private MaterialMenuIconToolbar materialMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Enable and initialize parse
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "3pWEzeHgdbgiES4KRdNmp56eoDTHcxhpXsCmAyP9", "JsGTeFwPQEquUqERwKng05XhmyDGsMsNMmHRd8hP");

        // Load data from database in a AsyncTask
        new LoadItems().execute();


        // Add the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        // Add the toggler  to the Toolbar **********************
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Animate icon to arrow
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
                //Toast.makeText(MapsActivity.this, "Clicked the fab", Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * private method to toggle list and map fragment
     * @param showMap boolean
     */
    private void toggleFragment(boolean showMap) {
        if (showMap) {
            GMapFragment gmf = GMapFragment.newInstance();


            Bundle data = new Bundle();
            data.putParcelableArrayList("markers", markers);
            gmf.setArguments(data);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, gmf);

            ft.commit();
        } else {
            ListItemsFragment lf = ListItemsFragment.newInstance();
            Bundle data = new Bundle();

            data.putParcelableArrayList("markers", markers);
            lf.setArguments(data);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.fragment_container, lf);

            ft.commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(MapsActivity.this, "MapsActivity:onResume", Toast.LENGTH_LONG).show();

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
            //startActivity(intent);
            startActivityForResult(intent, ADD_NEW_ITEM_REQUEST);
            return true;
        }
        if (id == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivityForResult(intent, SEARCH_REQUEST);
            return true;
        }
        if (id == R.id.action_clear_search) {
            new LoadItems().execute();
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request code to respond to
        if (requestCode == ADD_NEW_ITEM_REQUEST) {
            // Check if we need to refresh items
            if (resultCode == RESULT_OK) {
                new LoadItems().execute();
            }
        }
        if (requestCode == SEARCH_REQUEST) {
            // Check if user actually made a search
            if (resultCode == RESULT_OK) {
                // Retrive what user searched for and perform search to db
                String searchFor = data.getStringExtra("searchFor");
                //Toast.makeText(MapsActivity.this, searchFor, Toast.LENGTH_SHORT).show();
                new SearchItems().execute(searchFor);

            }
        }
    }

    // Temporary method for getting center pos of map
    @Override
    public void setCenterPos(LatLng centerPos) {
        this.centerPos = centerPos;
    }


    private class LoadItems extends AsyncTask<Void, Void, Void> {

        private ProgressDialog pb = new ProgressDialog(MapsActivity.this);

        @Override
        protected Void doInBackground(Void... params) {

            // Fetch markers from parse and add to map
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Items");
            try {

                List<ParseObject> markersFromParse = query.find();
                // Convert the parseObjects to ArrayList of Items
                ArrayList<Item> items = new ArrayList<Item>();

                Date currentDate = new Date();
                for(ParseObject object : markersFromParse) {
                    // Before converting the objects to Items, check if
                    // the date has expired and if so, remove that object
                    // from the database
                    Date toBeRem = object.getDate("timeToBeRemoved");
                    if (toBeRem.before(currentDate)) {
                        /*System.out.println("*******************************************************");
                        System.out.println(toBeRem + " should be removed. *************************");
                        System.out.println(currentDate + " is the current date. *************************");
                        System.out.println("*******************************************************");
                        */
                        object.deleteInBackground();
                    } else {
                        /*System.out.println("*******************************************************");
                        System.out.println(toBeRem + " should NOT be removed. *************************");
                        System.out.println(currentDate + " is the current date. *************************");
                        System.out.println("*******************************************************");
                        */
                        double latitude = object.getDouble("latitude");
                        double longitude = object.getDouble("longitude");
                        String postedBy = object.getString("createdBy");
                        String category = object.getString("category");
                        String description = object.getString("description");
                        Date toBeRemoved = object.getDate("timeToBeRemoved");
                        Item item = new Item(latitude, longitude, postedBy, category, description, toBeRemoved);
                        items.add(item);
                    }

                }
                markers = items;

            } catch (ParseException e) {
                System.out.println(" ParseException Error");
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            pb.setMessage("Loading...");
            pb.show();

        }

        @Override
        protected void onPostExecute(Void result) {
            pb.dismiss();

            // After we are done loading the database, start fragment
            if (findViewById(R.id.fragment_container) != null) {
                GMapFragment gMapFragment = GMapFragment.newInstance();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                // ******** Could probably be removed *********
                gMapFragment.setArguments(getIntent().getExtras());
                Bundle data = new Bundle();

                data.putParcelableArrayList("markers", markers);
                gMapFragment.setArguments(data);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, gMapFragment).commit();

            }
        }


    }


    private class SearchItems extends AsyncTask<String, Void, Void> {

        private ProgressDialog pb = new ProgressDialog(MapsActivity.this);

        @Override
        protected Void doInBackground(String... params) {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Items");
            try {

                query.whereEqualTo("category", params[0]);
                List<ParseObject> markersFromParse = query.find();
                // Convert the parseObjects to ArrayList of Items
                ArrayList<Item> items = new ArrayList<Item>();

                Date currentDate = new Date();
                for(ParseObject object : markersFromParse) {
                    // Before converting the objects to Items, check if
                    // the date has expired and if so, remove that object
                    // from the database
                    Date toBeRem = object.getDate("timeToBeRemoved");
                    if (toBeRem.before(currentDate)) {

                        object.deleteInBackground();
                    } else {

                        double latitude = object.getDouble("latitude");
                        double longitude = object.getDouble("longitude");
                        String postedBy = object.getString("createdBy");
                        String category = object.getString("category");
                        String description = object.getString("description");
                        Date toBeRemoved = object.getDate("timeToBeRemoved");
                        Item item = new Item(latitude, longitude, postedBy, category, description, toBeRemoved);
                        items.add(item);
                    }

                }
                markers = items;

            } catch (ParseException e) {
                System.out.println(" ParseException Error");
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            pb.setMessage("Searching...");
            pb.show();

        }

        @Override
        protected void onPostExecute(Void result) {
            pb.dismiss();

            // After we are done searching the database, start fragment
            if (findViewById(R.id.fragment_container) != null) {
                GMapFragment gMapFragment = GMapFragment.newInstance();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                gMapFragment.setArguments(getIntent().getExtras());
                Bundle data = new Bundle();

                data.putParcelableArrayList("markers", markers);
                gMapFragment.setArguments(data);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, gMapFragment).commit();

            }
        }


    }
}
