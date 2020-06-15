package com.example.project.domain;

import java.util.Date;

public class Income {

    private long id;
    private long income;
    private String currencyIncome;
    private Date data;
    private String bill;

    public Income(long id, long income, String currencyIncome, Date data, String bill) {
        this.id = id;
        this.income = income;
        this.currencyIncome = currencyIncome;
        this.data = data;
        this.bill = bill;
    }

    public Income(long income, String currencyIncome, Date data, String bill) {
        this.income = income;
        this.currencyIncome = currencyIncome;
        this.data = data;
        this.bill = bill;
    }

    public long getId() {
        return id;
    }

    public long getIncome() {
        return income;
    }

    public String getCurrencyIncome() {
        return currencyIncome;
    }

    public Date getData() {
        return data;
    }

    public String getBill() {
        return bill;
    }
}
