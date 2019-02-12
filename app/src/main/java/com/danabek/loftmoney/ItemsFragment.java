package com.danabek.loftmoney;


import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {
    private int ADD_ITEM_REQUEST_CODE = 111;

    public static ItemsFragment newInstances(String type) {
        ItemsFragment fragment = new ItemsFragment();

        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE, type);
        fragment.setArguments(bundle);

        return fragment;
    }


    public static final String KEY_TYPE = "type";
    private String token = "$2y$10$MI9aJHOPZNR1WLHMPoRkx.6geJcwuzU/JxArRxeOoK9KXyPs3DzfG";
    private SwipeRefreshLayout refresh;


    private ItemsAdapter adapter;
    private String type;
    private Api api;

    public ItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ItemsAdapter(requireContext());

        type = getArguments().getString(KEY_TYPE);
        Application application = getActivity().getApplication();
        App app = (App) application;
        api = app.getApi();
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
        //refresher
        refresh = view.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });

        //Add devider
        RecyclerView.LayoutManager layoutManager = (new LinearLayoutManager(requireContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recycler.addItemDecoration(dividerItemDecoration);

        loadItems();
    }

    private void loadItems() {
        //    new LoadItemsTask().start();
        Call call = api.getItems(type, token);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                refresh.setRefreshing(false);
                List<ItemPosition> items = (List<ItemPosition>) response.body();
                adapter.setItems(items);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                refresh.setRefreshing(false);
                Log.e(TAG, "LoadItems: ", t);
            }
        });
    }

    void onFabClick() {
        Intent intent = new Intent(requireContext(), AdditemActivity.class);
        startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ITEM_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String name = data.getStringExtra(AdditemActivity.KEY_NAME);
                String price = data.getStringExtra(AdditemActivity.KEY_PRICE);
                Log.d(TAG, "onFragmetResult = " + name);
                Log.d(TAG, "onFragmentResult = " + price);
                ItemPosition item = new ItemPosition(name,Double.valueOf(price),type);
                adapter.addItem(item);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}




