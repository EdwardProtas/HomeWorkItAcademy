package com.example.project.fragmentAmount;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.domain.Income;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

public class AmountFragment extends Fragment {

private AmountFragmentViewModel mAmountFragmentViewModel;

    public static final String AMOUN_All = "AMOUN_All";
    private TextView amoinAll;
    private TextView currenceAmoinAll;
    private TextView cashAll;
    private TextView cashCurrenceAmoinAll;
    private TextView kartAll;
    private TextView kartaCurrenceAmoinAll;
    private TextView nal;
    private TextView kar;
    private Activity mContext;
    private FragmentActivity mFragmentActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_amount , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        if(mContext != null) {
            amoinAll = view.findViewById(R.id.amoinAll);
            nal = view.findViewById(R.id.nal);
            kar = view.findViewById(R.id.kar);
            currenceAmoinAll = view.findViewById(R.id.currenceAmoinAll);
            cashAll = view.findViewById(R.id.cashAll);
            cashCurrenceAmoinAll = view.findViewById(R.id.cashCurrenceAmoinAll);
            kartAll = view.findViewById(R.id.kartAll);
            kartaCurrenceAmoinAll = view.findViewById(R.id.kartaCurrenceAmoinAll);
            initViewModel();
            Toolbar toolbar = getActivity().findViewById(R.id.toolBar);
            toolbar.setTitle("Счет");
        }
    }


    private void initViewModel() {
        mAmountFragmentViewModel = new ViewModelProvider(this , new AmountFragmentViewModelFactory(getContext()))
                .get(AmountFragmentViewModel.class);
        mAmountFragmentViewModel.getIncomeListLiveData().observe(getViewLifecycleOwner() , this::addAmoutInTextView);
        mAmountFragmentViewModel.getIncomeBillLiveData(kar.getText().toString()).observe(getViewLifecycleOwner(),this::addAmoutInTextKar);
        mAmountFragmentViewModel.getIncomeBillLiveData(nal.getText().toString()).observe(getViewLifecycleOwner(),this::addAmoutInTextNal);

    }

    private void addAmoutInTextNal(List<Income> incomes) {
        int money = 0;
        for (Income in : incomes) {
            money += Integer.parseInt(in.getIncome());
        }
        if (String.valueOf(money).equals("0")) {
            cashAll.setVisibility(View.INVISIBLE);
            cashCurrenceAmoinAll.setVisibility(View.INVISIBLE);
        } else {
            cashAll.setText(String.valueOf(money));
            cashCurrenceAmoinAll.setVisibility(View.VISIBLE);
        }
    }

    private void addAmoutInTextKar(List<Income> incomes) {
        int money = 0;
        for (Income in : incomes) {
            money += Integer.parseInt(in.getIncome());
        }
        if (String.valueOf(money).equals("0")) {
            kartAll.setVisibility(View.INVISIBLE);
            kartaCurrenceAmoinAll.setVisibility(View.INVISIBLE);
        } else {
            kartAll.setText(String.valueOf(money));
        } kartaCurrenceAmoinAll.setVisibility(View.VISIBLE);
    }

    private void addAmoutInTextView(List<Income> incomes) {
        int money = 0;
        for (Income in : incomes) {
            money += Integer.parseInt(in.getIncome());
        }
        if (String.valueOf(money).equals("0")) {
            amoinAll.setVisibility(View.INVISIBLE);
            currenceAmoinAll.setVisibility(View.INVISIBLE);
        } else {
            amoinAll.setText(String.valueOf(money));
        }   currenceAmoinAll.setVisibility(View.VISIBLE);
    }


}
