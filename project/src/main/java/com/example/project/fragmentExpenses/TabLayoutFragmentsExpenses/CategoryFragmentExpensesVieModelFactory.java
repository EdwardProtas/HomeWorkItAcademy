package com.example.project.fragmentExpenses.TabLayoutFragmentsExpenses;

import android.content.Context;

import com.example.project.domain.Expenses;
import com.example.project.domain.ExpensesMapper;
import com.example.project.repo.ExpensesRepo.ExpensesDataBaseRepository;
import com.example.project.repo.ExpensesRepo.ExpensesDataBaseRepositoryImp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CategoryFragmentExpensesVieModelFactory implements ViewModelProvider.Factory {

    private Context mContext;
    private Expenses mExpenses;

    public CategoryFragmentExpensesVieModelFactory(Context context ) {
        mContext = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(CategoryFragmentExpensesVieModel.class)) {
            return (T) new CategoryFragmentExpensesVieModel(getExpensesDataBaseRepository() , mExpenses);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }
    private ExpensesDataBaseRepository getExpensesDataBaseRepository() {
        return new ExpensesDataBaseRepositoryImp(mContext, new ExpensesMapper());

    }

}
