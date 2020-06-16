package com.example.project.domain;

import java.util.Date;

public class Income {

    private long id;
    private String income;
    private String currencyIncome;
    private Date data;
    private String bill;
    private String category;

    public Income(long id, String income, String currencyIncome, Date data, String bill, String category) {
        this.id = id;
        this.income = income;
        this.currencyIncome = currencyIncome;
        this.data = data;
        this.bill = bill;
        this.category = category;
    }

    public Income(String income, String currencyIncome, Date data, String bill, String category) {
        this.income = income;
        this.currencyIncome = currencyIncome;
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

    public String getIncome() {
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
