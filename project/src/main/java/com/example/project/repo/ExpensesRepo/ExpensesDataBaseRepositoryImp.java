package com.example.project.repo.ExpensesRepo;

import android.content.Context;

import com.example.project.database.expenses.ExpensesDao;
import com.example.project.database.expenses.ExpensesDataBase;
import com.example.project.database.expenses.ExpensesEntity;
import com.example.project.domain.Expenses;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

public class ExpensesDataBaseRepositoryImp implements ExpensesDataBaseRepository {

    private ExecutorService expensesExecutorService;
    private ExpensesDao expensesDao;
    private Function<ExpensesEntity, Expenses> mapper;

    public ExpensesDataBaseRepositoryImp(@NonNull final Context context, Function<ExpensesEntity, Expenses> mapper) {
        ExpensesDataBase expensesDataBase = ExpensesDataBase.getInstance(context);
        this.expensesExecutorService = expensesDataBase.getExpensesDataBaseExecutorService();
        this.expensesDao = expensesDataBase.getExpensesDao();
        this.mapper = mapper;
    }

    @Override
    public LiveData<List<Expenses>> getExpensesLiveData() {
        return Transformations.map(expensesDao.getAllLivaDataExpenses(), input ->
                input.stream().map(mapper).collect(Collectors.toList()));
    }

    @Override
    public Future<List<Expenses>> getExpenses() {
        return expensesExecutorService.submit(() ->
                expensesDao.getAllExpenses().stream().map(mapper).collect(Collectors.toList()));
    }

    @Override
    public LiveData<Expenses> getExpensesId(long id) {
        return Transformations.map(expensesDao.getByIdExpenses(id), input -> mapper.apply(input));
    }

    @Override
    public LiveData<Expenses> getExpensesDate(long date) {
        return Transformations.map(expensesDao.getByDateExpenses(date), input -> mapper.apply(input));
    }

    @Override
    public LiveData<Expenses> getExpensesBill(String bill) {
        return Transformations.map(expensesDao.getByBillExpenses(bill), input -> mapper.apply(input));
    }

    @Override
    public void addExpenses(final Expenses expenses) {
        expensesExecutorService.submit(() ->
                expensesDao.insert(new ExpensesEntity(expenses.getExpenses(), expenses.getCurrencyExpenses(),
                        expenses.getData(),expenses.getBill())));
    }

    @Override
    public void deleteExpenses(final Expenses expenses) {
        expensesExecutorService.submit(() ->
                expensesDao.delete(new ExpensesEntity(expenses.getExpenses(), expenses.getCurrencyExpenses(),
                        expenses.getData(),expenses.getBill())));
    }
}
