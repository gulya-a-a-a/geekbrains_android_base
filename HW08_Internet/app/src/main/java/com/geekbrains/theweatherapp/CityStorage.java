package com.geekbrains.theweatherapp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class CityStorage {

    static private CityStorage mInstance;
    static private LinkedList<City> mCities;

    private CityStorage() {
        mCities = new LinkedList<>();
    }

    static CityStorage getInstance() {
        if (mInstance == null) {
            mInstance = new CityStorage();
        }
        return mInstance;
    }

    City addCity(String cityName) {
        City city = new City(cityName);
        mCities.addFirst(city);
        return city;
    }

    City findCity(String cityName) {
        City foundCity;
        for (int i = 0; i < mCities.size(); i++) {
            foundCity = mCities.get(i);
            if (foundCity.getCityName().equals(cityName)) {
                return foundCity;
            }
        }
        return null;
    }

    City getCity(final int index) {
        return mCities.get(index);
    }

    int getStorageSize() {
        return mCities.size();
    }
}
