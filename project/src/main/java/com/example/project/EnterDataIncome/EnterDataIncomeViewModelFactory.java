package com.example.project.EnterDataIncome;

import android.content.Context;

import com.example.project.domain.Income;
import com.example.project.domain.IncomeMapper;
import com.example.project.fragmentIncome.IncomeFragmentViewModel;
import com.example.project.repo.IncomeRepo.IncomeDataBaseRepository;
import com.example.project.repo.IncomeRepo.IncomeDataBaseRepositoryImp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class EnterDataIncomeViewModelFactory implements ViewModelProvider.Factory {

    private Context mContext;
    private Income mIncome;

    public EnterDataIncomeViewModelFactory(Context context ) {
        mContext = context;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(EnterDataIncomeViewModel.class)) {
            return (T) new EnterDataIncomeViewModel(getIncomeDataBaseRepository() , mIncome);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }
    private IncomeDataBaseRepository getIncomeDataBaseRepository() {
        return new IncomeDataBaseRepositoryImp(mContext, new IncomeMapper());

    }
}
