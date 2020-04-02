package com.geekbrains.theweatherapp;

import java.util.ArrayList;
import java.util.List;

class CityStorage {

    static private CityStorage mInstance;
    static private ArrayList<City> mCities;

    private CityStorage() {
        mCities = new ArrayList<>();
    }

    static CityStorage getInstance() {
        if (mInstance == null) {
            mInstance = new CityStorage();
        }
        return mInstance;
    }

    City addCity(String cityName, List<Weather> weathers) {
        City city = new City(cityName, weathers);
        if (mCities.add(city)) {
            return city;
        }
        return null;
    }

    City getCity(final int index) {
        return mCities.get(index);
    }
}
