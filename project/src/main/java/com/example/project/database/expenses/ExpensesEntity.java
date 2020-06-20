package com.example.project.database.expenses;

import com.example.project.database.Converters;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName = "expenses" , indices = {@Index(value = {"id"}, unique = true)})
@TypeConverters(value = {Converters.class})
public class ExpensesEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String expenses;
    private String currencyExpenses;
    private Date data;
    private String bill;
    private String category;

    public ExpensesEntity(long id, String expenses, String currencyExpenses, Date data, String bill, String category) {
        this.id = id;
        this.expenses = expenses;
        this.currencyExpenses = currencyExpenses;
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

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public String getCurrencyExpenses() {
        return currencyExpenses;
    }

    public void setCurrencyExpenses(String currencyExpenses) {
        this.currencyExpenses = currencyExpenses;
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
