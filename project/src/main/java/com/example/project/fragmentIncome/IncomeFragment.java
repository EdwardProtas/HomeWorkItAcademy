package com.example.project.fragmentIncome;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.fragmentMain.MainFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
        return inflater.inflate(R.layout.fragment_income_filling , container , false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        buttonAddIncome(view);
    }

    private void buttonAddIncome(View view) {
        buttonAddIncome = view.findViewById(R.id.buttonAddIncome);
        buttonAddIncome.setOnClickListener(view1 -> listenerButtonAddIncome.OnListenerButtonAddIncome());
    }

    private void initRecyclerView(@NonNull View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new IncomeAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL , false));
        incomeAdapter = (IncomeAdapter) recyclerView.getAdapter();
    }

    public interface ListenerButtonAddIncome{
        void OnListenerButtonAddIncome();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ListenerButtonAddIncome){
            listenerButtonAddIncome = (ListenerButtonAddIncome) context;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listenerButtonAddIncome = null;
    }
}
