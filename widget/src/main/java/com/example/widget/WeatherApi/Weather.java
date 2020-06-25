package com.example.widget.WeatherApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {

    @SerializedName("main")
    private WeatherTempMain weatherTempMain;
    @SerializedName("weather")
    private List<WeatherMainIconWeather> weatherMainIconWeathers;

    public WeatherTempMain getWeatherTempMain() {
        return weatherTempMain;
    }

    public List<WeatherMainIconWeather> getWeatherMainIconWeathers() {
        return weatherMainIconWeathers;
    }

}
