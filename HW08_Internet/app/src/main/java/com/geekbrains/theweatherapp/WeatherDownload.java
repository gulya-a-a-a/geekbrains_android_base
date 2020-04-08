package com.geekbrains.theweatherapp;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherDownload {
    private static String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=f912bb6609c3957b0ed1ba6ffcc4c5d6";

    static JSONObject getWeatherData(final String cityName) {
        try {
            URL url = new URL(String.format(apiUrl, cityName));
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            StringBuilder rawData = new StringBuilder(1024);
            String tmpVar;

            while((tmpVar = reader.readLine()) != null){
                rawData.append(tmpVar).append("\n");
            }

            reader.close();

            JSONObject json = new JSONObject(rawData.toString());
            if (json.getInt("cod") != 200){
                return null;
            } else {
                return json;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
