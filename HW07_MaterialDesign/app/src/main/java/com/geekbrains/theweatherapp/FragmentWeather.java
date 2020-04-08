package com.geekbrains.theweatherapp;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.geekbrains.theweatherapp.Parcel.PARCEL_TAG;

public class FragmentWeather extends Fragment {

    private TextView mCityNameTV, mTempValue;
    private LinearLayout mWindSpeedLayout, mPressureLayout;
    private Button mInfoButton;
    private String mSearchUrl;

    private DecimalFormat mTempFormat;

    private RecyclerView mForecastRecyclerView;

    public static final int CITY_SELECTION_REQ_CODE = 0x6161;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_weather, container, false);


        findControls(layout);
        configServiceData();

        Parcel parcel = getParcel();
        if (parcel == null) {
            return layout;
        }

        City city = parcel.getCity();
        List<Weather> wths = city.getWeathers();

        mCityNameTV.setText(city.getCityName());
        setWeatherData(mTempValue, wths.get(0));
        mForecastRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));

        updateForecast(wths);

        setInfoButtonListener();

        return layout;
    }

    Parcel getParcel() {
        Parcel parcel;
        try {
            parcel = (Parcel) getArguments().getSerializable(PARCEL_TAG);
        } catch (NullPointerException ex) {
            return null;
        }
        return parcel;
    }

    void setWeatherData(final TextView tempTV, final Weather w) {
        tempTV.setText(mTempFormat.format(w.getTemp()));
        tempTV.setCompoundDrawablesWithIntrinsicBounds(0,
                w.getDrawableID(), 0, 0);
    }

    static FragmentWeather create(@NonNull Parcel parcel) {
        FragmentWeather f = new FragmentWeather();
        Bundle b = new Bundle();
        b.putSerializable(PARCEL_TAG, parcel);
        f.setArguments(b);
        return f;
    }

    private void configServiceData() {
        mSearchUrl = getString(R.string.wiki_search_url);
        mTempFormat = new DecimalFormat("+#;-#");
    }

    private void findControls(View layout) {
        mCityNameTV = layout.findViewById(R.id.city_name);
        mWindSpeedLayout = layout.findViewById(R.id.wind_speed_layout);
        mPressureLayout = layout.findViewById(R.id.pressure_layout);
        mInfoButton = layout.findViewById(R.id.info_button);
        mTempValue = layout.findViewById(R.id.temp_value);
        mForecastRecyclerView = layout.findViewById(R.id.forecast_list);
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

    private class ForecastHolder extends RecyclerView.ViewHolder {

        private TextView mTempTV, mDayTV;

        ForecastHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_forecast, parent, false));

            mTempTV = itemView.findViewById(R.id.forecast_temp);
            mDayTV = itemView.findViewById(R.id.forecast_day);
        }

        void bind(List<Weather> weathers, final int position) {
            setWeatherData(mTempTV, weathers.get(position));
            mDayTV.setText(getWeekDay(position));
        }

        private String getWeekDay(final int position) {
            String weekDay;
            switch (position) {
                case 0:
                    weekDay = getResources().getString(R.string.today);
                    break;
                case 1:
                    weekDay = getResources().getString(R.string.tomorrow);
                    break;
                default:
                    Date dt = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dt);
                    cal.add(Calendar.DATE, position);
                    SimpleDateFormat df = new SimpleDateFormat("EEE", Locale.getDefault());
                    weekDay = df.format(cal.getTime());
                    break;
            }
            return weekDay;
        }
    }

    private class ForecastAdapter extends RecyclerView.Adapter<ForecastHolder> {

        private List<Weather> mWeathers;

        ForecastAdapter(List<Weather> weathers) {
            mWeathers = weathers;
        }

        @NonNull
        @Override
        public ForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ForecastHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ForecastHolder holder, int position) {
            holder.bind(mWeathers, position);
        }

        @Override
        public int getItemCount() {
            return 7;
        }
    }

    private void updateForecast(List<Weather> weathers) {
        mForecastRecyclerView.setAdapter(new ForecastAdapter(weathers));
    }
}
