package com.geekbrains.theweatherapp;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.geekbrains.theweatherapp.Parcel.PARCEL_TAG;

public class FragmentWeather extends Fragment {

    private TextView mCityNameTV, mTempValue;
    private LinearLayout mWindSpeedLayout, mPressureLayout;
    private Button mInfoButton;
    private String mSearchUrl;

    public static final int CITY_SELECTION_REQ_CODE = 0x6161;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_weather, container, false);

        findControls(layout);

        TypedArray weather_picts = getResources().obtainTypedArray(R.array.weather_picts);
        String[] temps = getResources().getStringArray(R.array.temps);

        Parcel parcel = getParcel();


        mCityNameTV.setText(parcel.getCityName());
        mTempValue.setCompoundDrawablesWithIntrinsicBounds(0, weather_picts.getResourceId(parcel.getIndex(), -1), 0, 0);
        mTempValue.setText(temps[parcel.getIndex()]);

        setInfoButtonListener();

        return layout;
    }

    public Parcel getParcel() {
        return (Parcel) getArguments().getSerializable(PARCEL_TAG);
    }

    public static FragmentWeather create(@NonNull Parcel parcel) {
        FragmentWeather f = new FragmentWeather();
        Bundle b = new Bundle();
        b.putSerializable(PARCEL_TAG, parcel);
        f.setArguments(b);
        return f;
    }

    private void findControls(View layout) {
        mCityNameTV = layout.findViewById(R.id.city_name);
        mWindSpeedLayout = layout.findViewById(R.id.wind_speed_layout);
        mPressureLayout = layout.findViewById(R.id.pressure_layout);
        mInfoButton = layout.findViewById(R.id.info_button);
        mTempValue = layout.findViewById(R.id.temp_value);

        mSearchUrl = getString(R.string.wiki_search_url);
    }

    private void setInfoButtonListener() {
        mInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(mSearchUrl + mCityNameTV.getText().toString());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(uri);
                startActivity(browserIntent);
            }
        });
    }
}
