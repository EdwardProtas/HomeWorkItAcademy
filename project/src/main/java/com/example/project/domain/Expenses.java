package com.example.project.domain;

import java.util.Date;

public class Expenses {

    private long id;
    private long expenses;
    private String currencyExpenses;
    private Date data;
    private String bill;

    public Expenses(long id, long expenses, String currencyExpenses, Date data, String bill) {
        this.id = id;
        this.expenses = expenses;
        this.currencyExpenses = currencyExpenses;
        this.data = data;
        this.bill = bill;
    }

    public Expenses(long expenses, String currencyExpenses, Date data, String bill) {
        this.expenses = expenses;
        this.currencyExpenses = currencyExpenses;
        this.data = data;
        this.bill = bill;
    }

    public long getId() {
        return id;
    }

    public long getExpenses() {
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
