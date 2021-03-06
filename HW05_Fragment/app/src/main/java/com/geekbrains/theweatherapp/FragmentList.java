package com.geekbrains.theweatherapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

import static com.geekbrains.theweatherapp.Parcel.PARCEL_TAG;

public class FragmentList extends Fragment {
    private boolean mIsLandscape;
    private Parcel mCurrentCityData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCityList(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mIsLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            mCurrentCityData = (Parcel) savedInstanceState.getSerializable(PARCEL_TAG);
        } else {
            mCurrentCityData = new Parcel(0, getResources().getStringArray(R.array.cities)[0]);
        }

        if (mIsLandscape) {
            showTheWeather(mCurrentCityData);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(PARCEL_TAG, mCurrentCityData);
        super.onSaveInstanceState(outState);
    }

    private void initCityList(View view) {
        LinearLayout layout = (LinearLayout) view;
        String[] cities = getResources().getStringArray(R.array.cities);

        for (int i = 0; i < cities.length; i++) {
            final String city = cities[i];
            TextView tv = new TextView(getContext());
            tv.setText(city);
            tv.setTextSize(25);
            layout.addView(tv);
            final int fi = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentCityData = new Parcel(fi, city);
                    showTheWeather(mCurrentCityData);
                }
            });
        }
    }

    private void showTheWeather(Parcel parcel) {
        if (mIsLandscape) {
            FragmentWeather weather = (FragmentWeather) getFragmentManager().findFragmentById(R.id.weather_container_main);

            if (weather == null || weather.getParcel().getIndex() != parcel.getIndex()) {
                weather = FragmentWeather.create(parcel);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.weather_container_main, weather)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }

        } else {
            Intent intent = new Intent(getActivity(), ActivityWeather.class);
            intent.putExtra(PARCEL_TAG, parcel);
            startActivity(intent);
        }
    }
}
