package com.example.project.EnterDataExpenses;

import com.example.project.domain.Expenses;
import com.example.project.domain.Income;
import com.example.project.repo.ExpensesRepo.ExpensesDataBaseRepository;
import com.example.project.repo.IncomeRepo.IncomeDataBaseRepository;

import androidx.lifecycle.ViewModel;

public class EnterDataExpensesViewModel extends ViewModel {

    private ExpensesDataBaseRepository expensesDataBaseRepository;
    private Expenses mExpenses;

    public EnterDataExpensesViewModel(ExpensesDataBaseRepository expensesDataBaseRepository, Expenses expenses) {
        this.expensesDataBaseRepository = expensesDataBaseRepository;
        mExpenses = expenses;
    }

    void saveExpenses(Expenses expenses){
        expensesDataBaseRepository.addExpenses(expenses);
    }
}