package com.tommy.gratiskartan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class ItemInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        Item item = getIntent().getParcelableExtra("item");
        /*TextView postedBy = (TextView) findViewById(R.id.tv_info_postedBy);
        postedBy.setText(item.author);*/

        TextView category = (TextView) findViewById(R.id.tv_info_category);
        category.setText(item.category);

        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM 'kl' kk:mm");

        TextView availableTo = (TextView) findViewById(R.id.tv_info_available_to);
        availableTo.setText(dateFormat.format(item.toBeRemoved));

        TextView description = (TextView) findViewById(R.id.tv_info_description);
        description.setText(item.description);

        Button backButton = (Button) findViewById(R.id.btn_info_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
