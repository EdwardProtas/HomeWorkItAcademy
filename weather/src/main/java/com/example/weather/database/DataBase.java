package com.example.weather.database;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CityEntity.class}, version = 1)
public abstract class DataBase extends RoomDatabase {

    private static final int CORE_NUMBER = Runtime.getRuntime().availableProcessors();
    private static volatile  DataBase INSTANCE;

    public static DataBase getInstance(@NonNull final Context context){
        if (INSTANCE == null) {
            synchronized (DataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, DataBase.class, "db_city").build();
                }
            }
        }
        return INSTANCE;
    }

    private ExecutorService dataBaseCityExecutorService = Executors.newFixedThreadPool(CORE_NUMBER);

    public ExecutorService getDataBaseCityExecutorService(){
        return dataBaseCityExecutorService;
    }
}
