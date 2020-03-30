package com.geekbrains.theweatherapp;

import androidx.annotation.NonNull;

import java.io.Serializable;

class SettingsSingleton implements Serializable {

    private boolean mShowWindSpeed, mShowPressure;
    private float mTempValue;

    private String mSelectedCity;

    private static SettingsSingleton sInstance;

    private SettingsSingleton() {
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

    void setTempValue(float tempValue) {
        mTempValue = tempValue;
    }

    void RestoreSettings(@NonNull SettingsSingleton restoredSettings) {
        getInstance().setTempValue(restoredSettings.getTempValue());
        getInstance().setShowPressure(restoredSettings.getShowPressure());
        getInstance().setShowWindSpeed(restoredSettings.getShowWindSpeed());
    }

    boolean getShowWindSpeed() {
        return mShowWindSpeed;
    }

    boolean getShowPressure() {
        return mShowPressure;
    }

    float getTempValue() {
        return mTempValue;
    }

    public String getSelectedCity() {
        return mSelectedCity;
    }

    public void setSelectedCity(String selectedCity) {
        mSelectedCity = selectedCity;
    }
}
