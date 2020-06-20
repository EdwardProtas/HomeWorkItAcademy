package com.example.project.EnterDataExpenses;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.domain.Expenses;
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

public class EnterDataExpenses extends Fragment {

    private Spinner spinerCategoryCompleteTextView;
    private Spinner billCompleteTextView;
    private EditText amountCompleteTextView;
    private EditText dateCompleteTextView;
    private TextView currencyCompleteTextView;
    private ImageView incomeBlack;
    private ImageView incomeSave;
    private Calendar selectedDate;
    private AppCompatActivity mContext;
    private SharedPreferences sharedPreferences;
    private EnterDataExpensesViewModel mEnterDataExpensesViewModel;
    private String text;
    private String bill;

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
            spinerCategoryCompleteTextView = view.findViewById(R.id.spinerCategoryCompleteTextView);
            billCompleteTextView = view.findViewById(R.id.billCompleteTextView);
            amountCompleteTextView = view.findViewById(R.id.amountCompleteTextView);
            dateCompleteTextView = view.findViewById(R.id.dateCompleteTextView);
            currencyCompleteTextView = view.findViewById(R.id.currencyCompleteTextView);
            incomeSave = view.findViewById(R.id.incomeSave);
            incomeBlack = view.findViewById(R.id.incomeBlack);
            selectedDate = Calendar.getInstance();
            sharedPreferences = mContext.getSharedPreferences(MainActivity.MAIN_ACTINITY, Context.MODE_PRIVATE);
            initDate();
            clickDate();
            onBack();
            initViewModel();
            clickSave();
            initSpinner();
        }
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext, R.array.categoryExpenses, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerCategoryCompleteTextView.setAdapter(adapter);
        spinerCategoryCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                text = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(mContext, R.array.bill, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        billCompleteTextView.setAdapter(adapter2);
        billCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bill = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void onBack() {
        incomeBlack.setOnClickListener(view -> mContext.onBackPressed());
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
        dateCompleteTextView.setText(simpleDateFormat.format(selectedDate.getTime()));
    }


    private void initViewModel() {
        mEnterDataExpensesViewModel = new ViewModelProvider(this, new EnterDataExpensesViewModelFactory(getContext()))
                .get(EnterDataExpensesViewModel.class);
    }

    private void clickSave() {
        incomeSave.setOnClickListener(view -> {
            mEnterDataExpensesViewModel.saveExpenses(new Expenses(amountCompleteTextView.getText().toString(),
                    currencyCompleteTextView.getText().toString(), convert(dateCompleteTextView.getText().toString())
                    , bill, text));
            mContext.onBackPressed();
        });
    }

    private Date convert(String s) {
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
