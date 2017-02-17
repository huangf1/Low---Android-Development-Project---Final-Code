package edu.rosehulman.huangf1.low;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static edu.rosehulman.huangf1.low.ProductAdapter.EXTRA_CLICKURL;
import static edu.rosehulman.huangf1.low.ProductAdapter.EXTRA_IMAGE_ID;
import static edu.rosehulman.huangf1.low.ProductAdapter.EXTRA_PRICE;
import static edu.rosehulman.huangf1.low.ProductAdapter.EXTRA_RETAILER;

public class ShowSavedProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_saved_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        ListView listView = (ListView)findViewById(R.id.list_view_saved_product);
        ArrayList<String> savedProductName = new ArrayList<>();
        for (int i = 0; i < MainActivity.mSavedProducts.size(); i++) {
            savedProductName.add(MainActivity.mSavedProducts.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, savedProductName);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getBaseContext(), ProductDetailActivity.class);

                intent.putExtra(ProductAdapter.EXTRA_NAME, MainActivity.mSavedProducts.get(position).getName());
                intent.putExtra(EXTRA_IMAGE_ID, MainActivity.mSavedProducts.get(position).getPicUrl());

                intent.putExtra(EXTRA_PRICE, MainActivity.mSavedProducts.get(position).getPriceLabel());
                Log.d("TAG", "PRICE_IN: " + MainActivity.mSavedProducts.get(position).getPriceLabel());

                intent.putExtra(EXTRA_RETAILER, MainActivity.mSavedProducts.get(position).getRetailer());

                intent.putExtra(EXTRA_CLICKURL, MainActivity.mSavedProducts.get(position).getClickUrl());

                startActivity(intent);
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
