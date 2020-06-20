package com.example.project.fragmentMain;

import com.example.project.domain.Expenses;
import com.example.project.domain.Income;
import com.example.project.repo.ExpensesRepo.ExpensesDataBaseRepository;
import com.example.project.repo.IncomeRepo.IncomeDataBaseRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainFragmentViewModel extends ViewModel {

    private IncomeDataBaseRepository incomeDataBaseRepository;
    private MutableLiveData<List<Income>> incomeListLiveData = new MutableLiveData<>();

    private ExpensesDataBaseRepository expensesDataBaseRepository;
    private MutableLiveData<List<Expenses>> expensesListLiveData = new MutableLiveData<>();


    public MainFragmentViewModel(IncomeDataBaseRepository incomeDataBaseRepository, ExpensesDataBaseRepository expensesDataBaseRepository) {
        this.incomeDataBaseRepository = incomeDataBaseRepository;
        this.expensesDataBaseRepository = expensesDataBaseRepository;
        incomeListLiveData = (MutableLiveData<List<Income>>) incomeDataBaseRepository.getIncomeLiveData();
        expensesListLiveData = (MutableLiveData<List<Expenses>>) expensesDataBaseRepository.getExpensesLiveData();
    }

    LiveData<List<Expenses>> getExpenseseListLiveData() {
        return expensesListLiveData;
    }
    LiveData<List<Income>> getIncomeListLiveData() {
        return incomeListLiveData;
    }
}
