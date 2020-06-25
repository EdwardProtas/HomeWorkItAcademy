package com.example.widget.fragmentMain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.widget.R;
import com.example.widget.WeatherApi.WeatherListForecastMainIconTemp;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentMainAdapter extends RecyclerView.Adapter<FragmentMainAdapter.FroastViewHolder> {

    private List<WeatherListForecastMainIconTemp> weatherListForecastMainIconTempe;
    private String celsuia;

    public void setCelsuia(String celsuia) {
        this.celsuia = celsuia;
    }

    public void setWeatherListForecastMainIconTempe(List<WeatherListForecastMainIconTemp> weatherListForecastMainIconTempe) {
        this.weatherListForecastMainIconTempe = new ArrayList<>(weatherListForecastMainIconTempe);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FroastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_city, parent, false);
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
            Glide.with(itemView.getContext())
                    .load(weatherListForecastMainIconTemp.getWeatherMainIconWeathers().get(0).getIconId())
                    .into(weather_icon);
            oclock.setText(weatherListForecastMainIconTemp.ConvertorTime(weatherListForecastMainIconTemp.getDt() * 1000L));
            text_city_fragmentWeatherCity.setText(weatherListForecastMainIconTemp.getWeatherMainIconWeathers().get(0).getMainWeather());
            int celsuiaMain = (int) (Double.parseDouble(weatherListForecastMainIconTemp.getWeatherTempMain().getTemperature()) - 273);
            if (celsuia.equals(" °F")) {
                celsius.setText(((celsuiaMain + 32) + celsuia));
            } else celsius.setText(celsuiaMain + celsuia);
        }
    }
}
