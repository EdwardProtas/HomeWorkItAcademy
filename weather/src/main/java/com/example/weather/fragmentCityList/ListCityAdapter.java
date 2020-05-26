package com.example.weather.fragmentCityList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListCityAdapter extends RecyclerView.Adapter<ListCityAdapter.CityListViewHolder> {

    private ArrayList<String> arrayNameCitys = new ArrayList<>();
    private String nowNameCity;

    void addCity(String city){
        arrayNameCitys.add(city);
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public CityListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city,
                parent, false);
        return new CityListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityListViewHolder holder, int position) {
        holder.bind(arrayNameCitys.get(position));
    }

    @Override
    public int getItemCount() {
        return (arrayNameCitys != null) ? arrayNameCitys.size() : 0;
    }


    public class CityListViewHolder extends RecyclerView.ViewHolder {

        private TextView text_city_fragmentCity;
        private ImageView done_fragmentCity;


        public CityListViewHolder(@NonNull View itemView) {
            super(itemView);
            text_city_fragmentCity = itemView.findViewById(R.id.text_city_fragmentCity);
            done_fragmentCity = itemView.findViewById(R.id.done_fragmentCity);
        }

        private void bind(String nameCity) {
            text_city_fragmentCity.setText(nameCity);
        }
    }
}
