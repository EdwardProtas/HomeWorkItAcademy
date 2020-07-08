package com.example.widget;

import android.os.Bundle;

import com.example.widget.fragmentCityList.CityFragment;
import com.example.widget.fragmentMain.FragmentMain;
import com.example.widget.fragmentSetting.SettingFragment;

import androidx.appcompat.app.AppCompatActivity;


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
