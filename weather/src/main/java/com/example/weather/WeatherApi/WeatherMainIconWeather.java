package com.example.weather.WeatherApi;

import com.google.gson.annotations.SerializedName;

public class WeatherMainIconWeather {

    @SerializedName("main")
    private String mainWeather;
    @SerializedName("icon")
    private String iconId;

    public String getMainWeather() {
        return mainWeather;
    }

    public String getIconId() {
        return iconId;
    }
}
