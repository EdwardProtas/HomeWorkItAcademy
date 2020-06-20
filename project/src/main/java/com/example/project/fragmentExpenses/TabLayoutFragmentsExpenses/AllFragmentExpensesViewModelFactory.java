package com.example.project.fragmentExpenses.TabLayoutFragmentsExpenses;

import android.content.Context;

import com.example.project.domain.Expenses;
import com.example.project.domain.ExpensesMapper;
import com.example.project.fragmentExpenses.ExpensesFragmentViewModel;
import com.example.project.repo.ExpensesRepo.ExpensesDataBaseRepository;
import com.example.project.repo.ExpensesRepo.ExpensesDataBaseRepositoryImp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AllFragmentExpensesViewModelFactory implements ViewModelProvider.Factory {

    private Context mContext;
    private Expenses mExpenses;

    public AllFragmentExpensesViewModelFactory(Context context ) {
        mContext = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(AllFragmentExpensesViewModel.class)) {
            return (T) new AllFragmentExpensesViewModel(getExpensesDataBaseRepository() , mExpenses);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }
    private ExpensesDataBaseRepository getExpensesDataBaseRepository() {
        return new ExpensesDataBaseRepositoryImp(mContext, new ExpensesMapper());

    }
}