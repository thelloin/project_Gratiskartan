package com.tommy.gratiskartan;

/**
 * Created by tommy on 11/24/15.
 * A simple class representing a free item.
 */
public class Item {
    public double latitude;
    public double longitude;
    public String author;
    public String category;
    // time to live - Date()????
    public String description;
    // picture - String url?????

    public Item(double latitude, double longitude, String author,
                String category, String description) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.author = author;
        this.category = category;
        this.description = description;
    }
}
