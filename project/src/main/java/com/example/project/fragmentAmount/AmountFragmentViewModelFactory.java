package com.example.project.fragmentAmount;

import android.content.Context;

import com.example.project.domain.IncomeMapper;
import com.example.project.fragmentIncome.IncomeFragmentViewModel;
import com.example.project.repo.IncomeRepo.IncomeDataBaseRepository;
import com.example.project.repo.IncomeRepo.IncomeDataBaseRepositoryImp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AmountFragmentViewModelFactory implements ViewModelProvider.Factory {

    private Context mContext;

    public AmountFragmentViewModelFactory(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(AmountFragmentViewModel.class)) {
            return (T) new AmountFragmentViewModel(getIncomeDataBaseRepository());
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    private IncomeDataBaseRepository getIncomeDataBaseRepository() {
        return new IncomeDataBaseRepositoryImp(mContext, new IncomeMapper());
    }
}
