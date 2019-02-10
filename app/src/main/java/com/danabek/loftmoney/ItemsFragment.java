package com.danabek.loftmoney;


import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {

    public static ItemsFragment newInstances(String type) {
        ItemsFragment fragment = new ItemsFragment();

        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE, type);
        fragment.setArguments(bundle);

        return fragment;
    }


    public static final String KEY_TYPE = "type";
    private String token = "$2y$10$MI9aJHOPZNR1WLHMPoRkx.6geJcwuzU/JxArRxeOoK9KXyPs3DzfG";


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

        //Add devider
        RecyclerView.LayoutManager layoutManager = (new LinearLayoutManager(requireContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recycler.addItemDecoration(dividerItemDecoration);

        loadItems();
    }

    private void loadItems() {
        new LoadItemsTask().start();
    }

    private class LoadItemsTask implements Runnable, Handler.Callback {
        private Thread thread;
        private Handler handler;

        public LoadItemsTask() {
            thread = new Thread(this);
            handler = new Handler(this);
        }

        public void start() {
            thread.start();
        }

        @Override
        public void run() {
            Call call = api.getItems(type, token);
            try {
                Response<List<ItemPosition>> response = call.execute();
                List<ItemPosition> items = response.body();
                Message message = handler.obtainMessage(111,items);
                message.sendToTarget();
                adapter.setItems(items);
            } catch (IOException e) {
                Log.e(TAG,"LoadItems: ",e);
            }
        }

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 111) {
                List<ItemPosition> items = (List<ItemPosition>) msg.obj;
                adapter.setItems(items);
                return  true;
            }
            return false;
        }
    }

}
