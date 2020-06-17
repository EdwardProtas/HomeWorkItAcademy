package com.example.project.fragmentIncome.TabLayoutFragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.domain.Income;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryIncomeHolder> {

    private List<Income> incomeList = new ArrayList<>();

    public List<Income> getIncomeList() {
        return incomeList;
    }

    public void setIncomeList(List<Income> incomeList) {
        this.incomeList = new ArrayList<>(incomeList);
        notifyDataSetChanged();
    }

    public Income getIncomeAt(int position) {
        return incomeList.get(position);
    }


    @NonNull
    @Override
    public CategoryIncomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_holder, parent, false);
        return new CategoryIncomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryIncomeHolder holder, int position) {
        holder.bind(incomeList.get(position));
    }

    @Override
    public int getItemCount() {
        return (incomeList != null) ? incomeList.size() : 0;
    }

    public class CategoryIncomeHolder extends RecyclerView.ViewHolder{

        private TextView coment_holder;
        private TextView cym_holder;
        private TextView currency_holder;

        public CategoryIncomeHolder(@NonNull View itemView) {
            super(itemView);
            coment_holder = itemView.findViewById(R.id.coment_holder);
            cym_holder = itemView.findViewById(R.id.cym_holder);
            currency_holder = itemView.findViewById(R.id.currency_holder);
        }

        void bind(Income income) {
            coment_holder.setText(income.getCategory());
            cym_holder.setText(income.getIncome());
            currency_holder.setText(income.getCurrencyIncome());
        }
    }
}
