package com.example.widget;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.widget.WeatherApi.Weather;
import com.example.widget.WeatherApi.WeatherListForecastMainIconTemp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.example.widget.R.id.widgetIcon;
import static com.example.widget.fragmentMain.FragmentMain.API_KEY;
import static com.example.widget.fragmentMain.FragmentMain.URL_ICON;
import static com.example.widget.fragmentMain.FragmentMain.URL_WEATHER;

public class WidgetProvider extends AppWidgetProvider {

    private SharedPreferences mSharedPreferences;
    private String nameCity;
    private String settingCelsuiaFromSharedPreferences;
    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private String settingCelsuia;
    private AppWidgetTarget appWidgetTarget;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        setDataInWidget(context, appWidgetManager, appWidgetIds);
    }

    private void setDataInWidget(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        mSharedPreferences = context.getSharedPreferences(MainActivity.MAINACTINITY, Context.MODE_PRIVATE);
        nameCity = mSharedPreferences.getString("CITY", "Лондон");
        settingCelsuiaFromSharedPreferences = mSharedPreferences.getString("check", "");
        if (settingCelsuiaFromSharedPreferences.equals("true")) {
            settingCelsuia = " °C";
        } else settingCelsuia = " °F";
        String url = String.format(URL_WEATHER, nameCity, API_KEY);
        Request request = new Request.Builder()
                .url(url)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(@NotNull final Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    Type type = new TypeToken<WeatherListForecastMainIconTemp>() {
                    }.getType();
                    final WeatherListForecastMainIconTemp weather = new Gson().fromJson(json, type);
                    new Handler(context.getMainLooper()).post(() -> {
                        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
                        remoteViews.setTextViewText(R.id.widgetNameCity, nameCity);
                        int celsuia = (int) (Double.parseDouble(weather.getWeatherTempMain().getTemperature()) - 273);
                        if (settingCelsuia.equals(" °F")) {
                            remoteViews.setTextViewText(R.id.widgetTemp, (((celsuia + 32) + settingCelsuia)));
                        } else
                            remoteViews.setTextViewText(R.id.widgetTemp, (celsuia + settingCelsuia));
                        remoteViews.setTextViewText(R.id.widgetData, weather.convertDate(weather.getDt()));
                        String string = String.format(URL_ICON, weather.getWeatherMainIconWeathers().get(0).getIconId());
                      appWidgetTarget = new AppWidgetTarget(context , widgetIcon , remoteViews , appWidgetIds){
                          @Override
                          public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                              super.onResourceReady(resource, transition);
                          }
                      };
                      Glide.with(context).asBitmap().load(string).into(appWidgetTarget);
                        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
                    });
                }
            }
        });
    }
}

