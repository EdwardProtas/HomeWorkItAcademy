package com.example.project.fragmentIncome;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.project.R;
import com.example.project.domain.Income;
import com.example.project.fragmentIncome.TabLayoutFragments.AllFragment;
import com.example.project.fragmentIncome.TabLayoutFragments.CategoryFragment;
import com.example.project.fragmentIncome.TabLayoutFragments.DaysFragment;
import com.example.project.fragmentIncome.TabLayoutFragments.PagerAdapterIncome;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

public class IncomeFragment extends Fragment {

    private IncomeFragmentViewModel incomeFragmentViewModel;
    private FloatingActionButton buttonAddIncome;
    private TextView amoinIncome;
    private Activity mContext;
    private ListenerButtonAddIncome listenerButtonAddIncome;
    private TabLayout tabLayoutIncome;
    private ViewPager viewPagerIncome;
    private TextView currenceAmoinIncome;
    private PagerAdapterIncome pagerAdapter;

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
            viewPagerIncome = view.findViewById(R.id.viewPagerIncome);
            pagerAdapter = new PagerAdapterIncome(getChildFragmentManager(), tabLayoutIncome.getTabCount());
            pagerAdapter.AddFragment(new AllFragment() , "Все");
            pagerAdapter.AddFragment(new DaysFragment(), "Дни");
            pagerAdapter.AddFragment(new CategoryFragment(), "Категории");
            viewPagerIncome.setAdapter(pagerAdapter);
            tabLayoutIncome.setupWithViewPager(viewPagerIncome);
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
        if(String.valueOf(money).equals("0")) {
            amoinIncome.setVisibility(View.INVISIBLE);
            currenceAmoinIncome.setVisibility(View.INVISIBLE);
        } else {
            amoinIncome.setText("+" + money);
            amoinIncome.setVisibility(View.VISIBLE);
            currenceAmoinIncome.setVisibility(View.VISIBLE);
        }
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
