package com.example.project.EnterDataIncome;

import android.app.DatePickerDialog;
import android.content.Context;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.domain.Income;
import com.facebook.stetho.Stetho;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class EnterDataIncome extends Fragment {

    private EditText categoryCompleteTextView;
    private EditText billCompleteTextView;
    private EditText amountCompleteTextView;
    private EditText dateCompleteTextView;
    private TextView currencyCompleteTextView;
    private ImageView incomeBlack;
    private ImageView incomeSave;
    private Calendar selectedDate;
    private AppCompatActivity mContext;
    private View viewForOpenContextMenu;
    private View categoryViewForOpenContextMenu;
    private SharedPreferences sharedPreferences;
    private final static int MENU_JOB = 1;
    private final static int MENU_PRESENT = 2;
    private final static int MENU_CASH = 3;
    private final static int MENU_MAP = 4;
    private EnterDataIncomeViewModel enterDataIncomeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data_filling, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = (AppCompatActivity) getActivity();
        if (mContext != null) {
            Stetho.initializeWithDefaults(mContext);
            categoryCompleteTextView = view.findViewById(R.id.categoryCompleteTextView);
            billCompleteTextView = view.findViewById(R.id.billCompleteTextView);
            amountCompleteTextView = view.findViewById(R.id.amountCompleteTextView);
            dateCompleteTextView = view.findViewById(R.id.dateCompleteTextView);
            viewForOpenContextMenu = view.findViewById(R.id.viewForOpenContextMenu);
            currencyCompleteTextView = view.findViewById(R.id.currencyCompleteTextView);
            categoryViewForOpenContextMenu = view.findViewById(R.id.categoryViewForOpenContextMenu);
            incomeSave = view.findViewById(R.id.incomeSave);
            incomeBlack = view.findViewById(R.id.incomeBlack);
            selectedDate = Calendar.getInstance();
            sharedPreferences = mContext.getSharedPreferences(MainActivity.MAIN_ACTINITY , Context.MODE_PRIVATE);
            initCreateContextMenu();
            initDate();
            clickDate();
            onBack();
            initViewModel();
            clickSave();
        }
    }

    private void onBack() {
        incomeBlack.setOnClickListener(view -> mContext.onBackPressed());
    }

    private void initCreateContextMenu() {
        billCompleteTextView.setOnClickListener(view -> {
            registerForContextMenu(viewForOpenContextMenu);
            mContext.openContextMenu(viewForOpenContextMenu);
        });
        categoryCompleteTextView.setOnClickListener(view -> {
            registerForContextMenu(categoryViewForOpenContextMenu);
            mContext.openContextMenu(categoryViewForOpenContextMenu);
        });
    }

    private void clickDate() {
        dateCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(mContext, d,
                        selectedDate.get(Calendar.YEAR),
                        selectedDate.get(Calendar.MONTH),
                        selectedDate.get(Calendar.DAY_OF_MONTH))
                        .show();
            }

            DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    selectedDate.set(Calendar.YEAR, year);
                    selectedDate.set(Calendar.MONTH, monthOfYear);
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    initDate();
                }
            };
        });
    }

    private void initDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy" , Locale.getDefault());
        dateCompleteTextView.setText(simpleDateFormat.format(selectedDate.getTime()));
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        switch (v.getId()) {
            case R.id.categoryViewForOpenContextMenu:
                menu.add(0, MENU_JOB, 0, R.string.job);
                menu.add(0, MENU_PRESENT, 0, R.string.present);
                break;
            case R.id.viewForOpenContextMenu:
                menu.add(0, MENU_CASH, 0, R.string.cash);
                menu.add(0, MENU_MAP, 0, R.string.karta);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case MENU_CASH:
                billCompleteTextView.setText(R.string.cash);
                break;
            case MENU_MAP:
                billCompleteTextView.setText(R.string.karta);
                break;
            case MENU_JOB:
                categoryCompleteTextView.setText(R.string.job);
                break;
            case MENU_PRESENT:
                categoryCompleteTextView.setText(R.string.present);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void initViewModel() {
        enterDataIncomeViewModel = new ViewModelProvider(this , new EnterDataIncomeViewModelFactory(getContext()))
                .get(EnterDataIncomeViewModel.class);
    }

    private void clickSave(){
        incomeSave.setOnClickListener(view -> {
            enterDataIncomeViewModel.saveIncome(new Income(amountCompleteTextView.getText().toString(),
                    currencyCompleteTextView.getText().toString() , convert(dateCompleteTextView.getText().toString())
                    ,billCompleteTextView.getText().toString(),
                    categoryCompleteTextView.getText().toString()));
            mContext.onBackPressed();
        });
    }

    private Date convert(String s){
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
        try {
            date = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }
}
