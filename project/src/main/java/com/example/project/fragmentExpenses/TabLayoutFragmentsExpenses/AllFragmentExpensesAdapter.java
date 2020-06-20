package com.example.project.fragmentExpenses.TabLayoutFragmentsExpenses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.domain.Expenses;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllFragmentExpensesAdapter extends RecyclerView.Adapter<AllFragmentExpensesAdapter.AllExpensesHolder> {

    private List<Expenses> expensesList = new ArrayList<>();

    public List<Expenses> getExpensesList() {
        return expensesList;
    }

    public void setExpensesList(List<Expenses> expensesList) {
        this.expensesList = new ArrayList<>(expensesList);
        notifyDataSetChanged();
    }

    public Expenses getExpensesAt(int position) {
        return expensesList.get(position);
    }


    @NonNull
    @Override
    public AllExpensesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_holder, parent, false);
        return new AllExpensesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllExpensesHolder holder, int position) {
        holder.bind(expensesList.get(position));
    }

    @Override
    public int getItemCount() {
        return (expensesList != null) ? expensesList.size() : 0;
    }

    public class AllExpensesHolder extends RecyclerView.ViewHolder{

        private TextView date_holder;
        private TextView coment_holder;
        private TextView cym_holder;
        private TextView currency_holder;

        public AllExpensesHolder(@NonNull View itemView) {
            super(itemView);
            date_holder = itemView.findViewById(R.id.date_holder);
            coment_holder = itemView.findViewById(R.id.coment_holder);
            cym_holder = itemView.findViewById(R.id.cym_holder);
            currency_holder = itemView.findViewById(R.id.currency_holder);
        }

        void bind(Expenses expenses) {
            date_holder.setText(convertorTime(expenses));
            coment_holder.setText(expenses.getCategory());
            cym_holder.setText(expenses.getExpenses());
            currency_holder.setText(expenses.getCurrencyExpenses());
        }

        public String convertorTime(Expenses expenses) {
            return new SimpleDateFormat("dd.MM.yy", Locale.getDefault()).format(expenses.getData());
        }
    }
}
