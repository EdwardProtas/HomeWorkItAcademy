package com.example.project.database.income;

import com.example.project.database.expenses.ExpensesEntity;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface IncomeDao {

    @Query("SELECT * FROM income")
    LiveData<List<IncomeEntity>> getAllLivaDataIncome();

    @Query("SELECT * FROM income")
    List<IncomeEntity> getAllIncome();

    @Query("SELECT * FROM income WHERE id = :id")
    LiveData<IncomeEntity> getByIdIncome(long id);

    @Query("SELECT * FROM income WHERE data = :data")
    LiveData<List<IncomeEntity>> getByDateIncome(long data);

    @Query("SELECT * FROM income WHERE bill = :bill")
    LiveData<List<IncomeEntity>> getByBillIncome(String bill);

    @Query("SELECT * FROM income WHERE category = :category")
    LiveData<List<IncomeEntity>> getByCategoryIncome(String category);

    @Insert(onConflict = REPLACE)
    void insert(IncomeEntity incomeEntity);

    @Delete
    void delete(IncomeEntity... incomeEntity);
}
