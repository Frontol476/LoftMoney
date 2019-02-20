package com.danabek.loftmoney;


import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
    private ActionMode actionMode;
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
        adapter.setListener(new AdapterListener());
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String token = preferences.getString("auth_token", null);

        Call<List<ItemPosition>> call = api.getItems(type, token);
        call.enqueue(new Callback<List<ItemPosition>>() {
            @Override
            public void onResponse(Call<List<ItemPosition>> call, Response<List<ItemPosition>> response) {
                refresh.setRefreshing(false);
                List<ItemPosition> items = (List<ItemPosition>) response.body();
                adapter.setItems(items);
            }

            @Override
            public void onFailure(Call<List<ItemPosition>> call, Throwable t) {
                refresh.setRefreshing(false);
                Log.e(TAG, "LoadItems: ", t);
            }
        });
    }

    private void removeItem(Long id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String token = preferences.getString("auth_token", null);

        Call<Object> call = api.removeItem(id, token);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    void onFabClick() {
        Intent intent = new Intent(requireContext(), AdditemActivity.class);
        intent.putExtra(AdditemActivity.KEY_TYPE, type);
        startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ITEM_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            loadItems();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private class AdapterListener implements ItemsAdapterListener {
        @Override
        public void onItemClick(ItemPosition item, int position) {
            Log.i(TAG, "onItemClick: " + item.getName());
            if (actionMode == null) {
                return;
            }
            //  int selectedCount = adapter.getSelectedPositions().size();
            // actionMode.setTitle("Выбрано "+String.valueOf(selectedCount));
            toggleItem(position);
        }

        @Override
        public void onItemLongClick(ItemPosition item, int position) {
            Log.i(TAG, "onItemLongClick: " + item.getName());
            if (actionMode != null) {
                return;
            }
            getActivity().startActionMode(new ActionModeCallBack());
            //  int selectedCount = adapter.getSelectedPositions().size();
            // actionMode.setTitle("Выбрано " + String.valueOf(selectedCount));
            toggleItem(position);
        }


        private void toggleItem(int position) {
            adapter.toggleItem(position);
        }
    }

    public class ActionModeCallBack implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            actionMode = mode;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = new MenuInflater(requireContext());
            inflater.inflate(R.menu.menu_action_mode, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.delete_item) {
                showDialog();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            adapter.clearSelections();
        }

        void removeSelectedItems() {
            List<Integer> selectedPositions = adapter.getSelectedPositions();
            for (int i = selectedPositions.size() - 1; i >= 0; i--) {
                ItemPosition item = adapter.removeItem(selectedPositions.get(i));
                removeItem(item.getId());
            }
            actionMode.finish();
        }

        void showDialog() {
            int countSelectedPositions = adapter.getSelectedPositions().size();
            AlertDialog dialog = new AlertDialog.Builder(requireContext())
                    .setMessage(countSelectedPositions > 1 ? R.string.alert_message_delete_more : R.string.alert_message_delete_one)
                    .setPositiveButton(R.string.alert_message_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            removeSelectedItems();
                        }
                    })
                    .setNegativeButton(R.string.alert_message_no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .create();
            dialog.show();
        }
    }
}




