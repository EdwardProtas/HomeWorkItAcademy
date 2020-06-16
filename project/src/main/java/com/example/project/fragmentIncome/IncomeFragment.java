package com.example.project.fragmentIncome;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.project.R;
import com.example.project.domain.Income;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class IncomeFragment extends Fragment {

    private IncomeFragmentViewModel incomeFragmentViewModel;
    private RecyclerView recyclerView;
    private IncomeAdapter incomeAdapter;
    private FloatingActionButton buttonAddIncome;
    private TextView amoinIncome;
    private Context mContext;
    private ListenerButtonAddIncome listenerButtonAddIncome;
    private SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_income_filling, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        amoinIncome = view.findViewById(R.id.amoinIncome);
        initRecyclerView(view);
        buttonAddIncome(view);
        initViewModel();
        itemTouchDelete();
        amountIncomeAdapter();

    }

    private void buttonAddIncome(View view) {
        buttonAddIncome = view.findViewById(R.id.buttonAddIncome);
        buttonAddIncome.setOnClickListener(view1 -> listenerButtonAddIncome.OnListenerButtonAddIncome());
    }

    private void initRecyclerView(@NonNull View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new IncomeAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        incomeAdapter = (IncomeAdapter) recyclerView.getAdapter();
    }

    public interface ListenerButtonAddIncome {
        void OnListenerButtonAddIncome();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ListenerButtonAddIncome) {
            listenerButtonAddIncome = (ListenerButtonAddIncome) context;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listenerButtonAddIncome = null;
    }

    private void initViewModel() {
        if (getActivity() != null) {
            incomeFragmentViewModel = new ViewModelProvider(this, new IncomeFragmentViewModelFactory(getContext()))
                    .get(IncomeFragmentViewModel.class);
            incomeFragmentViewModel.getIncomeListLiveData().observe(getViewLifecycleOwner(), incomes ->
                    incomeAdapter.setIncomeList(incomes));

        }
    }

    private void itemTouchDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                incomeFragmentViewModel.delete(incomeAdapter.getIncomeAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void amountIncomeAdapter() {
        incomeFragmentViewModel.getIncomeListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Income>>() {
            @Override
            public void onChanged(List<Income> incomes) {
                int d = 0;
                for (int i = 0; i < incomes.size(); i++) {
                    int w = Integer.parseInt(incomes.get(i).getIncome());
                    d += w;
                }
                amoinIncome.setText(String.valueOf("+ " + d));
            }
        });
    }

}
