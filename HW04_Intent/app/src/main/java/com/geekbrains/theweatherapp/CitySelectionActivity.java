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

        configAllControls();

        if (savedInstanceState == null) {
            initSettings();
        }
    }

    private void configAllControls() {
        mSelectedCity = (EditText) findViewById(R.id.selected_city);
        mSelectedCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                SettingsSingleton.getInstance().setSelectedCity(mSelectedCity.getText().toString());
            }
        });

        mWindSpeedCB = (CheckBox) findViewById(R.id.wind_speed_cb);
        mWindSpeedCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsSingleton.getInstance().setShowWindSpeed(mWindSpeedCB.isChecked());
            }
        });
        mPressureCB = (CheckBox) findViewById(R.id.pressure_cb);
        mPressureCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsSingleton.getInstance().setShowPressure(mPressureCB.isChecked());
            }
        });

        mSaveButton = (Button) findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnswer(RESULT_OK);
            }
        });

        mCancelButton = (Button) findViewById(R.id.cancel_button);
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

    public static SettingsSingleton getSettingsFromIntent(Intent data){
        return (SettingsSingleton) data.getSerializableExtra(mSettingsTag);
    }

    private void initSettings() {
        SettingsSingleton.getInstance().setSelectedCity(mSelectedCity.getText().toString());
        SettingsSingleton.getInstance().setShowWindSpeed(mWindSpeedCB.isChecked());
        SettingsSingleton.getInstance().setShowPressure(mPressureCB.isChecked());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        SettingsSingleton settings = (SettingsSingleton) savedInstanceState.getSerializable(mSettingsTag);
        if (settings != null) {
            mSelectedCity.setText(SettingsSingleton.getInstance().getSelectedCity());
            mPressureCB.setChecked(SettingsSingleton.getInstance().getShowPressure());
            mWindSpeedCB.setChecked(SettingsSingleton.getInstance().getShowWindSpeed());
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        SettingsSingleton.getInstance().setSelectedCity(mSelectedCity.getText().toString());
        outState.putSerializable(mSettingsTag, SettingsSingleton.getInstance());
    }
}

