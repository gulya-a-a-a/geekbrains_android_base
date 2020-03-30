package com.geekbrains.theweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CheckBox mWindSpeedCB, mPressureCB;
    private EditText mSelectedCity;
    private static final String mClassName = "MainActivity";

    private static final String mSettingsTag = "WeatherSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);

        Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();

        configAllControls();

        if (savedInstanceState == null) {
            initSettings();
        }
    }

    private void initSettings() {
        SettingsSingleton.getInstance().setSelectedCity(mSelectedCity.getText().toString());
        SettingsSingleton.getInstance().setShowWindSpeed(mWindSpeedCB.isChecked());
        SettingsSingleton.getInstance().setShowPressure(mPressureCB.isChecked());
    }

    private void configAllControls() {
        mSelectedCity = (EditText) findViewById(R.id.selected_city);

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart()", Toast.LENGTH_SHORT).show();
        Log.d(mClassName, "onStart()");


    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume()", Toast.LENGTH_SHORT).show();
        Log.d(mClassName, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
        Log.d(mClassName, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop()", Toast.LENGTH_SHORT).show();
        Log.d(mClassName, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d(mClassName, "onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(mClassName, "onSaveInstanceState()");
        Toast.makeText(this, "onSaveInstanceState()", Toast.LENGTH_SHORT).show();

        SettingsSingleton.getInstance().setSelectedCity(mSelectedCity.getText().toString());
        outState.putSerializable(mSettingsTag, SettingsSingleton.getInstance());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(mClassName, "onRestoreInstanceState()");
        Toast.makeText(this, "onRestoreInstanceState()", Toast.LENGTH_SHORT).show();

        SettingsSingleton settings = (SettingsSingleton) savedInstanceState.getSerializable(mSettingsTag);
        SettingsSingleton set_ = SettingsSingleton.getInstance();
        if (settings != null) {
            SettingsSingleton.getInstance().RestoreSettings(settings);
            mSelectedCity.setText(SettingsSingleton.getInstance().getSelectedCity());
            mPressureCB.setChecked(SettingsSingleton.getInstance().getShowPressure());
            mWindSpeedCB.setChecked(SettingsSingleton.getInstance().getShowWindSpeed());
        }
    }
}
