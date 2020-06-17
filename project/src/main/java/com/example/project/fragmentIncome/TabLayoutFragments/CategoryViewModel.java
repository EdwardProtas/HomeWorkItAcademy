package com.example.project.fragmentIncome.TabLayoutFragments;

import com.example.project.domain.Income;
import com.example.project.repo.IncomeRepo.IncomeDataBaseRepository;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategoryViewModel extends ViewModel {

    private IncomeDataBaseRepository incomeDataBaseRepository;
    private MutableLiveData<Long> incomeIdLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Income>> incomeListLiveData = new MutableLiveData<>();
    private MutableLiveData<Date> incomeDateLiveData = new MutableLiveData<>();
    private MutableLiveData<String> incomeStringLiveData = new MutableLiveData<>();
    private Income income;

    CategoryViewModel(IncomeDataBaseRepository incomeDataBaseRepository, Income income) {
        this.incomeDataBaseRepository = incomeDataBaseRepository;
        this.income = income;
        incomeListLiveData = (MutableLiveData<List<Income>>) incomeDataBaseRepository.getIncomeLiveData();
    }

    LiveData<List<Income>> getIncomeListLiveData() {
        return incomeListLiveData;
    }

    public LiveData<Long> getIncomeIdLiveData() {
        return incomeIdLiveData;
    }

    public LiveData<Date> getIncomeDateLiveData() {
        return incomeDateLiveData;
    }

    public LiveData<String> getIncomeStringLiveData() {
        return incomeStringLiveData;
    }

    void openStringIncome(String bill) {
        incomeStringLiveData.setValue(bill);
    }

    void openDateIncome(Date date) {
        incomeDateLiveData.setValue(date);
    }

    void openIdIncome(long id) {
        incomeIdLiveData.setValue(id);
    }

    void delete(Income income) {
        incomeDataBaseRepository.deleteIncome(income);
    }
}
