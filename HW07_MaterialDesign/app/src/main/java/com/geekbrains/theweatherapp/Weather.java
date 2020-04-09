package com.geekbrains.theweatherapp;

import java.io.Serializable;

public class Weather implements Serializable {
    private int mDrawableID;
    private int mTemp;

    public Weather(int id, int temp) {
        mTemp = temp;
        mDrawableID = id;
    }

    public int getDrawableID() {
        return mDrawableID;
    }

    public int getTemp() {
        return mTemp;
    }
}
