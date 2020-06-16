package com.example.project.repo.IncomeRepo;

import android.content.Context;

import com.example.project.database.expenses.ExpensesEntity;
import com.example.project.database.income.IncomeDao;
import com.example.project.database.income.IncomeDataBase;
import com.example.project.database.income.IncomeEntity;
import com.example.project.domain.Income;
import com.example.project.domain.IncomeMapper;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;;
import java.util.function.Function;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

public class IncomeDataBaseRepositoryImp implements IncomeDataBaseRepository {

    private IncomeDao incomeDao;
    private ExecutorService incomeExecutorService;
    private Function<IncomeEntity, Income> mapper;

    public IncomeDataBaseRepositoryImp(@NonNull final Context context, Function<IncomeEntity, Income> mapper) {
        IncomeDataBase incomeDataBase = IncomeDataBase.getInstance(context);
        this.incomeDao = incomeDataBase.getIncomeDao();
        this.incomeExecutorService = incomeDataBase.getIncomeDataBaseExecutorService();
        this.mapper = mapper;
    }

    @Override
    public LiveData<List<Income>> getIncomeLiveData() {
        return Transformations.map(incomeDao.getAllLivaDataIncome(), input -> input
                .stream()
                .map(mapper)
                .collect(Collectors.toList()));
    }

    @Override
    public Future<List<Income>> getIncome() {
        return incomeExecutorService.submit(() -> incomeDao.getAllIncome()
                .stream()
                .map(mapper)
                .collect(Collectors.toList()));
    }

    @Override
    public LiveData<Income> getIncomeId(long id) {
        return Transformations.map(incomeDao.getByIdIncome(id), input -> mapper.apply(input));
    }

    @Override
    public LiveData<Income> getIncomeDate(long date) {
        return Transformations.map(incomeDao.getByDateIncome(date), input -> mapper.apply(input));
    }

    @Override
    public LiveData<Income> getIncomeBill(String bill) {
        return Transformations.map(incomeDao.getByBillIncome(bill), input -> mapper.apply(input));
    }

    @Override
    public void addIncome(final Income income) {
        incomeExecutorService.execute(() ->
                incomeDao.insert(new IncomeEntity(income.getId(),income.getIncome(), income.getCurrencyIncome(),
                        income.getData() , income.getBill() , income.getCategory())));
    }

    @Override
    public void deleteIncome(final Income income) {
        incomeExecutorService.execute(() ->
                incomeDao.delete(new IncomeEntity(income.getId(),income.getIncome(), income.getCurrencyIncome(),
                        income.getData(), income.getBill(),income.getCategory())));
    }
}
