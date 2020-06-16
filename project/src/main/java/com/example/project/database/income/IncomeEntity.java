package com.example.project.database.income;

import com.example.project.database.Converters;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName = "income" , indices = {@Index(value = {"id"}, unique = true)})
@TypeConverters(value = {Converters.class})
public class IncomeEntity {

    @PrimaryKey(autoGenerate =  true)
    private long id;
    private String income;
    private String currencyIncome;
    private Date data;
    private String bill;
    private String category;

    public IncomeEntity(long id , String income, String currencyIncome, Date data, String bill, String category) {
        this.id = id;
        this.income = income;
        this.currencyIncome = currencyIncome;
        this.data = data;
        this.bill = bill;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getCurrencyIncome() {
        return currencyIncome;
    }

    public void setCurrencyIncome(String currencyIncome) {
        this.currencyIncome = currencyIncome;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }
}
