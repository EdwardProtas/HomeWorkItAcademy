package com.example.project.fragmentIncome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.domain.Income;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeHolder> {

    private List<Income> incomeList = new ArrayList<>();
    private ListenerIncome listenerIncome;

    public void addIncome (Income income){
        incomeList.add(income);
        notifyDataSetChanged();
    }

    public void setIncomeList(List<Income> incomeList) {
        this.incomeList = new ArrayList<>(incomeList) ;
        notifyDataSetChanged();
    }

    interface ListenerIncome{
        void onClickLisneterIncome(Income income);
    }

    @NonNull
    @Override
    public IncomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_holder ,parent, false);
        return new IncomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeHolder holder, int position) {
        holder.bind(incomeList.get(position));
    }

    @Override
    public int getItemCount() {
        return (incomeList != null) ? incomeList.size() : 0;
    }

    public class IncomeHolder extends RecyclerView.ViewHolder {

        private TextView date_holder;
        private TextView coment_holder;
        private TextView cym_holder;
        private TextView currency_holder;

        public IncomeHolder(@NonNull View itemView) {
            super(itemView);
            date_holder = itemView.findViewById(R.id.date_holder);
            coment_holder = itemView.findViewById(R.id.coment_holder);
            cym_holder = itemView.findViewById(R.id.cym_holder);
            currency_holder = itemView.findViewById(R.id.currency_holder);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listenerIncome.onClickLisneterIncome(incomeList.get(getAdapterPosition()));
                }
            });
        }

        void bind(Income income) {
            date_holder.setText(convertorTime(income));
            coment_holder.setText(income.getBill());
            cym_holder.setText((int) income.getIncome());
            currency_holder.setText(income.getBill());
        }

        public String convertorTime(Income income) {
            return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(income.getData());
        }
    }
}
