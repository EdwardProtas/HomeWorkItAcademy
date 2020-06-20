package com.example.project.EnterDataExpenses;

import android.content.Context;

import com.example.project.domain.Expenses;
import com.example.project.domain.ExpensesMapper;
import com.example.project.domain.Income;
import com.example.project.domain.IncomeMapper;
import com.example.project.repo.ExpensesRepo.ExpensesDataBaseRepository;
import com.example.project.repo.ExpensesRepo.ExpensesDataBaseRepositoryImp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class EnterDataExpensesViewModelFactory implements ViewModelProvider.Factory {
    private Context mContext;
    private Expenses mExpenses;

    public EnterDataExpensesViewModelFactory(Context context ) {
        mContext = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(EnterDataExpensesViewModel.class)) {
            return (T) new EnterDataExpensesViewModel(getExpensesDataBaseRepository() , mExpenses);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }
    private ExpensesDataBaseRepository getExpensesDataBaseRepository() {
        return new ExpensesDataBaseRepositoryImp(mContext, new ExpensesMapper());

    }
}