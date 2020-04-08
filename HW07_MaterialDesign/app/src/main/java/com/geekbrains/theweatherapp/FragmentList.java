package com.geekbrains.theweatherapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.geekbrains.theweatherapp.Parcel.PARCEL_TAG;

public class FragmentList extends Fragment {
    private boolean mIsLandscape;
    private Parcel mCurrentCityData;

    private TextInputEditText mCityNameTV;
    private LinearLayout mCityListlayout;

    private CityStorage mCc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCc = CityStorage.getInstance();

        configControls(view);
        initCityList(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mIsLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            mCurrentCityData = (Parcel) savedInstanceState.getSerializable(PARCEL_TAG);
        } else {
            City city = new City(getResources().getStringArray(R.array.cities)[0], generateWeather(0));
            mCurrentCityData = new Parcel(0, city);
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

    private void configControls(View view) {
        mCityNameTV = view.findViewById(R.id.city_name_TI);
        mCityListlayout = view.findViewById(R.id.city_list);

        mCityNameTV.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId){
                    case EditorInfo.IME_ACTION_GO:
                        String newCityName = Objects.requireNonNull(mCityNameTV.getText()).toString();
                        if (!newCityName.isEmpty()) {
                            requestSnack(v, newCityName).show();
                        }
                        break;
                }
                return false;
            }
        });
    }

    private Snackbar requestSnack(View v, final String cityName) {
        return Snackbar.make(v, cityName, Snackbar.LENGTH_LONG).
                setAction(R.string.go, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        City newCity = mCc.findCity(cityName);
                        if (newCity == null) {
                            newCity = mCc.addCity(cityName, generateWeather(mCc.getStorageSize()));
                            addCityTextView(newCity, mCityListlayout, 0);
                        }
                        mCurrentCityData = new Parcel(mCc.getStorageSize() - 1, newCity);
                        showTheWeather(mCurrentCityData);
                    }
                });
    }

    private void addCityTextView(@NonNull final City city, LinearLayout layout, final int index) {
        TextView tv = new TextView(getContext());
        tv.setText(city.getCityName());
        tv.setTextSize(25);
        layout.addView(tv, 0);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSnack(v, city.getCityName()).show();
            }
        });
    }

    private void initCityList(View view) {
        String[] cities = getResources().getStringArray(R.array.cities);

        for (int i = 0; i < cities.length; i++) {
            City cityData = mCc.addCity(cities[i], generateWeather(i));
            addCityTextView(cityData, mCityListlayout, i);
        }
    }

    private List<Weather> generateWeather(final int index) {
        ArrayList<Weather> weathers = new ArrayList<>();
        TypedArray picturesArray = getResources().obtainTypedArray(R.array.weather_picts);
        int picturesQuantity = picturesArray.length();
        for (int i = 0; i < 7; i++) {
            weathers.add(new Weather(picturesArray.getResourceId((int) ((Math.random() * 10) % picturesQuantity), -1),
                    (int) ((Math.random() - 0.25) * 100) % 25));
        }
        return weathers;
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
