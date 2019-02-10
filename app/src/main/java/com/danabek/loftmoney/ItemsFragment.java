package com.danabek.loftmoney;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {

    public static ItemsFragment newInstances(int type) {
        ItemsFragment fragment = new ItemsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ItemsFragment.KEY_TYPE, ItemsFragment.TYPE_INCOMES);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static final int TYPE_UNKNOW = 0;
    public static final int TYPE_INCOMES = 1;
    public static final int TYPE_EXPENSES = 2;
    public static final int TYPE_BALANCE = 3;

    public static final String KEY_TYPE = "type";

    private ItemsAdapter adapter;
    private int type;

    public ItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ItemsAdapter(requireContext());

        type = getArguments().getInt(KEY_TYPE, TYPE_UNKNOW);


        if (type == TYPE_UNKNOW) {
            throw new IllegalStateException("Unknow fragment type");
        }


        Log.d("ItemsFragment", "type= " + type);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recycler = view.findViewById(R.id.recycler);

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        //Add devider
        RecyclerView.LayoutManager layoutManager = (new LinearLayoutManager(requireContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recycler.addItemDecoration(dividerItemDecoration);

        loadItems();
    }

    private void loadItems() {

    }


}
