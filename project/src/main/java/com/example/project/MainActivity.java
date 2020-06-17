package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.project.EnterDataIncome.EnterDataIncome;
import com.example.project.fragmentAmount.AmountFragment;
import com.example.project.fragmentExpenses.ExpensesFragment;
import com.example.project.fragmentIncome.IncomeFragment;
import com.example.project.fragmentMain.MainFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements MainFragment.ListenerButton, IncomeFragment.ListenerButtonAddIncome,
        NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer_layout;
    public static final String MAIN_ACTINITY = "MAINACTINITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        drawer_layout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        if(savedInstanceState == null) {
            initMainFragment();
        }

    }

    private void initMainFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new MainFragment(), "MainFragment")
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else super.onBackPressed();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.main:
                initMainFragment();
                break;
            case R.id.incomeMenu:
                OnIncomeFragment();
                break;
            case R.id.expensesMenu:
                OnExpensesFragment();
                break;
            default:
                break;
        }
        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
