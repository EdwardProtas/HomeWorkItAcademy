package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.project.fragmentAmount.AmountFragment;
import com.example.project.fragmentExpenses.ExpensesFragment;
import com.example.project.fragmentIncome.IncomeFragment;
import com.example.project.fragmentMain.MainFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.ListenerButton , IncomeFragment.ListenerButtonAddIncome {

    public static final String MAIN_ACTINITY = "MAINACTINITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new MainFragment(), "MainFragment")
                .commit();
    }

    @Override
    public void OnAmountFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new AmountFragment(), "AmoutFragmetn")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void OnIncomeFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new IncomeFragment(), "IncomeFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void OnExpensesFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new ExpensesFragment(), "ExpensesFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void OnListenerButtonAddIncome() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new EnterDataIncome(), "EnterDataIncome")
                .addToBackStack(null)
                .commit();
    }
}
