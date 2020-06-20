package com.example.project.repo.ExpensesRepo;

import com.example.project.domain.Expenses;
import com.example.project.domain.Income;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import androidx.lifecycle.LiveData;

public interface ExpensesDataBaseRepository {

    LiveData<List<Expenses>> getExpensesLiveData();
    Future<List<Expenses>> getExpenses();
    LiveData<Expenses> getExpensesId(long id);
    LiveData<List<Expenses>> getExpensesDate(long date);
    LiveData<List<Expenses>> getExpensesBill(String bill);
    LiveData<List<Expenses>>getExpensesCategory(String category);
    void addExpenses(final Expenses expenses);
    void deleteExpenses(final Expenses expenses);


}
