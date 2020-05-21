package com.example.databasemultithreading.dataBase;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ContactEntity.class}, version = 1)
public abstract class DataBase extends RoomDatabase {

    public abstract ContactDao getContactDao();

    private static final int CORE_NUMBER = Runtime.getRuntime().availableProcessors();
    private static volatile DataBase INSTANCE;

    public static DataBase getInstance(@NonNull final Context context) {
        if (INSTANCE == null) {
            synchronized (DataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, DataBase.class, "db_contact").build();
                }
            }
        }
        return INSTANCE;
    }

    private ExecutorService dataBaseExecutorService = Executors.newFixedThreadPool(CORE_NUMBER);

    public ExecutorService getDataBaseExecutorService() {
        return dataBaseExecutorService;
    }

}
