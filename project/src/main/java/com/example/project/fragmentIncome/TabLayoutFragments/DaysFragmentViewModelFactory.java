package com.example.project.fragmentIncome.TabLayoutFragments;

import android.content.Context;

import com.example.project.domain.Income;
import com.example.project.domain.IncomeMapper;
import com.example.project.repo.IncomeRepo.IncomeDataBaseRepository;
import com.example.project.repo.IncomeRepo.IncomeDataBaseRepositoryImp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DaysFragmentViewModelFactory implements ViewModelProvider.Factory {

    private Context mContext;
    private Income mIncome;

    public DaysFragmentViewModelFactory(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(DaysFragmentViewModel.class)) {
            return (T) new DaysFragmentViewModel(getIncomeDataBaseRepository() , mIncome);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }

    private IncomeDataBaseRepository getIncomeDataBaseRepository() {
        return new IncomeDataBaseRepositoryImp(mContext, new IncomeMapper());

    }

}
