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
import com.example.project.domain.Expenses;
import com.example.project.domain.Income;
import com.example.project.fragmentAmount.AmountFragment;
import com.example.project.fragmentExpenses.ExpensesFragment;
import com.example.project.fragmentIncome.IncomeFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class MainFragment extends Fragment {

    private MainFragmentViewModel mMainFragmentViewModel;
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
            toolbar1.setTitle("Доходы и расходы");
            initViewModel();
        }
    }

    private void initViewModel() {
        mMainFragmentViewModel = new ViewModelProvider(this , new MainFragmentViewModelFactory(getContext()))
                .get(MainFragmentViewModel.class);
        mMainFragmentViewModel.getIncomeListLiveData().observe(getViewLifecycleOwner() , this::initDBIncome);
        mMainFragmentViewModel.getExpenseseListLiveData().observe(getViewLifecycleOwner(),this::initDBExpenses);

    }

    private void initDBExpenses(List<Expenses> expenses) {
        int money = 0;
        for (Expenses in : expenses) {
            money += Integer.parseInt(in.getExpenses());
        }
        if(String.valueOf(money).equals("0")){
            expensesTextViewMain.setVisibility(View.INVISIBLE);
            currMainEx.setVisibility(View.INVISIBLE);
        }else {
            expensesTextViewMain.setText("-" + money);
            expensesTextViewMain.setVisibility(View.VISIBLE);
            currMainEx.setVisibility(View.VISIBLE);
        }
    }

    private void initDBIncome(List<Income> incomes) {
        int money = 0;
        for (Income in : incomes) {
            money += Integer.parseInt(in.getIncome());
        }
        if(String.valueOf(money).equals("0")) {
            amountTextViewMain.setVisibility(View.INVISIBLE);
            amountCurrMain.setVisibility(View.INVISIBLE);
            incomeTextViewMain.setVisibility(View.INVISIBLE);
            currMain.setVisibility(View.VISIBLE);
        }else {
            amountTextViewMain.setText(String.valueOf(money));
            amountTextViewMain.setVisibility(View.VISIBLE);
            amountCurrMain.setVisibility(View.VISIBLE);
            incomeTextViewMain.setText("+" + money);
            incomeTextViewMain.setVisibility(View.VISIBLE);
            currMain.setVisibility(View.VISIBLE);
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
