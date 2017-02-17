package edu.rosehulman.huangf1.low;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by thompsar on 2/9/2017.
 */

public class SubCategory implements Parcelable{

    private String subcategoryName;
    private int subcategoryCode;

    protected SubCategory(Parcel in) {
        subcategoryName = in.readString();
        subcategoryCode = in.readInt();
    }

    public static final Creator<SubCategory> CREATOR = new Creator<SubCategory>() {
        @Override
        public SubCategory createFromParcel(Parcel in) {
            return new SubCategory(in);
        }

        @Override
        public SubCategory[] newArray(int size) {
            return new SubCategory[size];
        }
    };

    public SubCategory() {
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public int getSubcategoryCode() {
        return subcategoryCode;
    }

    public void setSubcategoryCode(int subcategoryCode) {
        this.subcategoryCode = subcategoryCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subcategoryName);
        dest.writeInt(subcategoryCode);
    }
}
