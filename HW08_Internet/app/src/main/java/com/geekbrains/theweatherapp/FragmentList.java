package com.geekbrains.theweatherapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private final Handler handler = new Handler();

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mIsLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            mCurrentCityData = (Parcel) savedInstanceState.getSerializable(PARCEL_TAG);
        } else {
            City city = new City(getResources().getStringArray(R.array.cities)[0]);
            mCurrentCityData = new Parcel(0, city);
        }

        if (mIsLandscape) {
            showTheWeather(mCurrentCityData);
        }
    }

    private void getWeather(final String cityName){
        new Thread(){
            @Override
            public void run() {
                final JSONObject json = WeatherDownload.getWeatherData(cityName);
                if (json == null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "There is no city with such name",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            handleWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    private List<Weather> parseWeather(JSONObject json){
        ArrayList<Weather> weathers = new ArrayList<>();

        try {
            JSONArray jsonWeathers = json.getJSONArray("weather");
            Weather w = new Weather();
            w.setDrawableID(jsonWeathers.getJSONObject(0).getInt("id"));
            w.setTemp(json.getJSONObject("main").getInt("temp"));
            w.setPressure(json.getJSONObject("main").getInt("pressure"));
            w.setWindSpeed(json.getJSONObject("wind").getInt("speed"));
            weathers.add(w);
        } catch (Exception ex){
            ex.printStackTrace();
            return weathers;
        }

        return weathers;
    }

    private void handleWeather(JSONObject json){
        try {
            String cityName = json.getString("name");
            City newCity = mCc.findCity(cityName);
            if (newCity == null) {
                newCity = mCc.addCity(cityName);
                addCityTextView(newCity, mCityListlayout, 0);
            }
            newCity.setWeathers(parseWeather(json));

            mCurrentCityData = new Parcel(mCc.getStorageSize() - 1, newCity);
            showTheWeather(mCurrentCityData);
        } catch (JSONException jex){
            jex.printStackTrace();
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
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    String newCityName = Objects.requireNonNull(mCityNameTV.getText()).toString();
                    if (!newCityName.isEmpty()) {
                        requestSnack(v, newCityName).show();
                    }
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
                        getWeather(cityName);
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

    private void showTheWeather(Parcel parcel) {
        if (mIsLandscape) {
            assert getFragmentManager() != null;
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
