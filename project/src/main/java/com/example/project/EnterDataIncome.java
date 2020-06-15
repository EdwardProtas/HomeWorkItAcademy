package com.example.project;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class EnterDataIncome extends Fragment {

    private EditText categoryCompleteTextView;
    private EditText billCompleteTextView;
    private EditText amountCompleteTextView;
    private EditText dateCompleteTextView;
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
    public final static String DATE= "DATE";
    public final static String AMOUNT= "AMOUNT";
    public final static String CATEGORY= "CATEGORY";

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
            categoryCompleteTextView = view.findViewById(R.id.categoryCompleteTextView);
            billCompleteTextView = view.findViewById(R.id.billCompleteTextView);
            amountCompleteTextView = view.findViewById(R.id.amountCompleteTextView);
            dateCompleteTextView = view.findViewById(R.id.dateCompleteTextView);
            viewForOpenContextMenu = view.findViewById(R.id.viewForOpenContextMenu);
            categoryViewForOpenContextMenu = view.findViewById(R.id.categoryViewForOpenContextMenu);
            incomeSave = view.findViewById(R.id.incomeSave);
            incomeBlack = view.findViewById(R.id.incomeBlack);
            selectedDate = Calendar.getInstance();
            sharedPreferences = mContext.getSharedPreferences(MainActivity.MAIN_ACTINITY , Context.MODE_PRIVATE);
            initCreateContextMenu();
            initDate();
            clickDate();
            onBack();
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

    @Override
    public void onPause() {
        super.onPause();
        Intent intent  = new Intent();
        intent.putExtra(DATE , dateCompleteTextView.getText().toString());
        intent.putExtra(AMOUNT , amountCompleteTextView.getText().toString());
        intent.putExtra(CATEGORY , categoryCompleteTextView.getText().toString());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DATE , dateCompleteTextView.getText().toString());
        editor.putString(AMOUNT , amountCompleteTextView.getText().toString());
        editor.putString(CATEGORY , categoryCompleteTextView.getText().toString());
        editor.apply();
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
        dateCompleteTextView.setText(DateUtils.formatDateTime(mContext,
                selectedDate.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
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
}
