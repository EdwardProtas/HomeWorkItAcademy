package com.example.project.fragmentIncome.TabLayoutFragments;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapterIncome extends FragmentPagerAdapter {

   private final List<Fragment> mFragmentList = new ArrayList<>();
   private final List<String> mStringList = new ArrayList<>();


    public PagerAdapterIncome(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm, numOfTabs);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mStringList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mStringList.get(position);
    }

    public void AddFragment(Fragment fragment , String  title){
        mStringList.add(title);
        mFragmentList.add(fragment);
    }

}
