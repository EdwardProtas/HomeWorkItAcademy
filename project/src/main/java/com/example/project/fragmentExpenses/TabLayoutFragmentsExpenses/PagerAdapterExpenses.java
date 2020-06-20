package com.example.project.fragmentExpenses.TabLayoutFragmentsExpenses;

import com.example.project.fragmentIncome.TabLayoutFragments.AllFragment;
import com.example.project.fragmentIncome.TabLayoutFragments.CategoryFragment;
import com.example.project.fragmentIncome.TabLayoutFragments.DaysFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class PagerAdapterExpenses extends FragmentPagerAdapter {

    private int numOfTabs;

    public PagerAdapterExpenses(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm, numOfTabs);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AllFragmentExpenses();
            case 1:
                return new DaysFragmentExpenses();
            case 2:
                return new CategoryFragmentExpenses();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
