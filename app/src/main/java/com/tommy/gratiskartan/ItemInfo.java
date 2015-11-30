package com.tommy.gratiskartan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ItemInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        Item item = getIntent().getParcelableExtra("item");
        TextView postedBy = (TextView) findViewById(R.id.tv_info_postedBy);
        postedBy.setText(item.author);

        TextView category = (TextView) findViewById(R.id.tv_info_category);
        category.setText(item.category);

        TextView description = (TextView) findViewById(R.id.tv_info_description);
        description.setText(item.description);

    }
}
