package com.example.project.database.expenses;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ExpensesDao {

    @Query("SELECT * FROM expenses")
    LiveData<List<ExpensesEntity>> getAllLivaDataExpenses();

    @Query("SELECT * FROM expenses")
    List<ExpensesEntity> getAllExpenses();

    @Query("SELECT * FROM expenses WHERE id = :id")
    LiveData<ExpensesEntity> getByIdExpenses(long id);

    @Query("SELECT * FROM expenses WHERE data = :data")
    LiveData<ExpensesEntity> getByDateExpenses(long data);

  @Query("SELECT * FROM expenses WHERE bill = :bill")
    LiveData<ExpensesEntity> getByBillExpenses(String bill);

    @Insert(onConflict = REPLACE)
    void insert(ExpensesEntity expensesEntity);

    @Delete
    void delete(ExpensesEntity expensesEntity);
}
