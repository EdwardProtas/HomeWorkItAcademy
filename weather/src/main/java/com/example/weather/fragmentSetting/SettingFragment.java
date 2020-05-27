package com.example.weather.fragmentSetting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.weather.MainActivity;
import com.example.weather.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {

    private Switch switch_celsius;
    private Activity mActivity;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();
        if (mActivity != null) {
            switch_celsius = view.findViewById(R.id.switch_celsius);
            sharedPreferences = mActivity.getSharedPreferences(MainActivity.MAINACTINITY, Context.MODE_PRIVATE);
            switch_celsius.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("check", String.valueOf(b));
                    editor.apply();
                }
            });
            String checked = sharedPreferences.getString("check", "");
            if (checked.equals("true")) {
                switch_celsius.setChecked(true);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }
}
