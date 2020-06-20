package com.example.project.repo.IncomeRepo;


import com.example.project.domain.Income;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import androidx.lifecycle.LiveData;

public interface IncomeDataBaseRepository {

    LiveData<List<Income>> getIncomeLiveData();
    Future<List<Income>> getIncome();
    LiveData<Income> getIncomeId(long id);
    LiveData<List<Income>> getIncomeDate(long date);
    LiveData<List<Income>>getIncomeBill(String bill);
    LiveData<List<Income>>getIncomeCategory(String category);
    void addIncome(final Income income);
    void deleteIncome(final Income income);
}
