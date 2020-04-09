package com.geekbrains.theweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class CitySelectionActivity extends AppCompatActivity {

    private CheckBox mWindSpeedCB, mPressureCB;
    private EditText mSelectedCity;
    private Button mSaveButton, mCancelButton;

    private static final String mSettingsTag = "WeatherSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);

        findControls();
        configControls();

        if (savedInstanceState == null) {
            putSettings();
        }
    }

    private void findControls(){
        mSelectedCity = findViewById(R.id.selected_city);
        mWindSpeedCB = findViewById(R.id.wind_speed_cb);
        mPressureCB = findViewById(R.id.pressure_cb);
        mSaveButton = findViewById(R.id.save_button);
        mCancelButton = findViewById(R.id.cancel_button);
    }

    private void configControls() {
        mSelectedCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                SettingsSingleton.getInstance().setSelectedCity(mSelectedCity.getText().toString());
            }
        });
        mWindSpeedCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsSingleton.getInstance().setShowWindSpeed(mWindSpeedCB.isChecked());
            }
        });
        mPressureCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsSingleton.getInstance().setShowPressure(mPressureCB.isChecked());
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnswer(RESULT_OK);
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnswer(RESULT_CANCELED);
            }
        });
    }

    private void setAnswer(int result) {
        Intent settings = new Intent();
        if (result == RESULT_OK) {
            SettingsSingleton.getInstance().setSelectedCity(mSelectedCity.getText().toString());
            settings.putExtra(mSettingsTag, SettingsSingleton.getInstance());
        }
        setResult(result, settings);
        finish();
    }

    public static SettingsSingleton getSettingsFromIntent(Intent data) {
        return (SettingsSingleton) data.getSerializableExtra(mSettingsTag);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        SettingsSingleton.getInstance().setSelectedCity(mSelectedCity.getText().toString());
    }

    private void putSettings() {
        mSelectedCity.setText(SettingsSingleton.getInstance().getSelectedCity());
        mPressureCB.setChecked(SettingsSingleton.getInstance().getShowPressure());
        mWindSpeedCB.setChecked(SettingsSingleton.getInstance().getShowWindSpeed());
    }

    private void getSettings() {
        SettingsSingleton.getInstance().setSelectedCity(mSelectedCity.getText().toString());
        SettingsSingleton.getInstance().setShowWindSpeed(mWindSpeedCB.isChecked());
        SettingsSingleton.getInstance().setShowPressure(mPressureCB.isChecked());
    }
}

