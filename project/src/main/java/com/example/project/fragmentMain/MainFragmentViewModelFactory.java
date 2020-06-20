package com.example.project.fragmentMain;

import android.content.Context;

import com.example.project.domain.Expenses;
import com.example.project.domain.ExpensesMapper;
import com.example.project.domain.IncomeMapper;
import com.example.project.fragmentIncome.IncomeFragmentViewModel;
import com.example.project.repo.ExpensesRepo.ExpensesDataBaseRepository;
import com.example.project.repo.ExpensesRepo.ExpensesDataBaseRepositoryImp;
import com.example.project.repo.IncomeRepo.IncomeDataBaseRepository;
import com.example.project.repo.IncomeRepo.IncomeDataBaseRepositoryImp;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainFragmentViewModelFactory implements ViewModelProvider.Factory {

    private Context mContext;

    public MainFragmentViewModelFactory(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(MainFragmentViewModel.class)) {
            return (T) new MainFragmentViewModel(getIncomeDataBaseRepository() ,getExpensesDataBaseRepository());
        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }

    private IncomeDataBaseRepository getIncomeDataBaseRepository() {
        return new IncomeDataBaseRepositoryImp(mContext, new IncomeMapper());

    }

    private ExpensesDataBaseRepository getExpensesDataBaseRepository() {
        return new ExpensesDataBaseRepositoryImp(mContext, new ExpensesMapper());

    }
}
