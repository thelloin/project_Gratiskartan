package com.tommy.gratiskartan;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Date;

/**
 * Created by tommy on 11/24/15.
 * A simple class representing a item.
 */
public class Item implements Parcelable {

    public static final MyCreator CREATOR = new MyCreator();

    public double latitude;
    public double longitude;
    public String author;
    public String category;
    // time to live - Date()????
    public Date toBeRemoved;
    public String description;
    // picture - String url?????

    /**
     * This will be used only by the MyCreator
     * @param source
     */
    public Item(Parcel source) {
        /*
         * Reconstruct from the parcel
         */
        Log.d("Item", "Item(Parcel source): time to put back parcel data");
        latitude = source.readDouble();
        longitude = source.readDouble();
        author = source.readString();
        category = source.readString();
        description = source.readString();
        toBeRemoved = new Date(source.readLong());

    }

    public Item(double latitude, double longitude, String author,
                String category, String description, Date toBeRemoved) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.author = author;
        this.category = category;
        this.description = description;
        this.toBeRemoved = toBeRemoved;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.d("Item", "writeToParcel..." + flags);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(author);
        dest.writeString(category);
        dest.writeString(description);
        dest.writeLong(toBeRemoved.getTime());
    }

    /**
     * Required class for Parcelable
     */
    public static class MyCreator implements Parcelable.Creator<Item> {
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }
        public Item[] newArray(int size) {
            return new Item[size];
        }
    }
}
