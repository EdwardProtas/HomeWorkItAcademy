package com.example.project.fragmentIncome;

import android.content.Context;

import com.example.project.domain.Income;
import com.example.project.domain.IncomeMapper;
import com.example.project.repo.IncomeRepo.IncomeDataBaseRepository;
import com.example.project.repo.IncomeRepo.IncomeDataBaseRepositoryImp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class IncomeFragmentViewModelFactory implements ViewModelProvider.Factory {

    private Context mContext;
    private Income mIncome;

    public IncomeFragmentViewModelFactory(Context context, Income income) {
        mContext = context;
        mIncome = income;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(IncomeFragmentViewModel.class)) {
            return (T) new IncomeFragmentViewModel(getIncomeDataBaseRepository() , mIncome);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }

    private IncomeDataBaseRepository getIncomeDataBaseRepository() {
        return new IncomeDataBaseRepositoryImp(mContext, new IncomeMapper());

    }

}
