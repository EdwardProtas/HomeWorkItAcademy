package com.example.weather.fragmentMain;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();
        if(mActivity != null) {
            recyclerviewFragmentMain = view.findViewById(R.id.recyclerviewFragmentMain);
            buttonLocationCity = view.findViewById(R.id.buttonLocationCity);
            settings_black = view.findViewById(R.id.settings_black);
            snowFragmentMain = view.findViewById(R.id.snowFragmentMain);
            nameCityFragmentMain = view.findViewById(R.id.nameCityFragmentMain);
            textTempFragmentMain = view.findViewById(R.id.textTempFragmentMain);
            textWeatherfragmentMain = view.findViewById(R.id.textWeatherfragmentMain);
            initializationRecyclerView();
            if(listenerButton != null) {
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
        }
    }

    public  interface ListenerButton{
        void OnSettingButton();
        void OnAddCityButton();

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ListenerButton){
            listenerButton = (ListenerButton) context;
        }
    }

    private void initializationRecyclerView() {
        recyclerviewFragmentMain.setAdapter(new FragmentMainAdapter());
        recyclerviewFragmentMain.setLayoutManager(new LinearLayoutManager(mActivity , RecyclerView.VERTICAL , false));
        mFragmentMainAdapter = (FragmentMainAdapter) recyclerviewFragmentMain.getAdapter();
    }
}
