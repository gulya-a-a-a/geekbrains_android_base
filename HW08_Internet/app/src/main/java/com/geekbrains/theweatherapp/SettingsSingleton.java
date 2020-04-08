package com.geekbrains.theweatherapp;

import androidx.annotation.NonNull;

import java.io.Serializable;

class SettingsSingleton implements Serializable {

    private boolean mShowWindSpeed, mShowPressure;

    private String mSelectedCity;

    private static SettingsSingleton sInstance;

    private SettingsSingleton() {
        mShowPressure = mShowWindSpeed = true;
    }

    static SettingsSingleton getInstance() {
        if (sInstance == null) {
            sInstance = new SettingsSingleton();
        }
        return sInstance;
    }

    void setShowWindSpeed(boolean showWindSpeed) {
        mShowWindSpeed = showWindSpeed;
    }

    void setShowPressure(boolean showPressure) {
        mShowPressure = showPressure;
    }

    boolean getShowWindSpeed() {
        return mShowWindSpeed;
    }

    boolean getShowPressure() {
        return mShowPressure;
    }

    public String getSelectedCity() {
        return mSelectedCity;
    }

    public void setSelectedCity(String selectedCity) {
        mSelectedCity = selectedCity;
    }
}
