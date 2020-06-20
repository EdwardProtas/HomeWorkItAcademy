package com.example.project.fragmentIncome.TabLayoutFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.project.domain.Income;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AllFragment extends Fragment {

    private RecyclerView recyclerView;
    private IncomeAdapter incomeAdapter;
    private Activity mContext;
    private AllFragmentViewModel allFragmentViewModel;
    private Calendar selectedDate;
    private String importInRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        if (mContext != null) {
            initRecyclerView(view);
            initViewModel();
            itemTouchDelete();
            selectedDate = Calendar.getInstance();
            setHasOptionsMenu(true);

        }
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new IncomeAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        incomeAdapter = (IncomeAdapter) recyclerView.getAdapter();
    }

    private void initViewModel() {
        if (getActivity() != null) {
            allFragmentViewModel = new ViewModelProvider(this, new AllFragmentViewModelFactory(getContext()))
                    .get(AllFragmentViewModel.class);
            allFragmentViewModel.getIncomeListLiveData().observe(getViewLifecycleOwner(), this::loadingIncomeInRecycler);
        }
    }

    private void loadingIncomeInRecycler(List<Income> incomes) {
        incomeAdapter.setIncomeList(incomes);
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
                allFragmentViewModel.delete(incomeAdapter.getIncomeAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);
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
                allFragmentViewModel.getIncomeListLiveData()
                        .observe(getViewLifecycleOwner(), this::loadingIncomeInRecycler);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createDialogCategory() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View dialog = layoutInflater.inflate(R.layout.dialog_category, null);
        AlertDialog.Builder dialogCategory = new AlertDialog.Builder(getContext());
        dialogCategory.setView(dialog);
        TextView job = dialog.findViewById(R.id.job);
        TextView pres = dialog.findViewById(R.id.pres);
        final RadioButton radioJob = dialog.findViewById(R.id.radioJob);
        final RadioButton radioPresent = dialog.findViewById(R.id.radioPresent);
        final RadioGroup radioCategory = dialog.findViewById(R.id.radioCategory);
        radioCategory.clearCheck();
        radioCategory.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            switch (checkedId) {
                case R.id.radioJob:
                    importInRecycler = job.getText().toString();
                    break;
                case R.id.radioPresent:
                    importInRecycler = pres.getText().toString();
                    break;
            }
        });
        dialogCategory.setCancelable(false)
                .setTitle(R.string.byCategory)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    allFragmentViewModel.getIncomeCategoryLiveData(importInRecycler).observe(getViewLifecycleOwner(),
                            incomes -> incomeAdapter.setIncomeList(incomes));
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
                    allFragmentViewModel.getIncomeBillLiveData(importInRecycler).observe(getViewLifecycleOwner(), incomes -> {
                        Log.d("Tag3", incomes.toString());
                        incomeAdapter.setIncomeList(incomes);
                    });
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
                    allFragmentViewModel.getIncomeDateLiveData(dat)
                            .observe(getViewLifecycleOwner(), incomes ->
                                    incomeAdapter.setIncomeList(incomes));
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
