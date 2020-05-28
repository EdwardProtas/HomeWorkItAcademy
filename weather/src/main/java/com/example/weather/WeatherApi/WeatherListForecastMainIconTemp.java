package com.example.weather.WeatherApi;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    public com.example.weather.WeatherApi.WeatherTempMain getWeatherTempMain() {
        return WeatherTempMain;
    }


    public String ConvertorTime(long time){
        String dataFormate = new SimpleDateFormat("EEE HH:mm" , Locale.getDefault()).format(new Date(time));
        return dataFormate;
    }
}
