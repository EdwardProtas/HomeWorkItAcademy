package com.example.project.fragmentExpenses;

import com.example.project.domain.Expenses;
import com.example.project.repo.ExpensesRepo.ExpensesDataBaseRepository;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExpensesFragmentViewModel extends ViewModel {

    private ExpensesDataBaseRepository expensesDataBaseRepository;
    private MutableLiveData<Long> expensesIdLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Expenses>> expensesListLiveData = new MutableLiveData<>();
    private MutableLiveData<Long> expensesDateLiveData = new MutableLiveData<>();
    private MutableLiveData<String> expensesCategoryLiveData = new MutableLiveData<>();
    private MutableLiveData<String> expensesBillLiveData = new MutableLiveData<>();
    private Expenses expenses;

    ExpensesFragmentViewModel(ExpensesDataBaseRepository expensesDataBaseRepository, Expenses expenses) {
        this.expensesDataBaseRepository = expensesDataBaseRepository;
        this.expenses = expenses;
        expensesListLiveData = (MutableLiveData<List<Expenses>>) expensesDataBaseRepository.getExpensesLiveData();
    }

    LiveData<List<Expenses>> getExpenseseListLiveData() {
        return expensesListLiveData;
    }

    public LiveData<Long> getExpensesIdLiveData() {
        return expensesIdLiveData;
    }

    public LiveData<List<Expenses>> getExpensesDateLiveData(long date) {
        return expensesDataBaseRepository.getExpensesDate(date);
    }

    public LiveData<List<Expenses>> getExpensesBillLiveData(String bill) {
        return expensesDataBaseRepository.getExpensesBill(bill);
    }

    public LiveData<List<Expenses>> getIncomeCategoryLiveData(String category) {
        return expensesDataBaseRepository.getExpensesCategory(category);
    }
    public void setExpensesCategoryLiveData(String categoryLiveData) {
        this.expensesCategoryLiveData.setValue(categoryLiveData);
    }

    public void setExpensesDateLiveData(Long date) {
        this.expensesDateLiveData.setValue(date);
    }

    public void setExpensesBillLiveData(String incomeStringLiveData) {
        this.expensesBillLiveData.setValue(incomeStringLiveData);
    }
    void openIdExpenses(long id) {
        expensesIdLiveData.setValue(id);
    }

    void delete(Expenses expenses) {
        expensesDataBaseRepository.deleteExpenses(expenses);
    }

}
