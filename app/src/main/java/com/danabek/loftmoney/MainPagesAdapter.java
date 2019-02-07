package com.danabek.loftmoney;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainPagesAdapter extends FragmentPagerAdapter {
    public MainPagesAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0 ) {
            return new ItemsFragment();
        }else if (position == 1) {
            return new ItemsFragment();
        }else {
            return new ItemsFragment();
        }
    }

    @Override
    public int getCount() {

        return 2;
    }
}
