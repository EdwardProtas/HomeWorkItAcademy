package com.example.weather.WeatherApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("/data/2.5/forecast")
    Call<WeatherListForecast> geForecast (@Query("q") String cityName, @Query("appid") String appid);

    @GET("/data/2.5/weather")
    Call<WeatherListForecast> getWeather (@Query("q") String cityName, @Query("appid") String appid);
}
