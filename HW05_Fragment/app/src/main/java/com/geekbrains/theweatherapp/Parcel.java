package com.geekbrains.theweatherapp;

import java.io.Serializable;

public class Parcel implements Serializable {

    public static final String PARCEL_TAG = "CityData";

    private String mCityName;
    private int mIndex;

    public Parcel(int index, String cityName){
        mIndex = index;
        mCityName = cityName;
    }

    public int getIndex() {
        return mIndex;
    }

    public String getCityName() {
        return mCityName;
    }
}
