package com.geekbrains.theweatherapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class City implements Serializable {
    private String mCityName;
    private List<Weather> mWeathers;

    City(String cityName) {
        mCityName = cityName;
        mWeathers = new ArrayList<>();
    }

    String getCityName() {
        return mCityName;
    }

    List<Weather> getWeathers() {
        return mWeathers;
    }

    public void setWeathers(List<Weather> weathers) {
        mWeathers = weathers;
    }
}

