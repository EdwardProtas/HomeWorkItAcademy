package com.example.project.domain;

import com.example.project.database.income.IncomeEntity;

import java.util.function.Function;


public class IncomeMapper implements Function<IncomeEntity , Income> {
    @Override
    public Income apply(IncomeEntity input) {
        return new Income(input.getId() , input.getIncome(), input.getCurrencyIncome(),
                input.getData() , input.getBill());
    }
}
