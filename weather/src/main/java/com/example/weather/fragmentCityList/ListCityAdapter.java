package com.example.weather.fragmentCityList;

import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    private CityListener CityListener;

    public void addCity(String city) {
        this.arrayNameCitys.add(city);
        notifyDataSetChanged();
    }
    public ListCityAdapter(){}

    public ListCityAdapter(String nowNameCity) {
        this.nowNameCity = nowNameCity;
    }

    public void setNowNameCity(String nowNameCity) {
        this.nowNameCity = nowNameCity;
    }

    public void setArrayNameCitys(ArrayList<String> arrayNameCitys) {
        this.arrayNameCitys = arrayNameCitys;
    }

    public void setCityListener(ListCityAdapter.CityListener cityListener) {
        CityListener = cityListener;
    }

    interface CityListener {
        void onClickCityListener(String nameCity);
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

        private void bind(final String nameCity) {
            text_city_fragmentCity.setText(nameCity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CityListener.onClickCityListener(text_city_fragmentCity.getText().toString());
                    if (nameCity.equals(nowNameCity)) {
                        if (done_fragmentCity.getVisibility() == View.VISIBLE) {
                            done_fragmentCity.setVisibility(View.INVISIBLE);
                        } else {
                            done_fragmentCity.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }
    }
}
