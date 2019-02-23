package com.danabek.loftmoney;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BalanceFragment extends Fragment {
    public static BalanceFragment newInstances(int type) {
        Bundle args = new Bundle();
        BalanceFragment fragment = new BalanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static final int TYPE_BALANCE = 3;
    private TextView balanceView;
    private TextView expenseView;
    private TextView incomeView;
    private DiagramView diagramView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        balanceView = view.findViewById(R.id.balance_value);
        incomeView = view.findViewById(R.id.income_value);
        expenseView = view.findViewById(R.id.expense_value);
        diagramView = view.findViewById(R.id.diagram_view);

        loadBalance();

    }

    private void loadBalance() {
        balanceView.setText("64000");
        expenseView.setText("5400");
        incomeView.setText("72000");

        diagramView.update(72000, 5400);
    }
}
