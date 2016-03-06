package design.semicolon.modarenta.models;

import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by rakhe on 3/4/2016.
 */
@Parcel
public class Item {
    String mID;
    String mName;
    Float mCoordinates[];

    public Item(){

    }
    public Item(String name, LatLng point) {
        mName = name;
        mPoint = point;
    }

    public static ArrayList<Item> loadItems()
    {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("Sandals",new LatLng(37.371859,-122.021234)));
        items.add(new Item("coat",new LatLng(37.371715,-122.045628)));
        items.add(new Item("Gown",new LatLng(37.385015,-122.014943)));
        return  items;
    }
    public Float[] getCoordinates() {
        return mCoordinates;
    }

    public void setCoordinates(Float[] coordinates) {
        mCoordinates = coordinates;
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public LatLng getPoint() {
        return mPoint;
    }

    public void setPoint(LatLng point) {
        mPoint = point;
    }

    LatLng mPoint;
    String mLocation;
}
