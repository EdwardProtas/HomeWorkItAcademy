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

public class CategoryFragment extends Fragment {

    private RecyclerView recyclerViewCategory;
    private CategoryAdapter categoryAdapter;
    private Activity mContext;
    private CategoryViewModel categoryViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
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
        recyclerViewCategory = view.findViewById(R.id.recyclerViewCategory);
        recyclerViewCategory.setAdapter(new CategoryAdapter());
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        categoryAdapter = (CategoryAdapter) recyclerViewCategory.getAdapter();
    }

    private void initViewModel() {
        if (getActivity() != null) {
            categoryViewModel = new ViewModelProvider(this, new CategoryViewModelFactory(getContext()))
                    .get(CategoryViewModel.class);
            categoryViewModel.getIncomeListLiveData().observe(getViewLifecycleOwner(), this::loadingIncomeInRecycler);
        }
    }

    private void loadingIncomeInRecycler(List<Income> incomes) {
        categoryAdapter.setIncomeList(incomes);
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
                categoryViewModel.delete(categoryAdapter.getIncomeAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerViewCategory);
    }
}
