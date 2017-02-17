package edu.rosehulman.huangf1.low;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by thompsar on 1/16/2017.
 */
public class GetProductsTask extends AsyncTask<String, Void, ArrayList<Product>>{

    private ProductAdapter mUserActivity;
    String urlString;

    public GetProductsTask(ProductAdapter activity) {
        mUserActivity = activity;
    }

    @Override
    protected ArrayList<Product> doInBackground(String... urlStrings) {
        urlString = urlStrings[0];
        ObjectNode node = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            node = mapper.readValue(new URL(urlString), ObjectNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayNode products = (ArrayNode) node.get("products");

        ArrayList<Product> prods = new ArrayList<>();
        for (int i=0; i<products.size(); i++){
            String name = products.get(i).get("name").asText();
            String price = products.get(i).get("priceLabel").asText();
            String retailer = products.get(i).get("retailer").get("name").asText();
            String imageUrl = products.get(i).get("image").get("sizes").get("Original").get("url").asText();
            String clickUrl = products.get(i).get("clickUrl").asText();
            Product p = new Product(name , price, imageUrl, retailer, clickUrl);
            mUserActivity.addProduct(p);
            prods.add(p);
        }


//        Product product = null;
//        try {
//            product = (new ObjectMapper()).readValue(new URL(urlString), Product.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return prods;
    }

    @Override
    protected void onPostExecute(ArrayList<Product> products) {
        super.onPostExecute(products);
        mUserActivity.notifyDataSetChanged();
    }
}
