package com.example.project.fragmentIncome;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.domain.Income;

import com.example.project.fragmentIncome.TabLayoutFragments.PagerAdapterIncome;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.List;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

public class IncomeFragment extends Fragment {

    private IncomeFragmentViewModel incomeFragmentViewModel;
    private FloatingActionButton buttonAddIncome;
    private TextView amoinIncome;
    private Activity mContext;
    private ListenerButtonAddIncome listenerButtonAddIncome;
    private SharedPreferences sharedPreferences;
    public static final String AMOIN_INCOME = "AMOIN_INCOME";
    private TabLayout tabLayoutIncome;
    private TabItem allTabItem;
    private TabItem daysTabItem;
    private TabItem categoryTabItem;
    private ViewPager viewPagerIncome;
    private TextView currenceAmoinIncome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_income_filling, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        if (mContext != null) {
            setHasOptionsMenu(true);
            amoinIncome = view.findViewById(R.id.amoinIncome);
            currenceAmoinIncome = view.findViewById(R.id.currenceAmoinIncome);
            tabLayoutIncome = view.findViewById(R.id.tabLayoutIncome);
            allTabItem = view.findViewById(R.id.allTabItem);
            daysTabItem = view.findViewById(R.id.daysTabItem);
            categoryTabItem = view.findViewById(R.id.categoryTabItem);
            viewPagerIncome = view.findViewById(R.id.viewPagerIncome);
            PagerAdapterIncome pagerAdapter = new PagerAdapterIncome(getChildFragmentManager(), tabLayoutIncome.getTabCount());
            viewPagerIncome.setAdapter(pagerAdapter);
            tabLayoutIncome.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPagerIncome.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
            sharedPreferences = mContext.getSharedPreferences(MainActivity.MAIN_ACTINITY, Context.MODE_PRIVATE);
            buttonAddIncome(view);
            initViewModel();
            Toolbar toolbar = getActivity().findViewById(R.id.toolBar);
            toolbar.setTitle("Доходы");

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ListenerButtonAddIncome) {
            listenerButtonAddIncome = (ListenerButtonAddIncome) context;
        }
    }

    public interface ListenerButtonAddIncome {
        void OnListenerButtonAddIncome();
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AMOIN_INCOME, amoinIncome.getText().toString());
        editor.apply();
    }

    private void buttonAddIncome(View view) {
        buttonAddIncome = view.findViewById(R.id.buttonAddIncome);
        buttonAddIncome.setOnClickListener(view1 -> listenerButtonAddIncome.OnListenerButtonAddIncome());
    }

    private void initViewModel() {
        if (getActivity() != null) {
            incomeFragmentViewModel = new ViewModelProvider(this, new IncomeFragmentViewModelFactory(getContext()))
                    .get(IncomeFragmentViewModel.class);
            incomeFragmentViewModel.getIncomeListLiveData().observe(getViewLifecycleOwner(), this::amout);
        }
    }

    private void amout(List<Income> incomes) {
        int money = 0;
        for (Income in : incomes) {
            money += Integer.parseInt(in.getIncome());
            incomeFragmentViewModel.setIncomeDateLiveData(in.getData().getTime());
            incomeFragmentViewModel.setIncomeBillLiveData(in.getBill());
            incomeFragmentViewModel.getIncomeCategoryLiveData(in.getCategory());
        }
        amoinIncome.setText("+" + money);
        currenceAmoinIncome.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listenerButtonAddIncome = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



}
