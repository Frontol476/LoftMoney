package com.danabek.loftmoney;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalanceFragment extends Fragment {
    public static BalanceFragment newInstances(int type) {
        Bundle args = new Bundle();
        BalanceFragment fragment = new BalanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static final int TYPE_BALANCE = 3;
    private Api api;
    private TextView balanceView;
    private TextView expenseView;
    private TextView incomeView;
    private DiagramView diagramView;
    private SharedPreferences preferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application application = getActivity().getApplication();
        App app = (App) application;
        api = app.getApi();
        preferences = app.getPreference();
    }

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
        String token = preferences.getString("auth_token", null);
        Call<BalanceResponse> call = api.balance(token);
        call.enqueue(new Callback<BalanceResponse>() {
            @Override
            public void onResponse(Call<BalanceResponse> call, Response<BalanceResponse> response) {
                BalanceResponse balanceResponse = response.body();
                int balance = balanceResponse.getTotalIncome() - balanceResponse.getTotalExpense();
                balanceView.setText(getString(R.string.balance_fragment_count, balance));
                expenseView.setText(getString(R.string.balance_fragment_count, balanceResponse.getTotalExpense()));
                incomeView.setText(getString(R.string.balance_fragment_count, balanceResponse.getTotalIncome()));
                diagramView.update(balanceResponse.getTotalIncome(), balanceResponse.getTotalExpense());
            }

            @Override
            public void onFailure(Call<BalanceResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && preferences != null) {
            loadBalance();
        }
    }

}
