package com.example.project.EnterDataIncome;

import com.example.project.domain.Income;
import com.example.project.repo.IncomeRepo.IncomeDataBaseRepository;

import androidx.lifecycle.ViewModel;

public class EnterDataIncomeViewModel extends ViewModel {

    private IncomeDataBaseRepository incomeDataBaseRepository;
    private Income mIncome;

    public EnterDataIncomeViewModel(IncomeDataBaseRepository incomeDataBaseRepository, Income income) {
        this.incomeDataBaseRepository = incomeDataBaseRepository;
        this.mIncome = income;
    }

    void saveIncome(Income income){
        incomeDataBaseRepository.addIncome(income);
    }
}
