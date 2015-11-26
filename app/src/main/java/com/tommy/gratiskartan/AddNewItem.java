package com.tommy.gratiskartan;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;
import com.google.android.gms.maps.model.LatLng;

public class AddNewItem extends AppCompatActivity {

    // Back arrow button on the toolbar
    private MaterialMenuIconToolbar backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);


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
        //LatLng centerPos = get
    }
}
