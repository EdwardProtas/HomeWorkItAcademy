package com.example.project.domain;

import com.example.project.database.expenses.ExpensesEntity;

import java.util.function.Function;


public class ExpensesMapper implements Function<ExpensesEntity, Expenses> {

    @Override
    public Expenses apply(ExpensesEntity expensesEntity) {
        return new Expenses(expensesEntity.getId(), expensesEntity.getExpenses(),
                expensesEntity.getCurrencyExpenses() ,expensesEntity.getData() ,
                expensesEntity.getBill() , expensesEntity.getCategory());
    }
}
