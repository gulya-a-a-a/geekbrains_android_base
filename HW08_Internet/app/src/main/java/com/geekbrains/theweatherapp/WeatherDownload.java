package com.geekbrains.theweatherapp;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class WeatherDownload {
    private static String API_URL = "http://api.openweathermap.org/data/2.5";
    private static String API_KEY = "f912bb6609c3957b0ed1ba6ffcc4c5d6";
    private static String KEY_ARG = "x-api-key";
    private static String CITY_ARG = "q";

    private static JSONObject sendRequest(final String city, final String rt){
        try {
            URL url = new URL(String.format("%s/%s?q=%s&units=metric", API_URL, rt, city));
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.addRequestProperty(CITY_ARG, city);
            httpConnection.addRequestProperty(KEY_ARG, API_KEY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            StringBuilder rawData = new StringBuilder(1024);
            String tmpVar;

            while((tmpVar = reader.readLine()) != null){
                rawData.append(tmpVar).append("\n");
            }

            reader.close();

            JSONObject json = new JSONObject(rawData.toString());
            if (json.getInt("cod") != HttpURLConnection.HTTP_OK){
                return null;
            } else {
                return json;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static JSONObject getFiveDaysForecast(final String cityName){
        try {
            return sendRequest(cityName, "forecast");
        } catch (Exception ex){
            return null;
        }
    }

    static JSONObject getCurrentWeatherData(final String cityName) {
        try {
            return sendRequest(cityName, "weather");
        } catch (Exception ex){
            return null;
        }
    }
}
