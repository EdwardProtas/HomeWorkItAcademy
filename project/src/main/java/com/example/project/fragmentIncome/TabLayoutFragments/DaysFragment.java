package com.example.project.fragmentIncome.TabLayoutFragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.R;
import com.example.project.domain.Income;

import java.util.List;

public class DaysFragment extends Fragment {

    private RecyclerView recyclerViewDay;
    private DaysAdapter daysAdapter;
    private Activity mContext;
    private DaysFragmentViewModel daysViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_days, container, false);
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
        recyclerViewDay = view.findViewById(R.id.recyclerViewDay);
        recyclerViewDay.setAdapter(new DaysAdapter());
        recyclerViewDay.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        daysAdapter = (DaysAdapter) recyclerViewDay.getAdapter();
    }

    private void initViewModel() {
        if (getActivity() != null) {
            daysViewModel = new ViewModelProvider(this, new DaysFragmentViewModelFactory(getContext()))
                    .get(DaysFragmentViewModel.class);
            daysViewModel.getIncomeListLiveData().observe(getViewLifecycleOwner(), this::loadingIncomeInRecycler);
        }
    }

    private void loadingIncomeInRecycler(List<Income> incomes) {
        daysAdapter.setIncomeList(incomes);
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
                daysViewModel.delete(daysAdapter.getIncomeAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerViewDay);
    }
}
