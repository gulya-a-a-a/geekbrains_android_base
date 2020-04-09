package com.geekbrains.theweatherapp;

import java.io.Serializable;
import java.util.List;

public class City implements Serializable {
    private String mCityName;
    private List<Weather> mWeathers;

    public City(String cityName, List<Weather> weathers) {
        mCityName = cityName;
        mWeathers = weathers;
    }

    public List<Weather> getWeathers() {
        return mWeathers;
    }

    public String getCityName() {
        return mCityName;
    }
}

