package com.example.project.fragmentMain;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.project.EnterDataExpenses.EnterDataExpenses;
import com.example.project.EnterDataIncome.EnterDataIncome;
import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.fragmentAmount.AmountFragment;
import com.example.project.fragmentExpenses.ExpensesFragment;
import com.example.project.fragmentIncome.IncomeFragment;

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
    private TextView incomeTextViewMain;
    private TextView currMain;
    private TextView expensesTextViewMain;
    private TextView currMainEx;
    private TextView amountCurrMain;
    private TextView amountTextViewMain;
    private SharedPreferences mSharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();
        if (mActivity != null) {
            setHasOptionsMenu(true);
            allIncome = view.findViewById(R.id.allIncome);
            amountTextViewMain = view.findViewById(R.id.amountTextViewMain);
            amountCurrMain = view.findViewById(R.id.amountCurrMain);
            income = view.findViewById(R.id.income);
            expenses = view.findViewById(R.id.expenses);
            currMain = view.findViewById(R.id.currMain);
            expensesTextViewMain = view.findViewById(R.id.expensesTextViewMain);
            currMainEx = view.findViewById(R.id.currMainEx);
            incomeTextViewMain = view.findViewById(R.id.incomeTextViewMain);
            setHasOptionsMenu(true);
            OnListener();
            Toolbar toolbar1 = getActivity().findViewById(R.id.toolBar);
            toolbar1.setTitle("Главная");
        }
    }

    @Override
    public void onStart() {
        allSharedPreferences();

        super.onStart();
    }


    private void allSharedPreferences() {
        mSharedPreferences = mActivity.getSharedPreferences(MainActivity.MAIN_ACTINITY, Context.MODE_PRIVATE);
        String income = mSharedPreferences.getString(IncomeFragment.AMOIN_INCOME, "");
        if (!income.equals("")) {
            incomeTextViewMain.setText(income);
            currMain.setVisibility(View.VISIBLE);
        }
        String expenses = mSharedPreferences.getString(ExpensesFragment.AMOIN_EXPENSES, "");
        if (!income.equals("")) {
            expensesTextViewMain.setText(expenses);
            currMainEx.setVisibility(View.VISIBLE);
        }
        String amount = mSharedPreferences.getString(AmountFragment.AMOUN_All, "");
        if (!amount.equals("")) {
            amountTextViewMain.setText(amount);
            amountCurrMain.setVisibility(View.VISIBLE);
        }
    }

    private void OnListener() {
        allIncome.setOnClickListener(view -> listenerButton.OnAmountFragment());
        income.setOnClickListener(view -> listenerButton.OnIncomeFragment());
        expenses.setOnClickListener(view -> listenerButton.OnExpensesFragment());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main , menu);
        super.onCreateOptionsMenu(menu, inflater);
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
