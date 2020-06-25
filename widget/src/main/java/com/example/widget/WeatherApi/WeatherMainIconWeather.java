package com.example.widget.WeatherApi;

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

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }
}
