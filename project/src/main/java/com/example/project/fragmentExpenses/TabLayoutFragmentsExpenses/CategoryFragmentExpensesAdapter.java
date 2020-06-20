package com.example.project.fragmentExpenses.TabLayoutFragmentsExpenses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.domain.Expenses;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryFragmentExpensesAdapter extends RecyclerView.Adapter<CategoryFragmentExpensesAdapter.CategoryExpensesItem> {

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
    public CategoryExpensesItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_holder, parent, false);
        return new CategoryExpensesItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryExpensesItem holder, int position) {
        holder.bind(expensesList.get(position));
    }

    @Override
    public int getItemCount() {
        return (expensesList != null) ? expensesList.size() : 0;
    }

    public class CategoryExpensesItem extends RecyclerView.ViewHolder{

        private TextView coment_holder;
        private TextView cym_holder;
        private TextView currency_holder;

        public CategoryExpensesItem(@NonNull View itemView) {
            super(itemView);
            coment_holder = itemView.findViewById(R.id.coment_holder);
            cym_holder = itemView.findViewById(R.id.cym_holder);
            currency_holder = itemView.findViewById(R.id.currency_holder);
        }
        void bind(Expenses expenses) {
            coment_holder.setText(expenses.getCategory());
            cym_holder.setText(expenses.getExpenses());
            currency_holder.setText(expenses.getCurrencyExpenses());
        }
    }

}
