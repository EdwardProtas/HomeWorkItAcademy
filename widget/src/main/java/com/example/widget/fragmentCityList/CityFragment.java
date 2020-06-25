package com.example.widget.fragmentCityList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.example.widget.MainActivity;
import com.example.widget.R;
import com.example.widget.database.CityEntity;
import com.example.widget.database.DataBase;
import com.facebook.stetho.Stetho;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CityFragment extends Fragment {

    private ListCityAdapter listCityAdapter;
    private Activity activity;
    private RecyclerView recyclerView;
    private FloatingActionButton buttonAddCity;
    private DataBase dataBase;
    private SharedPreferences sharedPreferences;
    private String cityCity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        if (activity != null) {
            recyclerView = view.findViewById(R.id.recyclerview_city);
            buttonAddCity = view.findViewById(R.id.buttonAddCity);
            Stetho.initializeWithDefaults(activity);
            dataBase = DataBase.getInstance(activity);
            initializationRecyclerView();
            conclusionCityFromDatabase();
            touchNameCity();
            sharedPreferences = activity.getSharedPreferences(MainActivity.MAINACTINITY, Context.MODE_PRIVATE);
            if (sharedPreferences != null) {
                String city = sharedPreferences.getString("CITY", "");
                if (!city.equals("")) {
                    listCityAdapter.setNowNameCity(city);
                }
            }
            buttonAddCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initializationDialog();
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CITY", cityCity);
        editor.apply();
    }

    private void touchNameCity() {
        listCityAdapter.setCityListener(new ListCityAdapter.CityListener() {
            @Override
            public void onClickCityListener(String nameCity) {
                listCityAdapter.setNowNameCity(nameCity);
               cityCity = nameCity;
            }
        });
    }

    private void initializationDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View dialog = layoutInflater.inflate(R.layout.dialog, null);
        AlertDialog.Builder dialogCity = new AlertDialog.Builder(getContext());
        dialogCity.setView(dialog);
        final EditText userInput = dialog.findViewById(R.id.input_text);
        dialogCity.setCancelable(false)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listCityAdapter.addCity(String.valueOf(userInput.getText()));
                        dataBase.getDataBaseCityExecutorService().execute(new Runnable() {
                            @Override
                            public void run() {
                                dataBase.getCity().insert(new CityEntity(String.valueOf(userInput.getText())));
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .create()
                .show();
    }

    private void initializationRecyclerView() {
        recyclerView.setAdapter(new ListCityAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        listCityAdapter = (ListCityAdapter) recyclerView.getAdapter();
    }

    private void conclusionCityFromDatabase() {
        CompletableFuture.supplyAsync(new Supplier<ArrayList<String>>() {
            @Override
            public ArrayList<String> get() {
                List<CityEntity> cityEntities = dataBase.getCity().getAllCity();
                ArrayList<String> citys = new ArrayList<>();
                for (CityEntity citiEntity : cityEntities) {
                    citys.add(citiEntity.getNameCity());
                }
                return citys;
            }
        }, dataBase.getDataBaseCityExecutorService())
                .thenAcceptAsync(new Consumer<ArrayList<String>>() {
                    @Override
                    public void accept(ArrayList<String> strings) {
                        listCityAdapter.setArrayNameCitys(strings);
                    }
                }, ContextCompat.getMainExecutor(activity));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataBase.close();
    }
}
