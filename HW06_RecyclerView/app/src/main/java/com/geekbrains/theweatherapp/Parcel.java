package com.geekbrains.theweatherapp;

import java.io.Serializable;

public class Parcel implements Serializable {

    public static final String PARCEL_TAG = "CityData";

    private City mCity;
    private String mCityName;
    private int mIndex;

    Parcel(int index, String cityName, City city) {
        mIndex = index;
        mCityName = cityName;
        mCity = city;
    }

    public int getIndex() {
        return mIndex;
    }

    public String getCityName() {
        return mCityName;
    }

    public City getCity() {
        return mCity;
    }
}
