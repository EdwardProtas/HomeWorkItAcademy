package com.example.widget.WeatherApi;

import com.google.gson.annotations.SerializedName;

public class WeatherTempMain {

    @SerializedName("temp")
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

}
