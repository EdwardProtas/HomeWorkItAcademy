package com.example.project.fragmentIncome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.domain.Income;

import com.example.project.fragmentIncome.TabLayoutFragments.AllFragment;
import com.example.project.fragmentIncome.TabLayoutFragments.PagerAdapterIncome;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
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
            amoinIncome = view.findViewById(R.id.amoinIncome);
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
           incomeFragmentViewModel.getIncomeStringAmountLiveData().observe(getViewLifecycleOwner() , this::amout);
        }
    }

    private void amout(String s) {
        amoinIncome.setText(s);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        listenerButtonAddIncome = null;
    }
}
