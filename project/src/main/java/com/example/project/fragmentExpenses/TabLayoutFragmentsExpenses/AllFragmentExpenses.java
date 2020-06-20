package com.example.project.fragmentExpenses.TabLayoutFragmentsExpenses;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.domain.Expenses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AllFragmentExpenses extends Fragment {

    private RecyclerView recyclerViewAllExpenses;
    private AllFragmentExpensesAdapter mAllFragmentExpensesAdapter;
    private Activity mContext;
    private AllFragmentExpensesViewModel mAllFragmentExpensesViewModel;
    private Calendar selectedDate;
    private String importInRecycler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_expenses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        if (mContext != null) {
            initRecyclerView(view);
            initViewModel();
            itemTouchDelete();
            setHasOptionsMenu(true);
            selectedDate = Calendar.getInstance();
        }
    }

    private void initRecyclerView(View view) {
        recyclerViewAllExpenses = view.findViewById(R.id.recyclerViewAllExpenses);
        recyclerViewAllExpenses.setAdapter(new AllFragmentExpensesAdapter());
        recyclerViewAllExpenses.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        mAllFragmentExpensesAdapter = (AllFragmentExpensesAdapter) recyclerViewAllExpenses.getAdapter();
    }

    private void initViewModel() {
        if (getActivity() != null) {
            mAllFragmentExpensesViewModel = new ViewModelProvider(this,
                    new AllFragmentExpensesViewModelFactory(getContext()))
                    .get(AllFragmentExpensesViewModel.class);
            mAllFragmentExpensesViewModel.getExpenseseListLiveData()
                    .observe(getViewLifecycleOwner(), this::loadingExpensesInRecycler);
        }
    }

    private void loadingExpensesInRecycler(List<Expenses> expenses) {
        mAllFragmentExpensesAdapter.setExpensesList(expenses);
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
                mAllFragmentExpensesViewModel.delete(mAllFragmentExpensesAdapter.getExpensesAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerViewAllExpenses);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.byCategory:
                createDialogCategory();
                break;
            case R.id.byBill:
                createDialogBill();
                break;
            case R.id.byDate:
                createDialogData();
                break;
            case R.id.all:
                mAllFragmentExpensesViewModel.getExpenseseListLiveData()
                        .observe(getViewLifecycleOwner(), this::loadingExpensesInRecycler);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createDialogCategory() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View dialog = layoutInflater.inflate(R.layout.dialog_category_expenses, null);
        AlertDialog.Builder dialogCategory = new AlertDialog.Builder(getContext());
        dialogCategory.setView(dialog);
        TextView utilities = dialog.findViewById(R.id.utilities);
        TextView phone = dialog.findViewById(R.id.phone);
        TextView internet = dialog.findViewById(R.id.internet);
        TextView transport = dialog.findViewById(R.id.transport);
        TextView supermarket = dialog.findViewById(R.id.supermarket);
        TextView medicine = dialog.findViewById(R.id.medicine);
        TextView clothing = dialog.findViewById(R.id.clothing);
        TextView cafe = dialog.findViewById(R.id.cafe);
        TextView other = dialog.findViewById(R.id.other);
        final RadioButton radioutilities = dialog.findViewById(R.id.radioutilities);
        final RadioButton radiophone = dialog.findViewById(R.id.radiophone);
        final RadioButton radiointernet = dialog.findViewById(R.id.radiointernet);
        final RadioButton radiotransport = dialog.findViewById(R.id.radiotransport);
        final RadioButton radiosupermarket = dialog.findViewById(R.id.radiosupermarket);
        final RadioButton radiomedicine = dialog.findViewById(R.id.radiomedicine);
        final RadioButton radioclothing = dialog.findViewById(R.id.radioclothing);
        final RadioButton radiocafe = dialog.findViewById(R.id.radiocafe);
        final RadioButton radioother = dialog.findViewById(R.id.radioother);
        final RadioGroup radioCategory = dialog.findViewById(R.id.radioCategory);
        radioCategory.clearCheck();
        radioCategory.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            switch (checkedId) {
                case R.id.radioutilities:
                    importInRecycler = utilities.getText().toString();
                    break;
                case R.id.radiophone:
                    importInRecycler = phone.getText().toString();
                    break;
                case R.id.radiointernet:
                    importInRecycler = internet.getText().toString();
                    break;
                case R.id.radiotransport:
                    importInRecycler = transport.getText().toString();
                    break;
                case R.id.radiosupermarket:
                    importInRecycler = supermarket.getText().toString();
                    break;
                case R.id.radiomedicine:
                    importInRecycler = medicine.getText().toString();
                    break;
                case R.id.radioclothing:
                    importInRecycler = clothing.getText().toString();
                    break;
                case R.id.radioother:
                    importInRecycler = other.getText().toString();
                    case R.id.radiocafe:
                    importInRecycler = cafe.getText().toString();
                    break;
            }
        });
        dialogCategory.setCancelable(false)
                .setTitle(R.string.byCategory)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    mAllFragmentExpensesViewModel.getExpensesCategoryLiveData(importInRecycler).observe(getViewLifecycleOwner(),
                            expenses -> mAllFragmentExpensesAdapter.setExpensesList(expenses));
                })
                .setNegativeButton("Отмена", (dialogInterface, i) ->
                        dialogInterface.cancel())
                .create()
                .show();
    }

    private void createDialogBill() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View dialog = layoutInflater.inflate(R.layout.dialog_bill, null);
        AlertDialog.Builder dialogCategory = new AlertDialog.Builder(getContext());
        dialogCategory.setView(dialog);
        TextView cash = dialog.findViewById(R.id.cash);
        TextView karta = dialog.findViewById(R.id.karta);
        final RadioButton radioCash = dialog.findViewById(R.id.radioCash);
        final RadioButton radioKarta = dialog.findViewById(R.id.radioKarta);
        final RadioGroup radioBill = dialog.findViewById(R.id.radioBill);
        radioBill.clearCheck();
        radioBill.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            switch (checkedId) {
                case R.id.radioCash:
                    importInRecycler = cash.getText().toString();
                    break;
                case R.id.radioKarta:
                    importInRecycler = karta.getText().toString();
                    break;
            }
        });
        dialogCategory.setCancelable(false)
                .setTitle(R.string.byBill)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    mAllFragmentExpensesViewModel.getExpensesBillLiveData(importInRecycler).observe(getViewLifecycleOwner(),
                            expenses -> mAllFragmentExpensesAdapter.setExpensesList(expenses));
                })
                .setNegativeButton("Отмена", (dialogInterface, i) ->
                        dialogInterface.cancel())
                .create()
                .show();
    }

    private void createDialogData() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View dialog = layoutInflater.inflate(R.layout.dialog_data, null);
        AlertDialog.Builder dialogCategory = new AlertDialog.Builder(getContext());
        dialogCategory.setView(dialog);
        final EditText data_text = dialog.findViewById(R.id.data_text);
        formatData(data_text);
        clickEditText(data_text);
        dialogCategory.setCancelable(false)
                .setTitle(R.string.byDate)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    Date d = convert(data_text.getText().toString());
                    long dat = d.getTime();
                    mAllFragmentExpensesViewModel.getExpensesDateLiveData(dat)
                            .observe(getViewLifecycleOwner(), incomes ->
                                    mAllFragmentExpensesAdapter.setExpensesList(incomes));
                })
                .setNegativeButton("Отмена", (dialogInterface, i) ->
                        dialogInterface.cancel())
                .create()
                .show();
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
    private void formatData(EditText data_text) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
        data_text.setText(simpleDateFormat.format(selectedDate.getTime()));
    }

    private void clickEditText(EditText data_text) {
        data_text.setOnClickListener(view -> {
            DatePickerDialog.OnDateSetListener d = (view1, year, monthOfYear, dayOfMonth) -> {
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, monthOfYear);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                formatData(data_text);
            };
            new DatePickerDialog(mContext, d,
                    selectedDate.get(Calendar.YEAR),
                    selectedDate.get(Calendar.MONTH),
                    selectedDate.get(Calendar.DAY_OF_MONTH))
                    .show();
        });
    }
}
