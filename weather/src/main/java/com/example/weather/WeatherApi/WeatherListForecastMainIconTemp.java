package com.example.weather.WeatherApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherListForecastMainIconTemp {
    @SerializedName("dt")
    private int dt;
    @SerializedName("weather")
    private List<WeatherMainIconWeather> weatherMainIconWeathers;
    @SerializedName("main")
    private WeatherTempMain WeatherTempMain;

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public List<WeatherMainIconWeather> getWeatherMainIconWeathers() {
        return weatherMainIconWeathers;
    }

    public void setWeatherMainIconWeathers(List<WeatherMainIconWeather> weatherMainIconWeathers) {
        this.weatherMainIconWeathers = weatherMainIconWeathers;
    }

    public com.example.weather.WeatherApi.WeatherTempMain getWeatherTempMain() {
        return WeatherTempMain;
    }

    public void setWeatherTempMain(com.example.weather.WeatherApi.WeatherTempMain weatherTempMain) {
        WeatherTempMain = weatherTempMain;
    }
}
