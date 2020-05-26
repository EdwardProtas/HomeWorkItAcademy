package com.example.weather.WeatherApi;

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

    public void setWeatherTempMain(WeatherTempMain weatherTempMain) {
        this.weatherTempMain = weatherTempMain;
    }

    public List<WeatherMainIconWeather> getWeatherMainIconWeathers() {
        return weatherMainIconWeathers;
    }

    public void setWeatherMainIconWeathers(List<WeatherMainIconWeather> weatherMainIconWeathers) {
        this.weatherMainIconWeathers = weatherMainIconWeathers;
    }
}
