package com.danabek.loftmoney;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BalanceFragment extends Fragment {
    public static BalanceFragment newInstances(int type) {
        BalanceFragment fragment = new BalanceFragment();
        return fragment;
    }

    public static final int TYPE_BALANCE = 3;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance,container,false);

    }
}
