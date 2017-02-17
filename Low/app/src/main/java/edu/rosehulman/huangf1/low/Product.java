package edu.rosehulman.huangf1.low;

import org.json.JSONObject;

import android.icu.text.IDNA;
import android.media.Image;
import android.media.RemoteController;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by huangf1 on 1/15/2017.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Product implements Parcelable{


    private String picUrl;
    private String name;
    private int id;
    private String retailer;
    private String clickUrl;
    private String price;
    private String priceLabel;
    private Image image;
//    private JSONObject metadata;
    private JSONObject products;

    public Product() {
    }

    public Product(String n, String p, String url, String retail, String click){
        name = n;
        priceLabel = p;
        picUrl = url;
        retailer = retail;
        clickUrl = click;
    }

    protected Product(Parcel in) {
        picUrl = in.readString();
        name = in.readString();
        id = in.readInt();
        retailer = in.readString();
        clickUrl = in.readString();
        price = in.readString();
        priceLabel = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public JSONObject getProducts() {
        return products;
    }

    public void setProducts(JSONObject products) {
        this.products = products;
    }
    //
//    public IDNA.Info getMetadata() {
//        return metadata;
//    }
//
//    public void setMetadata(IDNA.Info metadata) {
//        this.metadata = metadata;
//    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public String getPriceLabel() {
        return priceLabel;
    }

    public void setPriceLabel(String priceLabel) {
        this.priceLabel = priceLabel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(picUrl);
        parcel.writeString(name);
        parcel.writeInt(id);
        parcel.writeString(retailer);
        parcel.writeString(clickUrl);
        parcel.writeString(price);
        parcel.writeString(priceLabel);
    }
}

