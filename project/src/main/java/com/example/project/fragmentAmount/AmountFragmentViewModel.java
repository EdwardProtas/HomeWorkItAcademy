package com.example.project.fragmentAmount;

import com.example.project.domain.Income;
import com.example.project.repo.IncomeRepo.IncomeDataBaseRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AmountFragmentViewModel extends ViewModel {


    private IncomeDataBaseRepository incomeDataBaseRepository;
    private MutableLiveData<Long> incomeIdLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Income>> incomeListLiveData = new MutableLiveData<>();
    private MutableLiveData<Long> incomeDateLiveData = new MutableLiveData<>();
    private MutableLiveData<String> incomeBillLiveData = new MutableLiveData<>();
    private MutableLiveData<String> incomeCategoryLiveData = new MutableLiveData<>();


    AmountFragmentViewModel(IncomeDataBaseRepository incomeDataBaseRepository) {
        this.incomeDataBaseRepository = incomeDataBaseRepository;
        incomeListLiveData = (MutableLiveData<List<Income>>) incomeDataBaseRepository.getIncomeLiveData();
    }

    LiveData<List<Income>> getIncomeListLiveData() {
        return incomeListLiveData;
    }

    public LiveData<Long> getIncomeIdLiveData() {
        return incomeIdLiveData;
    }

    public LiveData<List<Income>> getIncomeDateLiveData(Long s) {
        return incomeDataBaseRepository.getIncomeDate(s);
    }

    public LiveData<List<Income>> getIncomeBillLiveData(String bill) {
        return incomeDataBaseRepository.getIncomeBill(bill);
    }

    public LiveData<List<Income>> getIncomeCategoryLiveData(String category) {
        return incomeDataBaseRepository.getIncomeCategory(category);
    }

    public void setIncomeCategoryLiveData(String categoryLiveData) {
        this.incomeCategoryLiveData.setValue(categoryLiveData);
    }

    public void setIncomeDateLiveData(Long date) {
        this.incomeDateLiveData.setValue(date);
    }

    public void setIncomeBillLiveData(String incomeStringLiveData) {
        this.incomeBillLiveData.setValue(incomeStringLiveData);
    }

    void openIdIncome(long id) {
        incomeIdLiveData.setValue(id);
    }

}
