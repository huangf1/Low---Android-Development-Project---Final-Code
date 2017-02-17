package edu.rosehulman.huangf1.low;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static edu.rosehulman.huangf1.low.R.id.imageView;
import static edu.rosehulman.huangf1.low.R.id.retailerNameText;
import static edu.rosehulman.huangf1.low.R.id.retailer_name_textView;

/**
 * Created by thompsar on 1/15/2017.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public static final String EXTRA_CLICKURL = "EXTRA_CLICKURL";
    private int mCode;
    private SubCategory mCategory;
    private Context mContext;
    private ArrayList<Product> mProducts;
    private RecyclerView mRecyclerView;
    private String[] mSearchQuery;
    private String mUrl;
    private ViewHolder mView;

    public static final String EXTRA_NAME = "EXTRA_NAME";
    public static final String EXTRA_IMAGE_ID = "EXTRA_IMAGE_ID";
    public static final String EXTRA_PRICE = "EXTRA_PRICE";
    public static final String EXTRA_RETAILER = "EXTRA_RETAILER";

    public ProductAdapter(Context context, RecyclerView view, String searchQuery, SubCategory category) {
        mContext = context;
        mRecyclerView = view;
        mCategory = category;
        mSearchQuery = searchQuery.split(" ");
        mProducts = new ArrayList<>();

        mUrl = "https://api.shopstyle.com/api/v2/products?pid=uid8681-34276459-70&fts=";

        if (mCategory != null){
            mCode = mCategory.getSubcategoryCode();
            if (mCode==2001 || mCode==2002 || mCode==2003 || mCode==2004 || mCode==2005) {
                mUrl = mUrl + "womens+";
                mUrl = mUrl + mCategory.getSubcategoryName();
            }
            else{
                mUrl = mUrl + "mens+";
                mUrl = mUrl + mCategory.getSubcategoryName();
            }
        }
        else {

            //load products
            for (int i = 0; i < mSearchQuery.length; i++) {
                mUrl = mUrl + mSearchQuery[i] + "+";
            }
            mUrl = mUrl.substring(0, mUrl.length() - 1);
        }
        mUrl = mUrl + "&sort=PriceLoHi&offset=0&limit=100";
        (new GetProductsTask(this)).execute(mUrl);

    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Set Text", "set");
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
        holder.priceText.setText(mProducts.get(position).getPriceLabel());
        holder.productNameText.setText(mProducts.get(position).getName());
        holder.retailNameText.setText(mProducts.get(position).getRetailer());
        (new GetImageTask(holder.imageView)).execute(mProducts.get(position).getPicUrl());
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
//        return 0;
    }

    public void addProduct(Product product) {
        Log.d("Product Added", "added");
        mProducts.add(product);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

        private TextView retailNameText;
        private ImageView imageView;
        private TextView priceText;
        private TextView productNameText;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            priceText = (TextView) itemView.findViewById(R.id.productPriceText);
            productNameText = (TextView) itemView.findViewById(R.id.productNameText);
            retailNameText = (TextView) itemView.findViewById(R.id.retailerNameText);
            itemView.setOnTouchListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent detailIntent = new Intent(mContext, ProductDetailActivity.class);

                    detailIntent.putExtra(EXTRA_NAME, mProducts.get(position).getName());
                    detailIntent.putExtra(EXTRA_IMAGE_ID, mProducts.get(position).getPicUrl());

                    detailIntent.putExtra(EXTRA_PRICE, mProducts.get(position).getPriceLabel());
                    Log.d("TAG", "PRICE_IN: " + mProducts.get(position).getPriceLabel());

                    detailIntent.putExtra(EXTRA_RETAILER, mProducts.get(position).getRetailer());

                    detailIntent.putExtra(EXTRA_CLICKURL, mProducts.get(position).getClickUrl());

                    mContext.startActivity(detailIntent);
                }
            });

        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {

            return false;
        }
    }
}