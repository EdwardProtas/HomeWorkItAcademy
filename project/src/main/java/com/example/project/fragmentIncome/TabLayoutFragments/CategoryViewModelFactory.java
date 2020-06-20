package com.example.project.fragmentIncome.TabLayoutFragments;

import android.content.Context;

import com.example.project.domain.Income;
import com.example.project.domain.IncomeMapper;
import com.example.project.repo.IncomeRepo.IncomeDataBaseRepository;
import com.example.project.repo.IncomeRepo.IncomeDataBaseRepositoryImp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CategoryViewModelFactory implements ViewModelProvider.Factory {

    private Context mContext;

    public CategoryViewModelFactory(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(CategoryViewModel.class)) {
            return (T) new CategoryViewModel(getIncomeDataBaseRepository() );
        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }

    private IncomeDataBaseRepository getIncomeDataBaseRepository() {
        return new IncomeDataBaseRepositoryImp(mContext, new IncomeMapper());

    }
}
