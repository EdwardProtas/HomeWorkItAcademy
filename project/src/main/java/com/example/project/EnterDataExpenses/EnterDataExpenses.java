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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
    private final static int MENU_UTILITIES = 1;
    private final static int MENU_PHONE = 2;
    private final static int MENU_INTERNET = 3;
    private final static int MENU_TRANSPORT = 4;
    private final static int MENU_SUPERMARKET = 5;
    private final static int MENU_MEDICINE = 6;
    private final static int MENU_CLOTHING = 7;
    private final static int MENU_CAFE = 8;
    private final static int MENU_PRESENT = 9;
    private final static int MENU_OTHER = 10;
    private final static int MENU_CASH = 11;
    private final static int MENU_MAP = 12;
    private EnterDataExpensesViewModel mEnterDataExpensesViewModel;

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
            sharedPreferences = mContext.getSharedPreferences(MainActivity.MAIN_ACTINITY, Context.MODE_PRIVATE);
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
        dateCompleteTextView.setText(simpleDateFormat.format(selectedDate.getTime()));
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        switch (v.getId()) {
            case R.id.categoryViewForOpenContextMenu:
                menu.add(0, MENU_UTILITIES, 0, R.string.utilities);
                menu.add(0, MENU_PHONE, 0, R.string.phone);
                menu.add(0, MENU_INTERNET, 0, R.string.internet);
                menu.add(0, MENU_TRANSPORT, 0, R.string.transport);
                menu.add(0, MENU_SUPERMARKET, 0, R.string.supermarket);
                menu.add(0, MENU_MEDICINE, 0, R.string.medicine);
                menu.add(0, MENU_CLOTHING, 0, R.string.clothing);
                menu.add(0, MENU_CAFE, 0, R.string.cafe);
                menu.add(0, MENU_PRESENT, 0, R.string.present);
                menu.add(0, MENU_OTHER, 0, R.string.other);
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
        switch (item.getItemId()) {
            case MENU_CASH:
                billCompleteTextView.setText(R.string.cash);
                break;
            case MENU_MAP:
                billCompleteTextView.setText(R.string.karta);
                break;
            case MENU_UTILITIES:
                categoryCompleteTextView.setText(R.string.utilities);
                break;
            case MENU_PRESENT:
                categoryCompleteTextView.setText(R.string.present);
                break;
            case MENU_PHONE:
                categoryCompleteTextView.setText(R.string.phone);
                break;
            case MENU_INTERNET:
                categoryCompleteTextView.setText(R.string.internet);
                break;
            case MENU_TRANSPORT:
                categoryCompleteTextView.setText(R.string.transport);
                break;
            case MENU_SUPERMARKET:
                categoryCompleteTextView.setText(R.string.supermarket);
                break;
            case MENU_MEDICINE:
                categoryCompleteTextView.setText(R.string.medicine);
                break;
            case MENU_CLOTHING:
                categoryCompleteTextView.setText(R.string.clothing);
                break;
            case MENU_CAFE:
                categoryCompleteTextView.setText(R.string.cafe);
                break;
            case MENU_OTHER:
                categoryCompleteTextView.setText(R.string.other);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void initViewModel() {
        mEnterDataExpensesViewModel = new ViewModelProvider(this, new EnterDataExpensesViewModelFactory(getContext()))
                .get(EnterDataExpensesViewModel.class);
    }

    private void clickSave() {
        incomeSave.setOnClickListener(view -> {
            mEnterDataExpensesViewModel.saveExpenses(new Expenses(amountCompleteTextView.getText().toString(),
                    currencyCompleteTextView.getText().toString(), convert(dateCompleteTextView.getText().toString())
                    , billCompleteTextView.getText().toString(),
                    categoryCompleteTextView.getText().toString()));
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
