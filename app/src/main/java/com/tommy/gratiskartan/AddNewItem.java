package com.tommy.gratiskartan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddNewItem extends AppCompatActivity {

    private double curLatitude, curLongitude;

    // Back arrow button on the toolbar
    private MaterialMenuIconToolbar backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        Intent intent = getIntent();
        curLatitude = intent.getDoubleExtra(MapsActivity.CUR_LATITUDE_POS, 0);
        curLongitude = intent.getDoubleExtra(MapsActivity.CUR_LONGITUDE_POS, 0);

        //Toast.makeText(AddNewItem.this, curLatitude + " : " + curLongitude, Toast.LENGTH_LONG).show();
        // Setting up and configure the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.add_item_toolbar);
        setSupportActionBar(myToolbar);

        backArrow = new MaterialMenuIconToolbar(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN) {
            @Override
            public int getToolbarViewId() {
                return R.id.add_item_toolbar;
            }
        };
        backArrow.setState(MaterialMenuDrawable.IconState.ARROW);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Executes when back arrow is pressed
                // Nothing is saved, no need to reload db in main_activity
                setResult(RESULT_CANCELED);
                finish();
            }
        });


        Button addItemButton = (Button) findViewById(R.id.btn_add_item);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here we add the item to the database and return to main activity
                //saveItemToBD();
                new SaveItemToDB().execute();
                //finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_item_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        //Toast.makeText(MapsActivity.this, "Clicked a menuOption", Toast.LENGTH_SHORT).show();
        int id = menuItem.getItemId();
        if (id == R.id.menu_add_item_done) {
            new SaveItemToDB().execute();
            return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }


    private ParseObject getObjectToSave() {
        Spinner spinner_cat = (Spinner) findViewById(R.id.spinner_category);
        String category = String.valueOf(spinner_cat.getSelectedItem());

        EditText ed_description = (EditText) findViewById(R.id.ed_description);
        String description = ed_description.getText().toString();


        double lat = 0.0;
        double longi = 0.0;
        RadioButton my_pos = (RadioButton) findViewById(R.id.radio_btn_my_position);
        if (my_pos.isChecked()) {
            GPSTracker gps = new GPSTracker(AddNewItem.this);
            if (gps.canGetLocation()) {
                lat = gps.getLatitude();
                longi = gps.getLongitude();
            } else {
                gps.showSettingsAlert();
            }
        } else {
            lat = curLatitude;
            longi = curLongitude;
        }

        Spinner spinner_ttl = (Spinner) findViewById(R.id.spinner_days);
        //int ttl = Integer.getInteger(String.valueOf(spinner_ttl.getSelectedItem()));
        int ttl = Integer.parseInt(String.valueOf(spinner_ttl.getSelectedItem()));
        Date toBeRemoved = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(toBeRemoved);
        c.add(Calendar.DATE, ttl);
        toBeRemoved = c.getTime();


        ParseObject object = new ParseObject("Items");
        object.put("latitude", lat);
        object.put("longitude", longi);
        object.put("createdBy", "Anonymous");
        object.put("category",category);
        object.put("description", description);
        object.put("timeToBeRemoved", toBeRemoved);
        //object.saveInBackground();
        /*try {
            object.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        return object;

    }

    private class SaveItemToDB extends AsyncTask<Void, Void, Void> {

        private ProgressDialog pb = new ProgressDialog(AddNewItem.this);

        @Override
        protected Void doInBackground(Void... params) {

            ParseObject objToSave = getObjectToSave();

            try {
                objToSave.save();
            } catch (ParseException e) {
                e.printStackTrace();
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
            pb.setMessage("Saving...");
            pb.show();

        }

        @Override
        protected void onPostExecute(Void result) {
            pb.dismiss();

            // Saved item to DB, set flag and return..
            setResult(RESULT_OK);
            finish();


        }


    }
}
