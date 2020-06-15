package com.example.project.database.expenses;

import android.content.Context;

import com.example.project.database.income.IncomeDao;
import com.example.project.database.income.IncomeDataBase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ExpensesEntity.class}, version = 1)
public abstract class ExpensesDataBase extends RoomDatabase {

    private static final int CORE_NUMBER = Runtime.getRuntime().availableProcessors();
    private static volatile ExpensesDataBase INSTANCE;

    public static ExpensesDataBase getInstance(@NonNull final Context context) {
        if (INSTANCE == null) {
            synchronized (ExpensesDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, ExpensesDataBase.class, "db_expenses")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private ExecutorService expensesDataBaseExecutorService = Executors.newFixedThreadPool(CORE_NUMBER);

    public ExecutorService getExpensesDataBaseExecutorService() {
        return expensesDataBaseExecutorService;
    }

    public abstract ExpensesDao getExpensesDao();

}
