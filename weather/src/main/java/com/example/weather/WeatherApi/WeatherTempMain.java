package com.example.weather.WeatherApi;

import com.google.gson.annotations.SerializedName;

public class WeatherTempMain {

    @SerializedName("temp")
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
