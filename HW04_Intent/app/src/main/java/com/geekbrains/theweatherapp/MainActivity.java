package com.geekbrains.theweatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String mClassName = "MainActivity";
    private TextView mCityNameTV;
    private LinearLayout mWindSpeedLayout, mPressureLayout;
    public static final int CITY_SELECTION_REQ_CODE = 0x6161;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();

        findControls();
        setCityNameListener();
    }

    private void findControls() {
        mCityNameTV = (TextView) findViewById(R.id.city_name);
        mWindSpeedLayout = (LinearLayout) findViewById(R.id.wind_speed_layout);
        mPressureLayout = (LinearLayout) findViewById(R.id.pressure_layout);
    }

    private void setCityNameListener() {
        mCityNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent citySelectionIntent = new Intent(MainActivity.this, CitySelectionActivity.class);
                startActivityForResult(citySelectionIntent, CITY_SELECTION_REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == CITY_SELECTION_REQ_CODE) {
            if (data == null) {
                return;
            }
            SettingsSingleton settings = CitySelectionActivity.getSettingsFromIntent(data);
            if (settings != null) {
                mCityNameTV.setText(settings.getSelectedCity());
                mPressureLayout.setVisibility(settings.getShowPressure() ? LinearLayout.VISIBLE : LinearLayout.INVISIBLE);
                mWindSpeedLayout.setVisibility(settings.getShowWindSpeed() ? LinearLayout.VISIBLE : LinearLayout.INVISIBLE);
            }
        }
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
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(mClassName, "onRestoreInstanceState()");
        Toast.makeText(this, "onRestoreInstanceState()", Toast.LENGTH_SHORT).show();
    }
}
