package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.weather.fragmentCityList.CityFragment;
import com.example.weather.fragmentMain.FragmentMain;
import com.example.weather.fragmentSetting.SettingFragment;


public class MainActivity extends AppCompatActivity implements FragmentMain.ListenerButton {

    public static final String MAINACTINITY = "MAINACTINITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new FragmentMain(), "FragmentMain").commit();

    }

    @Override
    public void OnSettingButton() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new SettingFragment(), "SettingFragment")
                .addToBackStack(null).commit();
    }

    @Override
    public void OnAddCityButton() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new CityFragment(), "CityFragment")
                .addToBackStack(null).commit();
    }
}
