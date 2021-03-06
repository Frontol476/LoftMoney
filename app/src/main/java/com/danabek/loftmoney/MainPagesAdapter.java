package com.danabek.loftmoney;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainPagesAdapter extends FragmentPagerAdapter {
     static final int PAGE_EXPENSES = 0;
     static final int PAGE_INCOMES = 1;
     static final int PAGE_BALANCE = 2;
     static final int PAGE_COUNT = 3;

    private Context context;

    public MainPagesAdapter(@NonNull FragmentManager fm, Context context) {

        super(fm);
        this.context = context;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case PAGE_INCOMES:
                return ItemsFragment.newInstances(ItemPosition.TYPE_INCOME);
            case PAGE_EXPENSES:
                return ItemsFragment.newInstances(ItemPosition.TYPE_EXPENSE);
            case PAGE_BALANCE:
                return BalanceFragment.newInstances(BalanceFragment.TYPE_BALANCE);
            default:
                return new ItemsFragment();
        }
    }

    @Override
    public int getCount() {

        return PAGE_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case PAGE_INCOMES:
                return context.getString(R.string.main_tab_incomes);
            case PAGE_EXPENSES:
                return context.getString(R.string.main_tab_expenses);
            case PAGE_BALANCE:
                return context.getString(R.string.main_tab_balance);
            default:
                return "";
        }
    }
}
