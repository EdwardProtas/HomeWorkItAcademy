package com.example.project.database.income;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {IncomeEntity.class}, version = 1)
public abstract class IncomeDataBase extends RoomDatabase {

    private static final int CORE_NUMBER = Runtime.getRuntime().availableProcessors();
    private static volatile IncomeDataBase INSTANCE;

    public static IncomeDataBase getInstance(@NonNull final Context context) {
        if (INSTANCE == null) {
            synchronized (IncomeDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, IncomeDataBase.class, "db_income")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private ExecutorService incomeDataBaseExecutorService = Executors.newFixedThreadPool(CORE_NUMBER);

    public ExecutorService getIncomeDataBaseExecutorService() {
        return incomeDataBaseExecutorService;
    }

    public abstract IncomeDao getIncomeDao();

}
