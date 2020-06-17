package com.example.project.fragmentIncome.TabLayoutFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.domain.Income;
import com.example.project.fragmentIncome.IncomeAdapter;
import com.example.project.fragmentIncome.IncomeFragment;
import com.example.project.fragmentIncome.IncomeFragmentViewModel;
import com.example.project.fragmentIncome.IncomeFragmentViewModelFactory;

import java.util.List;


public class AllFragment extends Fragment {

    private RecyclerView recyclerView;
    private IncomeAdapter incomeAdapter;
    private Activity mContext;
    private AllFragmentViewModel allFragmentViewModel;
    private IncomeFragment incomeFragment = new IncomeFragment();
    private String amoun;
    public static final String AMOIN = "AMOIN";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        if (mContext != null) {
            initRecyclerView(view);
            initViewModel();
            itemTouchDelete();
        }
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new IncomeAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        incomeAdapter = (IncomeAdapter) recyclerView.getAdapter();
    }

    private void initViewModel() {
        if (getActivity() != null) {
            allFragmentViewModel = new ViewModelProvider(this, new AllFragmentViewModelFactory(getContext()))
                    .get(AllFragmentViewModel.class);
            allFragmentViewModel.getIncomeListLiveData().observe(getViewLifecycleOwner(), this::loadingIncomeInRecycler);
        }
    }

    private void loadingIncomeInRecycler(List<Income> incomes) {
        incomeAdapter.setIncomeList(incomes);
        int d = 0;
        for (int i = 0; i < incomes.size(); i++) {
            int w = Integer.parseInt(incomes.get(i).getIncome());
            d += w;
        }
    }


    private void itemTouchDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                allFragmentViewModel.delete(incomeAdapter.getIncomeAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);
    }
}
