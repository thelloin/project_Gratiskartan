package com.tommy.gratiskartan;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseObject;

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

        Toast.makeText(AddNewItem.this, curLatitude + " : " + curLongitude, Toast.LENGTH_LONG).show();
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
                finish();
            }
        });
        // ************************************************


        Button addItemButton = (Button) findViewById(R.id.btn_add_item);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here we add the item to the database and return to main activity
                saveItemToBD();
                finish();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_item_menu, menu);
        return true;
    }

    private void saveItemToBD() {
        /*ParseObject testObject = new ParseObject("TestMarkers");
        testObject.put("latitude", 58.3957299);
        testObject.put("longitude", 15.57960033);
        testObject.put("postedBy", "DummyUser3");
        testObject.put("category", "category3");
        testObject.put("description", "description3");
        testObject.saveInBackground();
        */
        Spinner spinner_cat = (Spinner) findViewById(R.id.spinner_category);
        String categoty = String.valueOf(spinner_cat.getSelectedItem());

        EditText ed_description = (EditText) findViewById(R.id.ed_description);
        String description = ed_description.getText().toString();

        ParseObject object = new ParseObject("TestMarkers");
        object.put("latitude", curLatitude);
        object.put("longitude", curLongitude);
        object.put("postedBy", "User");
        object.put("category",categoty);
        object.put("description", description);
        object.saveInBackground();



    }
}
