package com.example.weather.WeatherApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherListForecast {

    @SerializedName("list")
    private List<WeatherListForecastMainIconTemp> WeatherListForecastMainIconTemps;


    public List<WeatherListForecastMainIconTemp> getWeatherListForecastMainIconTemps() {
        return WeatherListForecastMainIconTemps;
    }

    public void setWeatherListForecastMainIconTemps(List<WeatherListForecastMainIconTemp> weatherListForecastMainIconTemps) {
        WeatherListForecastMainIconTemps = weatherListForecastMainIconTemps;
    }
}
