package com.example.widget.fragmentMain;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.widget.MainActivity;
import com.example.widget.R;
import com.example.widget.WeatherApi.Weather;
import com.example.widget.WeatherApi.WeatherListForecast;
import com.example.widget.WeatherApi.WeatherListForecastMainIconTemp;
import com.example.widget.WeatherApi.WeatherMainIconWeather;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentMain extends Fragment {

    private RecyclerView recyclerviewFragmentMain;
    private FragmentMainAdapter mFragmentMainAdapter;
    private Activity mActivity;
    private FloatingActionButton buttonLocationCity;
    private ImageView settings_black;
    private ImageView snowFragmentMain;
    private TextView nameCityFragmentMain;
    private TextView textTempFragmentMain;
    private TextView textWeatherfragmentMain;
    private ListenerButton listenerButton;
    private String nameCity;
    private String settingCelsuiaFromSharedPreferences;
    private String settingCelsuia;
    private SharedPreferences mSharedPreferences;
    public static final String API_KEY = "76b845fc0918054c69189b202a030b1f";
    public static final String URL_FORECAST = "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s";
    public static final String URL_WEATHER = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";
    public static final String URL_ICON = "http://openweathermap.org/img/wn/%s@2x.png";
    private OkHttpClient mOkHttpClient = new OkHttpClient();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();
        if (mActivity != null) {
            mSharedPreferences = mActivity.getSharedPreferences(MainActivity.MAINACTINITY, Context.MODE_PRIVATE);
            recyclerviewFragmentMain = view.findViewById(R.id.recyclerviewFragmentMain);
            buttonLocationCity = view.findViewById(R.id.buttonLocationCity);
            settings_black = view.findViewById(R.id.settings_black);
            snowFragmentMain = view.findViewById(R.id.snowFragmentMain);
            nameCityFragmentMain = view.findViewById(R.id.nameCityFragmentMain);
            textTempFragmentMain = view.findViewById(R.id.textTempFragmentMain);
            textWeatherfragmentMain = view.findViewById(R.id.textWeatherfragmentMain);
            nameCity = mSharedPreferences.getString("CITY", "Лондон");
            settingCelsuiaFromSharedPreferences = mSharedPreferences.getString("check", "");
            if (settingCelsuiaFromSharedPreferences.equals("true")) {
                settingCelsuia = " °C";
            } else settingCelsuia = " °F";
            initializationRecyclerView();
            if (listenerButton != null) {
                settings_black.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listenerButton.OnSettingButton();
                    }
                });
                buttonLocationCity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listenerButton.OnAddCityButton();
                    }
                });
            }
            setWeather(nameCity);
            setForecast(nameCity);
        }
    }

    private void setForecast(String nameCity) {
        String url = String.format(URL_FORECAST, nameCity, API_KEY);
        Request request = new Request.Builder()
                .url(url)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(mActivity, "onFailure", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    Type type = new TypeToken<WeatherListForecast>() {
                    }.getType();
                    final WeatherListForecast weatherListForecast = new Gson().fromJson(json, type);
                    new Handler(mActivity.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            for (WeatherListForecastMainIconTemp weather1 : weatherListForecast.getWeatherListForecastMainIconTemps()) {
                                String string = String.format(URL_ICON,  weather1.getWeatherMainIconWeathers().get(0).getIconId());
                                WeatherMainIconWeather weather = weather1.getWeatherMainIconWeathers().get(0);
                                weather.setIconId(string);
                            }
                            mFragmentMainAdapter.setCelsuia(settingCelsuia);
                            mFragmentMainAdapter.setWeatherListForecastMainIconTempe(weatherListForecast.getWeatherListForecastMainIconTemps());
                        }
                    });
                }
            }
        });
    }

    private void setWeather(final String nameCity) {
        String url = String.format(URL_WEATHER, nameCity, API_KEY);
        Request request = new Request.Builder()
                .url(url)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    Type type = new TypeToken<Weather>() {
                    }.getType();
                    final Weather weather = new Gson().fromJson(json, type);
                    new Handler(mActivity.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            nameCityFragmentMain.setText(nameCity);
                            int celsuia = (int) (Double.parseDouble(weather.getWeatherTempMain().getTemperature()) - 273);
                            if (settingCelsuia.equals(" °F")) {
                                textTempFragmentMain.setText(((celsuia + 32) + settingCelsuia));
                            } else textTempFragmentMain.setText((celsuia + settingCelsuia));
                            textWeatherfragmentMain.setText(weather.getWeatherMainIconWeathers().get(0).getMainWeather());
                            String string = String.format(URL_ICON, weather.getWeatherMainIconWeathers().get(0).getIconId());
                            Glide.with(mActivity).load(string).into(snowFragmentMain);
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(mActivity, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface ListenerButton {
        void OnSettingButton();

        void OnAddCityButton();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ListenerButton) {
            listenerButton = (ListenerButton) context;
        }
    }

    private void initializationRecyclerView() {
        recyclerviewFragmentMain.setAdapter(new FragmentMainAdapter());
        recyclerviewFragmentMain.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false));
        mFragmentMainAdapter = (FragmentMainAdapter) recyclerviewFragmentMain.getAdapter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listenerButton = null;
    }
}
