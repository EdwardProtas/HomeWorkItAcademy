package com.example.project.fragmentMain;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.example.project.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    private Activity mActivity;
    private ListenerButton listenerButton;
    private LinearLayout allIncome;
    private LinearLayout income;
    private LinearLayout expenses;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        Toolbar toolbar = view.findViewById(R.id.toolBar);
        allIncome = view.findViewById(R.id.allIncome);
        income = view.findViewById(R.id.income);
        expenses = view.findViewById(R.id.expenses);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(toolbar);
        }
        OnListener();

    }

    private void OnListener() {
        allIncome.setOnClickListener(view -> listenerButton.OnAmountFragment());
        income.setOnClickListener(view -> listenerButton.OnIncomeFragment());
        expenses.setOnClickListener(view -> listenerButton.OnExpensesFragment());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public interface ListenerButton {
        void OnAmountFragment();
        void OnIncomeFragment();
        void OnExpensesFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ListenerButton) {
            listenerButton = (ListenerButton) context;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listenerButton = null;
    }
}
