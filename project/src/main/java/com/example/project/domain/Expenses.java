package com.example.project.domain;

import java.util.Date;

public class Expenses {

    private long id;
    private String expenses;
    private String currencyExpenses;
    private Date data;
    private String bill;
    private String category;

    public Expenses(long id, String expenses, String currencyExpenses, Date data, String bill, String category) {
        this.id = id;
        this.expenses = expenses;
        this.currencyExpenses = currencyExpenses;
        this.data = data;
        this.bill = bill;
        this.category = category;
    }

    public Expenses(String expenses, String currencyExpenses, Date data, String bill, String category) {
        this.expenses = expenses;
        this.currencyExpenses = currencyExpenses;
        this.data = data;
        this.bill = bill;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public long getId() {
        return id;
    }

    public String getExpenses() {
        return expenses;
    }

    public String getCurrencyExpenses() {
        return currencyExpenses;
    }

    public Date getData() {
        return data;
    }

    public String getBill() {
        return bill;
    }
}
