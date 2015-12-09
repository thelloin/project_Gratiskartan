package com.tommy.gratiskartan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    private void search() {

        Spinner spinner_cat = (Spinner) findViewById(R.id.spinner);
        String category = String.valueOf(spinner_cat.getSelectedItem());
        Intent data = new Intent();
        data.putExtra("searchFor", category);
        setResult(RESULT_OK, data);


        finish();
    }
}
