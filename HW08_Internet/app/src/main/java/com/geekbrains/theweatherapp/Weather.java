package com.geekbrains.theweatherapp;

import java.io.Serializable;

class Weather implements Serializable {

    private int mDrawableID;
    private int mTemp,
            mPressure,
            mWindSpeed;

    Weather() {    }

    int getTemp() {
        return mTemp;
    }

    int getPressure() {
        return mPressure;
    }

    void setPressure(int pressure) {
        mPressure = pressure;
    }

    int getWindSpeed() {
        return mWindSpeed;
    }

    void setWindSpeed(int windSpeed) {
        mWindSpeed = windSpeed;
    }

    int getDrawableID() {
        return mDrawableID;
    }

    void setDrawableID(int weatherCode) {
        if (weatherCode < 300) {
            mDrawableID = R.drawable.storm;
        } else if (weatherCode < 600){
            mDrawableID = R.drawable.rain;
        } else if (weatherCode < 700){
            mDrawableID = R.drawable.snow;
        } else if (weatherCode == 800) {
            mDrawableID = R.drawable.sunny;
        } else {
            mDrawableID = R.drawable.cloudy;
        }
    }

    public void setTemp(int temp) {
        mTemp = temp;
    }
}
