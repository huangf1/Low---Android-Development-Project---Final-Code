package edu.rosehulman.huangf1.low;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView mNameTextView;
    private ImageView mImageView;
    private TextView mPriceTextView;
    private TextView mRetailerTextView;
    private Button mBuyButton;
    private String mUrl;
    private Button mSaveButton;

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mNameTextView = (TextView)findViewById(R.id.product_name_textView);
        mImageView = (ImageView)findViewById(R.id.product_imageView);
        mPriceTextView = (TextView)findViewById(R.id.price_textView);
        mRetailerTextView = (TextView)findViewById(R.id.retailer_name_textView);

        mIntent = getIntent();
        mNameTextView.setText(mIntent.getStringExtra(ProductAdapter.EXTRA_NAME));
        mPriceTextView.setText("" + mIntent.getStringExtra(ProductAdapter.EXTRA_PRICE));
        Log.d("TAG", "PRICE_OUT: " + mIntent.getStringExtra(ProductAdapter.EXTRA_PRICE));
//        mPriceTextView.setText("$99.99");
        mRetailerTextView.setText(mIntent.getStringExtra(ProductAdapter.EXTRA_RETAILER));
        mUrl = mIntent.getStringExtra(ProductAdapter.EXTRA_CLICKURL);
//        mRetailerTextView.setText("Amazon.com");
//        mImageView.setImageResource(intent.getIntExtra(ProductAdapter.EXTRA_IMAGE_ID, 0));
        String url = mIntent.getStringExtra(ProductAdapter.EXTRA_IMAGE_ID);
        (new GetImageTask(mImageView)).execute(url);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBuyButton = (Button)findViewById(R.id.buy_button);
        mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(mUrl));
                startActivity(i);
            }
        });

        mSaveButton = (Button)findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSaveButton.setText("SAVED");
                MainActivity.mSavedProducts.add(
                        new Product(mIntent.getStringExtra(ProductAdapter.EXTRA_NAME),
                                "" + mIntent.getStringExtra(ProductAdapter.EXTRA_PRICE),
                                mIntent.getStringExtra(ProductAdapter.EXTRA_IMAGE_ID),
                                mIntent.getStringExtra(ProductAdapter.EXTRA_RETAILER),
                                mIntent.getStringExtra(ProductAdapter.EXTRA_CLICKURL)));

            }
        });
    }

}
