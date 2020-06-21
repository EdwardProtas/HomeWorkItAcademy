package com.example.project.fragmentExpenses;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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

import android.widget.TextView;


import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.domain.Expenses;
import com.example.project.fragmentExpenses.TabLayoutFragmentsExpenses.AllFragmentExpenses;
import com.example.project.fragmentExpenses.TabLayoutFragmentsExpenses.CategoryFragmentExpenses;
import com.example.project.fragmentExpenses.TabLayoutFragmentsExpenses.DaysFragmentExpenses;
import com.example.project.fragmentExpenses.TabLayoutFragmentsExpenses.PagerAdapterExpenses;

import com.example.project.fragmentIncome.TabLayoutFragments.AllFragment;
import com.example.project.fragmentIncome.TabLayoutFragments.CategoryFragment;
import com.example.project.fragmentIncome.TabLayoutFragments.DaysFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


import java.util.Calendar;
import java.util.List;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

public class ExpensesFragment extends Fragment {

    private ExpensesFragmentViewModel expensesFragmentViewModel;
    private FloatingActionButton buttonAddIncome;
    private TextView amoinIncome;
    private TextView currAmoinExpenses;
    private Activity mContext;
    private ListenerButtonAddExpenses listenerButtonAddExpenses;
    private TabLayout tabLayoutExpenses;
    private ViewPager viewPagerExpenses;
    private Calendar selectedDate;
    private  PagerAdapterExpenses pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expenses_filling , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        if (mContext != null) {
            selectedDate = Calendar.getInstance();
            amoinIncome = view.findViewById(R.id.amoinIncome);
            currAmoinExpenses = view.findViewById(R.id.currAmoinExpenses);
            tabLayoutExpenses = view.findViewById(R.id.tabLayoutExpenses);
            viewPagerExpenses = view.findViewById(R.id.viewPagerExpenses);
            pagerAdapter = new PagerAdapterExpenses(getChildFragmentManager(), tabLayoutExpenses.getTabCount());
            pagerAdapter.AddFragment(new AllFragmentExpenses(), "Все");
            pagerAdapter.AddFragment(new DaysFragmentExpenses(), "Дни");
            pagerAdapter.AddFragment(new CategoryFragmentExpenses(), "Категории");
            viewPagerExpenses.setAdapter(pagerAdapter);
            tabLayoutExpenses.setupWithViewPager(viewPagerExpenses);
            buttonAddIncome(view);
            initViewModel();
            setHasOptionsMenu(true);
            Toolbar toolbar = getActivity().findViewById(R.id.toolBar);
            toolbar.setTitle("Расходы");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ListenerButtonAddExpenses) {
            listenerButtonAddExpenses = (ListenerButtonAddExpenses) context;
        }
    }

    public interface ListenerButtonAddExpenses {
        void OnListenerButtonAddExpenses();
    }


    private void buttonAddIncome(View view) {
        buttonAddIncome = view.findViewById(R.id.buttonAddIncome);
        buttonAddIncome.setOnClickListener(view1 -> listenerButtonAddExpenses.OnListenerButtonAddExpenses());
    }

    private void initViewModel() {
        if (getActivity() != null) {
            expensesFragmentViewModel = new ViewModelProvider(this, new ExpensesFragmentViewModelFactory(getContext()))
                    .get(ExpensesFragmentViewModel.class);
            expensesFragmentViewModel.getExpenseseListLiveData().observe(getViewLifecycleOwner() , this::amoutExpenses);
        }
    }

    private void amoutExpenses(List<Expenses> expenses) {
        int money = 0 ;
        for (Expenses in: expenses) {
            money += Integer.parseInt(in.getExpenses());
            expensesFragmentViewModel.setExpensesBillLiveData(in.getBill());
            expensesFragmentViewModel.setExpensesCategoryLiveData(in.getCategory());
            expensesFragmentViewModel.setExpensesDateLiveData(in.getData().getTime());
        }
        if(String.valueOf(money).equals("0")) {
            amoinIncome.setVisibility(View.INVISIBLE);
            currAmoinExpenses.setVisibility(View.INVISIBLE);
        }else {

            amoinIncome.setText("-" + money);
            amoinIncome.setVisibility(View.VISIBLE);
            currAmoinExpenses.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listenerButtonAddExpenses = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
