package com.geekbrains.theweatherapp;

import java.io.Serializable;

public class Parcel implements Serializable {

    public static final String PARCEL_TAG = "CityData";

    private City mCity;
    private int mIndex;

    Parcel(int index, City city) {
        mIndex = index;
        mCity = city;
    }

    public int getIndex() {
        return mIndex;
    }

    public City getCity() {
        return mCity;
    }
}
