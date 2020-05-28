package com.example.weather.fragmentMain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.WeatherApi.WeatherListForecastMainIconTemp;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentMainAdapter extends RecyclerView.Adapter<FragmentMainAdapter.FroastViewHolder> {

    private ArrayList<WeatherListForecastMainIconTemp> weatherListForecastMainIconTempe;




    @NonNull
    @Override
    public FroastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main , parent , false);
        return new FroastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FroastViewHolder holder, int position) {
        holder.bind(weatherListForecastMainIconTempe.get(position));
    }

    @Override
    public int getItemCount() {
        return (weatherListForecastMainIconTempe != null) ? weatherListForecastMainIconTempe.size() : 0;
    }

    public class FroastViewHolder extends RecyclerView.ViewHolder {

        private ImageView weather_icon;
        private TextView oclock;
        private TextView text_city_fragmentWeatherCity;
        private TextView celsius;

        public FroastViewHolder(@NonNull View itemView) {
            super(itemView);
            weather_icon = itemView.findViewById(R.id.weather_icon);
            oclock = itemView.findViewById(R.id.oclock);
            text_city_fragmentWeatherCity = itemView.findViewById(R.id.text_city_fragmentWeatherCity);
            celsius = itemView.findViewById(R.id.celsius);
        }

        void bind(WeatherListForecastMainIconTemp weatherListForecastMainIconTemp) {
            weather_icon.setImageResource(Integer.parseInt(weatherListForecastMainIconTemp.getWeatherMainIconWeathers().get(0).getIconId()));
            oclock.setText(weatherListForecastMainIconTemp.ConvertorTime(weatherListForecastMainIconTemp.getDt() * 1000L));
            text_city_fragmentWeatherCity.setText(weatherListForecastMainIconTemp.getWeatherMainIconWeathers().get(0).getMainWeather());
            celsius.setText(weatherListForecastMainIconTemp.getWeatherTempMain().getTemperature());
        }
    }
}
