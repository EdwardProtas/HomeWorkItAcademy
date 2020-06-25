package com.example.widget.WeatherApi;

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

    public com.example.widget.WeatherApi.WeatherTempMain getWeatherTempMain() {
        return WeatherTempMain;
    }


    public String ConvertorTime(long time){
        String dataFormate = new SimpleDateFormat("HH:mm" , Locale.getDefault()).format(new Date(time));
        return dataFormate;
    }

    public String convertDate(long date){
        return new SimpleDateFormat("dd.MM.yy", Locale.getDefault()).format(new Date(date * 1000));
    }
}
